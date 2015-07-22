package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
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
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;

import java.util.List;
import java.util.UUID;

import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlTransient;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal</code>
 * implementation of WalletFactoryProjectProposal.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProject implements DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProject {

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
    @XmlAttribute( required=true )
    @Override
    public UUID getId() {
        return id;
    }

    @XmlElement( required=true )
    @Override
    public String getName() {
        return name;
    }

    @XmlElements({
            @XmlElement(name="proposal", type=WalletFactoryMiddlewareProjectProposal.class),
    })
    @XmlElementWrapper
    @Override
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

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginFileSystem interface variables.
     */
    @XmlTransient
    private UUID pluginId;

    /**
     * DealsWithPluginFileSystem interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @XmlTransient
    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    /**
     * DealsWithPluginIdentity interface implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    @XmlTransient
    public UUID getPluginId() {
        return pluginId;
    }
}
