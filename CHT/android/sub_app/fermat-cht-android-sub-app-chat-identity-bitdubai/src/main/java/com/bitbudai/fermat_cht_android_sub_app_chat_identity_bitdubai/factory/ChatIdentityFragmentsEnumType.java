package com.bitbudai.fermat_cht_android_sub_app_chat_identity_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * FERMAT-ORG
 * Developed by Lozadaa on 05/04/16.
 */
public enum ChatIdentityFragmentsEnumType implements FermatFragmentsEnumType<ChatIdentityFragmentsEnumType> {


    CHT_CHAT_CREATE_IDENTITY_FRAGMENT("CHTCIF"),
    CHT_CHAT_GEOLOCATION_IDENTITY_FRAGMENT("CHTGIF");





    private String key;

    ChatIdentityFragmentsEnumType(String key) {
        this.key = key;
    }

    public static ChatIdentityFragmentsEnumType getValue(String name) {
        for (ChatIdentityFragmentsEnumType fragments : ChatIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

}
