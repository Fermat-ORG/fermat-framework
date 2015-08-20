package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Language;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Skin;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.interfaces.DeveloperIdentity;

import java.util.List;

/**
 * Created by Franklin Marcano 12/08/15.
 */
public interface WalletDescriptorFactoryProject extends DescriptorFactoryProject{
    public List<LanguageDescriptorFactoryProject> getLanguages();
    public List<SkinDescriptorFactoryProject> getSkins();

    public void setLanguages(List<LanguageDescriptorFactoryProject> languages);
    public void setSkins (List<SkinDescriptorFactoryProject> skins);

    public DeveloperIdentity getDeveloperIdentity();


    Skin getDefaultSkin();
    Language getDefaultLanguage();

}
