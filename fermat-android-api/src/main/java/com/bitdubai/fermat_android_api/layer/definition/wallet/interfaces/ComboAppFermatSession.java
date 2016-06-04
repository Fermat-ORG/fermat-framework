package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

/**
 * Created by Matias Furszyfer on 2016.06.03..
 */
public interface ComboAppFermatSession<A extends FermatApp> extends FermatSession{

    /**
     * Devuelve el module manager
     */
    <T> T getModuleManager(Class<T> classType) throws InvalidParameterException;

}
