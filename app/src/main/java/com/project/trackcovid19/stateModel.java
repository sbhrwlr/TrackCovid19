package com.project.trackcovid19;

public class stateModel {

    String statename;

    public stateModel(String statename, String concases, String actcases, String reccases, String deaths) {
        this.statename = statename;
        this.concases = concases;
        this.actcases = actcases;
        this.reccases = reccases;
        this.deaths = deaths;
    }

    String concases;
    String actcases;
    String reccases;
    String deaths;

    public String getStatename() {
        return statename;
    }

    public void setStatename(String statename) {
        this.statename = statename;
    }

    public String getConcases() {
        return concases;
    }

    public void setConcases(String concases) {
        this.concases = concases;
    }

    public String getActcases() {
        return actcases;
    }

    public void setActcases(String actcases) {
        this.actcases = actcases;
    }

    public String getReccases() {
        return reccases;
    }

    public void setReccases(String reccases) {
        this.reccases = reccases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }


}
