package com.bitdubai.fermat_pip_api.layer.external_api.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public interface NominatimManager<T extends Country> extends FermatManager {

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    List<T> getCountryList();

}
