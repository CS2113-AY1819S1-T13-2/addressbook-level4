package com.t13g2.forum.logic.commands;

import static java.util.Objects.requireNonNull;

import com.t13g2.forum.commons.core.EventsCenter;
import com.t13g2.forum.commons.events.model.UserLoginEvent;
import com.t13g2.forum.logic.CommandHistory;
import com.t13g2.forum.logic.commands.exceptions.CommandException;
import com.t13g2.forum.model.Context;
import com.t13g2.forum.model.Model;
/**
 *
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": logout from the forum book. ";


    public static final String MESSAGE_SUCCESS = "Good bye : %1$s";
    public static final String MESSAGE_FAIL = "No user logged in to Logout";
    private String userName;
    private Boolean canLogout;

    public LogoutCommand() {
        if (Context.getInstance().getCurrentUser() != null) {
            this.userName = Context.getInstance().getCurrentUser().getUsername();
            Context.getInstance().setCurrentUser(null);
            canLogout = true;
        } else {
            canLogout = false;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (canLogout) {
            EventsCenter.getInstance().post(new UserLoginEvent("", false));
            return new CommandResult(String.format(MESSAGE_SUCCESS, userName));
        } else {
            return new CommandResult(String.format(MESSAGE_FAIL, userName));
        }
    }
}
