package com.project.trackcovid19;

public class districtModel {


    String districtname;
    String cCases;
    public districtModel(String districtname, String cCases, String aCases, String rCases, String dCases) {
        this.districtname = districtname;
        this.cCases = cCases;
    }

    public String getDistrictname() {
        return districtname;
    }

    public void setDistrictname(String districtname) {
        this.districtname = districtname;
    }

    public String getcCases() {
        return cCases;
    }

    public void setcCases(String cCases) {
        this.cCases = cCases;
    }

}
