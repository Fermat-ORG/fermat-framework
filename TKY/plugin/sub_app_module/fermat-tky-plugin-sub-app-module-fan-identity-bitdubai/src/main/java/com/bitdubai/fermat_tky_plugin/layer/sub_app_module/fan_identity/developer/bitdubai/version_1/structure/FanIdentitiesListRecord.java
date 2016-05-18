package com.bitdubai.fermat_tky_plugin.layer.sub_app_module.fan_identity.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces.FanIdentitiesList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/05/16.
 */
public class FanIdentitiesListRecord implements FanIdentitiesList, Serializable {

    private final List<Fan> fanList;

    public FanIdentitiesListRecord(List<Fan> fanList) {
        this.fanList = fanList;
    }

    @Override
    public List<Fan> getFanIdentitiesList() {
        return fanList;
    }
}
