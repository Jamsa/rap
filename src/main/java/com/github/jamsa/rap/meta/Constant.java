package com.github.jamsa.rap.meta;

public class Constant {
    public static final  String FIELD_TYPE_STRING="STRING";
    public static final  String FIELD_TYPE_FLOAT="FLOAT";
    public static final  String FIELD_TYPE_DOUBLE="DOUBLE";
    public static final  String FIELD_TYPE_INT="INT";
    public static final  String FIELD_TYPE_LONG="LONG";
    public static final  String FIELD_TYPE_CLOB="CLOB";
    public static final  String FIELD_TYPE_DATE="DATE";
    public static final  String FIELD_TYPE_DATETIME="DATETIME";

    /*public static final  String MODEL_VIEW_OBJECT_TYPE_MAIN="MAIN";
    public static final  String MODEL_VIEW_OBJECT_TYPE_ADDITIONAL="ADDITIONAL";
    public static final  String MODEL_VIEW_OBJECT_TYPE_SUBTABLE="SUBTABLE";*/

    public static final String RECORD_ADD_ROWS_KEY="addRows";
    public static final String RECORD_UPDATE_ROWS_KEY="updateRows";
    public static final String RECORD_DELETE_ROWS_KEY="deleteRows";

    /*public static final int viewObjectTypeIndex(String type){
        int result = -1;
        switch (type){
            case MODEL_VIEW_OBJECT_TYPE_ADDITIONAL:
                result = 3;
                break;
            case MODEL_VIEW_OBJECT_TYPE_SUBTABLE:
                result = 2;
                break;
            case MODEL_VIEW_OBJECT_TYPE_MAIN:
                result  = 1;
                break;
            default:
                result = -1;
                break;

        }
    }*/
}
