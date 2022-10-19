package com.vendoranalytics.xyz.business;

import java.util.ArrayList;

public class Location {
    private ArrayList<String> indiaLocations = new ArrayList<>();
    private ArrayList<String> usLocations = new ArrayList<>();
    private ArrayList<String> canadaLocations = new ArrayList<>();
    private ArrayList<String> otherLocations = new ArrayList<>();

    public Location() {
        this.setIndiaLocations();
        this.setUSLocation();
        this.setCanadaLocations();
    }

    public ArrayList<String> getIndiaLocations() {
        return indiaLocations;
    }

    public void setIndiaLocations(ArrayList<String> indiaLocations) {
        this.indiaLocations = indiaLocations;
    }

    public ArrayList<String> getUsLocations() {
        return usLocations;
    }

    public void setUsLocations(ArrayList<String> usLocations) {
        this.usLocations = usLocations;
    }

    public ArrayList<String> getCanadaLocations() {
        return canadaLocations;
    }

    public void setCanadaLocations(ArrayList<String> canadaLocations) {
        this.canadaLocations = canadaLocations;
    }

    private void setIndiaLocations() {
        indiaLocations.add("BANGALORE");
        indiaLocations.add("MUMBAI");
        indiaLocations.add("PUNE");
        indiaLocations.add("LUCKNOW");
        indiaLocations.add("NOIDA");
        indiaLocations.add("GURGAON");
        indiaLocations.add("NEW DELHI");
        indiaLocations.add("CHENNAI");
        indiaLocations.add("VIJAYAWADA");
        indiaLocations.add("KOLKATA");
        indiaLocations.add("AHMEDABAD");
        indiaLocations.add("HYDERABAD");
    }

    private void setUSLocation() {
        usLocations.add("ANN ARBOR");
        usLocations.add("SALT LAKE CITY");
        usLocations.add("RESEARCH TRIANGLE PARK");
        usLocations.add("MAITLAND");
        usLocations.add("RICHFIELD");
        usLocations.add("HERNDON");
        usLocations.add("SAN JOSE");
        usLocations.add("APPLETONCITY");
        usLocations.add("DENVER");
        usLocations.add("NASHVILLE");
        usLocations.add("RICHARDSON");
        usLocations.add("CALGARY");
        usLocations.add("SAN FRANCISCO");
        usLocations.add("WASHINGTON");
        usLocations.add("PROVIDENCE");
        usLocations.add("AUSTIN");
        usLocations.add("PHILADELPHIA");
        usLocations.add("BOSTON");
        usLocations.add("COLUMBIA");
        usLocations.add("ALPHARETTA");
        usLocations.add("HARTFORD");
        usLocations.add("DALLAS");
        usLocations.add("OKLAHOMA CITY");
        usLocations.add("IRVINE");
        usLocations.add("BOXBOROUGH");
        usLocations.add("SEATTLE");
        usLocations.add("ELK GROVE VILLAGE");
        usLocations.add("CHICAGO");
        usLocations.add("SANTA CLARA");
        usLocations.add("TAMPA");
        usLocations.add("GLENDALE");
        usLocations.add("FRANKFORT");
        usLocations.add("NEW YORK");
        usLocations.add("RANCHO CORDOVA");
        usLocations.add("ATLANTA");
        usLocations.add("MIAMI");
        usLocations.add("READING");
        usLocations.add("WALTHAM");
        usLocations.add("ALLEN");
        usLocations.add("CENTENNIAL");
        usLocations.add("MINNEAPOLIS");
        usLocations.add("PORTLAND");
        usLocations.add("LEHI");
        usLocations.add("PHOENIX");
        usLocations.add("ISELIN");
        usLocations.add("SAN MATEO");
        usLocations.add("ANNAPOLIS JUNCTION");
        usLocations.add("HOUSTON");
        usLocations.add("ROSEVILLE");
        usLocations.add("BELLEVUE");
        usLocations.add("BOISE");
        usLocations.add("KNOXVILLE");
        usLocations.add("INDIANAPOLIS");
        usLocations.add("TOPEKA");
        usLocations.add("SPRING");
        usLocations.add("PIERRE");
        usLocations.add("SAN DIEGO");
        usLocations.add("MEMPHIS");
        usLocations.add("EDINA");
        usLocations.add("ASHBURN");
        usLocations.add("BROOK PARK");
        usLocations.add("CONCORD");
        usLocations.add("ROCKVILLE");
        usLocations.add("BROOKLYN");
        usLocations.add("WEST DES MOINES");
        usLocations.add("ALLENTOWN");
        usLocations.add("RENO");
        usLocations.add("SANDY");
        usLocations.add("LAWRENCEVILLE");
        usLocations.add("HILLSBORO");
        usLocations.add("HELENA");
        usLocations.add("BOCA RATON");
        usLocations.add("LINCOLN");
        usLocations.add("COSTA MESA");
        usLocations.add("BENTONVILLE");
        usLocations.add("TRENTON");
        usLocations.add("FULTON");
        usLocations.add("CLAYTON");
        usLocations.add("SYRACUSE");
        usLocations.add("SUNNYVALE");
        usLocations.add("HOLMDEL");
        usLocations.add("SIERRA VISTA");
        usLocations.add("TULSA");
        usLocations.add("STAMFORD");
        usLocations.add("CARY");
        usLocations.add("SANTA FE");
        usLocations.add("TEMPE");
        usLocations.add("MILPITAS");
        usLocations.add("JACKSON");
        usLocations.add("HONOLULU");
        usLocations.add("CARLSBAD");
        usLocations.add("CHARLESTON");
        usLocations.add("MINNEAPOLIS-ST. PAUL MN");
        usLocations.add("BATON ROUGE");
        usLocations.add("MONTPELIER");
        usLocations.add("COLORADO SPRINGS");
        usLocations.add("FREMONT");
        usLocations.add("REDWOOD CITY");
        usLocations.add("STRONGSVILLE");
        usLocations.add("MAYNARD");
        usLocations.add("NEWTON");
        usLocations.add("Lewis Center");
        usLocations.add("MOUNTAIN VIEW");
        usLocations.add("CARROLLTON");
        usLocations.add("DOVER");
        usLocations.add("LOS ALTOS");
        usLocations.add("JUNEAU");
        usLocations.add("PLEASANTON");
        usLocations.add("ROANOKE");
        usLocations.add("REDWOOD FALLS");
        usLocations.add("ROSEBURG");
        usLocations.add("GOLETA");
        usLocations.add("MARTINEZ");
        usLocations.add("PALO ALTO");
        usLocations.add("MONTEVIDEO");
        usLocations.add("Davis");
    }

    private void setCanadaLocations() {
        canadaLocations.add("VANCOUVER");
        canadaLocations.add("OTTAWA");
        canadaLocations.add("MONTREAL");
        canadaLocations.add("HALIFAX");
        canadaLocations.add("MISSISSAUGA");
        canadaLocations.add("KANATA");
        canadaLocations.add("FREDERICTON");
        canadaLocations.add("SAINT-LAURENT");
        canadaLocations.add("REGINA");
        canadaLocations.add("YELLOWKNIFE");
    }
}
