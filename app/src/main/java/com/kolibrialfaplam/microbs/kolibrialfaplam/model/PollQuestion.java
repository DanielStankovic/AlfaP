package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class PollQuestion {

    @SerializedName("WorkOrderQuestionID")
    private int workOrderQuestionID;
    @SerializedName("WorkOrderQuestionTypeID")
    private int workOrderQuestionTypeID;
    @SerializedName("WorkOrderPollID")
    private int workOrderPollID;
    @SerializedName("QuestionText")
    private String questionText;
    @SerializedName("PollQuestionAnswer")
    private String pollQuestionAnswer;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    private QuestionType pollQuestionType;

    public enum QuestionType{
        YESNO,
        FREEANSWER
    }

    public PollQuestion(int workOrderQuestionID, int workOrderQuestionTypeID, int workOrderPollID, String questionText, String modifiedDate) {
        this.workOrderQuestionID = workOrderQuestionID;
        this.workOrderQuestionTypeID = workOrderQuestionTypeID;
        this.workOrderPollID = workOrderPollID;
        this.questionText = questionText;
        this.modifiedDate = modifiedDate;
    }

    public PollQuestion(int workOrderQuestionID, QuestionType pollQuestionType, String questionText) {
        this.workOrderQuestionID = workOrderQuestionID;
        this.pollQuestionType = pollQuestionType;
        this.questionText = questionText;
    }

    public PollQuestion() {
    }


    public int getWorkOrderQuestionID() {
        return workOrderQuestionID;
    }

    public void setWorkOrderQuestionID(int workOrderQuestionID) {
        this.workOrderQuestionID = workOrderQuestionID;
    }

    public int getWorkOrderQuestionTypeID() {
        return workOrderQuestionTypeID;
    }

    public void setWorkOrderQuestionTypeID(int workOrderQuestionTypeID) {
        this.workOrderQuestionTypeID = workOrderQuestionTypeID;
    }

    public int getWorkOrderPollID() {
        return workOrderPollID;
    }

    public void setWorkOrderPollID(int workOrderPollID) {
        this.workOrderPollID = workOrderPollID;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getPollQuestionAnswer() {
        return pollQuestionAnswer;
    }

    public void setPollQuestionAnswer(String pollQuestionAnswer) {
        this.pollQuestionAnswer = pollQuestionAnswer;
    }

    public QuestionType getPollQuestionType() {
        return pollQuestionType;
    }

    public void setPollQuestionType(QuestionType pollQuestionType) {
        this.pollQuestionType = pollQuestionType;
    }
}
