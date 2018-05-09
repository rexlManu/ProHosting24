package de.rexlmanu.supportbot.support;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.rexlmanu.supportbot.SupportBot;
import de.rexlmanu.supportbot.bot.TeamspeakConnector;
import de.rexlmanu.supportbot.utils.TSUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SupportManager {

    private final TeamspeakConnector teamspeakConnector = SupportBot.getInstance().getTeamspeakConnector();
    private ConcurrentHashMap<Integer, Support> supports = new ConcurrentHashMap<>();

    public static final int SUPPORT_NOTIFY_GROUPID = 296;


    public ConcurrentHashMap<Integer, Support> getSupports() {
        return supports;
    }

    public void registerNewSupport(Client client, SupportReason supportReason) {
        Support support = new Support(client, supportReason, SupportState.CHOOSING);
        String supportID = SupportID.generateID();
        support.setSupportID(new SupportID(supportID));

        final Map<ChannelProperty, String> properties = new HashMap<>();

        properties.put(ChannelProperty.CHANNEL_FLAG_PERMANENT, "1");
        properties.put(ChannelProperty.CHANNEL_ORDER, "766");
        properties.put(ChannelProperty.CHANNEL_MAXCLIENTS, "0");
        String channelbeginn = "» Support × #";
        int channelID = teamspeakConnector.getTs3Api().createChannel(channelbeginn + supportID, properties);
        support.setChannelID(channelID);
        support.setSupportState(SupportState.WAITING);
        supports.put(client.getId(), support);

        teamspeakConnector.getTs3Api().moveClient(client.getId(), channelID);

        TSUtils.sendMessage("Es wurde ein Channel erstellt mit deinem Problem.", client);
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "----------------------------------------");
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "Neue Supportanfrage");
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "» User ● " + client.getNickname());
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "» Grund ● " + supportReason.getGrund());
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "Zum Supporten » !support " + supportID);
        TSUtils.broadcast(SUPPORT_NOTIFY_GROUPID, "----------------------------------------");
    }

    public void unregisterSupport(Client client) {
        Support support = supports.get(client.getId());
        TSUtils.sendMessage("Der Support wurde beendet.", client);
        teamspeakConnector.getTs3Api().kickClientFromChannel(client.getId());
        teamspeakConnector.getTs3Api().deleteChannel(support.getChannelID());
        supports.remove(client.getId());
    }

    public boolean isGetingSupport(Client client) {
        return supports.containsKey(client.getId());
    }

    public void setSupports(ConcurrentHashMap<Integer, Support> supports) {
        this.supports = supports;
    }
}
