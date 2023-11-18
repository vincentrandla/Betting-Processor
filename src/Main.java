import datareader.MatchDataReader;
import datareader.PlayerDataReader;
import model.LegalPlayer;
import model.Match;
import model.Player;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Match> matchDataList = new ArrayList<>();
        List<Player> playerDataList = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();
        List<Player> illegalPlayers = new ArrayList<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();
        double casinoBalance = 0;

        try {
            List<Match> matchDataReader = MatchDataReader.readMatchDataFromFile("match_data.txt");
            matchDataList.addAll(matchDataReader);
        } catch (IOException e) {
            System.err.println("Error reading match data: " + e.getMessage());
        }

        try {
            List<Player> playerDataReader = PlayerDataReader.readPlayerDataFromFile("player_data.txt");
            playerDataList.addAll(playerDataReader);
            playerIds.addAll(playerDataList.stream().map(Player::getId).collect(Collectors.toSet()));
        } catch (IOException e) {
            System.err.println("Error reading player data: " + e.getMessage());
        }

        for (String id: playerIds) {
            try {
                List<Player> playerAction = playerDataList.stream()
                        .filter(e -> e.getId().equals(id))
                        .toList();

                double incomeOutcome = 0;
                double playerOutcome = 0;
                double all_games = 0;
                double won_games = 0;
                for (Player action: playerAction) {
                    switch (action.getAction()) {
                        case "DEPOSIT" -> incomeOutcome += action.getAmount();
                        case "WITHDRAW" -> {
                            incomeOutcome -= action.getAmount();
                            if (incomeOutcome < 0) {
                                illegalPlayers.add(action);
                                throw new RuntimeException("Withdrew too much");
                            }
                        }
                        case "BET" -> {
                            if (action.getAmount() > incomeOutcome) {
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
                                    playerOutcome += action.getAmount()*matchData.getaCoef();
                                } else if (matchData.getResult().equals("B")) {
                                    playerOutcome += action.getAmount()*matchData.getbCoed();
                                }
                            } else if (!matchData.getResult().equals("DRAW") &&
                                    !matchData.getResult().equals(action.getBetSide())
                            ) {
                                playerOutcome -= action.getAmount();
                            }
                        }
                    }
                }
                incomeOutcome += playerOutcome;
                casinoBalance -= playerOutcome;
                legalPlayers.add(new LegalPlayer(id, incomeOutcome, won_games/all_games));

            } catch (RuntimeException e) {
                System.err.println("Illegal action found: " + id + " " + e.getMessage());
            }
        }

        PrintWriter pw = getPrintWriter(legalPlayers, illegalPlayers, casinoBalance);
        pw.close();
    }

    private static PrintWriter getPrintWriter(List<LegalPlayer> legalPlayers, List<Player> illegalPlayers, double casinoBalance) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter("result.txt"));
        for (LegalPlayer player: legalPlayers) {
            pw.print(player.getPlayerId());
            pw.print(" ");
            pw.print(player.getFinalAmount());
            pw.print(" ");
            pw.print(player.getWinPercentage());
            pw.println();
        }
        pw.println();
        for (Player action: illegalPlayers) {
            pw.print(action.getId());
            pw.print(" ");
            pw.print(action.getAction());
            pw.print(" ");
            pw.print(action.getMatchId());
            pw.print(" ");
            pw.print(action.getAmount());
            pw.print(" ");
            pw.print(action.getBetSide());
            pw.println();
        }
        pw.println();
        pw.print(casinoBalance);
        return pw;
    }
}