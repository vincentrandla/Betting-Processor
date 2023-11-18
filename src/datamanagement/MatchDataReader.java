package datamanagement;

import model.Match;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchDataReader {
    public static List<Match> readMatchDataFromFile(String filepath) throws IOException {
        List<Match> matchDataReader = new ArrayList<>();

        try (BufferedReader md = new BufferedReader(new FileReader(filepath))) {
            String md_line = md.readLine();
            while (md_line != null) {
                String[] lines = md_line.split(",");
                String id = lines[0];
                double a = Double.parseDouble(lines[1]);
                double b = Double.parseDouble(lines[2]);
                String result = lines[3];
                Match matchData = new Match(id, a, b, result);
                matchDataReader.add(matchData);
                md_line = md.readLine();
            }
        }

        return matchDataReader;
    }
}
