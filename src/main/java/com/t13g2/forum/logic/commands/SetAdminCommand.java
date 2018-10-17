package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_ADMIN_SET;
import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.UnitOfWork;

//@@xllx1
/**
 * Set a certain user as admin or set the admin as user
 */
public class SetAdminCommand extends Command {
    public static final String COMMAND_WORD = "setAdmin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set/Revert (1/0) a user as admin. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + PREFIX_ADMIN_SET + "SET/REVERT "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john"
        + PREFIX_ADMIN_SET + "true  ";

    public static final String MESSAGE_SUCCESS = "%1$s now is %s an admin";
    public static final String MESSAGE_INVALID_USER = "This user does not exist.";
    public static final String MESSAGE_DUPLICATE_SET = "This user has already been an admin.";
    public static final String MESSAGE_DUPLICATE_REVERT = "This user is not an admin.";

    private final String userNametoSetAdmin;
    private final boolean setAdmin;

    /**
     * Creates an AnnounceCommand to announce the specified {@code toAnnounce}.
     */
    public SetAdminCommand(String userName, boolean set) {
        requireNonNull(userName);
        userNametoSetAdmin = userName;
        setAdmin = set;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String isAdmin = "";
        User userToSet;
        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            userToSet = unitOfWork.getUserRepository().getUserByUsername(userNametoSetAdmin);
            if (setAdmin && userToSet.isAdmin()) {
                throw new CommandException(MESSAGE_DUPLICATE_SET);
            } else if (!setAdmin && !userToSet.isAdmin()) {
                throw new CommandException(MESSAGE_DUPLICATE_REVERT);
            } else {
                userToSet.setAdmin(!userToSet.isAdmin());
                unitOfWork.commit();
            }
            if (!setAdmin) {
                isAdmin = "not";
            }
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(MESSAGE_INVALID_USER);
        } catch (CommandException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNametoSetAdmin, isAdmin));
    }
}
