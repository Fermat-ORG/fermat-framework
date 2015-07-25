package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;

import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectProposal {

    /**
     * @return the id of the project proposal
     */
    UUID getId();

    /**
     * @return alias of the proposal
     */
    String getAlias();

    /**
     * @return state of the proposal
     */
    FactoryProjectState getState();

    /**
     * @return project to which it belongs
     */
    WalletFactoryProject getProject();

}
