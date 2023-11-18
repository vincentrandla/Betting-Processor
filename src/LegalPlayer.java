public class LegalPlayer {
    private String playerId;
    private double finalAmount;
    private double winPercentage;

    public LegalPlayer(String playerId, double finalAmount, double winPercentage) {
        this.playerId = playerId;
        this.finalAmount = finalAmount;
        this.winPercentage = winPercentage;
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

}