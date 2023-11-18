public class Match {
    private String id;
    private double aCoef;
    private double bCoef;
    private String result;

    public Match(String id, double aCoef, double bCoed, String result) {
        this.id = id;
        this.aCoef = aCoef;
        this.bCoef = bCoed;
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public double getaCoef() {
        return aCoef;
    }

    public double getbCoed() {
        return bCoef;
    }

    public String getResult() {
        return result;
    }
}