package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantAddWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantCreateEmptyWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantDeleteWalletFactoryProjectSkinException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureFromXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetObjectStructureXmlException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectLanguageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CantGetWalletFactoryProjectNavigationStructureException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectLanguageFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.CentGetWalletFactoryProjectSkinFileException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.LanguageNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.exceptions.SkinNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.adapters.FactoryProjectStateAdapter;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
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
import ae.javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal</code>
 * implementation of WalletFactoryProjectProposal.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
@XmlRootElement( name = "proposal" )
public class WalletFactoryMiddlewareProjectProposal implements WalletFactoryProjectProposal {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String alias;

    private FactoryProjectState state;

    private List<WalletFactoryProjectLanguage> languages = new ArrayList<>();

    private WalletFactoryProject walletFactoryProject;


    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProjectProposal() {
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state, WalletFactoryProject walletFactoryProject) {
        this.id = UUID.randomUUID();
        this.alias = alias;
        this.state = state;
        this.walletFactoryProject = walletFactoryProject;
    }

    public WalletFactoryMiddlewareProjectProposal(UUID id, String alias, FactoryProjectState state, WalletFactoryProject walletFactoryProject) {
        this.id = id;
        this.alias = alias;
        this.state = state;
        this.walletFactoryProject = walletFactoryProject;
    }

    public WalletFactoryMiddlewareProjectProposal(String alias, FactoryProjectState state, List<WalletFactoryProjectLanguage> languages) {
        this.id = UUID.randomUUID();
        this.alias = alias;
        this.state = state;
        this.languages = languages;
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
    public WalletFactoryProject getProject() {
        return walletFactoryProject;
    }

    @Override
    public FactoryProjectState getState() {
        return state;
    }

    @Override
    public List<WalletFactoryProjectLanguage> getLanguages() {
        return languages;
    }

    @Override
    public WalletFactoryProjectLanguage getLanguageFileByName(String name) throws CentGetWalletFactoryProjectLanguageFileException, LanguageNotFoundException {
        if (languages == null) {
            return null;
        } else {
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getName().equals(name)) {
                    try {
                        lan.getFile();
                        return lan;
                    } catch (CantGetWalletFactoryProjectLanguageException e) {
                        throw new CentGetWalletFactoryProjectLanguageFileException(CentGetWalletFactoryProjectLanguageFileException.DEFAULT_MESSAGE, e, "Can't get language file.", "");
                    }
                }
            }
            throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "Language not found.", "");
        }
    }

    @Override
    public WalletFactoryProjectLanguage getLanguageFileById(UUID id) throws CentGetWalletFactoryProjectLanguageFileException, LanguageNotFoundException {
        if (languages == null) {
            return null;
        } else {
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getId().equals(id)) {
                    try {
                        lan.getFile();
                        return lan;
                    } catch (CantGetWalletFactoryProjectLanguageException e) {
                        throw new CentGetWalletFactoryProjectLanguageFileException(CentGetWalletFactoryProjectLanguageFileException.DEFAULT_MESSAGE, e, "Can't get language file.", "");
                    }
                }
            }
            throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "Language not found.", "");
        }
    }

    @Override
    public List<WalletFactoryProjectLanguage> getLanguageFilesByType(Languages type) throws CentGetWalletFactoryProjectLanguageFileException {
        if (languages == null) {
            return null;
        } else {
            List<WalletFactoryProjectLanguage> collected = new ArrayList<>();
            for(WalletFactoryProjectLanguage lan : languages) {
                if (lan.getType().equals(type)) collected.add(lan);
            }
            return collected;
        }
    }

    @Override
    public WalletFactoryProjectLanguage addLanguage(byte[] file, String name, Languages type) throws CantAddWalletFactoryProjectLanguageException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ CREADO EL SKIN
        WalletFactoryProjectLanguage walletFactoryProjectLanguage = new WalletFactoryMiddlewareProjectLanguage(file, name, type);
        languages.add(walletFactoryProjectLanguage);
        return walletFactoryProjectLanguage;
    }

    @Override
    public void deleteLanguage(UUID id) throws CantDeleteWalletFactoryProjectLanguageException, LanguageNotFoundException {
        // TODO VER SI HAY QUE SOBREESCRIBIR EL ARCHIVO UNA VEZ BORRADO EL language

        if (languages == null) {
            throw new CantDeleteWalletFactoryProjectLanguageException(CantDeleteWalletFactoryProjectLanguageException.DEFAULT_MESSAGE, null, "There's not languages in the proposal", "");
        } else {
            for(int i = 0 ; i < languages.size() ; i++) {
                if (languages.get(i).getId().equals(id)) {
                    languages.remove(i);
                    return;
                }
            }
            throw new LanguageNotFoundException(LanguageNotFoundException.DEFAULT_MESSAGE, null, "Language not found.", "");
        }
    }

    /**
     * WalletFactoryProject Proposal implementation methods
     */

    /**
     * construct the path of the project proposal
     * @return project proposal path
     */
    public String getProposalPath() {
        String initialPath = "wallet_factory_projects";

        return initialPath + "/" +
               walletFactoryProject.getName() + "/" +
               alias;
    }
}
