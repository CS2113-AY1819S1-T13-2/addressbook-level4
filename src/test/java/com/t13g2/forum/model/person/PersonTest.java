package com.t13g2.forum.model.person;

import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static com.t13g2.forum.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.t13g2.forum.testutil.PersonBuilder;
import com.t13g2.forum.testutil.TypicalPersons;

public class PersonTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.isSamePerson(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(TypicalPersons.ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(TypicalPersons.ALICE).build();
        assertTrue(TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalPersons.ALICE.equals(TypicalPersons.ALICE));

        // null -> returns false
        assertFalse(TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        assertFalse(TypicalPersons.ALICE.equals(5));

        // different person -> returns false
        assertFalse(TypicalPersons.ALICE.equals(TypicalPersons.BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(TypicalPersons.ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(TypicalPersons.ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(TypicalPersons.ALICE.equals(editedAlice));
    }
}
