package com.example.abl.studentagenda;

public class Hour {
    public int getIdhour() {
        return idhour;
    }

    public void setIdhour(int idhour) {
        this.idhour = idhour;
    }

    public String getHbeg() {
        return hbeg;
    }

    public void setHbeg(String hbeg) {
        this.hbeg = hbeg;
    }

    public String getMbeg() {
        return mbeg;
    }

    public void setMbeg(String mbeg) {
        this.mbeg = mbeg;
    }

    public String getHend() {
        return hend;
    }

    public void setHend(String hend) {
        this.hend = hend;
    }

    public String getMend() {
        return mend;
    }

    public void setMend(String mend) {
        this.mend = mend;
    }

    protected int idhour;
    protected String hbeg;
    protected String mbeg;
    protected String hend;
    protected String mend;
}
