//@@author Meowzz95
package com.t13g2.forum.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sun.istack.NotNull;
import com.t13g2.forum.commons.util.Extensions;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.model.forum.Module;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;
import com.t13g2.forum.storage.forum.IForumBookStorage;

/**
 *Provides APIs to manipulate {@link ForumThread}
 */
public class ForumThreadRepository extends BaseRepository implements IForumThreadRepository {
    /**
     * Creates a new BaseRepository instance.
     *
     * @param forumBookStorage of type IForumBookStorage
     */
    public ForumThreadRepository(IForumBookStorage forumBookStorage) {
        super(forumBookStorage);
    }


    /**
     * Adds an {@link ForumThread} into database
     *
     * @param forumThread
     * @return object Id added
     */
    @Override
    public int addThread(@NotNull ForumThread forumThread) {
        Objects.requireNonNull(forumThread, "forumThread can't be null");
        forumBookStorage.getForumThreads().getList().add(forumThread);
        forumBookStorage.getForumThreads().setDirty();
        return forumThread.getId();
    }

    /**
     * Update an {@link ForumThread}
     * @param forumThread
     */
    @Override
    public void updateThread(@NotNull ForumThread forumThread) {
        Objects.requireNonNull(forumThread, "forumThread can't be null");
        List<ForumThread> forumThreads = forumBookStorage.getForumThreads().getList();
        Extensions.updateObjectInList(forumThreads, forumThread);
        forumBookStorage.getForumThreads().setDirty();
    }

    /**
     * Gets a thread by its Id
     * @param forumThreadId
     * @return the {@link ForumThread} queried
     * @throws EntityDoesNotExistException
     */
    @Override
    public ForumThread getThread(int forumThreadId) throws EntityDoesNotExistException {
        return this.getById(forumBookStorage.getForumThreads().getList(), forumThreadId);
    }

    /**
     * Gets all threads under a certain module
     * @param moduleId
     * @return a list of {@link ForumThread}
     */
    @Override
    public List<ForumThread> getThreadsByModule(int moduleId) {
        return forumBookStorage.getForumThreads().getList().stream()
            .filter(aForumThread -> aForumThread.getModuleId() == moduleId).collect(Collectors.toList());
    }

    /**
     * Gets all threads under a certain module
     * @param module
     * @return a list of {@link ForumThread}
     */
    @Override
    public List<ForumThread> getThreadsByModule(@NotNull Module module) {
        Objects.requireNonNull(module, "module can't be null");
        return this.getThreadsByModule(module.getId());
    }

    /**
     * Deletes an {@link ForumThread}
     * @param forumThreadId
     */
    @Override
    public void deleteThread(int forumThreadId) {
        List<ForumThread> pointer = forumBookStorage.getForumThreads().getList();
        Optional<ForumThread> toBeDeleted = pointer.stream().filter(forumThread -> forumThread.getId()
            == forumThreadId).findFirst();
        toBeDeleted.ifPresent(pointer::remove);
        forumBookStorage.getForumThreads().setDirty();
    }

    /**
     * Deletes an {@link ForumThread}
     * @param forumThread
     */
    @Override
    public void deleteThread(ForumThread forumThread) {
        this.deleteThread(forumThread.getId());
    }
}
