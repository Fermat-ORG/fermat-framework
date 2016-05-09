package CryptoCustomerWalletModuleActorIdentityImpl;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantCreateMessageSignatureException;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleActorIdentityImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Roy on 6/02/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    private String alias = new String();
    private byte[] img = new byte[0];

    @Test
    public void Construction_ValidParameters_NewObjectCreated (){

        CryptoCustomerWalletModuleActorIdentityImpl cryptoCustomerWalletModuleActorIdentity = new CryptoCustomerWalletModuleActorIdentityImpl(
                this.alias,
                this.img
        );
        assertThat(cryptoCustomerWalletModuleActorIdentity).isNotNull();
    }
}
