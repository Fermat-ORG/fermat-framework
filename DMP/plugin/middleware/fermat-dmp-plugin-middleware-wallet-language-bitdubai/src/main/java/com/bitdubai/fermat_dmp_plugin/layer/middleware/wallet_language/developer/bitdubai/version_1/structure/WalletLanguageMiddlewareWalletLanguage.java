package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.enums.LanguageState;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_language.interfaces.WalletLanguage;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletLanguageMiddlewareWalletLanguage</code>
 * implementation of WalletLanguageMiddlewareWalletLanguage.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletLanguageMiddlewareWalletLanguage implements WalletLanguage {

    UUID id;

    UUID languageId;

    String name;

    Languages type;

    LanguageState state;

    String translatorPublicKey;

    Version version;


    public WalletLanguageMiddlewareWalletLanguage(UUID id, UUID languageId, String name, Languages type, LanguageState state, String translatorPublicKey, Version version) {
        this.id = id;
        this.languageId = languageId;
        this.name = name;
        this.type = type;
        this.state = state;
        this.translatorPublicKey = translatorPublicKey;
        this.version = version;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getLanguageId() {
        return languageId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Languages getType() {
        return type;
    }

    @Override
    public LanguageState getState() {
        return state;
    }

    @Override
    public String getTranslatorPublicKey() {
        return translatorPublicKey;
    }

    @Override
    public Version getVersion() {
        return version;
    }

}
