package seedu.address.ui;

import java.util.Objects;

/**
 * Represents a command result or error to be displayed in the UI.
 * Guarantees: Immutable.
 */
public class UiCommandResult {
    private final String feedbackToUser;

    public UiCommandResult(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UiCommandResult)) {
            return false;
        }

        UiCommandResult otherUiCommandResult = (UiCommandResult) other;
        return feedbackToUser.equals(otherUiCommandResult.feedbackToUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser);
    }
}
