package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.DescriptorFactoryProjectType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.DescriptorFactoryProject;

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
public class DescriptorFactoryMiddlewareProject implements DescriptorFactoryProject {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String developerPublicKey;

    private Wallets type;

    private String path;

    private String publisherIdentityKey;

    private DescriptorFactoryProjectType descriptorFactoryProjectType;

    private String description;

    /**
     * Class Constructors
     */
    public DescriptorFactoryMiddlewareProject() {
    }

    public DescriptorFactoryMiddlewareProject(String name, String developerPublicKey, Wallets type, String path) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.type = type;
        this.path = path;
    }

    public DescriptorFactoryMiddlewareProject(UUID id, String name, String developerPublicKey, Wallets type, String path) {
        this.id = id;
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.type = type;
        this.path = path;
    }

    /**
     * private Class getters
     */
    @Override
    public UUID getId() { return id; }

    @Override
    public UUID getWalletsId() {
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
    public Wallets getWallestType() {
        return type;
    }

    public String getPath() { return path + name; }

    @Override
    public String getDescription() {return description;}

}
