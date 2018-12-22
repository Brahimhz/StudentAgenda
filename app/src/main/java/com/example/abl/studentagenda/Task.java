package com.example.abl.studentagenda;

public class Task {
    public String getSubtask() {
        return subtask;
    }

    public void setSubtask(String subtask) {
        this.subtask = subtask;
    }

    public String getDatebeg() {
        return datebeg;
    }

    public void setDatebeg(String datebeg) {
        this.datebeg = datebeg;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String subtask;
    private String datebeg;
    private String datefin;
    private String details;

}
