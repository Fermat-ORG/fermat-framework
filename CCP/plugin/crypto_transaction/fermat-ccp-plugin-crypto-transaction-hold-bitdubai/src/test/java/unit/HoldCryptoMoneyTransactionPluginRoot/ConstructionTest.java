package unit.HoldCryptoMoneyTransactionPluginRoot;

import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.HoldCryptoMoneyTransactionPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @Test
    public void Construction_ValidParameters_NewObjectCreated(){

        HoldCryptoMoneyTransactionPluginRoot holdCryptoMoneyTransactionPluginRoot = new HoldCryptoMoneyTransactionPluginRoot();
        assertThat(holdCryptoMoneyTransactionPluginRoot).isNotNull();
    }

}
