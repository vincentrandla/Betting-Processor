package model;

import model.LegalPlayer;

import java.util.List;

public class ProcessResult {
    private final List<LegalPlayer> legalPlayers;
    private final double casinoBalance;

    public ProcessResult(List<LegalPlayer> legalPlayers, double casinoBalance) {
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
