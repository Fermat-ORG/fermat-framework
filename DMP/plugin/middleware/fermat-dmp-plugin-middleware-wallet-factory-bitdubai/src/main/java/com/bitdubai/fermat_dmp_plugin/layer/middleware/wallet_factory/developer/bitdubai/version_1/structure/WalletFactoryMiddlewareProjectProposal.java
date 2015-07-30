package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal</code>
 * implementation of WalletFactoryProjectProposal.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProjectProposal implements WalletFactoryProjectProposal {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String alias;

    private FactoryProjectState state;

    private String path;


    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectProposal() {
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state, String path) {
        this.id = UUID.randomUUID();
        this.alias = alias;
        this.state = state;
        this.path = path;
    }

    public WalletFactoryMiddlewareProjectProposal(UUID id, String alias, FactoryProjectState state, String path) {
        this.id = id;
        this.alias = alias;
        this.state = state;
        this.path = path;
    }

    /**
     * private Class getters
     */
    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public FactoryProjectState getState() {
        return state;
    }

    @Override
    public String getPath() { return path + alias; }

}

