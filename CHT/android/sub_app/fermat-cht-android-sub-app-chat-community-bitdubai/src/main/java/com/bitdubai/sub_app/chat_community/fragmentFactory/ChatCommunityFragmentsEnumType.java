package com.bitdubai.sub_app.chat_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * ChatCommunityFragmentsEnumType
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public enum ChatCommunityFragmentsEnumType implements FermatFragmentsEnumType<ChatCommunityFragmentsEnumType> {

    CHT_SUB_APP_CHAT_COMMUNITY_FRAGMENT("CHTSACCF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTIONS_FRAGMENT("CHTSACCCF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_DETAIL_FRAGMENT("CHTSACCCDF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT("CHTSACCCNF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT("CHTSACCCOPF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_WORLD_FRAGMENT("CHTSACCCWF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_CONTACTS_LIST_FRAGMENT("CHTSACCCCLF"),
    CHT_SUB_APP_CHAT_COMMUNITY_CONNECTION_SETTINGS_FRAGMENT("CHTSACCCSF");

    private String key;

    ChatCommunityFragmentsEnumType(String key) {
        this.key = key;
    }

    ChatCommunityFragmentsEnumType() {
    }

    public static ChatCommunityFragmentsEnumType getValue(String name) {
        for (ChatCommunityFragmentsEnumType fragments : ChatCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return key;
    }
}
