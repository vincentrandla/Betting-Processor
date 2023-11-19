package model;

public class Match {
    private String id;
    private double aCoefficient;
    private double bCoefficient;
    private String result;

    public Match(String id, double aCoefficient, double bCoed, String result) {
        this.id = id;
        this.aCoefficient = aCoefficient;
        this.bCoefficient = bCoefficient;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public double getaCoefficient() {
        return aCoefficient;
    }

    public double getbCoefficient() {
        return bCoefficient;
    }

    public String getResult() {
        return result;
    }
}