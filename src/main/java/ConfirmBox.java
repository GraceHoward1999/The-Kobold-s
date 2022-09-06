import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * ConfirmBox to display a window and get a yes or no answer from the user.
 *
 */
public class ConfirmBox {

    static boolean answer;

    /**
     * Displays the title as the window title, and the message as text inside
     * the window. Prompts the users for yes or no confirmation and returns
     * their answer.
     * @param title The title for the window
     * @param message  The message to display in the window
     * @return True or false depending on the user's selection
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Yes");
        yesButton.setId("yesButton");

        Button noButton = new Button("No");
        noButton.setId("noButton");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(yesButton, noButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, buttonsBox);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

}
