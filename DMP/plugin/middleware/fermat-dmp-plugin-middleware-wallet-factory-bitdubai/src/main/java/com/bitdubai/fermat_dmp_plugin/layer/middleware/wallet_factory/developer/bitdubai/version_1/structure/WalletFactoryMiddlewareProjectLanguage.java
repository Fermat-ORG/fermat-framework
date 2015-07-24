package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
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
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.LanguagesAdapter;

import java.util.Map;
import java.util.UUID;

import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlRootElement;
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
public class WalletFactoryMiddlewareProjectLanguage implements WalletFactoryProjectLanguage {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private Languages type;

    private Version version;

    private String translatorPublicKey;

    private WalletFactoryProjectProposal walletFactoryProjectProposal;

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectLanguage() {
    }

    public WalletFactoryMiddlewareProjectLanguage(String name, Languages type, Version version, String translatorPublicKey, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.version = version;
        this.translatorPublicKey = translatorPublicKey;
    }

    public WalletFactoryMiddlewareProjectLanguage(UUID id, String name, Languages type, Version version, String translatorPublicKey, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.version = version;
        this.translatorPublicKey = translatorPublicKey;
    }

    /**
     * private Class getters
     */
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Languages getType() {
        return type;
    }

    public Version getVersion() {
        return version;
    }

    @Override
    public String getTranslatorPublicKey() {
        return translatorPublicKey;
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }
}