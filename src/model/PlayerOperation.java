package model;

import java.util.List;

public class PlayerOperation {
    private final List<LegalPlayer> legalPlayers;
    private final double casinoBalance;

    public PlayerOperation(List<LegalPlayer> legalPlayers, double casinoBalance) {
        this.legalPlayers = legalPlayers;
        this.casinoBalance = casinoBalance;
    }

    public List<LegalPlayer> getLegalPlayers() {
        return legalPlayers;
    }

    public double getCasinoBalance() {
        return casinoBalance;
    }
}
