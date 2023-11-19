package gamelogic;

import model.LegalPlayer;
import model.Match;
import model.Player;
import model.PlayerOperation;

import java.util.List;
import java.util.Set;

public class Operation {

    public static PlayerOperation playerOperation(
            Set<String> playerIds, List<Player> playerDataList,
            List<Match> matchDataList, List<Player> illegalPlayers,
            List<LegalPlayer> legalPlayers, double casinoBalance) {
        
        double moneyMovement = 0;
        double playerOutcome = 0;
        double allGames = 0;
        double wonGames = 0;

        for (String id : playerIds) {
            try {
                List<Player> playerAction = playerDataList.stream()
                        .filter(e -> e.getId().equals(id))
                        .toList();

                for (Player action : playerAction) {
                    switch (action.getAction()) {
                        case "DEPOSIT" -> moneyMovement += action.getAmount();

                        case "WITHDRAW" -> {
                            moneyMovement -= action.getAmount();
                            if (moneyMovement < 0) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Withdraw amount exceeds coin balance.");
                            }
                        }

                        case "BET" -> {
                            if (action.getAmount() > moneyMovement) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Bet amount exceeds coin balance.");
                            }
                            Match matchData;
                            try {
                                matchData = matchDataList.stream()
                                        .filter(e -> e.getId().equals(action.getMatchId()))
                                        .findFirst()
                                        .orElseThrow();
                            } catch (Exception e) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Match ID not found.");
                            }

                            allGames++;

                            if (matchData.getResult().equals(action.getBetSide())) {
                                wonGames++;
                                if (matchData.getResult().equals("A")) {
                                    playerOutcome += action.getAmount() * matchData.getaCoefficient();
                                } else if (matchData.getResult().equals("B")) {
                                    playerOutcome += action.getAmount() * matchData.getbCoefficient();
                                }
                            } else if (!matchData.getResult().equals("DRAW") &&
                                    !matchData.getResult().equals(action.getBetSide())
                            ) {
                                playerOutcome -= action.getAmount();
                            }
                        }
                    }
                }
                moneyMovement += playerOutcome;
                casinoBalance -= playerOutcome;
                legalPlayers.add(new LegalPlayer(id, moneyMovement, wonGames / allGames));

            } catch (RuntimeException e) {
                System.err.println("Illegal action found: PlayerID " + id + " " + e.getMessage());
            }
        }

        return new PlayerOperation(legalPlayers, casinoBalance);
    }

}


