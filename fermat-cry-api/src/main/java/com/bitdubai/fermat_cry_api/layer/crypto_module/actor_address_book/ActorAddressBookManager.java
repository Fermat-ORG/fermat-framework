package com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantRegisterActorAddressBook;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._9_crypto_module.user_address_book.ActorAddressBookManager</code>
 * haves all consumable methods from the plugin Actor Address Book
 *
 * Created by Natalia on 12/06/15.
 * @version 1.0
 */

public interface ActorAddressBookManager {

    public ActorAddressBook getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorAddressBook;

    public List<ActorAddressBook> getAllActorAddressBookByActorId(UUID actorId) throws CantGetActorAddressBook;

    public void registerActorAddressBook(UUID actorId, Actors actorType, CryptoAddress cryptoAddress) throws CantRegisterActorAddressBook;

}