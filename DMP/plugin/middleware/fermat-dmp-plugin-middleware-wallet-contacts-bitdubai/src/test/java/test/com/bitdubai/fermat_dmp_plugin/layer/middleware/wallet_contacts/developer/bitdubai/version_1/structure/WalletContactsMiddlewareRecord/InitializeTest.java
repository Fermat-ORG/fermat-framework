package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;
import java.util.UUID;

import org.junit.Test;

/**
 * Created by Nerio on 24/07/15.
 *
 */
public class InitializeTest {

    WalletContactsMiddlewareRecord walletContactsMiddlewareDatabaseConstants;

    @Test
    public void initializeTest (){
        CryptoAddress goodAddress = new CryptoAddress("mmxw6KZzLEXfnvRSVkNUkg5TrG19DiBeaD", CryptoCurrency.BITCOIN);
        walletContactsMiddlewareDatabaseConstants = new WalletContactsMiddlewareRecord(UUID.randomUUID(),"Luis",Actors.DEVICE_USER,UUID.randomUUID(),goodAddress, new ECCKeyPair().getPublicKey());

        assert walletContactsMiddlewareDatabaseConstants.getActorId() != null;
        assert walletContactsMiddlewareDatabaseConstants.getActorName() != null;
        assert walletContactsMiddlewareDatabaseConstants.getActorType() != null;
        assert walletContactsMiddlewareDatabaseConstants.getContactId() != null;
        assert walletContactsMiddlewareDatabaseConstants.getReceivedCryptoAddress() != null;
        assert walletContactsMiddlewareDatabaseConstants.getWalletPublicKey() != null;


        walletContactsMiddlewareDatabaseConstants = new WalletContactsMiddlewareRecord(UUID.randomUUID(), goodAddress, "Luis");

        assert walletContactsMiddlewareDatabaseConstants.getContactId() != null;
        assert walletContactsMiddlewareDatabaseConstants.getReceivedCryptoAddress() != null;
        assert walletContactsMiddlewareDatabaseConstants.getActorName() != null;
    }
}