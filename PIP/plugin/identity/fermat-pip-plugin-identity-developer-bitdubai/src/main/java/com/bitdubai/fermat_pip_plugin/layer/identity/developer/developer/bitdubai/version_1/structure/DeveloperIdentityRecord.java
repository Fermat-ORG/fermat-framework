package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;

/**
 * The class <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityRecord</code>
 * represents a developer identity record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * @version 1.0
 */
public class DeveloperIdentityRecord implements DeveloperIdentity {

    String alias;
    String publicKey;

    public DeveloperIdentityRecord(String alias, String publicKey) {
        this.alias = alias;
        this.publicKey = publicKey;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getPublicKey() {
        return publicKey;
    }
}
