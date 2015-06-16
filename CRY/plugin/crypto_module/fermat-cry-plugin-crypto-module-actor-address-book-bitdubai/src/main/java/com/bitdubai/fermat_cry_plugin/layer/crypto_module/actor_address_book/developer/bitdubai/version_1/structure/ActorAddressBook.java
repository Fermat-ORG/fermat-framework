package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;



import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.UserTypes;

import java.util.UUID;

/**
 * Created by Natalia on 16/06/2015
 */

/**
 * This class manages the relationship between users and crypto addresses by storing them on a Database Table.
 */


public class ActorAddressBook implements com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.ActorAddressBook{



    /**
     * ActorAddressBook Interface member variables.
     */



    private UserTypes userTypes;
    private UUID user_id ;
    private CryptoAddress userCryptoAddress;



    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;


    /**
     * UsesDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;


    /**
     * Constructor.
     */
    public ActorAddressBook(UUID user_id, UserTypes userTypes, CryptoAddress userCryptoAddress){

        /**
         * Set actor settings.
         */
        this.user_id = user_id;
        this.userTypes = userTypes;
        this.userCryptoAddress = userCryptoAddress;

    }


    @Override
    public UUID getActorId(){
        return this.user_id;
    }

    @Override
    public UserTypes  getActorType(){
        return this.userTypes;
    }

    @Override
    public CryptoAddress getCryptoAddress(){
        return this.userCryptoAddress;
    }

}