package seedu.address.ui;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.MainApp;

public class UiPartTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void constructor_nullFileUrl_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new UiPart<Object>((URL) null);
    }

    @Test
    public void constructor_missingFileUrl_throwsAssertionError() throws Exception {
        URL missingFileUrl = new URL(testFolder.getRoot().toURI().toURL(), "missingFile.fxml");
        thrown.expect(AssertionError.class);
        new UiPart<Object>(missingFileUrl);
    }

    @Test
    public void constructor_invalidFileUrl_throwsAssertionError() {
        URL invalidFileUrl = getTestFileUrl("invalidFile.fxml");
        thrown.expect(AssertionError.class);
        new UiPart<Object>(invalidFileUrl);
    }

    @Test
    public void constructor_nullFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new UiPart<Object>((String) null);
    }

    @Test
    public void constructor_missingFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new UiPart<Object>(getTestFilePath("missingFile.fxml"));
    }

    @Test
    public void constructor_invalidFileName_throwsAssertionError() {
        thrown.expect(AssertionError.class);
        new UiPart<Object>(getTestFilePath("invalidFile.fxml"));
    }

    private String getTestFilePath(String fileName) {
        return "UiPartTest/" + fileName;
    }

    private URL getTestFileUrl(String fileName) {
        String testFilePath = "/view/" + getTestFilePath(fileName);
        URL testFileUrl = MainApp.class.getResource(testFilePath);
        assertNotNull(testFilePath + " does not exist.", testFileUrl);
        return testFileUrl;
    }

}
