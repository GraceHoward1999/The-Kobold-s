import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.util.Pair;

public class Settings {
    private final char COMMENT_CHAR = '#';
    private final String SETTINGS_PATH = "settings.ini";
    private final String RULE_DELIMITER = "=";

    private final String DEFAULT_DB_LOCATION = "";

    private File settingsFile;
    private ArrayList<Pair<String, String>> rulePairs;

    public Settings() {
        settingsFile = new File (SETTINGS_PATH);
        FetchSettings();
    }

    /**
     * Finds a setting from the list of settings
     * @param setting the setting name to look for (corresponds to Pair key)
     * @return the settings value if found, null if not found (corresponds to Pair value)
     */
    public String FindSetting(String setting) {
        Iterator<Pair<String, String>> ruleIterator = rulePairs.iterator();
        Pair<String, String> curRule = null;

        while(ruleIterator.hasNext()) {
            curRule = ruleIterator.next();
            if (curRule.getKey().equals(setting)) {
                return curRule.getValue();
            }
        }   

        return null;
    }

    private int FetchSettings() {
        // if file exists, parse file
        try {
            ParseSettings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // TODO:
            // if file doesn't exist, create file with default settings
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void ParseSettings() throws IOException {
        Scanner reader = new Scanner(new FileReader(settingsFile));
        String curLine;
        String[] curPair;

        // while reader has next line, process next line
        while (reader.hasNextLine()) {
            curLine = reader.nextLine();
            if (curLine.charAt(0) != COMMENT_CHAR) {
                curPair = curLine.split(RULE_DELIMITER);
                rulePairs.add(new Pair<String, String>(curPair[0], curPair[1]));
            }
        }
    }

    private void GenDefaultSettings() {
        // TODO: how to generate initial file??
    }
}
