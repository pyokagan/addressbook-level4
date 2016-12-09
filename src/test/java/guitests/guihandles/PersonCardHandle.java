package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TestUtil;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public List<String> getTags() {
        Region tagsContainer = getTagsContainer();
        return getTagsAsList(tagsContainer);
    }

    private List<String> getTagsAsList(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream()
                .map(node -> TestUtil.getTagAsString(((Labeled) node).getText()))
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return (Region) guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSamePerson(ReadOnlyPerson person) {
        return getFullName().equals(person.getName().fullName) && getPhone().equals(person.getPhone().value)
                && getEmail().equals(person.getEmail().value)
                && getAddress().equals(person.getAddress().value)
                && getTags().equals(person.getTags().asObservableList()
                        .stream()
                        .map(tag -> tag.toString())
                        .collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPhone().equals(handle.getPhone())
                    && getEmail().equals(handle.getEmail())
                    && getAddress().equals(handle.getAddress())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
