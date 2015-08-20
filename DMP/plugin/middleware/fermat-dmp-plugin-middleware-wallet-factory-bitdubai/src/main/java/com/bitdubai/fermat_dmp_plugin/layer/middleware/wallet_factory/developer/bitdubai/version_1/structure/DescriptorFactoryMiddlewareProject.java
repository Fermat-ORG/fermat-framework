package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.LanguageDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.SkinDescriptorFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletDescriptorFactoryProject;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.DescriptorFactoryMiddlewareProject</code>
 * implementation of DescriptorFactoryProject.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class DescriptorFactoryMiddlewareProject implements WalletDescriptorFactoryProject {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String developerPublicKey;

    private Wallets type;

    private String path;

    private FactoryProjectState state;

    private String publisherIdentityKey;

    private DescriptorFactoryProjectType descriptorFactoryProjectType;

    private String description;

    private List<LanguageDescriptorFactoryProject> languageDescriptorFactoryProjectList;

    private List<SkinDescriptorFactoryProject> skinDescriptorFactoryProjectList;


    /**
     * Class Constructors
     */
    public DescriptorFactoryMiddlewareProject() {
    }

    public DescriptorFactoryMiddlewareProject(String name, String developerPublicKey, Wallets type, String path, FactoryProjectState state, String description, String publisherIdentityKey, DescriptorFactoryProjectType descriptorFactoryProjectType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.description = description;
        this.publisherIdentityKey = publisherIdentityKey;
        this.descriptorFactoryProjectType = descriptorFactoryProjectType;
        this.type = type;
        this.path = path;
        this.state = state;
    }

    public DescriptorFactoryMiddlewareProject(UUID id, String name, String developerPublicKey, Wallets type, String path, FactoryProjectState state, String description, String publisherIdentityKey, DescriptorFactoryProjectType descriptorFactoryProjectType) {
        this.id = id;
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.publisherIdentityKey = publisherIdentityKey;
        this.descriptorFactoryProjectType = descriptorFactoryProjectType;
        this.description = description;
        this.type = type;
        this.path = path;
        this.state = state;
    }

    /**
     * private Class getters
     */
    @Override
    public UUID getId() { return id; }

    @Override
    public UUID getWalletId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDeveloperPublicKey() {
        return developerPublicKey;
    }

    @Override
    public String getPublisherIdentityKey() {
       return publisherIdentityKey;
    }

    @Override
    public DescriptorFactoryProjectType getDescriptorProjectType() {return descriptorFactoryProjectType; }

    @Override
    public Wallets getWalletType() {
        return type;
    }

    public String getPath() { return path + name; }

    public FactoryProjectState getState() {
        return state;
    }

    @Override
    public String getDescription() {return description;}

    @Override
    public List<LanguageDescriptorFactoryProject> getLanguages() {return languageDescriptorFactoryProjectList; }

    @Override
    public List<SkinDescriptorFactoryProject> getSkins() {return skinDescriptorFactoryProjectList;}

    @Override
    public void setLanguages(List<LanguageDescriptorFactoryProject> languages) {

    }

    @Override
    public void setSkins(List<SkinDescriptorFactoryProject> skins) {

    }

    @Override
    public String getAuthor() {
        return null;
    }

    @Override
    public int getDefaultSizeInBytes() {
        return 0;
    }


}
