import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Class for outputting to and adding onto a monthly log file. 
 * The filename of the log file is in the format "MON-YEAR.log".
 * 
 * If a log file for the current month doesn't exist, a new file will be generated.
 * If a file does exist, any logs will be appended to that file.
 */
public final class Log 
{
    private static final String LOG_DIRECTORY_PATH = "/logs";

    public static boolean promptUser = false;

    private Log() { /* This class is static and must not be instantiated */ };
    
    /**
     * Saves a specific message to the log file.
     * @param message The message to be saved.
     */
    public static void LogMessage(String message)
    {
        saveToLog(message + "\n");
    }

    /**
     * Saves a log for an event displaying what the event was followed by it's description.
     * The format for these logs are "### NAME ### - EVENT".
     * 
     * @param eventName Name of the event being logged.
     * @param event Description of the event.
     */
    public static void LogEvent(String eventName, String event)
    {
        saveToLog("### " + eventName + " ### " + event + "\n");
    }

    /**
     * Responsible for outputting any log messages out to the monthly log file. 
     * Creates a new file if one for the current month does not exist, appends to the existing file otherwise.
     * @param message Message to be logged.
     */
    private static void saveToLog(String message)
    {
        File logFile = new File(getMonthlyFileName());
        File logDirectory = new File(System.getProperty("user.dir") + LOG_DIRECTORY_PATH);

        try 
        {
            if (!logDirectory.exists())
            {
                System.out.println("Making log directory at: " + logDirectory.getAbsolutePath());
                logDirectory.mkdir();
            }

            if (!logFile.exists())
            {
                System.out.println("Making new log file at: " + logFile.getAbsolutePath());
                logFile.createNewFile();
            }
        
            BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));

            String dateAndTime = new Date().toString();
            String logDateAndTime = dateAndTime.substring(0, 3) + " " + dateAndTime.substring(8, 19) + ": ";

            out.write(logDateAndTime + message);

            out.flush();
            out.close();
        }
        catch (IOException e)
        {
            System.err.println(e);

            if (promptUser)
            {
                // TODO: Prompt User
            }
        }

    }

    /**
     * Returns a string representation of the filename of the log file for current month.
     * @return The filename of the current month's log file.
     */
    private static String getMonthlyFileName()
    {
        String date = new Date().toString();
        String fileName = date.substring(4, 7) + "-" + date.substring(date.length() - 4) + ".log";
        String filePath = System.getProperty("user.dir") + LOG_DIRECTORY_PATH + "/" + fileName;

        // TODO: Remove after debugging
        System.out.println(filePath);

        return filePath;
    }
}
