package datamanagement;

import model.Player;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PlayerDataReader {
    public static List<Player> readPlayerDataFromFile(String filePath) throws IOException {
        List<Player> playerDataReader = new ArrayList<>();
        Set<String> playerIds = new TreeSet<>();

        try (BufferedReader pd = new BufferedReader(new FileReader(filePath))) {
            String pd_line = pd.readLine();
            while (pd_line != null) {
                String[] lines = pd_line.split(",");
                String id = lines[0];
                String action = lines[1];
                String matchId = lines[2];
                double amount = Double.parseDouble(lines[3]);
                String betSide = lines[2].isEmpty() ? "" : lines[4];
                Player playerData = new Player(id, action, matchId, (long) amount, betSide);
                playerDataReader.add(playerData);
                playerIds.add(id);
                pd_line = pd.readLine();
            }
        }

        return playerDataReader;
    }


}
