package com.bitdubai.fermat_dmp_plugin.layer._15_middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.wallet_contacts.WalletContact;
import com.bitdubai.fermat_api.layer._1_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer._5_user.UserTypes;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._15_middleware.wallet_contacts.developer.bitdubai.version_1.structure.CryptoWalletContact</code>
 * is the implementation of the representation of a Crypto Wallet Contact in the platform
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletContact implements WalletContact {

    /**
     * Represent the contactId
     */
    UUID contactId;

    /**
     * Represent the deliveredCryptoAddress
     * address that the system gives to another user to send this wallet money
     */
    CryptoAddress deliveredCryptoAddress;

    /**
     * Represent the receivedCryptoAddress
     * address that the system gives to another user to send this wallet money
     */
    CryptoAddress receivedCryptoAddress;

    /**
     * Represent the id of the wallet
     */
    UUID walletId;

    /**
     * Represent the userId
     */
    UUID userId;

    /**
     * Represent the userName
     */
    String userName;

    /**
     * Represent the userType
     */
    UserTypes userType;


    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param userName user's id
     */
    public CryptoWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String userName) {
        this.contactId = contactId;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.userName = userName;
    }

    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param deliveredCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param userId user's id
     * @param userName user's id
     * @param userType user's type
     * @param walletId wallet's id
     */
    public CryptoWalletContact(UUID contactId, CryptoAddress deliveredCryptoAddress, CryptoAddress receivedCryptoAddress, UUID userId, String userName, UserTypes userType, UUID walletId) {
        this.contactId = contactId;
        this.deliveredCryptoAddress = deliveredCryptoAddress;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.userId = userId;
        this.userName = userName;
        this.userType = userType;
        this.walletId = walletId;

    }

    /**
     * Return the contactId
     *
     * @return UUID
     */
    @Override
    public UUID getContactId() {
        return contactId;
    }

    /**
     * Set the contactId
     *
     * @param contactId contact's id
     */
    @Override
    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    /**
     * Return the walletId
     *
     * @return UUID
     */
    @Override
    public UUID getWalletId() {
        return walletId;
    }

    /**
     * Set the walletId
     *
     * @param walletId wallet's id
     */
    @Override
    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    /**
     * Return the userType
     *
     * @return UserTypes
     */
    @Override
    public UserTypes getUserType() {
        return userType;
    }

    /**
     * Set the userType
     *
     * @param userType user's type
     */
    @Override
    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    @Override
    public CryptoAddress getDeliveredCryptoAddress() {
        return deliveredCryptoAddress;
    }

    /**
     * Set the deliveredCryptoAddress
     *
     * @param deliveredCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     */
    @Override
    public void setDeliveredCryptoAddress(CryptoAddress deliveredCryptoAddress) {
        this.deliveredCryptoAddress = deliveredCryptoAddress;
    }

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    @Override
    public CryptoAddress getReceivedCryptoAddress() {
        return receivedCryptoAddress;
    }

    /**
     * Set the receivedCryptoAddress
     *
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     */
    @Override
    public void setReceivedCryptoAddress(CryptoAddress receivedCryptoAddress) {
        this.receivedCryptoAddress = receivedCryptoAddress;
    }

    /**
     * Return the userId
     *
     * @return UUID
     */
    @Override
    public UUID getUserId() {
        return userId;
    }

    /**
     * Set the userId
     *
     * @param userId user's id
     */
    @Override
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    /**
     * Return the userName
     *
     * @return String
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * Set the userName
     *
     * @param userName user's name
     */
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }
}