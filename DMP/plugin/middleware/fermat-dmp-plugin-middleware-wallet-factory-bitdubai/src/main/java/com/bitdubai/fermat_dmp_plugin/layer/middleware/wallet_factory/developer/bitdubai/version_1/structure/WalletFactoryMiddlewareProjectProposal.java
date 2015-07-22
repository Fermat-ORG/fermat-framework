package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
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
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
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

    // TODO COMPROBAR ATRIBUTOS REQUERIDOS

    /**
     * Private class Attributes
     */
    private UUID id;

    private String alias;

    private FactoryProjectState state;

    private List<WalletFactoryProjectSkin> skins = new ArrayList<>();

    private List<WalletFactoryProjectLanguage> languages = new ArrayList<>();

    private WalletFactoryProject walletFactoryProject;


    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectProposal() {
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state, List<WalletFactoryProjectSkin> skins, List<WalletFactoryProjectLanguage> languages) {
        this.id = UUID.randomUUID();
        this.alias = alias;
        this.state = state;
        this.skins = skins;
        this.languages = languages;
    }

    /**
     * private Class getters
     */
    @XmlAttribute
    @Override
    public UUID getId() {
        return id;
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
        // TODO NAVIGATION STRUCTURE
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
        // TODO ADD EXCEPTION SKINNOTFOUNDED
        if (skins == null) {
            return null;
        } else {
            for(WalletFactoryProjectSkin skin : skins) {
                if (skin.getName().equals(skinName)) return skin;
            }
            return null;
        }
    }

    @Override
    public WalletFactoryProjectLanguage getLanguageFileByName(String name) throws CentGetWalletFactoryProjectLanguageFileException {
        // TODO ADD EXCEPTION LANGUAGENOTFOUNDED
        if (languages == null) {
            return null;
        } else {
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getName().equals(name)) return lan;
            }
            return null;
        }
    }

    @Override
    public List<WalletFactoryProjectLanguage> getLanguageFilesByType(Languages type) throws CentGetWalletFactoryProjectLanguageFileException {
        if (languages == null) {
            return null;
        } else {
            List<WalletFactoryProjectLanguage> collected = new ArrayList<>();
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getType().equals(type)) collected.add(lan);
            }
            return collected;
        }
    }

    @Override
    public WalletFactoryProjectSkin createEmptySkin(String name) throws CantAddWalletFactoryProjectSkinException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ CREADO EL SKIN
        WalletFactoryProjectSkin walletFactoryProjectSkin = new WalletFactoryMiddlewareProjectSkin(name, "", new ArrayList<WalletFactoryProjectResource>());
        skins.add(walletFactoryProjectSkin);
        return walletFactoryProjectSkin;
    }


    @Override
    public void deleteSkin(UUID id) throws CantDeleteWalletFactoryProjectSkinException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ BORRADO EL SKIN

        if (skins == null) {
            throw new CantDeleteWalletFactoryProjectSkinException(CantDeleteWalletFactoryProjectSkinException.DEFAULT_MESSAGE, null, "There's not skins in the proposal", "");
        } else {
            for(WalletFactoryProjectSkin skin : skins) {
                if (skin.getId().equals(id)) {
                    skins.remove(skin);
                    return;
                }
            }
            // TODO ADD EXCEPTION SKINNOTFOUNDED
        }
    }

    @Override
    public WalletFactoryProjectLanguage addLanguage(byte[] file, String name, Languages type) throws CantAddWalletFactoryProjectLanguageException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ CREADO EL SKIN
        WalletFactoryProjectLanguage walletFactoryProjectLanguage = new WalletFactoryMiddlewareProjectLanguage(file, name, type);
        languages.add(walletFactoryProjectLanguage);
        return walletFactoryProjectLanguage;
    }

    @Override
    public void deleteLanguage(UUID id) throws CantDeleteWalletFactoryProjectLanguageException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ BORRADO EL language

        if (languages == null) {
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, null, "There's not languages in the proposal", "");
        } else {
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getId().equals(id)) {
                    languages.remove(lan);
                    return;
                }
            }
            // TODO ADD EXCEPTION LANGUAGENOTFOUNDED
        }
    }

    /**
     * private Class setters
     */
    public void setId(UUID id) {
        this.id = id;
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
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private UUID pluginId;

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
