import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.poi.hssf.record.EOFRecord;

public class Settings {
    private final char COMMENT_CHAR = '#';
    private final String SETTINGS_PATH = "settings.txt";

    private File settingsFile;
    private String DBdirectory;

    public Settings() {
        settingsFile = new File (SETTINGS_PATH);
    }

    private int FetchSettings() {
        // if file exists, parse file
        if (settingsFile.exists()) {

        }
        // if file doesn't exist, create file with default settings
        return 0;
    }

    private void ParseSettings() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
        String curLine;

        // while reader has next line, process next line
        curLine = reader.readLine();
    }
}
