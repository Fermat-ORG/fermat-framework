package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models;

/**
 * ContactList Model
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 14/01/15.
 * @version 1.0
 */
public class ChatsList {
    private long id;
    private long id2;
    private String name;
    private String lastmessage;
    private Long userId;
    private Long userId2;
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId2() {
        return id2;
    }

    public void setId2(long id2) {
        this.id2 = id2;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastmessage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastMessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
}
