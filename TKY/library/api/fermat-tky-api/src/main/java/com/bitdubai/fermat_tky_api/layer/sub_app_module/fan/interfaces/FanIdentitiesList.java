package com.bitdubai.fermat_tky_api.layer.sub_app_module.fan.interfaces;

import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/05/16.
 */
public interface FanIdentitiesList extends Serializable {

    List<Fan> getFanIdentitiesList();

}
