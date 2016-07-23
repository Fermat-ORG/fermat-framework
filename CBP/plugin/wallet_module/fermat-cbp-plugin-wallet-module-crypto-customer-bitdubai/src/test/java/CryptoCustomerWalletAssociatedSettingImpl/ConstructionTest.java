package CryptoCustomerWalletAssociatedSettingImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletAssociatedSettingImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by roy on 6/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    UUID id = UUID.randomUUID();
    String customerPublicKey = new String();
    Platforms platforms = Platforms.BANKING_PLATFORM;
    String walletPublicKey = new String();
    FermatEnum merchandise = new FermatEnum() {
        @Override
        public String getCode() {
            return null;
        }
    };
    String banckAccount = new String();
    CurrencyType currencyType = CurrencyType.BANK_MONEY;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoCustomerWalletAssociatedSettingImpl cryptoCustomerWalletAssociatedSetting = new CryptoCustomerWalletAssociatedSettingImpl();
        assertThat(cryptoCustomerWalletAssociatedSetting).isNotNull();
    }
}
