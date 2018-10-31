//@@Meowzz95
package com.t13g2.forum.storage.forum;

/**
 *
 */
public class ForumBookStorage implements IForumBookStorage {
    protected IStorage underlyingStorage;

    private AnnouncementStorage announcements;
    private CommentStorage comments;
    private ForumThreadStorage forumThreads;
    private ModuleStorage modules;
    private UserStorage users;


    public ForumBookStorage(IStorage underlyingStorage) {
        this.underlyingStorage = underlyingStorage;
        init();
    }

    /**
     *
     */
    private void init() {
        loadAnnouncement();
        loadComment();
        loadForumThread();
        loadUser();
        loadModule();
    }

    @Override
    public void commit() {
        saveAnnouncement();
        saveComment();
        saveForumThread();
        saveUser();
        saveModule();
    }

    @Override
    public void save(Class clazz) {

    }

    @Override
    public void load(Class clazz) {

    }

    @Override
    public void saveAnnouncement() {
        if (announcements.isDirty()) {
            underlyingStorage.write(announcements);
        }

    }

    @Override
    public void loadAnnouncement() {
        announcements = underlyingStorage.read(AnnouncementStorage.class);
        if (announcements == null) {
            announcements = new AnnouncementStorage();
            saveAnnouncement();
        }
    }

    @Override
    public void saveComment() {
        if (comments.isDirty()) {
            underlyingStorage.write(comments);
        }
    }

    @Override
    public void loadComment() {
        comments = underlyingStorage.read(CommentStorage.class);
        if (comments == null) {
            comments = new CommentStorage();
            saveComment();
        }
    }

    @Override
    public void saveForumThread() {
        if (forumThreads.isDirty()) {
            underlyingStorage.write(forumThreads);
        }

    }

    @Override
    public void loadForumThread() {
        forumThreads = underlyingStorage.read(ForumThreadStorage.class);
        if (forumThreads == null) {
            forumThreads = new ForumThreadStorage();
            saveForumThread();
        }
    }


    @Override
    public void saveUser() {
        if (users.isDirty()) {
            underlyingStorage.write(users);

        }
    }

    @Override
    public void loadUser() {
        users = underlyingStorage.read(UserStorage.class);
        if (users == null) {
            users = new UserStorage();
            saveUser();
        }
    }

    @Override
    public void saveModule() {
        if (modules.isDirty()) {
            underlyingStorage.write(modules);
        }
    }

    @Override
    public void loadModule() {
        modules = underlyingStorage.read(ModuleStorage.class);
        if (modules == null) {
            modules = new ModuleStorage();
            saveModule();
        }
    }

    @Override
    public AnnouncementStorage getAnnouncements() {
        return announcements;
    }

    @Override
    public ForumThreadStorage getForumThreads() {
        return forumThreads;
    }

    @Override
    public UserStorage getUsers() {
        return users;
    }

    @Override
    public ModuleStorage getModules() {
        return modules;
    }

    @Override
    public CommentStorage getComments() {
        return comments;
    }


}
