import model.LegalPlayer;
import model.Match;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Action {

    static List<LegalPlayer> legalPlayers(Set<String> playerIds, List<Player> playerDataList,
                                            List<Match> matchDataList, List<Player> illegalPlayers,
                                            List<LegalPlayer> legalPlayers, double casinoBalance) {
        for (String id : playerIds) {
            try {
                List<Player> playerAction = playerDataList.stream()
                        .filter(e -> e.getId().equals(id))
                        .toList();

                double moneyMovement = 0;
                double playerOutcome = 0;
                double all_games = 0;
                double won_games = 0;
                for (Player action : playerAction) {
                    switch (action.getAction()) {
                        case "DEPOSIT" -> moneyMovement += action.getAmount();
                        case "WITHDRAW" -> {
                            moneyMovement -= action.getAmount();
                            if (moneyMovement < 0) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Withdrew too much");
                            }
                        }
                        case "BET" -> {
                            if (action.getAmount() > moneyMovement) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Bet too much");
                            }
                            Match matchData;
                            try {
                                matchData = matchDataList.stream()
                                        .filter(e -> e.getId().equals(action.getMatchId()))
                                        .findFirst()
                                        .orElseThrow();
                            } catch (Exception e) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("model.Match ID not found. ");
                            }
                            all_games++;
                            if (matchData.getResult().equals(action.getBetSide())) {
                                won_games++;
                                if (matchData.getResult().equals("A")) {
                                    playerOutcome += action.getAmount() * matchData.getaCoef();
                                } else if (matchData.getResult().equals("B")) {
                                    playerOutcome += action.getAmount() * matchData.getbCoed();
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
                legalPlayers.add(new LegalPlayer(id, moneyMovement, won_games / all_games));

            } catch (RuntimeException e) {
                System.err.println("Illegal action found: PlayerID " + id + " " + e.getMessage());
            }
        }

        return legalPlayers;
    }

}


