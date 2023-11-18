import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Match> matchDataList = new ArrayList<>();
        List<Player> playerDataList = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();
        List<Player> illegalPlayers = new ArrayList<>();
        List<LegalPlayer> legalPlayers = new ArrayList<>();
        double casinoBalance = 0;

        BufferedReader pd = new BufferedReader(new FileReader("player_data.txt"));
        String pd_line = pd.readLine();
        while (pd_line != null) {
            String[] lines = pd_line.split(",");
            String id = lines[0];
            String action = lines[1];
            String matchId = lines[2];
            double amount = Double.parseDouble(lines[3]);
            String betSide = lines[2].isEmpty() ? "" : lines[4];
            Player playerData = new Player(id, action, matchId, amount, betSide);
            playerDataList.add(playerData);
            playerIds.add(id);
            pd_line = pd.readLine();
        }
        pd.close();

        BufferedReader md = new BufferedReader(new FileReader("match_data.txt"));
        String md_line = md.readLine();
        while (md_line != null) {
            String[] lines = md_line.split(",");
            String id = lines[0];
            double a = Double.parseDouble(lines[1]);
            double b = Double.parseDouble(lines[2]);
            String result = lines[3];
            Match matchData = new Match(id, a, b, result);
            matchDataList.add(matchData);
            md_line = md.readLine();
        }
        md.close();

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
                                throw new RuntimeException("Match ID not found. ");
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
        pw.close();
    }
}