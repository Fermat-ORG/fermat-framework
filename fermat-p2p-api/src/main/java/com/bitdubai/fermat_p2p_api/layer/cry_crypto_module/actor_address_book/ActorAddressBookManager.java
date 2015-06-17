package com.bitdubai.fermat_p2p_api.layer.cry_crypto_module.actor_address_book;

import com.bitdubai.fermat_p2p_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_p2p_api.layer.pip_user.UserTypes;
import com.bitdubai.fermat_p2p_api.layer.cry_crypto_module.actor_address_book.exceptions.CantGetActorCryptoAddress;
import com.bitdubai.fermat_p2p_api.layer.cry_crypto_module.actor_address_book.exceptions.CantRegisterActorCryptoAddress;


import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer._9_crypto_module.user_address_book.UserAddressBookManager</code>
 * haves all consumable methods from the plugin User Address Book
 *
 * Created by Natalia on 12/06/15.
 * @version 1.0
 */

public interface ActorAddressBookManager {

    public ActorAddressBook getActorAddressBookByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorCryptoAddress;

    public List<ActorAddressBook> getAllActorAddressBookByUserId(UUID userId) throws CantGetActorCryptoAddress;

    public void registerActorCryptoAddress (UserTypes userType, UUID userId,CryptoAddress cryptoAddress) throws CantRegisterActorCryptoAddress;

}