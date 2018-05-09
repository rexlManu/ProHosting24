package de.rexlmanu.supportbot.listeners;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.rexlmanu.supportbot.SupportBot;
import de.rexlmanu.supportbot.bot.TeamspeakConnector;
import de.rexlmanu.supportbot.support.Support;
import de.rexlmanu.supportbot.support.SupportManager;
import de.rexlmanu.supportbot.support.SupportReason;
import de.rexlmanu.supportbot.support.SupportState;
import de.rexlmanu.supportbot.utils.TSUtils;

public class TeamspeakListener implements TS3Listener {

    private final SupportManager supportManager = SupportBot.getInstance().getSupportManager();
    private final SupportBot supportBot = SupportBot.getInstance();
    private final TeamspeakConnector teamspeakConnector = supportBot.getTeamspeakConnector();

    public static final int SUPPORTWARTERAUM_CHANNELID = 766;

    @Override
    public void onTextMessage(TextMessageEvent e) {
        if (e.getInvokerId() != SupportBot.getInstance().getTeamspeakConnector().getTs3Query().getApi().whoAmI().getId()) {
            if (e.getTargetMode() == TextMessageTargetMode.CLIENT) {

                String message = e.getMessage();
                Client client = TSUtils.getClient(e.getInvokerId());
                if (message.startsWith("!support")) {
                    if (TSUtils.hasGroup(client, SupportManager.SUPPORT_NOTIFY_GROUPID)) {

                        final boolean[] alreadyblowjobing = {false};

                        supportManager.getSupports().forEach((integer, support) -> {
                            if (support.getSupporterClientID() == client.getId()) {
                                alreadyblowjobing[0] = true;
                            }
                        });

                        if (alreadyblowjobing[0]) {
                            TSUtils.sendMessage("Du supportest bereits jemanden.", client);
                            return;
                        }

                        String supportIDraw = message.replace("!support ", "");
                        final Support[] currentSupport = {null};
                        supportManager.getSupports().forEach((integer, support) -> {
                            if (support.getSupportID().getID().equals(supportIDraw)) {
                                currentSupport[0] = support;
                            }
                        });
                        Support support = currentSupport[0];
                        if (support == null) {
                            TSUtils.sendMessage("Es konnte kein Support gefunden werden mit der ID.", client);
                        } else {
                            if (support.getSupportState().equals(SupportState.WAITING)) {
                                support.setSupportState(SupportState.GETSUPPORT);
                                support.setSupporterClientID(client.getId());
                                teamspeakConnector.getTs3Api().moveClient(e.getInvokerId(), support.getChannelID());
                                TSUtils.sendMessage("Du supportest jetzt " + support.getClient().getNickname() + ".", client);
                                TSUtils.sendMessage("Mit !exit beendest du den Support wieder.", client);
                            } else {
                                TSUtils.sendMessage("Dieser User wird bereits supportet.", client);
                            }


                        }
                    } else {
                        TSUtils.sendMessage("Du musst die Support-Notify Gruppe besitzen.", client);
                    }
                } else if (message.equalsIgnoreCase("!exit")) {
                    final Support[] currentSupport = {null};
                    supportManager.getSupports().forEach((integer, support) -> {
                        if (support.getSupporterClientID() == client.getId()) {
                            currentSupport[0] = support;
                        }
                    });
                    Support support = currentSupport[0];
                    if (support == null) {
                        TSUtils.sendMessage("Du supportest zurzeit niemanden.", client);
                    } else {
                        TSUtils.sendMessage("Du hast erfolgreich den Support beendet.", client);
                        supportManager.unregisterSupport(support.getClient());
                    }


                } else {

                    if (supportManager.isGetingSupport(client)) {
                        Support support = supportManager.getSupports().get(client);
                        switch (support.getSupportState()) {
                            case END:
                                TSUtils.sendMessage("Du wurdest bereits supportet.", client);
                                break;
                            case WAITING:
                                TSUtils.sendMessage("Bitte warte bis ein Supporter dir hilft.", client);
                                break;
                            case CHOOSING:
                                TSUtils.sendMessage("Bitte wähle deinen Grund aus.", client);
                                break;
                            case GETSUPPORT:
                                TSUtils.sendMessage("Du herhälst gerade Support.", client);
                                break;
                        }
                    } else {
                        if (client.getChannelId() != SUPPORTWARTERAUM_CHANNELID) {
                            TSUtils.sendMessage("Bitte befinde dich im Support Warteraum.", client);
                            return;
                        }

                        int id = 0;

                        try {
                            id = Integer.valueOf(e.getMessage());
                        } catch (NumberFormatException exc) {
                            TSUtils.sendMessage("Bitte gibe die ID von der Option ein.", client);
                            return;
                        }
                        SupportReason supportReason;

                        if (SupportReason.getReasonFromId(id) == null) {
                            TSUtils.sendMessage("Es konnte keine Option mit der ID gefunden werden.", client);
                            return;
                        }
                        TSUtils.sendMessage("Es wurden 5 Supporter benachrichtet.", client);
                        TSUtils.sendMessage("Bitte warte einen Augenblick.", client);

                        supportReason = SupportReason.getReasonFromId(id);
                        supportManager.registerNewSupport(client, supportReason);

                    }
                }

//            } else {
//                TSUtils.sendMessage("Bitte befinde dich im Support Warteraum.", client);
//            }

            }

        }
    }

    @Override
    public void onClientJoin(ClientJoinEvent e) {
    }

    @Override
    public void onClientLeave(ClientLeaveEvent e) {
        Client client = TSUtils.getClient(e.getClientId());
        if (supportManager.isGetingSupport(client)) {
            supportManager.unregisterSupport(client);
        }
    }

    @Override
    public void onServerEdit(ServerEditedEvent e) {

    }

    @Override
    public void onChannelEdit(ChannelEditedEvent e) {

    }

    @Override
    public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {

    }

    @Override
    public void onClientMoved(ClientMovedEvent e) {
        int clientId = e.getClientId();
        Client client = TSUtils.getClient(clientId);
        if (e.getTargetChannelId() == SUPPORTWARTERAUM_CHANNELID) {
            if (TSUtils.hasGroup(client, SupportManager.SUPPORT_NOTIFY_GROUPID)) {
                TSUtils.sendMessage("Du kannst mit Support-Notify nicht in den Support Warteraum.", client);
                teamspeakConnector.getTs3Api().kickClientFromChannel(client);
                return;
            } else if (supportManager.isGetingSupport(client)) {
                supportManager.unregisterSupport(client);
                return;
            }
            TSUtils.sendMessage("Willkommen im Support Warteraum.", client);
            TSUtils.sendMessage("Folgende Support Optionen sind verfügbar:", client);
            for (SupportReason supportReason : SupportReason.values()) {
                TSUtils.sendMessage("     » " + supportReason.getGrund() + " ︳ " + supportReason.getId(), client);
            }
            TSUtils.sendMessage("Bitte sende die ID deiner Option.", client);
        } else if (supportManager.isGetingSupport(client)) {
            Support support = supportManager.getSupports().get(clientId);
            if (support.getSupportState().equals(SupportState.WAITING)) {
                if (client.getChannelId() == support.getChannelID() && e.getTargetChannelId() != support.getChannelID()) {
                    supportManager.unregisterSupport(client);
                }
            } else if (support.getSupportState().equals(SupportState.GETSUPPORT)) {
                teamspeakConnector.getTs3Api().moveClient(support.getClient().getId(), support.getChannelID());
                TSUtils.sendMessage("Bitte verlasse den Support nicht während dem Support.", client);
            }
        } else {
            supportManager.getSupports().forEach((integer, support) -> {
                if (support.getSupporterClientID() == clientId) {
                    teamspeakConnector.getTs3Api().moveClient(clientId, support.getChannelID());
                }
            });
        }
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent e) {

    }

    @Override
    public void onChannelDeleted(ChannelDeletedEvent e) {

    }

    @Override
    public void onChannelMoved(ChannelMovedEvent e) {
    }

    @Override
    public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {

    }

    public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {

    }
}
