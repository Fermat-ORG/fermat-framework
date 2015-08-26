package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;


import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Nerio on 24/07/15.
 */
public class initializateTest {

    /**
     * Represent the contactId
     */
    UUID contactId;

    /**
     * Represent the receivedCryptoAddress
     * address that the system gives to another actor to send this wallet money
     */
    CryptoAddress receivedCryptoAddress;

    /**
     * Represent the id of the wallet
     */
    UUID walletId;

    /**
     * Represent the actorId
     */
    UUID actorId;

    /**
     * Represent the actorName
     */
    String actorName;

    /**
     * Represent the actorType
     */
    Actors actorType;

    WalletContactsMiddlewareRecord walletContactsMiddlewareDatabaseConstants;

    @Test
    public void initializateTest (){
        CryptoAddress goodAddress = new CryptoAddress("mmxw6KZzLEXfnvRSVkNUkg5TrG19DiBeaD", CryptoCurrency.BITCOIN);
        walletContactsMiddlewareDatabaseConstants = new WalletContactsMiddlewareRecord(UUID.randomUUID(),"Luis",Actors.DEVICE_USER,UUID.randomUUID(),goodAddress, new ECCKeyPair().getPublicKey());

        walletContactsMiddlewareDatabaseConstants.getActorId();
        walletContactsMiddlewareDatabaseConstants.getActorName();
        walletContactsMiddlewareDatabaseConstants.getActorType();
        walletContactsMiddlewareDatabaseConstants.getContactId();
        walletContactsMiddlewareDatabaseConstants.getReceivedCryptoAddress();
        walletContactsMiddlewareDatabaseConstants.getWalletPublicKey();


        walletContactsMiddlewareDatabaseConstants = new WalletContactsMiddlewareRecord(UUID.randomUUID(),goodAddress,"Luis");

        walletContactsMiddlewareDatabaseConstants.getContactId();
        walletContactsMiddlewareDatabaseConstants.getReceivedCryptoAddress();
        walletContactsMiddlewareDatabaseConstants.getActorId();
    }
}