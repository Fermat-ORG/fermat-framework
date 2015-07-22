package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectResourceException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common.FactoryProjectStateAdapter;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common.LanguagesAdapter;

import java.util.List;
import java.util.UUID;

import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
import ae.javax.xml.bind.annotation.XmlTransient;
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectLanguage</code>
 * implementation of WalletFactoryProjectLanguage.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "language" )
public class WalletFactoryMiddlewareProjectLanguage implements DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProjectLanguage {

    // TODO COMPROBAR ATRIBUTOS REQUERIDOS

    /**
     * Private class Attributes
     */
    private UUID id;

    private byte[] file;

    private String name;

    private Languages type;

    private WalletFactoryProjectProposal walletFactoryProjectProposal;

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectLanguage() {
    }

    public WalletFactoryMiddlewareProjectLanguage(String name, Languages type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
    }

    public WalletFactoryMiddlewareProjectLanguage(byte[] file, String name, Languages type) {
        this.id = UUID.randomUUID();
        this.file = file;
        this.name = name;
        this.type = type;
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
    public String getName() {
        return name;
    }


    @XmlJavaTypeAdapter( LanguagesAdapter.class )
    @XmlAttribute
    @Override
    public Languages getType() {
        return type;
    }

    @Override
    public byte[] getFile() throws CantGetWalletFactoryProjectLanguageException {
        if (file != null) {
            return file;
        } else {
            try {
                PluginBinaryFile newFile = pluginFileSystem.getBinaryFile(pluginId, getLanguagePath(), name, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                newFile.loadFromMedia();
                return newFile.getContent();
            } catch (CantCreateFileException | FileNotFoundException | CantLoadFileException e) {
                throw new CantGetWalletFactoryProjectLanguageException(CantGetWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, e, "Can't get file.", "");
            }
        }
    }

    /**
     * construct the path of the project languages
     *
     * @return project language path
     */
    public String getLanguagePath() {
        String initialPath = "wallet_factory_projects";
        String languagePath = "languages";

        WalletFactoryProject project = walletFactoryProjectProposal.getProject();

        return initialPath + "/" +
                project.getName() + "/" +
                walletFactoryProjectProposal.getAlias() + "/" +
                languagePath;
    }

    /**
     * private Class setters
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Languages type) {
        this.type = type;
    }

    /**
     * set parent after unmarshal (xml conversion)
     */
    public void afterUnmarshal(Unmarshaller u, Object parent) {
        if (parent != null) {
            WalletFactoryMiddlewareProjectProposal walletFactoryMiddlewareProjectProposal = (WalletFactoryMiddlewareProjectProposal) parent;
            walletFactoryProjectProposal = walletFactoryMiddlewareProjectProposal;
            setPluginFileSystem(walletFactoryMiddlewareProjectProposal.getPluginFileSystem());
            setPluginId(walletFactoryMiddlewareProjectProposal.getPluginId());
        }
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    private UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}