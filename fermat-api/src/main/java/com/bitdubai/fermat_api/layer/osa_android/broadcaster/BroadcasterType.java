package com.bitdubai.fermat_api.layer.osa_android.broadcaster;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by mati on 2016.02.02..
 */
public enum BroadcasterType implements FermatEnum{

    UPDATE_VIEW ("UV");

            ;

    private final String code;

    BroadcasterType(final String code) {
        this.code = code;
    }

    public static BroadcasterType getByCode(String code) {

        switch (code) {
            case "UV": return UPDATE_VIEW;
            default:      return UPDATE_VIEW;
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }


}
