public class Player {
    private String id;
    private String action;
    private String matchId;
    private double amount;
    private String betSide;

    public Player(String id, String action, String matchId, double amount, String betSide) {
        this.id = id;
        this.action = action;
        this.matchId = matchId;
        this.amount = amount;
        this.betSide = betSide;
    }

    public Player(String id, String action, double amount) {
        this.id = id;
        this.action = action;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getMatchId() {
        return matchId;
    }

    public double getAmount() {
        return amount;
    }

    public String getBetSide() {
        return betSide;
    }
}
