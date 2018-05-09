package de.rexlmanu.supportbot.bot;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventType;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import de.rexlmanu.supportbot.listeners.TeamspeakListener;

public class TeamspeakConnector {

    private TS3Config ts3Config;
    private TS3Query ts3Query;
    private TS3Api ts3Api;

    public TeamspeakConnector(String teamspeakip, String queryport) {
        this.ts3Config = new TS3Config();
        ts3Config.setHost(teamspeakip);
        ts3Config.setReconnectStrategy(ReconnectStrategy.disconnect());
        ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        ts3Config.setQueryPort(Integer.valueOf(queryport));
        ts3Config.setEnableCommunicationsLogging(true);
    }

    public void connect(String queryusername, String querypassword) {
        this.ts3Query = new TS3Query(this.ts3Config);
        ts3Query.connect();
        this.ts3Api = ts3Query.getApi();
        boolean login = ts3Api.login(queryusername, querypassword);
        if (login) {
            ts3Api.selectVirtualServerById(1);
            ts3Api.setNickname("SupportBot - ProHosting24");
            ts3Api.sendServerMessage("SupportBot coded by rexlManu ist nun online!");
        } else {
            System.out.println("Teamspeak Connection konnte nicht herstellt werden...");
            System.exit(0);
        }
    }

    public void disconnect() {
        ts3Query.exit();
    }

    public void register() {
        ts3Api.registerAllEvents();
        ts3Api.addTS3Listeners(new TeamspeakListener());
    }

    public TS3Config getTs3Config() {
        return ts3Config;
    }

    public void setTs3Config(TS3Config ts3Config) {
        this.ts3Config = ts3Config;
    }

    public TS3Query getTs3Query() {
        return ts3Query;
    }

    public void setTs3Query(TS3Query ts3Query) {
        this.ts3Query = ts3Query;
    }

    public TS3Api getTs3Api() {
        return ts3Api;
    }

    public void setTs3Api(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }
}
