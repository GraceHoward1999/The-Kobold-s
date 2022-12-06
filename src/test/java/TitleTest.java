import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
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
public class TitleTest extends ApplicationTest {

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
        clickOn("Titles");
        try{
            clickOn("#TitleSearch");
            write("Example Man");
            //dumb workaround bc click on won't work right
            for (int i = 0; i < 6; i++)
            {
                press(KeyCode.TAB);
                    release(KeyCode.TAB);
            }
            press(KeyCode.ENTER);
            release(KeyCode.ENTER);
        }
        catch(Exception e){};
        clickOn("#deleteCustomerButton");
        clickOn("#yesButton");
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
            clickOn("Titles");
            clickOn("#addTitleButtonMain");
            clickOn("#newTitleTitle");
            write("Example Man");
            clickOn("#newTitlePrice");
            write("5.00");
            clickOn("#newTitleNotes");
            write("Just testing");

            clickOn("#addTitleButton");
        
        int after = controller.getTitles().size();

        assertEquals(before + 1, after);
    }

    @Test
    public void testFlaggingTitle()
    {
        Title example = controller.getSelectedTitle();
        assertFalse(example.flaggedProperty().get());
        //dumb workaround bc click on won't work right
        for (int i = 0; i < 6; i++)
        {
           press(KeyCode.TAB);
            release(KeyCode.TAB);
        }
        press(KeyCode.ENTER);
        release(KeyCode.ENTER);

        push(new KeyCodeCombination(KeyCode.M, KeyCodeCombination.CONTROL_DOWN));

    }

    /**
     * Test deleting the titles that was added by testAddingTitle ().
     * @result titles will be deleted without any errors
     * The total number of titles after this method execute will
     * decrement by five from the original size.
     */
     @Test
    public void testZeletingTitle () {
        int before = controller.getTitles().size();

            clickOn("Titles");
            clickOn("#TitleSearch");
            write("Example Man");
            //dumb workaround bc click on won't work right
            for (int i = 0; i < 6; i++)
            {
                press(KeyCode.TAB);
                release(KeyCode.TAB);
            }
            press(KeyCode.ENTER);
            release(KeyCode.ENTER);
            //clickOn("Example Man");
            clickOn("#deleteTitleButton");
            clickOn("#yesButton");
        

        int after = controller.getTitles().size();

        assertEquals(before - 1, after);
    }
}

