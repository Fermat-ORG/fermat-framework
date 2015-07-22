package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletFactoryProject {

    String getDeveloperPublicKey();

    UUID getId();

    String getName();

    List<WalletFactoryProjectProposal> getProposals() throws CantGetWalletFactoryProjectProposalsException;

    WalletFactoryProjectProposal getProposalByName(String proposal) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException;

    WalletFactoryProjectProposal getProposalById(UUID id) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException;

    String getProjectXml(WalletFactoryProject walletFactoryProject) throws CantGetObjectStructureXmlException;

    WalletFactoryProject getProjectFromXml(String stringXml) throws CantGetObjectStructureFromXmlException;

}
