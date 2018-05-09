package de.rexlmanu.supportbot.support;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import java.util.Random;

public class SupportID {

    private String ID;

    public SupportID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private static final String[] AD = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "G", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final Random RANDOM = new Random();

    public static String generateID() {
        String id = "";
        for (int i = 0; i < 7; i++) {
            id += AD[RANDOM.nextInt(AD.length)];
        }
        return id;
    }
}
