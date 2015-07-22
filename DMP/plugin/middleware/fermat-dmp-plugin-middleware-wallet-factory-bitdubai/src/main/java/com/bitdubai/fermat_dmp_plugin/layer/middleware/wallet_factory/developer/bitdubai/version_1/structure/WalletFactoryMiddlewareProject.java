package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectProposalsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.ProposalNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;

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

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String developerPublicKey;

    private List<WalletFactoryProjectProposal> proposals;

    @Override
    public String getDeveloperPublicKey() {
        return developerPublicKey;
    }

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProject() {
    }

    public WalletFactoryMiddlewareProject(String name, String developerPublicKey, List<WalletFactoryProjectProposal> proposals) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.proposals = proposals;
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


    public List<WalletFactoryProjectProposal> getProposals() throws CantGetWalletFactoryProjectProposalsException {
        return proposals;
    }

    @Override
    public WalletFactoryProjectProposal getProposalByName(String proposal) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        if (proposals == null) {
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, null, "There isn't proposal.", "");
        } else {
            for(WalletFactoryProjectProposal prop : proposals) {
                if (prop.getAlias().equals(proposal)) return prop;
            }
            throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "Proposal not found.", "");
        }
    }

    @Override
    public WalletFactoryProjectProposal getProposalById(UUID id) throws CantGetWalletFactoryProjectProposalException, ProposalNotFoundException {
        if (proposals == null) {
            throw new CantGetWalletFactoryProjectProposalException(CantGetWalletFactoryProjectProposalException.DEFAULT_MESSAGE, null, "There isn't proposal.", "");
        } else {
            for(WalletFactoryProjectProposal prop : proposals) {
                if (prop.getId().equals(id)) return prop;
            }
            throw new ProposalNotFoundException(ProposalNotFoundException.DEFAULT_MESSAGE, null, "Proposal not found.", "");
        }
    }
}
