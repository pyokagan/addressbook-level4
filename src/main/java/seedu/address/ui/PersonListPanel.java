package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    public PersonListPanel(ObservableList<Person> personList, ObservableValue<Index> selectedIndex,
            Consumer<Index> onSelectedIndexChange) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.intValue() < 0) {
                onSelectedIndexChange.accept(null);
                return;
            }

            logger.fine("Selection in person list panel changed to : '" + newValue + "'");
            onSelectedIndexChange.accept(Index.fromZeroBased(newValue.intValue()));
        });
        selectedIndex.addListener((observable, oldValue, newValue) -> {
            int expectedIndex = newValue == null ? -1 : newValue.getZeroBased();
            if (personListView.getSelectionModel().getSelectedIndex() == expectedIndex) {
                return;
            }

            personListView.getSelectionModel().clearSelection();
            if (newValue != null) {
                personListView.scrollTo(newValue.getZeroBased());
                personListView.getSelectionModel().select(newValue.getZeroBased());
            }
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
