package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /**
     * Hides the UI.
     * This may trigger JavaFX to shutdown if the {@code implicitExit} attribute is set.
     *
     * @see javafx.application.Platform#setImplicitExit(boolean)
     */
    void hide();

}
