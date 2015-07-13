package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.UserTypes;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public class ExtraUser implements User{

    /**
     * ExtraUser Interface member variables.
     */

    private String name;
    private UUID id;
    private UserTypes type;


    /**
     * User interface implementation.
     */


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public UserTypes getType(){
        return this.type;
    }


}
