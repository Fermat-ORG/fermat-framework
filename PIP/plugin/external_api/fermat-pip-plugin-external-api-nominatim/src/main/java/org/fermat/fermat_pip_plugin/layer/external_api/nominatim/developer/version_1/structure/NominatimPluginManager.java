package org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.external_api.interfaces.NominatimManager;

import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.CountryRecord;
import org.fermat.fermat_pip_plugin.layer.external_api.nominatim.developer.version_1.NominatimPluginRoot;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 03/06/16.
 */
public class NominatimPluginManager implements NominatimManager {

    /**
     * Represents the plugin root class
     */
    NominatimPluginRoot nominatimPluginRoot;

    /**
     * Constructor with parameters
     * @param nominatimPluginRoot
     */
    public NominatimPluginManager(NominatimPluginRoot nominatimPluginRoot){
        this.nominatimPluginRoot = nominatimPluginRoot;
    }

    /**
     * This method returns a list of Countries available in external api
     * @return
     */
    @Override
    public List<CountryRecord> getCountryList() {
        return null;
    }
}
