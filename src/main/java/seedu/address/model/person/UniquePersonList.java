package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A person is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating of
 * persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniquePersonList implements Iterable<Person> {

    private final ObservableSet<Person> internalSet = FXCollections.observableSet();
    private final HashMap<PersonKey, Person> samePersonMap = new HashMap<>();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return samePersonMap.containsKey(new PersonKey(toCheck));
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        samePersonMap.put(new PersonKey(toAdd), toAdd);
        internalSet.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        if (!contains(target)) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
            throw new DuplicatePersonException();
        }

        Person actualTarget = samePersonMap.get(new PersonKey(target));
        samePersonMap.remove(new PersonKey(target));
        samePersonMap.put(new PersonKey(editedPerson), editedPerson);
        internalSet.remove(actualTarget);
        internalSet.add(editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new PersonNotFoundException();
        }
        Person actualToRemove = samePersonMap.get(new PersonKey(toRemove));
        samePersonMap.remove(new PersonKey(toRemove));
        internalSet.remove(actualToRemove);
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        samePersonMap.clear();
        samePersonMap.putAll(replacement.samePersonMap);
        internalSet.clear();
        internalSet.addAll(replacement.internalSet);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        requireAllNonNull(persons);
        samePersonMap.clear();
        internalSet.clear();
        persons.forEach(this::add);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableSet}.
     */
    public ObservableSet<Person> asUnmodifiableObservableSet() {
        return FXCollections.unmodifiableObservableSet(internalSet);
    }

    @Override
    public Iterator<Person> iterator() {
        return internalSet.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalSet.equals(((UniquePersonList) other).internalSet));
    }

    @Override
    public int hashCode() {
        return internalSet.hashCode();
    }

    /**
     */
    private static class PersonKey {
        private final Person person;

        private PersonKey(Person person) {
            this.person = person;
        }

        @Override
        public int hashCode() {
            return person.samePersonHashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof PersonKey)) {
                return false;
            }

            return person.isSamePerson(((PersonKey) other).person);
        }
    }
}
