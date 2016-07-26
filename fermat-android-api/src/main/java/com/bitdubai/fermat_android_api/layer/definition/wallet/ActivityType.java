package com.bitdubai.fermat_android_api.layer.definition.wallet;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */
public enum ActivityType {

    ACTIVITY_TYPE_SUB_APP("SubAppActivity"),
    ACTIVITY_TYPE_WALLET("WalletActivity"),
    ACTIVITY_TYPE_FACTORY("WalletFactoryActivity"),
    ACTIVITY_TYPE_DESKTOP("desktopActivity");

    private final String code;

    ActivityType(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static ActivityType getByCode(String code) {

        switch (code) {
            case "SubAppActivity":
                return ActivityType.ACTIVITY_TYPE_SUB_APP;
            case "WalletActivity":
                return ActivityType.ACTIVITY_TYPE_WALLET;
            case "WalletFactoryActivity":
                return ACTIVITY_TYPE_FACTORY;
        }

        /**
         * Return by default.
         */
        return ActivityType.ACTIVITY_TYPE_SUB_APP;
    }
}
