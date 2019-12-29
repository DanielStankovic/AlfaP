package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class NoSignatureComment {

    @SerializedName("CommentID")
    private int commentID;
    @SerializedName("CommentText")
    private String commentText;
    @SerializedName("ModifiedDate")
    private String modifiedDate;
    @SerializedName("IsActive")
    private boolean isActive;

    public NoSignatureComment(int commentID, String commentText, String modifiedDate, boolean isActive) {
        this.commentID = commentID;
        this.commentText = commentText;
        this.modifiedDate = modifiedDate;
        this.isActive = isActive;
    }

    public NoSignatureComment() {
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
