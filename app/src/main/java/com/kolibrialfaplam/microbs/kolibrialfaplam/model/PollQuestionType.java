package com.kolibrialfaplam.microbs.kolibrialfaplam.model;

import com.google.gson.annotations.SerializedName;

public class PollQuestionType {

    @SerializedName("WorkOrderQuestionTypeID")
    private int workOrderQuestionTypeID;
    @SerializedName("TypeName")
    private String typeName;
    @SerializedName("TypeCode")
    private String typeCode;
    @SerializedName("ModifiedDate")
    private String modifiedDate;

    public PollQuestionType(int workOrderQuestionTypeID, String typeName, String typeCode, String modifiedDate) {
        this.workOrderQuestionTypeID = workOrderQuestionTypeID;
        this.typeName = typeName;
        this.typeCode = typeCode;
        this.modifiedDate = modifiedDate;
    }

    public int getWorkOrderQuestionTypeID() {
        return workOrderQuestionTypeID;
    }

    public void setWorkOrderQuestionTypeID(int workOrderQuestionTypeID) {
        this.workOrderQuestionTypeID = workOrderQuestionTypeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
