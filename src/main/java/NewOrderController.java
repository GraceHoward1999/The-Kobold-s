import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;

/**
 * This Controller controls the New Order window. It allows the window
 * to get the text that is entered in the fields and save it in the
 * database.
 */
public class NewOrderController {

    private Connection conn;
    private int customerId;

    @FXML private Button addOrderButton;
    @FXML private ComboBox<String> setTitle;
    @FXML private TextField setQuantity;
    @FXML private TextField setIssue;

    @FXML private Text orderTitleErrorText;
    @FXML private Text orderQuantityErrorText;

    private ObservableList<Title> titles  = FXCollections.observableArrayList();
    private ObservableList<String> titlesStr  = FXCollections.observableArrayList();


    /**
     * Creates an order based on the fields and ComboBox and adds it
     * to the database
     * @param event Event that triggered this method
     */
    @FXML
    void newOrder(ActionEvent event) {
        PreparedStatement s = null;
        String sql = "INSERT INTO Orders (customerId, titleId, quantity, issue) VALUES (?, ?, ?, ?)";
        orderQuantityErrorText.setVisible(false);
        orderTitleErrorText.setVisible(false);

        if (getChoice(setTitle) == -1) {
            orderTitleErrorText.setVisible(true);
            return;
        }
        else if (setQuantity.getText().equals("")) {
            orderQuantityErrorText.setVisible(true);
            return;
        }
        else {
            int titleID = getChoice(setTitle);
            String issue = setIssue.getText();
            if (issue == "") {
                issue = null;
            }
            String quantity = setQuantity.getText();
            int customerId = this.customerId;

            try {
                s = conn.prepareStatement(sql);
                s.setString(1, Integer.toString(customerId));
                s.setString(2, Integer.toString(titleID));
                s.setString(3, quantity);
                s.setObject(4, issue, Types.INTEGER);


                int rowsAffected = s.executeUpdate();

                if (rowsAffected == 0) {
                    //TODO: Throw an error
                } else if (rowsAffected > 1) {
                    //TODO: Throw an error
                }
                s.close();
            } catch (SQLException sqlExcept) {
                sqlExcept.printStackTrace();
            }
        }
        Stage window = (Stage) addOrderButton.getScene().getWindow();
        window.close();
    }

    /**
     * Populate the ComboBox with the titles in titlesStr, add listener to handle typing over selection
     */
    public void setNewOrder(){
        setTitle.setItems(this.titlesStr);
        setTitle.getSelectionModel().selectFirst();
        setTitle.setEditable(true);
        // setTitle.getEditor().focusedProperty().addListener(observable -> {

        // });
        FxUtilTest.autoCompleteComboBoxPlus(setTitle, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.equals(typedText));
    }

    /**
     * Sets the connection for this controller
     * @param conn the connection for this controller
     */
    public void setConnection(Connection conn) {
        this.conn = conn;
    }

    /**
     * Sets the customer ID for this controller
     * @param id ID of the customer to set
     */
    public void setCustomerID(int id) {
        this.customerId = id;
    }

    /**
     * Populates titlesStr based off of an ObservableList of Titles
     * @param getTitles ObservableList of Titles to add to titlesStr
     */
    public void populate(ObservableList<Title> getTitles){
        this.titles = getTitles;
        for(int i=0; i < titles.size(); i++){
            titlesStr.add(titles.get(i).getTitle());
        }
    }

    /**
     * Gets the choice from the ComboBox
     * @param setTitle ComboBox containing the title value
     * @return the ID of the title selected
     */
    public int getChoice(ComboBox<String> setTitle ){
        String name = FxUtilTest.getComboBoxValue(setTitle);

        for(int i=0; i < titles.size(); i++){
            if (titles.get(i).getTitle().equals(name)){
                return titles.get(i).getId();
            }
        }
        return -1;
    }
}