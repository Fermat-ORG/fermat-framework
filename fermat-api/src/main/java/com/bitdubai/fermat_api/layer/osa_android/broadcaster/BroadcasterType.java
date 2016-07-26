package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by mati on 2016.02.02..
 */
public enum BroadcasterType implements FermatEnum {

    UPDATE_VIEW("UV"),
    NOTIFICATION_SERVICE("NS"),
    NOTIFICATION_PROGRESS_SERVICE("NPS");


    private final String code;

    BroadcasterType(final String code) {
        this.code = code;
    }

    public static BroadcasterType getByCode(String code) {

        switch (code) {
            case "UV":
                return UPDATE_VIEW;
            case "NS":
                return NOTIFICATION_SERVICE;
            case "NPS":
                return NOTIFICATION_PROGRESS_SERVICE;
            default:
                return UPDATE_VIEW;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }


}
