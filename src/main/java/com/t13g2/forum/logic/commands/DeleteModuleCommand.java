package com.t13g2.forum.logic.commands;

import static com.t13g2.forum.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static java.util.Objects.requireNonNull;

import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
import com.t13g2.forum.model.UnitOfWork;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

//@@author xllx1

/**
 * Deletes a module to the forum book by admin
 */
public class DeleteModuleCommand extends Command {
    public static final String COMMAND_WORD = "deleteModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a module. "
        + "\nParameters: "
        + PREFIX_MODULE_CODE + "MODULE CODE "
        + "\nExample: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2113";

    public static final String MESSAGE_SUCCESS = "%1$s is now deleted.";
    public static final String MESSAGE_INVALID_MODULE = "There is no module matching %1$s.";

    private final String moduleCodeToDelete;

    /**
     * Creates an DeleteModuleCommand to create the specified {@code module}.
     */
    public DeleteModuleCommand(String moduleCode) {
        requireNonNull(moduleCode);
        moduleCodeToDelete = moduleCode;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        // if user has not login or is not admin, then throw exception
        // if user has not login or is not admin, then throw exception
        if (!Context.getInstance().isLoggedIn()) {
            throw new CommandException(User.MESSAGE_NOT_LOGIN);
        } else if (!Context.getInstance().isCurrentUserAdmin()) {
            throw new CommandException(User.MESSAGE_NOT_ADMIN);
        }

        try (UnitOfWork unitOfWork = new UnitOfWork()) {
            Module moduleToDelete = unitOfWork.getModuleRepository().getModuleByCode(moduleCodeToDelete);
            unitOfWork.getModuleRepository().removeModule(moduleToDelete);
            unitOfWork.commit();
        } catch (EntityDoesNotExistException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_MODULE,
                moduleCodeToDelete));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Context.getInstance().setCurrentModuleId(-1);
        return new CommandResult(String.format(MESSAGE_SUCCESS, moduleCodeToDelete));
    }
}
