package com.bitdubai.fermat_api.layer.ccp_identity.designer.interfaces;

import com.bitdubai.fermat_api.layer.ccp_identity.designer.exceptions.CantCreateNewDesignerException;
import com.bitdubai.fermat_api.layer.ccp_identity.designer.exceptions.CantGetUserDesignerIdentitiesException;

import java.util.List;

/**
 * The Interface <code>DesignerIdentityManager</code>
 * indicates the functionality of a TranslatorManager
 * <p/>
 *
 * Created by natalia on 03/08/15.
 * @version 1.0
 * @since Java JDK 1.7
 */

public interface DesignerIdentityManager {

    /**
     * This method will give us a list of all the Designers associated to the actual Device User logged in
     * @return List of Designer
     * @throws CantGetUserDesignerIdentitiesException
     */
   public List<DesignerIdentity> getDesignersFromCurrentDeviceUser() throws CantGetUserDesignerIdentitiesException;

    /**
     This method creates a new Designer Identity for the logged in Device User and returns the
     * associated public key
     * @param alias
     * @return Designer object
     * @throws CantCreateNewDesignerException
     */
    public DesignerIdentity createNewDesigner(String alias) throws CantCreateNewDesignerException;
}
