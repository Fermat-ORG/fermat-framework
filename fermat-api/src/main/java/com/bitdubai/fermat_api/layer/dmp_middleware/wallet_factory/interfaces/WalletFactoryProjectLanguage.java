package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;

import java.util.Map;
import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectLanguage {

    // TODO TRANSLATOR?

    UUID getId();

    byte[] getFile() throws CantGetWalletFactoryProjectLanguageException;

    String getName();

    Languages getType();

    Version getVersion();

    Map<String, String> getStrings();

    WalletFactoryProjectProposal getWalletFactoryProjectProposal();

}
