import javafx.scene.control.TextFormatter;

import java.util.function.UnaryOperator;

/**
 * A utility class to format a text string into a phone number in real time
 */
public class PhoneNumberFilter implements UnaryOperator<TextFormatter.Change> {

    /**
     * Applies a specific text format to the text described by a TextFormatter change
     * @param change the change to apply the format to
     * @return the change with the formatted text
     */
    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if (change.isContentChange()) {
            handleBackspaceOverSpecialCharacter(change);
            String phoneStr = change.getText().replaceAll("[-() ]*", "");
            if (phoneStr.matches("[0-9]*")) {
                int originalNewTextLength = change.getControlNewText().length();
                change.setText(formatNumber(change.getControlNewText()));
                change.setRange(0, change.getControlText().length());
                int caretOffset = change.getControlNewText().length() - originalNewTextLength;
                change.setCaretPosition(change.getCaretPosition() + caretOffset);
                change.setAnchor(change.getAnchor() + caretOffset);
                return change;
            } else {
                return null;
            }
        }
        return change;
    }

    /**
     * Makes sure that when a user deletes a character, the special characters related are deleted as well
     * @param change any TextFormatter.Change
     */
    private void handleBackspaceOverSpecialCharacter(TextFormatter.Change change) {
        if (change.isDeleted() && (change.getSelection().getLength() == 0)) {
            if (!Character.isDigit(change.getControlText().charAt(change.getRangeStart()))) {
                if (change.getRangeStart() > 0) {
                    change.setRange(change.getRangeStart() - 1, change.getRangeEnd() - 1);
                }
            }
        }
    }

    /**
     * Formats a string into XXX-XXX-XXXX format
     * @param numbers the string to format
     * @return the formatted string
     */
    private String formatNumber(String numbers) {
        numbers = numbers.replaceAll("[^\\d]", "");
        numbers = numbers.substring(0, Math.min(10, numbers.length()));
        if (numbers.length() == 0) {
            return "";
        }
        if (numbers.length() < 7) {
            return numbers.replaceFirst("(\\d{3})(\\d+)", "$1-$2");
        }
        return numbers.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
    }
}