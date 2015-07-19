package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;

import java.util.List;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {

    // TODO DEVELOPER?


    String getName();

    List<String> getProposals() throws CantGetWalletFactoryProjectProposalsException;

    WalletFactoryProjectProposal getProposal(String proposal) throws CantGetWalletFactoryProjectProposalException;

}
