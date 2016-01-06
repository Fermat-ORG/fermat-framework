package com.bitdubai.fermat_ccp_api.layer.module.intra_user_identity.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;

/**
 * Created by natalia on 05/01/16.
 */
public interface IntraUserModuleIdentity extends ActiveActorIdentityInformation {


    /**
     * The method <code>getPhrase</code> returns the phrase created by the intra user
     * @return string phrase object
     */

    String getPhrase();

    /**
     * The method <code>getActorType</code> returns the Actors types of the intra user
     * @return Actors type enum
     */

    Actors getActorType();



}
