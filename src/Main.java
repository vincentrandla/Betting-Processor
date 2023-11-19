import datamanagement.MatchDataReader;
import datamanagement.PlayerDataReader;
import datamanagement.ResultDataWriter;
import gamelogic.Operation;
import model.LegalPlayer;
import model.Match;
import model.Player;
import model.PlayerOperation;

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


        PlayerOperation playerOperation = Operation.playerOperation(playerIds, playerDataList, matchDataList, illegalPlayers, legalPlayers, casinoBalance);
        ResultDataWriter.writeResultsToFile("src/result.txt", playerOperation, illegalPlayers);
    }
}
