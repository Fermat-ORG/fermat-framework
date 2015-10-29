/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

*/
/**
 * Created by natalia on 11/09/15.
 *//*

public class GetterTest {

    private WalletContactsMiddlewareRecord  walletContactsMiddlewareRecord;

    private List<CryptoAddress> cryptoAddresses = new ArrayList<CryptoAddress>();


    @Test
    public void getsTest() throws Exception {

        walletContactsMiddlewareRecord = new WalletContactsMiddlewareRecord(UUID.randomUUID(),
                "actorPublicKey",
                "actorAlias",
                "actorFirstName",
                "actorLastName",
                Actors.DEVICE_USER,
                cryptoAddresses,"walletPublicKey");

        assertThat(walletContactsMiddlewareRecord.getWalletPublicKey()).isEqualTo("walletPublicKey");

        assertThat(walletContactsMiddlewareRecord.getActorType()).isEqualTo(Actors.DEVICE_USER);

        assertThat(walletContactsMiddlewareRecord.getActorPublicKey()).isEqualTo("actorPublicKey");

        assertThat(walletContactsMiddlewareRecord.getCryptoAddresses()).isEqualTo(cryptoAddresses);

    }

    @Test
    public void getsTest_Constructor2() throws Exception {

        UUID contactId = UUID.randomUUID();
        walletContactsMiddlewareRecord = new WalletContactsMiddlewareRecord(contactId,
                "actorAlias",
                "actorFirstName",
                "actorLastName",
                cryptoAddresses);

        assertThat(walletContactsMiddlewareRecord.getActorAlias()).isEqualTo("actorAlias");

        assertThat(walletContactsMiddlewareRecord.getActorFirstName()).isEqualTo("actorFirstName");

        assertThat(walletContactsMiddlewareRecord.getActorLastName()).isEqualTo("actorLastName");

        assertThat(walletContactsMiddlewareRecord.getContactId()).isEqualTo(contactId);

    }




}

*/
