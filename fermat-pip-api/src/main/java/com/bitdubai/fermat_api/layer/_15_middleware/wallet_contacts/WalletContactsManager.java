package com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts;

import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.WalletContactsManager</code>
 * indicates the functionality of a WalletContactsManager
 * <p/>
 *
 * Created by loui on 18/02/15.
 * Reviewed by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactsManager {

    public List<WalletContact> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException;

    public List<WalletContact> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException;

    public WalletContact createWalletContact(CryptoAddress deliveredCryptoAddress, CryptoAddress receivedCryptoAddress, UUID userId, String userName, UserTypes userType, UUID walletId) throws CantCreateWalletContactException;

    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String userName) throws CantUpdateWalletContactException;

    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException;

    public WalletContact getWalletContactByNameAndWalletId(String userName, UUID walletId) throws CantGetWalletContactException;

    public WalletContact getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException;
}
