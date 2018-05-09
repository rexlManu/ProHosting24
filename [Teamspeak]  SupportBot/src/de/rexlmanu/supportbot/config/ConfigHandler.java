package de.rexlmanu.supportbot.config;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigHandler {

    private File rootdir;
    private File configdir;

    private File config;
    private JSONObject jsonObject;

    public ConfigHandler() {
        this.rootdir = new File("./SupportBot");
        this.configdir = new File(rootdir + "//configs");
        this.rootdir.mkdir();
        this.configdir.mkdir();

        this.config = new File(configdir, "config.json");
        if (!config.exists()) {
            try {
                config.createNewFile();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("TeamspeakIP", "localhost");
                jsonObject.put("TeamspeakPort", "9987");
                jsonObject.put("QueryUsername", "serveradmin");
                jsonObject.put("QueryPassword", "pw");
                jsonObject.put("QueryPort", "10011");
                write(config, jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(new String(Files.readAllBytes(config.toPath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    private void write(File file, String string) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(string);
        fileWriter.flush();
        fileWriter.close();
    }
}
