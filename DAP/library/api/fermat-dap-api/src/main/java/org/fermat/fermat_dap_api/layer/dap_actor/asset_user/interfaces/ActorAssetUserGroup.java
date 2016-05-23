package org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces;

import java.io.Serializable;

/**
 * Created by Luis Campo on 25/11/15.
 */
public interface ActorAssetUserGroup extends Serializable{
    /**
     * Returns the group id
     * @return groupId
     */
    String getGroupId();

    /**
     * Returns the name of the group
     * @return groupName
     */
    String getGroupName();

}
