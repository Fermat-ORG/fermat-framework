package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.List;
import java.util.UUID;

import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.XmlTransient;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal</code>
 * implementation of WalletFactoryProjectProposal.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "proposal" )
public class WalletFactoryMiddlewareProjectProposal implements DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectProposal {

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private UUID pluginId;


    String alias;

    WalletFactoryProject walletFactoryProject;

    FactoryProjectState state;

    public WalletFactoryMiddlewareProjectProposal() {
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state) {
        this.alias = alias;
        this.state = state;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public WalletFactoryProject getProject() {
        return walletFactoryProject;
    }

    @Override
    public FactoryProjectState getState() {
        return state;
    }

    @Override
    public String getWalletNavigationStructure() throws CantGetWalletFactoryProjectNavigationStructureException {
        return null;
    }

    @Override
    public List<WalletFactoryProjectSkin> getSkinList() {
        return null;
    }

    @Override
    public List<String> getLanguages() {
        return null;
    }

    @Override
    public WalletFactoryProjectSkin getSkin(String skinName) throws CentGetWalletFactoryProjectSkinFileException {
        return null;
    }

    @Override
    public WalletFactoryProjectLanguage getLanguageFile(String fileName) throws CentGetWalletFactoryProjectLanguageFileException {
        return null;
    }

    @Override
    public void createEmptySkin(WalletFactoryProjectSkin name) throws CantAddWalletFactoryProjectSkinException {

    }


    @Override
    public void deleteSkin(WalletFactoryProjectSkin name) throws CantDeleteWalletFactoryProjectSkinException {

    }

    @Override
    public void addLanguage(WalletFactoryProjectLanguage language) throws CantAddWalletFactoryProjectLanguageException {

    }

    @Override
    public void deleteLanguage(WalletFactoryProjectLanguage language) throws CantDeleteWalletFactoryProjectLanguageException {

    }

    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @XmlTransient
    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @XmlTransient
    public UUID getPluginId() {
        return pluginId;
    }
}
