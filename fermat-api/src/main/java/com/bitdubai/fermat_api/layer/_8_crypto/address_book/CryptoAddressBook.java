package com.bitdubai.fermat_api.layer._8_crypto.address_book;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._5_user.User;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;
import com.bitdubai.fermat_api.layer._8_crypto.address_book.exceptions.*;

import java.util.UUID;

/**
 * Created by ciencias on 3/18/15.
 */
public interface CryptoAddressBook {

    public User getUserByCryptoAddress (CryptoAddress cryptoAddress) throws CantGetUserCryptoAddress;

    public void registerUserCryptoAddress (UserTypes userType, UUID userId,CryptoAddress cryptoAddress) throws CantRegisterUserCryptoAddress;

}
