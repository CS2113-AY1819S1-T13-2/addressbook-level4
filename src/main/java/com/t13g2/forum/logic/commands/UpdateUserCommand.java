package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_USER_NAME;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 * Deletes a specific user by admin
 */
public class UpdateUserCommand extends Command {
    public static final String COMMAND_WORD = "updateUser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Update a certain user's password, email and/or phone. "
        + "Parameters: "
        + PREFIX_USER_NAME + "USER NAME "
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_USER_NAME + "john ";

    public static final String MESSAGE_SUCCESS = "%1$s successfully updated.";
    public static final String MESSAGE_INVALID_USER = "No user named %1$s found. Please verify and try again!";

    private final String userNameToUpdate = "";
    private final Person userToUpdate;

    /**
     * Creates an DeleteUserCommand to delete the specified {@code userName}.
     */
    public UpdateUserCommand(Person personToUpdate) {
        requireNonNull(personToUpdate);
        this.userToUpdate = personToUpdate;
    }
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        // if user has not login or is not admin, then throw exception
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        }

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            User userToUpdate = unitOfWork.getUserRepository().getUserByUsername(userNameToUpdate);
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_USER, userNameToUpdate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //model.updatePerson(userNameToUpdate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, userNameToUpdate));
    }
}
