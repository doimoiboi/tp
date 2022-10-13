package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//    @Test
//    public void execute_validIndexUnfilteredList_success() {
//        Task taskToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_AS_DONE_SUCCESS, taskToMark);
//
//        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.markTask(taskToMark);
//
//        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() {
//        showPersonAtIndex(model, INDEX_FIRST_PERSON);
//
//        Task taskToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_AS_DONE_SUCCESS, taskToMark);
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.markTask(taskToMark);
//        showNoPerson(expectedModel);
//
//        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkCommand markFirstCommand = new MarkCommand(INDEX_FIRST_PERSON);
        MarkCommand markSecondCommand = new MarkCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(INDEX_FIRST_PERSON);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}