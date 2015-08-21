/*
 * @#WalletDescriptorFactoryMiddlewareProject.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletDescriptorFactoryMiddlewareProject</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 20/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletDescriptorFactoryMiddlewareProject extends DescriptorFactoryMiddlewareProject implements WalletDescriptorFactoryProject {


    public WalletDescriptorFactoryMiddlewareProject() {
    }

    public WalletDescriptorFactoryMiddlewareProject(UUID id, String name, String developerPublicKey, Wallets type, String path, FactoryProjectState state, String description, String publisherIdentityKey, DescriptorFactoryProjectType descriptorFactoryProjectType) {
        super(id, name, developerPublicKey, type, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);
    }

    public WalletDescriptorFactoryMiddlewareProject(String name, String developerPublicKey, Wallets type, String path, FactoryProjectState state, String description, String publisherIdentityKey, DescriptorFactoryProjectType descriptorFactoryProjectType) {
        super(name, developerPublicKey, type, path, state, description, publisherIdentityKey, descriptorFactoryProjectType);
    }

    @Override
    public List<LanguageDescriptorFactoryProject> getLanguages() {
        return null;
    }

    @Override
    public List<SkinDescriptorFactoryProject> getSkins() {
        return null;
    }

    @Override
    public void setLanguages(List<LanguageDescriptorFactoryProject> languages) {

    }

    @Override
    public void setSkins(List<SkinDescriptorFactoryProject> skins) {

    }

    @Override
    public DeveloperIdentity getDeveloperIdentity() {
        return null;
    }

    @Override
    public SkinDescriptorFactoryProject getDefaultSkin() {
        return null;
    }

    @Override
    public LanguageDescriptorFactoryProject getDefaultLanguage() {
        return null;
    }
}
