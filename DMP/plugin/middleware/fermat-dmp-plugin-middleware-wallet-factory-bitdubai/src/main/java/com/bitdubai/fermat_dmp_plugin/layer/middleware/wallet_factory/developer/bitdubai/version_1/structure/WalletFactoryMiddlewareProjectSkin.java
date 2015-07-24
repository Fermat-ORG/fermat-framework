package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin</code>
 * implementation of WalletFactoryProjectResource.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectSkin implements WalletFactoryProjectSkin {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String designerPublicKey;

    private Version version;

    private WalletFactoryProjectProposal walletFactoryProjectProposal;

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectSkin() {
    }

    public WalletFactoryMiddlewareProjectSkin(String name, String designerPublicKey, Version version, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.designerPublicKey = designerPublicKey;
        this.version = version;
        this.walletFactoryProjectProposal = walletFactoryProjectProposal;
    }

    public WalletFactoryMiddlewareProjectSkin(UUID id, String name, String designerPublicKey, Version version, WalletFactoryProjectProposal walletFactoryProjectProposal) {
        this.id = id;
        this.name = name;
        this.designerPublicKey = designerPublicKey;
        this.version = version;
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
    public String getDesignerPublicKey() {
        return designerPublicKey;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public WalletFactoryProjectProposal getWalletFactoryProjectProposal() {
        return walletFactoryProjectProposal;
    }
}
