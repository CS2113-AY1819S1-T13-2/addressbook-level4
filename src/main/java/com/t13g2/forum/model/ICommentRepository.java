//@@Meowzz95
package com.t13g2.forum.model;

import java.util.List;

import com.t13g2.forum.model.forum.Comment;
import com.t13g2.forum.model.forum.ForumThread;
import com.t13g2.forum.storage.forum.EntityDoesNotExistException;

/**
 *
 */
public interface ICommentRepository {
    int addComment(Comment comment);

    void deleteComment(Comment comment);

    void deleteComment(int commentId);

    Comment getComment(int commentId) throws EntityDoesNotExistException;

    List<Comment> getCommentsByThread(int threadId);

    List<Comment> getCommentsByThread(ForumThread forumThread);
}
