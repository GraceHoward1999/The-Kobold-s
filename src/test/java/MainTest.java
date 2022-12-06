import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest extends ApplicationTest {

    private Controller controller;

    @Override
    public void start (Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();

        controller = fxmlLoader.getController();
    }

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    /**
     * This method serves to delete failed data from other tests.
     * It should always pass.
     */
    @Test
    public void safetyNonTest()
    {
        assertTrue(true);
        clickOn("Customers");
        try{
            clickOn("Ample");
        }
        catch(Exception e){};
        clickOn("#deleteCustomerButton");
        clickOn("#yesButton");
    }
    /**
     * Test adding a new customer using the GUI interaction .
     * @result 5 customers will be added without any errors
     * The total number of customers after this method execute will
     * increment by five from the original size.
     */
    @Test
    public void testAddingCustomer () {
        int before = controller.getCustomers().size();

        clickOn("Customers");
        clickOn("#addCustomerButtonMain");
        clickOn("#newCustomerFirstName");
        write("Ex");
        clickOn("#newCustomerLastName");
        write("Ample");
        clickOn("#newCustomerPhone");
        write("523-555-4468");
        clickOn("#addCustomerButton");

        int after = controller.getCustomers().size();

        assertEquals(before + 1, after);
    }

    @Test
    public void testEditCustomer()
    {
        /**clickOn("Customers");
        clickOn("#addCustomerButtonMain");
        clickOn("#newCustomerFirstName");
        write("Ex");
        clickOn("#newCustomerLastName");
        write("Ample");
        clickOn("#newCustomerPhone");
        write("523-555-4468");
        clickOn("#addCustomerButton");*/

        clickOn("Ample");
        clickOn("#editCustomerButton");
        clickOn("#updateCustomerFirstName");
        write("Null");
        clickOn("#updateCustomerNotes");
        write("Cogito ergo cogito");
        clickOn("#updateCustomerButton");

        clickOn("Ample");
        Customer example = controller.getSelectedCustomer();
        assertEquals(example.getNotes(),"Cogito ergo cogito");

        /**clickOn("Ample");
        clickOn("#deleteCustomerButton");
        clickOn("#yesButton");*/
    }

    @Test
    public void testDelinqCustomer()
    {
        /**clickOn("Customers");
        clickOn("#addCustomerButtonMain");
        clickOn("#newCustomerFirstName");
        write("Ex");
        clickOn("#newCustomerLastName");
        write("Ample");
        clickOn("#newCustomerPhone");
        write("523-555-4468");
        clickOn("#addCustomerButton");*/

        clickOn("Ample");
        clickOn("#Delinq");

        clickOn("Ample");
        Customer example = controller.getSelectedCustomer();
        assertTrue(example.getDelinquent());

        clickOn("Ample");
        clickOn("#Delinq");

        clickOn("Ample");
        assertFalse(example.getDelinquent());
    }

    /**
     * Tests creating an order for a customer, then deleting it.
     */
    @Test
    public void testCreatingDeletingOrder()
    {
        clickOn("Ample");
        clickOn("#newOrderButton");
        clickOn("#addOrderButton");

        int total = 0;
        Customer example = controller.getSelectedCustomer();
        ObservableList<Order> a = controller.getOrderTable();
        for (Order order : a) {
            if (order.getCustomerId() == example.getId()) total++;
        }
        assertTrue(total == 1);

        clickOn("Ample");
        press(KeyCode.TAB);
        release(KeyCode.TAB);
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);
        clickOn("#deleteOrderButton");
        clickOn("#yesButton");

        total = 0;
        ObservableList<Order> b = controller.getOrderTable();
        for (Order order : b) {
            if (order.getCustomerId() == example.getId()) total++;
        }
        System.out.println(total);
        assertTrue(total == 0);

    }

        /**
     * Test deleting the 5 customers that was added by testAddingCustomer().
     * @result 5 customers will be deleted without any errors
     * The total number of customers after this method execute will
     * decrement by five from the original size.
     */
    @Test
    public void testZeletingCustomer () {
        int before = controller.getCustomers().size();
        clickOn("Ample");
        clickOn("#deleteCustomerButton");
        clickOn("#yesButton");
        int after = controller.getCustomers().size();

        assertEquals(before - 1, after);
    }
    
    /**
     * Confirms that when something is deleted, data associated is cleared properly.
     */
    @Test
    public void verifyDeletingDataCleared()
    {
        clickOn("Customers");
        clickOn("#addCustomerButtonMain");
        clickOn("#newCustomerFirstName");
        write("Ex");
        clickOn("#newCustomerLastName");
        write("Ample");
        clickOn("#newCustomerPhone");
        write("523-555-4468");
        clickOn("#addCustomerButton");

        clickOn("Ample");
        Customer example = controller.getSelectedCustomer();
        assertFalse(example.getDelinquent());

        int total = 0;
        ObservableList<Order> b = controller.getOrderTable();
        for (Order order : b) {
            if (order.getCustomerId() == example.getId()) total++;
        }
        assertTrue(total == 0);

        clickOn("#deleteCustomerButton");
        clickOn("#yesButton");
    }
}