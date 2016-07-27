package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by mati on 2016.06.04..
 */
public interface ComboAppType2FermatSession extends FermatSession {

    /**
     * Devuelve la session
     */
    <T extends FermatSession> T getFermatSession(String appPublicKey, Class<T> sessionType) throws InvalidParameterException;

}
