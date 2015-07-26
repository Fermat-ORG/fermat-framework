package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;

import java.util.UUID;

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
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
    }

    public WalletFactoryMiddlewareProjectLanguage(UUID id, String name, Languages type, Version version, String translatorPublicKey, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.version = version;
        this.translatorPublicKey = translatorPublicKey;
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
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