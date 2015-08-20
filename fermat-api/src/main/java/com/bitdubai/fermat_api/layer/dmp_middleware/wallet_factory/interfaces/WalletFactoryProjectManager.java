package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.PublicKey;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;

import java.util.List;
import java.util.UUID;

public interface WalletFactoryProjectManager {

    // getters project
    WalletFactoryProject getWalletFactoryProjectById(UUID id) throws CantGetWalletFactoryProjectException;
    List<WalletFactoryProject> getWalletFactoryProjectsByIDeveloperPublicKey(PublicKey publicKey) throws CantGetWalletFactoryProjectException;
    WalletFactoryProject getWalletFactoryProjectsByIDeveloperPublicKey(PublicKey publicKey, UUID id) throws CantGetWalletFactoryProjectException;
    List<WalletFactoryProject> getWalletFactoryProjectByState (WalletFactoryProjectState walletFactoryProjectState) throws CantGetWalletFactoryProjectException;
    List<WalletFactoryProject> getAllWalletFactoryProjects() throws CantGetWalletFactoryProjectException;

    // Edition of wallet factory project
    WalletFactoryProject createEmptyWalletFactoryProject() throws CantCreateWalletFactoryProjectException;
    void saveWalletFactoryProjectChanges(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect;
    void deleteWalletProjectFactory(WalletFactoryProject walletFactoryProject) throws CantDeleteWalletFactoryProjectException;
}