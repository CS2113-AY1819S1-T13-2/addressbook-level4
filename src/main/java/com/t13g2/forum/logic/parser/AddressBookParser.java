package com.t13g2.forum.logic.parser;

import static com.t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static com.t13g2.forum.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.t13g2.forum.logic.commands.AddUserCommand;
import com.t13g2.forum.logic.commands.AdminUpdatePasswordCommand;
import com.t13g2.forum.logic.commands.AnnounceCommand;
import com.t13g2.forum.logic.commands.BlockUserFromCreatingCommand;
import com.t13g2.forum.logic.commands.CheckAnnouncmentCommand;
import com.t13g2.forum.logic.commands.Command;
import com.t13g2.forum.logic.commands.CreateCommentCommand;
import com.t13g2.forum.logic.commands.CreateModuleCommand;
import com.t13g2.forum.logic.commands.CreateThreadCommand;
import com.t13g2.forum.logic.commands.DeleteCommentCommand;
import com.t13g2.forum.logic.commands.DeleteModuleCommand;
import com.t13g2.forum.logic.commands.DeleteThreadCommand;
import com.t13g2.forum.logic.commands.DeleteUserCommand;
import com.t13g2.forum.logic.commands.EditCommand;
import com.t13g2.forum.logic.commands.ExitCommand;
import com.t13g2.forum.logic.commands.HelpCommand;
import com.t13g2.forum.logic.commands.HistoryCommand;
import com.t13g2.forum.logic.commands.ListModuleCommand;
import com.t13g2.forum.logic.commands.LoginCommand;
import com.t13g2.forum.logic.commands.LogoutCommand;
import com.t13g2.forum.logic.commands.SelectModuleCommand;
import com.t13g2.forum.logic.commands.SelectThreadCommand;
import com.t13g2.forum.logic.commands.SetAdminCommand;
import com.t13g2.forum.logic.commands.UpdateCommentCommand;
import com.t13g2.forum.logic.commands.UpdateModuleCommand;
import com.t13g2.forum.logic.commands.UpdateThreadCommand;
import com.t13g2.forum.logic.commands.UserDeleteCommand;
import com.t13g2.forum.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        // case AddCommand.COMMAND_WORD:
        //     return new AddCommandParser().parse(arguments);
        //
        case EditCommand.COMMAND_WORD:
            return new UpdateUserCommandParser().parse(arguments);
        //
        // case SelectCommand.COMMAND_WORD:
        //     return new SelectCommandParser().parse(arguments);
        //
        // case DeleteCommand.COMMAND_WORD:
        //     return new DeleteCommandParser().parse(arguments);
        //
        // case ClearCommand.COMMAND_WORD:
        //     return new ClearCommand();
        //
        // case FindCommand.COMMAND_WORD:
        //     return new FindCommandParser().parse(arguments);
        //
        // case ListCommand.COMMAND_WORD:
        //     return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        // case UndoCommand.COMMAND_WORD:
        //     return new UndoCommand();
        //
        // case RedoCommand.COMMAND_WORD:
        //     return new RedoCommand();

        case AnnounceCommand.COMMAND_WORD:
            return new AnnounceCommandParser().parse(arguments);

        case CheckAnnouncmentCommand.COMMAND_WORD:
            return new CheckAnnouncmentCommand();

        case BlockUserFromCreatingCommand.COMMAND_WORD:
            return new BlockUserFromPostingCommandParser().parse(arguments);

        case SetAdminCommand.COMMAND_WORD:
            return new SetAdminCommandParser().parse(arguments);

        case AdminUpdatePasswordCommand.COMMAND_WORD:
            return new AdminUpdatePasswordCommandParser().parse(arguments);

        case CreateModuleCommand.COMMAND_WORD:
            return new CreateModuleCommandParser().parse(arguments);

        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);

        case UpdateModuleCommand.COMMAND_WORD:
            return new UpdateModuleCommandParser().parse(arguments);

        //@@author HansKoh
        case CreateThreadCommand.COMMAND_WORD:
            return new CreateThreadCommandParser().parse(arguments);

        case CreateCommentCommand.COMMAND_WORD:
            return new CreateCommentCommandParser().parse(arguments);

        case UpdateThreadCommand.COMMAND_WORD:
            return new UpdateThreadCommandParser().parse(arguments);

        case UpdateCommentCommand.COMMAND_WORD:
            return new UpdateCommentCommandParser().parse(arguments);

        case DeleteThreadCommand.COMMAND_WORD:
            return new DeleteThreadCommandParser().parse(arguments);

        case DeleteCommentCommand.COMMAND_WORD:
            return new DeleteCommentCommandParser().parse(arguments);

        case ListModuleCommand.COMMAND_WORD:
            return new ListModuleCommand();

        case SelectModuleCommand.COMMAND_WORD:
            return new SelectModuleCommandParser().parse(arguments);

        case SelectThreadCommand.COMMAND_WORD:
            return new SelectThreadCommandParser().parse(arguments);

        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case AddUserCommand.COMMAND_WORD:
            return new AddUserCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case UserDeleteCommand.COMMAND_WORD:
            return  new UserDeleteCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
