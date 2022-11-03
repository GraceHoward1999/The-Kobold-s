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
     * Test adding a new customer using the GUI interaction .
     * @result 5 customers will be added without any errors
     * The total number of customers after this method execute will
     * increment by five from the original size.
     */
    @Test
    public void testAddingCustomer () {
        int before = controller.getCustomers().size();

        int added = 5;

        for (int i = 1; i <= added; i++){
            clickOn("Customers");
            clickOn("#addCustomerButtonMain");
            clickOn("#newCustomerFirstName");
            write("TestFirst" + i);
            clickOn("#newCustomerLastName");
            write("TestLast" + i);
            clickOn("#newCustomerPhone");
            write("911-911-9911");
            clickOn("#newCustomerEmail");
            write("Test@dumpy.net");
            clickOn("#addCustomerButton");
        }

        int after = controller.getCustomers().size();

        assertEquals(before + added, after);
    }

    /**
     * Test adding a new Titels using the GUI interaction .
     * @result 5 titles will be added without any errors.
     * The total number of titles after this method execute will
     * increment by five from the original size.
     */
    @Test
    public void testAddingTitle () {
        int before = controller.getTitles().size();
        int added = 5;

        for (int i = 1; i <= added; i++) {
            clickOn("Titles");
            clickOn("#addTitleButtonMain");
            clickOn("#newTitleTitle");
            write("The test"+ i);
            clickOn("#newTitlePrice");
            write("5.00");
            clickOn("#newTitleNotes");
            write("Just testing");

            clickOn("#addTitleButton");
        }

        int after = controller.getTitles().size();

        assertEquals(before + added, after);
    }

    /**
     * Test deleting the 5 titles that was added by testAddingTitle ().
     * @result 5 titles will be deleted without any errors
     * The total number of titles after this method execute will
     * decrement by five from the original size.
     */
    @Test
    public void testDeletingTitle () {
        int before = controller.getTitles().size();
        int added = 5;

        for (int i = 1; i <= added; i++) {
            clickOn("Titles");
            clickOn("The test"+ i);
            clickOn("#deleteTitleButton");
            clickOn("#yesButton");
        }

        int after = controller.getTitles().size();

        assertEquals(before - added, after);
    }

    /**
     * Test deleting the 5 customers that was added by testAddingCustomer().
     * @result 5 customers will be deleted without any errors
     * The total number of customers after this method execute will
     * decrement by five from the original size.
     */
    @Test
    public void testDeletingCustomer () {
        int before = controller.getCustomers().size();
        int added = 5;

        for (int i = 1; i <= added; i++) {
            clickOn("Customers");
            clickOn("TestLast" + i);
            clickOn("#deleteCustomerButton");
            clickOn("#yesButton");
        }

        int after = controller.getCustomers().size();

        assertEquals(before - added, after);
    }
}