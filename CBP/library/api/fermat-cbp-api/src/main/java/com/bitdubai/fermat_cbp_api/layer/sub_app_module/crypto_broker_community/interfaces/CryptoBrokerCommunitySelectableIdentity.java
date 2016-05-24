package com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces;

import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantSelectIdentityException;

import java.io.Serializable;


/**
 * The interface <code>IntraUserLoginIdentity</code>
 * provides the methods to get the information of an identity a user can use to select.
 * 
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public interface CryptoBrokerCommunitySelectableIdentity extends ActiveActorIdentityInformation, Serializable {

    /**
     * The method <code>select</code> you can select an identity to work with.
     *
     * @return the profile image of the crypto broker
     */
    void select() throws CantSelectIdentityException;

}
