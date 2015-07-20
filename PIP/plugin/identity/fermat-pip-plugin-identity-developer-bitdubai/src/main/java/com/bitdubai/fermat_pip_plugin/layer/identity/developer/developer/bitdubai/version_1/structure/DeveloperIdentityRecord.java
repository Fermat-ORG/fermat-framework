package com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_pip_api.layer.pip_identity.developer.interfaces.DeveloperIdentity;

/**
 * The class <code>com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.structure.DeveloperIdentityRecord</code>
 * represents a developer identity record.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/07/15.
 * Updated by Raul Pena   - (raul.pena@gmail.com)  on 16/07/15.
 *
 * @version 1.0
 */
public class DeveloperIdentityRecord implements DeveloperIdentity {

    private String alias;
    private String publicKey;

    public DeveloperIdentityRecord () {

        super ();
    }

    public DeveloperIdentityRecord (String alias, String publicKey) {

        super ();

        this.alias = alias;
        this.publicKey = publicKey;
    }

    @Override
    public String getAlias () {
        return alias;
    }

    public void setAlias (String alias) {
        this.alias = alias;
    }

    @Override
    public String getPublicKey () {
        return publicKey;
    }

    public void setPublicKey (String publicKey) {
        this.publicKey = publicKey;
    }
}
