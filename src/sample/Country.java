package sample;

import java.math.BigDecimal;

public class Country {
    private String countryName;
    private int totalCase;
    private int totalDeaths;
    private int newCase;
    private int population;
    private double mortality;
    private BigDecimal attackRate;
    private String date;
    private int newDeath;


    public Country(String countryName, String date, int newCase, int newDeath){
        this.countryName=countryName;
        this.date=date;
        this.newCase=newCase;
        this.newDeath=newDeath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Country(String countryName, int totalCase, int newCase, int newDeath, int totalDeaths, int population, double mortality, BigDecimal attackRate){
        this.totalDeaths=totalDeaths;
        this.population=population;
        this.countryName=countryName;
        this.newCase=newCase;
        this.totalCase=totalCase;
        this.newDeath=newDeath;
        this.mortality=mortality;
        this.attackRate=attackRate;

    }

    public BigDecimal getAttackRate() {
        return attackRate;
    }

    public void setAttackRate(BigDecimal attackRate) {
        this.attackRate = attackRate;
    }

    public double getMortality() {
        return mortality;
    }

    public void setMortality(double mortality) {
        this.mortality = mortality;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }


    public int getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(int totalCase) {
        this.totalCase = totalCase;
    }

    public int getNewCase() {
        return newCase;
    }

    public void setNewCase(int newCase) {
        this.newCase = newCase;
    }
}
