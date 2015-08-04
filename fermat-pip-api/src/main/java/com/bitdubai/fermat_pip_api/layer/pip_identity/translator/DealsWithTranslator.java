package com.bitdubai.fermat_pip_api.layer.pip_identity.translator;


import com.bitdubai.fermat_pip_api.layer.pip_identity.translator.interfaces.TranslatorManager;

/**
 * The Class <code>com.bitdubai.fermat_pip_api.layer.pip_identity.translator.DealsWithTranslator</code>
 * indicates that the plugin needs the functionality of a TranslatorManager
 * <p/>
 *
 * Created by natalia on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */

public interface DealsWithTranslator {

    void setTranslatorIdentityManager(TranslatorManager translatorIdentityManager);
}
