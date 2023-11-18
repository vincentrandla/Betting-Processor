import model.LegalPlayer;
import model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResultDataWriter {
    static void writeResultsToFile(String fileName, List<LegalPlayer> legalPlayers, List<Player> illegalPlayers, double casinoBalance) throws IOException {
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
