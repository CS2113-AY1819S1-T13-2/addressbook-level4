//@@author Meowzz95
package com.t13g2.forum.storage.forum;

/**
 *
 */

public interface IForumBookStorage {

    void commit();

    void save(Class clazz);

    void load(Class clazz);

    void saveAnnouncement();

    void loadAnnouncement();

    void saveComment();

    void loadComment();

    void saveForumThread();

    void loadForumThread();

    void saveUser();

    void loadUser();

    void saveModule();

    void loadModule();

    boolean isFresh();

    AnnouncementStorage getAnnouncements();

    ForumThreadStorage getForumThreads();

    UserStorage getUsers();

    ModuleStorage getModules();

    CommentStorage getComments();


}
