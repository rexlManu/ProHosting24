package de.rexlmanu.supportbot.support;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class Support {

    private Client client;
    private SupportID supportID;
    private int supporterClientID;
    private int channelID;
    private SupportReason supportReason;
    private SupportState supportState;

    public Support(Client client, SupportReason supportReason, SupportState supportState) {
        this.client = client;
        this.supportReason = supportReason;
        this.supportState = supportState;
    }

    public SupportState getSupportState() {
        return supportState;
    }

    public void setSupportState(SupportState supportState) {
        this.supportState = supportState;
    }


    public SupportID getSupportID() {
        return supportID;
    }

    public void setSupportID(SupportID supportID) {
        this.supportID = supportID;
    }

    public int getSupporterClientID() {
        return supporterClientID;
    }

    public void setSupporterClientID(int supporterClientID) {
        this.supporterClientID = supporterClientID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public SupportReason getSupportReason() {
        return supportReason;
    }

    public void setSupportReason(SupportReason supportReason) {
        this.supportReason = supportReason;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
