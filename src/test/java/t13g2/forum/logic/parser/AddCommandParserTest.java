package t13g2.forum.logic.parser;

import static t13g2.forum.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import t13g2.forum.logic.commands.AddCommand;
import t13g2.forum.model.person.Address;
import t13g2.forum.model.person.Email;
import t13g2.forum.model.person.Name;
import t13g2.forum.model.person.Person;
import t13g2.forum.model.person.Phone;
import t13g2.forum.model.tag.Tag;
import t13g2.forum.testutil.PersonBuilder;
import t13g2.forum.logic.commands.CommandTestUtil;
import t13g2.forum.testutil.TypicalPersons;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(TypicalPersons.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND).build();

        // whitespace only preamble
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.PREAMBLE_WHITESPACE + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_AMY
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(TypicalPersons.BOB).withTags(CommandTestUtil.VALID_TAG_FRIEND, CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(TypicalPersons.AMY).withTags().build();
        CommandParserTestUtil.assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.VALID_PHONE_BOB + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC
                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        CommandParserTestUtil.assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
                + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
