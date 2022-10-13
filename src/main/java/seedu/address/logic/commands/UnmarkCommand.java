package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Marks a task identified using it's displayed index from the task list as not done.
 */
public class UnmarkCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": marks the task identified by the index number used in the displayed task list as NOT done.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_AS_NOT_DONE_SUCCESS = "Marked as NOT done! Task: %1$s";

    private final Index targetIndex;

    public UnmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Task taskToMark = lastShownList.get(targetIndex.getZeroBased());
        model.unmarkTask(taskToMark);

        return new CommandResult(String.format(MESSAGE_MARK_AS_NOT_DONE_SUCCESS, taskToMark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnmarkCommand // instanceof handles nulls
                && targetIndex.equals(((UnmarkCommand) other).targetIndex)); // state check
    }
}