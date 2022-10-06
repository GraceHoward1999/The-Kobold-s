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
 * This Controller controls the Edit Customer window. It allows the window
 * to get the text that is entered in the fields and save it in the
 * database.
 */
public class EditCustomerController implements Initializable {

    private Connection conn;
    private Customer customer;

    @FXML private Button updateCustomerButton;

    @FXML private TextField updateCustomerEmail;
    @FXML private TextField updateCustomerFirstName;
    @FXML private TextField updateCustomerLastName;
    @FXML private TextField updateCustomerPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFormatter<String> textFormatter = new TextFormatter<String>(new DefaultStringConverter(), "", new PhoneNumberFilter());
        updateCustomerPhone.setTextFormatter(textFormatter);
    }

    /**
     * Updates the Customer that has been set for this Controller. Sets
     * the values that have been entered in the text fields for the
     * Customer in the database.
     * @param event
     */
    @FXML
    void updateCustomer(ActionEvent event) {
        Stage window = (Stage) updateCustomerButton.getScene().getWindow();

        String firstName = updateCustomerFirstName.getText();
        String lastName = updateCustomerLastName.getText();
        String phone = updateCustomerPhone.getText();
        String email = updateCustomerEmail.getText();
        if (phone.length() > 0 && phone.length() < 12) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Phone number must be 10 digits", ButtonType.OK);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText("");
            alert.show();
            return;
        }

        Statement get = null;
        PreparedStatement update = null;
        String sql = """
        UPDATE Customers
        SET firstname = ?, lastname = ?, phone = ?, email= ?
        WHERE CUSTOMERID = ?
        """;

        try
        {
            get = conn.createStatement();
            update = conn.prepareStatement(sql);
            ResultSet result = get.executeQuery("SELECT * FROM CUSTOMERS");
            while (result.next()) {
                String testFirstName = result.getString("FIRSTNAME");
                String testLastName = result.getString("LASTNAME");
                if (testFirstName.equals(firstName) && testLastName.equals(lastName)) {
                    String testPhone = result.getString("PHONE");
                    String testEmail = result.getString("EMAIL");
                    if (testPhone.equals(phone) && testEmail.equals(email)) {
                        if(this.customer.getId() != result.getInt("CustomerID"))
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Cannot create duplicate Customers. If two Customers have the exact same name, make sure they have different phones or emails.", ButtonType.OK);
                            alert.setTitle("Duplicate Customer Entry");
                            alert.setHeaderText("");
                            alert.show();
                            return;
                        }
                        update.close();
                        window.close();
                    }
                }
            }

            update = conn.prepareStatement(sql);
            update.setString(1, firstName);
            update.setString(2, lastName);
            update.setString(3, phone);
            update.setString(4, email);
            update.setString(5, Integer.toString(customer.getId()));
            int rowsAffected = update.executeUpdate();

            update.close();
        }
        catch (SQLException sqlExcept)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Database error. This is either a bug, or you messed with the DragonSlayer/derbyDB folder.", ButtonType.OK);
            alert.setTitle("Database Error");
            alert.setHeaderText("");
            alert.show();
        }
        
        window.close();
    }

    /**
     * Sets the connection for this controller
     * @param conn The connection to set for this controller
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * Sets the Customer to edit
     * @param customer The customer that will be edited
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
        updateCustomerFirstName.setText(customer.getFirstName());
        updateCustomerLastName.setText(customer.getLastName());
        if (customer.getPhone().length() == 12) {
            String phone = customer.getPhone().substring(0, 3) + customer.getPhone().substring(4, 7) + customer.getPhone().substring(8);
            System.out.println(phone);
            updateCustomerPhone.setText(phone);
        }
        updateCustomerEmail.setText(customer.getEmail());
    }
}