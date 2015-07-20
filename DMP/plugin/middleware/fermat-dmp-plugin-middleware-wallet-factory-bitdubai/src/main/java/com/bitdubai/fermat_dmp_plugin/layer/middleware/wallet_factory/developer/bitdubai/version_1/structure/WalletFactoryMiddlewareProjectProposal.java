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
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common.FactoryProjectStateAdapter;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common.ResourceTypeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.XmlTransient;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

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

    FactoryProjectState state;

    List<WalletFactoryProjectSkin> skins = new ArrayList<>();

    List<WalletFactoryProjectLanguage> languages = new ArrayList<>();

    WalletFactoryProject walletFactoryProject;

    public WalletFactoryMiddlewareProjectProposal() {
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state, List<WalletFactoryProjectSkin> skins, List<WalletFactoryProjectLanguage> languages) {
        this.alias = alias;
        this.state = state;
        this.skins = skins;
        this.languages = languages;
    }

    @XmlElement
    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public WalletFactoryProject getProject() {
        return walletFactoryProject;
    }

    @XmlJavaTypeAdapter( FactoryProjectStateAdapter.class )
    @XmlAttribute( name = "state" )
    @Override
    public FactoryProjectState getState() {
        return state;
    }

    @Override
    public String getWalletNavigationStructure() throws CantGetWalletFactoryProjectNavigationStructureException {
        return null;
    }

    @XmlElements({
        @XmlElement(name="skin", type=WalletFactoryMiddlewareProjectSkin.class),
    })
    @XmlElementWrapper
    @Override
    public List<WalletFactoryProjectSkin> getSkins() {
        return skins;
    }

    @XmlElements({
        @XmlElement(name="language", type=WalletFactoryMiddlewareProjectLanguage.class),
    })
    @XmlElementWrapper
    @Override
    public List<WalletFactoryProjectLanguage> getLanguages() {
        return languages;
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
    public WalletFactoryProjectLanguage addLanguage(byte[] file, String name) throws CantAddWalletFactoryProjectLanguageException {
        return null;
    }

    @Override
    public void deleteLanguage(WalletFactoryProjectLanguage language) throws CantDeleteWalletFactoryProjectLanguageException {

    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setState(FactoryProjectState state) {
        this.state = state;
    }

    public void setSkins(List<WalletFactoryProjectSkin> skins) {
        this.skins = skins;
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
