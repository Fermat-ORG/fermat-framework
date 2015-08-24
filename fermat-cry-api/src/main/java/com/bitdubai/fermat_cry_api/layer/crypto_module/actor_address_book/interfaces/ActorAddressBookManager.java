package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces;

import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;

import java.io.Serializable;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager</code>
 * indicates the functionality of a ActorAddressBookManager.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/06/15.
 * @version 1.0
 */
public interface ActorAddressBookManager extends Serializable{

    ActorAddressBookRegistry getActorAddressBookRegistry() throws CantGetActorAddressBookRegistryException;

}
