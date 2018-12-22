package com.example.abl.studentagenda;


public class Module {
    protected String name;
    protected String abvname;
    protected float coff;
    protected int modevtest;
    protected int modevexam;
    protected float notetd;
    protected float notetp;
    protected float noteexam;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbvname() {
        return abvname;
    }

    public void setAbvname(String abvname) {
        this.abvname = abvname;
    }

    public float getCoff() {
        return coff;
    }

    public void setCoff(float coff) {
        this.coff = coff;
    }

    public int getModevtest() {
        return modevtest;
    }

    public void setModevtest(int modevtest) {
        this.modevtest = modevtest;
    }

    public int getModevexam() {
        return modevexam;
    }

    public void setModevexam(int modevexam) {
        this.modevexam = modevexam;
    }

    public float getNotetd() {
        return notetd;
    }

    public void setNotetd(float notetd) {
        this.notetd = notetd;
    }

    public float getNotetp() {
        return notetp;
    }

    public void setNotetp(float notetp) {
        this.notetp = notetp;
    }

    public float getNoteexam() {
        return noteexam;
    }

    public void setNoteexam(float noteexam) {
        this.noteexam = noteexam;
    }
}

