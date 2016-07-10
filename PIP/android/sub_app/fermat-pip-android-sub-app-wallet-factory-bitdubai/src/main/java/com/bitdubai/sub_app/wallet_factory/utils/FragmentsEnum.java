package com.bitdubai.sub_app.wallet_factory.utils;

/**
 * Created by Nicolas on 06/05/2015.
 */
public enum FragmentsEnum {
    PROFILE("Profile"),
    DESKTOP("Desktop"),
    CONTACTS("Contacts"),
    COMMUNITY("Community");

    String title;

    FragmentsEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
