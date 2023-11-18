package model;

public class Player {
    private String id;
    private String action;
    private String matchId;
    private long amount;
    private String betSide;

    public Player(String id, String action, String matchId, long amount, String betSide) {
        this.id = id;
        this.action = action;
        this.matchId = matchId;
        this.amount = amount;
        this.betSide = betSide;
    }

/*    public Player(String id, String action, long amount) {
        this.id = id;
        this.action = action;
        this.amount = amount;
    }*/

    public String getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getMatchId() {
        return matchId;
    }

    public long getAmount() {
        return amount;
    }

    public String getBetSide() {
        return betSide;
    }
}
