package com.bitdubai.fermat_api.layer.dmp_identity.translator.interfaces;

import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantCreateNewTranslatorException;
import com.bitdubai.fermat_api.layer.dmp_identity.translator.exceptions.CantGetUserTranslatorIdentitiesException;

import java.util.List;

/**
 * The Interface <code>TranslatorManager</code>
 * indicates the functionality of a TranslatorManager
 * <p/>
 *
 * Created by natalia on 03/08/15.
 * @version 1.0
 * @since Java JDK 1.7
 */

public interface TranslatorManager {

    /**
     * This method will give us a list of all the translators associated to the actual Device User logged in
     *
     * @return the list of Translator associated to the current logged in Device User.
     * @throws CantGetUserTranslatorIdentitiesException
     */
    public List<Translator> getTranslatorsFromCurrentDeviceUser() throws CantGetUserTranslatorIdentitiesException;

    /**
     * This method creates a new translator Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias the alias that the user choose as translator identity
     * @return the new translator just created
     * @throws CantCreateNewTranslatorException
     */
    public Translator createNewTranslator(String alias) throws CantCreateNewTranslatorException;
}
