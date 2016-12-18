package guitests;

import org.junit.Test;

public class StatusBarFooterTest extends AddressBookGuiTest {

    @Test
    public void statusBar_commandSucceeds_statusUpdated() {
        commandBox.runCommand(td.hoon.getAddCommand());
        assertSyncStatusUpdated(true);
    }

    @Test
    public void commandBox_commandFails_textStays() {
        commandBox.runCommand("invalid command");
        assertSyncStatusUpdated(false);
    }

}
