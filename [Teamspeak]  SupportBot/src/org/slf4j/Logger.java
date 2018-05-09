package org.slf4j;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import com.github.theholywaffle.teamspeak3.api.wrapper.QueryError;

public class Logger {
    public void error(String s, Exception e) {
//        System.out.println(s + " - " + e.getMessage());
    }

    public void error(String s, String e) {

//        System.out.println(s + " - " + e);
    }

    public void debug(String s, String msg) {
//        System.out.println(s + " - " + msg);
    }

    public void debug(String s, int msg) {

//        System.out.println(s + " - " + msg);
    }

    public void warn(String s) {

//        System.out.println(s);
    }

    public void error(String s) {

//        System.out.println(s);
    }

    public void warn(String s, String response) {

//        System.out.println(s+" - "+response);
    }

    public void warn(String s, QueryError response) {
//        System.out.println(s+" - "+response.getMessage());
    }

    public void debug(String s, String name, String response)
    {
//        System.out.println(s+" - "+response+" - "+name);
    }

    public void warn(String user_callback_threw_exception, Exception e) {
//        System.out.println(user_callback_threw_exception);
    }

    public void warn(String s, int uploadId, String message) {

//        System.out.println(s);
    }

    public void info(String s, int uploadId) {

//        System.out.println(s);
    }
}
