import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javafx.util.Pair;

public class Settings {
    private final char COMMENT_CHAR = '#';
    private final String SETTINGS_PATH = "settings.ini";
    private final String RULE_DELIMITER = "=";

    // default config Pairs (Key = Setting, Value = Setting value), add new settings here
    private final Pair<String, String> DEFAULT_DB_LOCATION = new Pair<String, String>("dbLocation", System.getProperty("user.home") + "\\DragonSlayer\\derbyDB");

    private File settingsFile;
    private ArrayList<Pair<String, String>> rulePairs = new ArrayList<Pair<String, String>>();

    public Settings() {
        settingsFile = new File (SETTINGS_PATH);

        try {
            // if file was created, create settings file. Otherwise, the file already existed, so parse it
            if (settingsFile.createNewFile()) {
                createSettings();
            }
            else {
                parseSettings();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException sce) {
            sce.printStackTrace();
            System.out.println("Security exception detected, try running the program as an administrator");
        }
    }

    /**
     * Finds a setting from the list of settings
     * @param setting the setting name to look for (corresponds to Pair key)
     * @return the settings value if found, null if not found (corresponds to Pair value)
     */
    public String getSetting(String setting) {
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

    private void parseSettings() throws IOException {
        Scanner reader = new Scanner(new FileReader(settingsFile));
        String curLine;
        String[] curPair;

        // while reader has next line, process next line
        while (reader.hasNextLine()) {
            curLine = reader.nextLine();
            if (curLine.charAt(0) != COMMENT_CHAR) {
                curPair = curLine.split(RULE_DELIMITER);
                rulePairs.add(new Pair<String, String>(curPair[0].trim(), curPair[1].trim()));
            }
        }

        reader.close();
    }

    private void createSettings() throws IOException {
        FileWriter writer = new FileWriter(settingsFile);
        // generate db location setting
        // instructions
        writer.write(COMMENT_CHAR + " This is the location of the database. To move the database, change the path below and run the Dragon Slayer software\n");
        writer.write(COMMENT_CHAR + " WARNING: The movement process involves deleting files, making a manual backup before starting the program is advised\n");
        // Setting that will be parsed
        writer.write(DEFAULT_DB_LOCATION.getKey() + " " + RULE_DELIMITER + " " + DEFAULT_DB_LOCATION.getValue());


        writer.close();
    }
}
