package com.bitdubai.fermat_api.layer.modules;

import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

/**
 * Created by mati on 2016.04.21..
 */
public interface ModuleSettingsImpl<Z extends FermatSettings> {

    void persistSettings(String publicKey, final Z settings) throws CantPersistSettingsException;

    Z loadAndGetSettings(String publicKey) throws CantGetSettingsException, SettingsNotFoundException;
}
