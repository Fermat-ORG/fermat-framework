package com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.pip_identity.developer.interfaces.DealsWithDeveloperIdentity</code>
 * indicates that the plugin needs the functionality of a DeveloperIdentityManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithDeveloperIdentity {
    void setDeveloperIdentityManager(DeveloperIdentityManager developerIdentityManager);
}
