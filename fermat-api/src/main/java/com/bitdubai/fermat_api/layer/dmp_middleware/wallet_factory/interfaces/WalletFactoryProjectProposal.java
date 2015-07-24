package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;

import java.util.UUID;

/**
 * Created by eze on 2015.07.15..
 */
public interface WalletFactoryProjectProposal {

    UUID getId();

    // alias of the proposal of version
    String getAlias();

    // state of the proposal
    FactoryProjectState getState();

    // project to which it belongs
    WalletFactoryProject getProject();

}
