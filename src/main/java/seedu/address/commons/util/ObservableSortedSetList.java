package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.ObservableListBase;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.WeakSetChangeListener;

/**
 * ObservableList from ObservableSet
 */
public class ObservableSortedSetList<E> extends ObservableListBase<E> {

    private final ObservableSet<? extends E> source;
    private final Comparator<? super E> comparator;
    private final SetChangeListener<E> sourceListener;
    private final ArrayList<E> sortedList;

    public ObservableSortedSetList(ObservableSet<? extends E> source, Comparator<? super E> comparator) {
        requireNonNull(source);
        this.source = source;
        this.comparator = comparator;
        sortedList = new ArrayList<>(source);
        sortedList.sort(this.comparator);
        sourceListener = this::sourceChanged;
        this.source.addListener(new WeakSetChangeListener<>(sourceListener));
    }

    public final ObservableSet<? extends E> getSource() {
        return source;
    }

    @Override
    public E get(int index) {
        return sortedList.get(index);
    }

    @Override
    public int size() {
        return sortedList.size();
    }

    /**
     */
    private void insertElement(E element) {
        assert element != null;
        int index = 0;
        while (index < sortedList.size() && comparator.compare(sortedList.get(index), element) <= 0) {
            index += 1;
        }
        sortedList.add(index, element);
        nextAdd(index, index + 1);
    }

    /**
     */
    private void removeElement(E element) {
        assert element != null;
        int index = Collections.binarySearch(sortedList, element, comparator);
        assert index >= 0 : "element must be in the list";
        E removedElement = sortedList.get(index);
        sortedList.remove(index);
        nextRemove(index, removedElement);
    }

    /**
     */
    private void sourceChanged(SetChangeListener.Change<? extends E> change) {
        requireNonNull(change);
        if (change.wasAdded()) {
            beginChange();
            insertElement(change.getElementAdded());
            endChange();
        } else if (change.wasRemoved()) {
            beginChange();
            removeElement(change.getElementRemoved());
            endChange();
        }
    }

}
