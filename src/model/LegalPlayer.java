package model;

public class LegalPlayer {
    private String playerId;
    private double finalAmount;
    private double winPercentage;
    String formattedWinPercentage;

    public LegalPlayer(String playerId, double finalAmount, double winPercentage) {
        this.playerId = playerId;
        this.finalAmount = finalAmount;
        this.winPercentage = winPercentage;
        this.formattedWinPercentage = String.format("%.2f", winPercentage);
    }

    public String getPlayerId() {
        return playerId;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public double getWinPercentage() {
        return winPercentage;
    }

    public String getFormattedWinPercentage() {
        return formattedWinPercentage;
    }
}