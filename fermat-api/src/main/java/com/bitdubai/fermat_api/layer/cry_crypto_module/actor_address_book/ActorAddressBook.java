package com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.UserTypes;

import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.exceptions.CantGetActorCryptoAddress;
import com.bitdubai.fermat_api.layer.cry_crypto_module.actor_address_book.exceptions.CantRegisterActorCryptoAddress;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public interface ActorAddressBook {

    public User getActorByCryptoAddress(CryptoAddress cryptoAddress) throws CantGetActorCryptoAddress;

    public void registerActorCryptoAddress(UserTypes userType, UUID userId, CryptoAddress cryptoAddress) throws CantRegisterActorCryptoAddress;

}
