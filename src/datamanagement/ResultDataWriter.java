package datamanagement;

import model.LegalPlayer;
import model.Player;
import model.ProcessResult;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResultDataWriter {
    public static void writeResultsToFile(String fileName, ProcessResult result, List<Player> illegalPlayers) throws IOException {
        double casinoBalance = result.getCasinoBalance();
        List<LegalPlayer> legalPlayers = result.getLegalPlayers();
        PrintWriter pw = new PrintWriter(new FileWriter(fileName));
        for (LegalPlayer player : legalPlayers) {
            pw.print(player.getPlayerId());
            pw.print(" ");
            int finalAmountAsInt = (int) player.getFinalAmount();
            pw.print(finalAmountAsInt);
            pw.print(" ");
            pw.print(player.getFormattedWinPercentage());
            pw.println();
        }
        pw.println();
        for (Player action : illegalPlayers) {
            pw.print(action.getId());
            pw.print(" ");
            pw.print(action.getAction());
            pw.print(" ");
            pw.print(action.getMatchId() != null && !action.getMatchId().isEmpty() ? action.getMatchId() : "null");
            pw.print(" ");
            int amountAsInt = (int) action.getAmount();
            pw.print(amountAsInt);
            pw.print(" ");
            pw.print(action.getBetSide() != null && !action.getBetSide().isEmpty() ? action.getBetSide() : "null");
            pw.println();
        }
        pw.println();
        int casinoBalanceAsInt = (int) casinoBalance;
        pw.print(casinoBalanceAsInt);
        pw.close();
    }
}

