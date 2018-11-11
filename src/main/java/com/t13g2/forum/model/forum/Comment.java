package com.t13g2.forum.model.forum;

/**
 * Represents comment under thread in ForumBook
 */
public class Comment extends BaseModel {
    public static final String MESSAGE_COMMENT_ID_CONSTRAINTS =
            "Comment ID should only contain an integer, and it should not be blank";
    public static final String COMMENT_ID_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private int threadId;
    private String content;
    public Comment(){}
    public Comment(int threadId, String content) {
        this.threadId = threadId;
        this.content = content;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
