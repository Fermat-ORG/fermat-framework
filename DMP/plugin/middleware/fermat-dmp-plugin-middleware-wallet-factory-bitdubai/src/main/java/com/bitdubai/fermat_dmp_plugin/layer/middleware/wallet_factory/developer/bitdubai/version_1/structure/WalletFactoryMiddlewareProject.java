package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProject</code>
 * implementation of WalletFactoryProject.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareProject implements WalletFactoryProject {

    /**
     * Private class Attributes
     */
    private UUID id;

    private String name;

    private String developerPublicKey;

    private Wallets type;

    /**
     * Class Constructors
     */
    public WalletFactoryMiddlewareProject() {
    }

    public WalletFactoryMiddlewareProject(String name, String developerPublicKey, Wallets type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.type = type;
    }

    public WalletFactoryMiddlewareProject(UUID id, String name, String developerPublicKey, Wallets type) {
        this.id = id;
        this.name = name;
        this.developerPublicKey = developerPublicKey;
        this.type = type;
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

    @Override
    public String getDeveloperPublicKey() {
        return developerPublicKey;
    }

    @Override
    public Wallets getType() {
        return type;
    }
}
