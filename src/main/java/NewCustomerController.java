import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * This Controller controls the New Customer window. It allows the window
 * to get the text that is entered in the fields and save it in the
 * database.
 */
public class NewCustomerController implements Initializable {

    private Connection conn;

    int rowsAffected = 0;

    @FXML private Button addCustomerButton;

    @FXML private TextField newCustomerEmail;
    @FXML private TextField newCustomerFirstName;
    @FXML private TextField newCustomerLastName;
    @FXML private TextField newCustomerPhone;

    /**
     * Initialize the window by setting a TextFormatter for the phone number
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFormatter<String> textFormatter = new TextFormatter<String>(new DefaultStringConverter(), "", new PhoneNumberFilter());
        newCustomerPhone.setTextFormatter(textFormatter);
    }

    /**
     * Creates a customer based off of the text fields and adds it
     * to the database
     * @param event Event that triggered this method
     */
    @FXML
    void addCustomer(ActionEvent event) {
        String firstName = newCustomerFirstName.getText();
        String lastName = newCustomerLastName.getText();
        String phone = newCustomerPhone.getText();
        String email = newCustomerEmail.getText();

        Statement get = null;
        PreparedStatement insert = null;
        String sql = "INSERT INTO Customers (firstname, lastname, phone, email) VALUES (?, ?, ?, ?)";
        try
        {
            get = conn.createStatement();
            ResultSet result = get.executeQuery("SELECT * FROM CUSTOMERS");
            while (result.next()) {
                String testFirstName = result.getString("FIRSTNAME");
                String testLastName = result.getString("LASTNAME");
                if (testFirstName.equals(firstName) && testLastName.equals(lastName)) {
                    String testPhone = result.getString("PHONE");
                    String testEmail = result.getString("EMAIL");
                    if (testPhone.equals(phone) && testEmail.equals(email)) {
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot create duplicate Customers. If two Customers have the exact same name, make sure they have different phones or emails.", ButtonType.OK);
                            alert.setTitle("Duplicate Customer Entry");
                            alert.setHeaderText("");
                            alert.show();
                            return;
                    }
                }
                else if (firstName == "" & lastName == "")
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot create a customer with no name.", ButtonType.OK);
                        alert.setTitle("No Name Entered");
                        alert.setHeaderText("");
                        alert.show();
                        return;
                }
            }

            insert = conn.prepareStatement(sql);
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setString(3, phone);
            insert.setString(4, email);
            rowsAffected = insert.executeUpdate();

            insert.close();
        }
        catch (SQLException sqlExcept)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error. This is either a bug, or you messed with the DragonSlayer/derbyDB folder.", ButtonType.OK);
            alert.setTitle("Database Error");
            alert.setHeaderText("");
            alert.show();
        }
        Stage window = (Stage) addCustomerButton.getScene().getWindow();
        window.close();
    }

    /**
     * Sets the database connection for this controller
     * @param conn Connection to set for this controller
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

}