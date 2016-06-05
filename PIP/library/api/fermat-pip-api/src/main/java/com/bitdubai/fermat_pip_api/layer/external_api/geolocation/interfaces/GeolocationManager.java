package com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;

import java.util.HashMap;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public interface GeolocationManager<T extends Country> extends FermatManager {

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    HashMap<String, T> getCountryList()
            throws CantConnectWithExternalAPIException, CantCreateBackupFileException, CantCreateCountriesListException;

}
