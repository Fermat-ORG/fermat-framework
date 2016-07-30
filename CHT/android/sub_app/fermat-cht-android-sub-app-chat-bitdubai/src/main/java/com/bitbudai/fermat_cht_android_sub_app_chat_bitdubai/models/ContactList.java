package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models;

/**
 * ContactList Model
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 14/01/15.
 * @version 1.0
 */
public class ContactList {
    private long id;
    private String name;
    private Long userId;
    private String dateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
