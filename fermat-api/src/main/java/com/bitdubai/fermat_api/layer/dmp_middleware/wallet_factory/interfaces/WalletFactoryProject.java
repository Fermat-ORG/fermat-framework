package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
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

    Wallets getType();

}
