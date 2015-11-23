package com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantCreateNewPublisherException;
import com.bitdubai.fermat_wpd_api.layer.wpd_identity.publisher.exceptions.CantGetUserPublisherIdentitiesException;

import java.util.List;

/**
 * The Interface <code>DeveloperIdentityManager</code>
 * indicates the functionality of a DeveloperIdentityManager
 * <p/>
 *
 * Created by Nerio on 13/08/15
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface PublisherIdentityManager extends FermatManager {

    /**
     * This method will give us a list of all the developers associated to the actual Device User logged in
     *
     * @return the list of Developers associated to the current logged in Device User.
     * @throws CantGetUserPublisherIdentitiesException
     */
    List<PublisherIdentity> getPublishersFromCurrentDeviceUser() throws CantGetUserPublisherIdentitiesException;

    /**
     * This method creates a new Developer Identity for the logged in Device User and returns the
     * associated public key
     *
     * @param alias the alias that the user choose as developer identity
     * @return the new developer just created
     * @throws CantCreateNewPublisherException
     */
    PublisherIdentity createNewPublisher(String alias) throws CantCreateNewPublisherException;

}
