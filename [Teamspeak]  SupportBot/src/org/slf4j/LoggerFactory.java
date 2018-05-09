package org.slf4j;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.TS3Query;

public class LoggerFactory {
    public static Logger getLogger(Class<?> ts3QueryClass) {
        return new Logger();
    }
}
