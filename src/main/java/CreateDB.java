import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

//import java.util.ArrayList;

/**
 * This class provides a way to create a database for the application.
 */
public class CreateDB {
    static Settings settings = new Settings();
    public static void main(String args[]) {
        new CreateDB().go();
        System.out.println("CreateDB finished.");
        Log.LogEvent("Database Created", "Created a new database at: " + settings.getSetting("dbLocation") + ";create=true");
    }

    /**
     * Creates an embedded Apache Derby database with the specified schema.
     */
    void go() {
        System.out.println("Creating embedded DB");

        Connection conn = null;
        Statement s = null;

        try {
            String dbName = "derbyDB";
            //NOTE: This is the install location of the database, hardcoding to work on my machine now.
            //We will need to change this in the future.

            conn = DriverManager.getConnection("jdbc:derby:" + settings.getSetting("dbLocation") + ";create=true");

            System.out.println("Connected to db " + dbName);
            conn.setAutoCommit(false);

            s = conn.createStatement();
            s.execute("""
                    CREATE TABLE Customers
                    (
                        CustomerID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                        FirstName varchar(255),
                        LastName varchar(255),
                        Phone varchar(255),
                        Email varchar(255),
                        Notes varchar(255),
                        DELINQUENT boolean default false not null,
                        PRIMARY KEY (CustomerID)
                    )""");
            System.out.println("Created table Customer");
            s.execute("""
                    CREATE TABLE Titles
                    (
                        TitleID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                        Title varchar(255),
                        Price int,
                        Notes varchar(8000),
                        FLAGGED boolean default false not null,
                        DATE_FLAGGED date,
                        ISSUE_FLAGGED int,
                        ProductId varchar(255),
                        DateCreated date(0),
                        PRIMARY KEY (TitleID)
                    )""");
            System.out.println("Created table Title");
            s.execute("""
                    CREATE TABLE Orders
                    (
                        CustomerID int REFERENCES Customers(CustomerID),
                        TitleID int REFERENCES Titles(TitleID),
                        Quantity int,
                        Issue int
                    )""");
            System.out.println("Created table Order");

            conn.commit();
            System.out.println("Committed the transaction");

//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException se) {
//                if (((se.getErrorCode() == 50000)
//                        && ("XJ015".equals(se.getSQLState())))) {
//                    System.out.println("Derby shut down normally");
//                } else {
//                    System.out.println("Derby did not shut down normally");
//                    se.printStackTrace();
//                }
//            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
//        finally {
//            //Cleanup
//            try {
//                if (s != null) {
//                    s.close();
//                    s = null;
//                }
//            } catch (SQLException sqle) {
//                sqle.printStackTrace();
//            }
//            try {
//                if (conn != null) {
//                    conn.close();
//                    conn = null;
//                }
//            } catch (SQLException sqle) {
//                sqle.printStackTrace();
//            }
//        }
    }
}
