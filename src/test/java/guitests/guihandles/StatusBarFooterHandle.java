package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import org.controlsfx.control.StatusBar;
import seedu.address.TestApp;

/**
 * A handle for the status bar at the footer of the application.
 */
public class StatusBarFooterHandle extends GuiHandle {

    public static final String SYNC_STATUS_ID = "#syncStatus";
    public static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";

    private String lastSyncStatusText;

    public StatusBarFooterHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public String getLastSyncStatus() {
        return lastSyncStatusText;
    }

    public void setLastSyncStatus(String status) {
        this.lastSyncStatusText = status;
    }

    public String getSyncStatus() {
        return ((StatusBar) getNode(SYNC_STATUS_ID)).getText();
    }

    public String getSaveLocation() {
        return ((StatusBar) getNode(SAVE_LOCATION_STATUS_ID)).getText();
    }
}
