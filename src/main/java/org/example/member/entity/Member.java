package org.example.member.entity;

public class Member {
    private String ID;
    private String PW;
    public Member (String ID, String PW) {
        this.ID = ID;
        this.PW = PW;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getPW() {
        return PW;
    }
    public void setPW(String PW) {
        this.PW = PW;
    }
}