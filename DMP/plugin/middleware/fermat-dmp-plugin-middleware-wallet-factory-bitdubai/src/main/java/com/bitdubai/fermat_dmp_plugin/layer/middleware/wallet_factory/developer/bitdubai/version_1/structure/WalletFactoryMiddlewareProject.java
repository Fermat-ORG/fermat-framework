package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
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
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.UUID;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.JAXBException;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;
import ae.javax.xml.bind.annotation.XmlAttribute;
import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.XmlElementWrapper;
import ae.javax.xml.bind.annotation.XmlElements;
import ae.javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement( name = "project" )
public class WalletFactoryMiddlewareProject implements DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, DealsWithPluginIdentity, WalletFactoryProject {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String developerPublicKey;

    private List<WalletFactoryProjectProposal> proposals;

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProject() {
    }

    public WalletFactoryMiddlewareProject(UUID id, String name, String developerPublicKey) {
        this.id = id;
        this.name = name;
        this.developerPublicKey = developerPublicKey;
    }

    public WalletFactoryMiddlewareProject(String name, String developerPublicKey) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developerPublicKey = developerPublicKey;
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

    @XmlElement( required=true )
    @Override
    public String getDeveloperPublicKey() {
        return developerPublicKey;
    }

/*
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
    }*/
/*
    @Override
    public String getProjectXml(WalletFactoryProject walletFactoryProject) throws CantGetObjectStructureXmlException {
        try {
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProject.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProject.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(walletFactoryProject, outputStream);

            return outputStream.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new CantGetObjectStructureXmlException(CantGetObjectStructureXmlException.DEFAULT_MESSAGE, e, "Can't get Project XML.", "");
        }
    }

    @Override
    public WalletFactoryProject getProjectFromXml(String stringXml) throws CantGetObjectStructureFromXmlException {
        try {
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProject.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProject.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(stringXml);
            return (WalletFactoryMiddlewareProject) jaxbUnmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new CantGetObjectStructureFromXmlException(CantGetObjectStructureFromXmlException.DEFAULT_MESSAGE, e, "Can't get Project from XML.", "");
        }
    }
*/
    /**
     * private Class setters
     */
    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeveloperPublicKey(String developerPublicKey) {
        this.developerPublicKey = developerPublicKey;
    }

    public void setProposals(List<WalletFactoryProjectProposal> proposals) {
        this.proposals = proposals;
    }

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

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
     * DealWithPluginDatabaseSystem Interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

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
