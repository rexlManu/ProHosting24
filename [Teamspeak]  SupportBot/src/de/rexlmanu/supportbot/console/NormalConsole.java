package de.rexlmanu.supportbot.console;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

public class NormalConsole extends Console {

    public enum LoginState {
        SETUP,
        NORMAL
    }

    private LoginState loginState;

    public NormalConsole(boolean setup) {
        this.loginState = setup ? LoginState.SETUP : LoginState.NORMAL;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void onCommand(String[] args) {

    }
}
