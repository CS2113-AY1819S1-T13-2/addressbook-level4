package com.t13g2.forum.testutil;

import com.t13g2.forum.model.forum.User;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {
    public static final User JANEDOE = new UserBuilder().withName("janeDoe").withPassword("123").withIsAdmin(false)
        .withIsBlock(false).withEmail("janeDoe@test.com").withPhone("87654321").build();
    public static final User JIM = new UserBuilder().withName("jim").withPassword("123").withIsAdmin(false)
        .withIsBlock(false).withEmail("jim@test.com").withPhone("87654321").build();
    public static final User JONE = new UserBuilder().withName("jone").withPassword("123").withIsAdmin(false)
        .withIsBlock(false).withEmail("jone@test.com").withPhone("87654321").build();
    public static final User JOSH = new UserBuilder().withName("josh").withPassword("123").withIsAdmin(false)
        .withIsBlock(false).withEmail("josh@test.com").withPhone("87654321").build();
    public static final User JOSHUA = new UserBuilder().withName("joshua").withPassword("123").withIsAdmin(false)
        .withIsBlock(false).withEmail("joshua@test.com").withPhone("87654321").build();


    //prevent instantiation.
    private TypicalUsers() {}
}
