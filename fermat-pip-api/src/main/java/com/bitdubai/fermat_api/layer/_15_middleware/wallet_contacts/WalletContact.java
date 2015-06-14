package com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts;

import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.WalletContact</code>
 * indicates the functionality of a WalletContact and its attributes
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContact {


    /**
     * Return the contactId
     *
     * @return UUID
     */
    public UUID getContactId();

    /**
     * Set the contactId
     *
     * @param contactId contact's id
     */
    public void setContactId(UUID contactId);

    /**
     * Return the walletId
     *
     * @return UUID
     */
    public UUID getWalletId();

    /**
     * Set the walletId
     *
     * @param walletId wallet's id
     */
    public void setWalletId(UUID walletId);

    /**
     * Return the userType
     *
     * @return UserTypes
     */
    public UserTypes getUserType();

    /**
     * Set the userType
     *
     * @param userType user's type
     */
    public void setUserType(UserTypes userType);

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    public CryptoAddress getDeliveredCryptoAddress();

    /**
     * Set the deliveredCryptoAddress
     *
     * @param deliveredCryptoAddress delivered cryptoAddress
     */
    public void setDeliveredCryptoAddress(CryptoAddress deliveredCryptoAddress);

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    public CryptoAddress getReceivedCryptoAddress();

    /**
     * Set the receivedCryptoAddress
     *
     * @param receivedCryptoAddress received cryptoAddress
     */
    public void setReceivedCryptoAddress(CryptoAddress receivedCryptoAddress);


    /**
     * Return the userId
     *
     * @return UUID
     */
    public UUID getUserId();

    /**
     * Set the userId
     *
     * @param userId user's id
     */
    public void setUserId(UUID userId);


    /**
     * Return the userName
     *
     * @return String
     */
    public String getUserName();

    /**
     * Set the userName
     *
     * @param userName user's name
     */
    public void setUserName(String userName);
}
