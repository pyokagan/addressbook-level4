package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final SimpleObjectProperty<UiCommandResult> uiCommandResult = new SimpleObjectProperty<>();

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        uiCommandResult.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                resultDisplay.setText("");
                return;
            }
            resultDisplay.setText(newValue.getFeedbackToUser());
        });
    }

    public Property<UiCommandResult> uiCommandResultProperty() {
        return uiCommandResult;
    }

}
