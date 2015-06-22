package com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRecord;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBook;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.exceptions.CantInitializeActorAddressBookException;

import java.util.List;
import java.util.UUID;

/**
 * The class <code>com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.structure.ActorAddressBookCryptoModuleRegistry</code>
 * haves all consumable methods from the plugin Actor Address Book
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 17/06/15.
 * @version 1.0
 */
public class ActorAddressBookCryptoModuleRegistry implements ActorAddressBookRegistry, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    /**
     * ActorAddressBookCryptoModuleRecord Interface member variables.
     */
    private ActorAddressBookCryptoModuleDao actorAddressBookDao;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginIdentityInterface member variables.
     */
    private UUID pluginId;

    public void initialize() throws CantInitializeActorAddressBookException {
        /**
         * I will try to create and initialize a new DAO
         */
        actorAddressBookDao = new ActorAddressBookCryptoModuleDao(errorManager, pluginDatabaseSystem, pluginId);
        actorAddressBookDao.initialize();

    }

    /**
     * Address Book Manager implementation.
     */

    @Override

    public ActorAddressBookRecord getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorAddressBook {
        return actorAddressBookDao.getActorAddressBookByCryptoAddress(cryptoAddress);
    }

    @Override
    public List<ActorAddressBookRecord> getAllActorAddressBookByActorId(UUID actorId) throws CantGetActorAddressBook {
        return actorAddressBookDao.getAllActorAddressBookByActorId(actorId);
    }


    @Override
    public void registerActorAddressBook(UUID actorId, Actors actorType, CryptoAddress cryptoAddress) throws CantRegisterActorAddressBook {

        /**
         * Here I create the Address book record for new user.
         */
        actorAddressBookDao.registerActorAddressBook(actorId, actorType, cryptoAddress);

    }


    /**
     *DealWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealsWithPluginIdentity methods implementation.
     */
    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }
}
