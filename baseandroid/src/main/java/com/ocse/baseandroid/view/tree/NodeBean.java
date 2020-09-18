package com.ocse.baseandroid.view.tree;

public class NodeBean {
    private String PID;
    private String ID;
    private String NAME;

    public NodeBean(String PID, String ID, String NAME) {
        this.PID = PID;
        this.ID = ID;
        this.NAME = NAME;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
