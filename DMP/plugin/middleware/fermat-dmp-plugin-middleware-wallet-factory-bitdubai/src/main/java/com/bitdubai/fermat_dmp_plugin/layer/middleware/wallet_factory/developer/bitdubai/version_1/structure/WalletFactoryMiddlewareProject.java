package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;

import java.util.List;
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
public class WalletFactoryMiddlewareProject implements WalletFactoryProject {

    // TODO COMPROBAR ATRIBUTOS REQUERIDOS

    private UUID id;

    private String name;

    private List<String> proposals;

    @Override
    public String getDeveloperPublicKey() {
        return null;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }


    public List<String> getProposals() throws CantGetWalletFactoryProjectProposalsException {
        return proposals;
    }

    @Override
    public WalletFactoryProjectProposal getProposal(String proposal) throws CantGetWalletFactoryProjectProposalException {
        return null;
    }
}
