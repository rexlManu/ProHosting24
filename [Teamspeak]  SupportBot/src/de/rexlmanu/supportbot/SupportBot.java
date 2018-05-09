package de.rexlmanu.supportbot;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import de.rexlmanu.supportbot.bot.TeamspeakConnector;
import de.rexlmanu.supportbot.config.ConfigHandler;
import de.rexlmanu.supportbot.listeners.TeamspeakListener;
import de.rexlmanu.supportbot.support.SupportManager;

public class SupportBot {

    private static SupportBot instance;

    public SupportBot() {
    }

    private ConfigHandler configHandler;
    private TeamspeakConnector teamspeakConnector;
    private SupportManager supportManager;

    private String teamspeakip, queryport, queryusername, querypasswort;

    public void onLaunch() {
        instance = this;
        printStartMessage();
        System.out.println("");
        System.out.println("SupportBot v1 coded by rexlManu - www.rexlmanu.de");
        System.out.println("");

        this.configHandler = new ConfigHandler();
        this.teamspeakip = configHandler.getJsonObject().getString("TeamspeakIP");
        this.queryport = configHandler.getJsonObject().getString("QueryPort");
        this.queryusername = configHandler.getJsonObject().getString("QueryUsername");
        this.querypasswort = configHandler.getJsonObject().getString("QueryPassword");
        TeamspeakListener.SUPPORTWARTERAUM_CHANNELID = configHandler.getJsonObject().getInt("SupportChannel");
        SupportManager.SUPPORT_NOTIFY_GROUPID = configHandler.getJsonObject().getInt("SupportGroup");
        System.out.println(teamspeakip);


        this.teamspeakConnector = new TeamspeakConnector(teamspeakip, queryport);
        this.teamspeakConnector.connect(queryusername, querypasswort, configHandler.getJsonObject().getInt("Virtuelserver"));
        this.supportManager = new SupportManager();
        this.teamspeakConnector.register();

    }

    private void printStartMessage() {
        System.out.println("   _____                                          _     ____            _   \n" +
                "  / ____|                                        | |   |  _ \\          | |  \n" +
                " | (___    _   _   _ __    _ __     ___    _ __  | |_  | |_) |   ___   | |_ \n" +
                "  \\___ \\  | | | | | '_ \\  | '_ \\   / _ \\  | '__| | __| |  _ <   / _ \\  | __|\n" +
                "  ____) | | |_| | | |_) | | |_) | | (_) | | |    | |_  | |_) | | (_) | | |_ \n" +
                " |_____/   \\__,_| | .__/  | .__/   \\___/  |_|     \\__| |____/   \\___/   \\__|\n" +
                "                  | |     | |                                               \n" +
                "                  |_|     |_|                                      ");
    }

    public static SupportBot getInstance() {
        return instance;
    }

    public static void setInstance(SupportBot instance) {
        SupportBot.instance = instance;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public void setConfigHandler(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    public TeamspeakConnector getTeamspeakConnector() {
        return teamspeakConnector;
    }

    public void setTeamspeakConnector(TeamspeakConnector teamspeakConnector) {
        this.teamspeakConnector = teamspeakConnector;
    }

    public String getTeamspeakip() {
        return teamspeakip;
    }

    public void setTeamspeakip(String teamspeakip) {
        this.teamspeakip = teamspeakip;
    }

    public String getQueryport() {
        return queryport;
    }

    public void setQueryport(String queryport) {
        this.queryport = queryport;
    }

    public String getQueryusername() {
        return queryusername;
    }

    public void setQueryusername(String queryusername) {
        this.queryusername = queryusername;
    }

    public String getQuerypasswort() {
        return querypasswort;
    }

    public void setQuerypasswort(String querypasswort) {
        this.querypasswort = querypasswort;
    }

    public SupportManager getSupportManager() {
        return supportManager;
    }
}
