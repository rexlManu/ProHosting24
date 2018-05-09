package de.rexlmanu.supportbot.support;
/*
* Class created by rexlManu
* Twitter: @rexlManu | Website: rexlManu.de
* Coded with IntelliJ
*/

public enum  SupportReason {
    Allgemein("Allgemein", "Bei Allgemeinen Fragen zu unseren Produkten.", 1),
    Buchhaltung("Buchhaltung", "Bei Buchaltung Problemen oder Fragen.", 2),
    Domain("Domain", "Bei Domain Fragen zu unseren Produkten.", 3),
    vServer("vServer", "Bei vServer Fragen zu unseren Produkten.", 4),
    Sonstiges("Sonstiges", "Bei Sonstigen Fragen zu unseren Produkten.", 5);

    private String grund;
    private String desc;
    private int id;

    SupportReason(String grund, String desc, int id) {
        this.grund = grund;
        this.desc = desc;
        this.id = id;
    }

    public String getGrund() {
        return grund;
    }

    public void setGrund(String grund) {
        this.grund = grund;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static SupportReason getReasonFromId(int id){
        for (SupportReason supportReason : SupportReason.values()) {
            if(supportReason.getId() == id){
                return supportReason;
            }
        }
        return null;
    }
}
