package com.bitdubai.fermat_android_api.layer.definition.wallet;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum ActivityType {

    ACTIVITY_TYPE_SUB_APP ("SubAppActivity"),
    ACTIVITY_TYPE_WALLET   ("WalletActivity");

    private final String code;

    ActivityType(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static ActivityType getByCode(String code) {

        switch (code) {
            case "SubAppActivity": return ActivityType.ACTIVITY_TYPE_SUB_APP;
            case "WalletActivity": return ActivityType.ACTIVITY_TYPE_WALLET;
        }

        /**
         * Return by default.
         */
        return ActivityType.ACTIVITY_TYPE_SUB_APP;
    }
}
