package de.rexlmanu.supportbot.utils;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import de.rexlmanu.supportbot.SupportBot;

public class TSUtils {

    private static final SupportBot instance = SupportBot.getInstance();
    private static final TS3Api api = instance.getTeamspeakConnector().getTs3Api();

    public static Client getClient(int clientid) {
        for (Client client : instance.getTeamspeakConnector().getTs3Api().getClients()) {
            if (client.getId() == clientid) {
                return client;
            }
        }
        return null;
    }

    public static void sendMessage(String message, Client client) {
        api.sendPrivateMessage(client.getId(), message);
    }

    public static void broadcast(int groupid, String message) {
        for (Client client : api.getClients()) {
            if (hasGroup(client, groupid)) {
                sendMessage(message, client);
            }
        }
    }

    public static boolean hasGroup(Client client, int groupid) {
        for (int i : client.getServerGroups()) {
            if (i == groupid) {
                return true;
            }
        }
        return false;
    }
}
