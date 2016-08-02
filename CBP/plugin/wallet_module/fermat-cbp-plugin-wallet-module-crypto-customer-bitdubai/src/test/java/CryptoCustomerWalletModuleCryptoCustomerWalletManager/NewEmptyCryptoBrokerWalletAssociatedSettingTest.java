package CryptoCustomerWalletModuleCryptoCustomerWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantCreateNewActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantGetListPlatformsException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.exceptions.CantUpdateActorExtraDataException;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraData;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.ActorExtraDataManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.QuotesExtraData;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantNewEmptyCryptoCustomerWalletAssociatedSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerWalletModuleCryptoCustomerWalletManager;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by roy on 9/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewEmptyCryptoBrokerWalletAssociatedSettingTest {
    @Test
    public void newEmptyCryptoBrokerWalletAssociatedSetting() throws CantNewEmptyCryptoCustomerWalletAssociatedSettingException{
        CryptoCustomerWalletModuleCryptoCustomerWalletManager cryptoCustomerWalletModuleCryptoCustomerWalletManager = mock(CryptoCustomerWalletModuleCryptoCustomerWalletManager.class);
        when(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCryptoBrokerWalletAssociatedSetting()).thenReturn(new CryptoCustomerWalletAssociatedSetting() {
            @Override
            public UUID getId() {
                return null;
            }

            @Override
            public void setId(UUID id) {

            }

            @Override
            public String getCustomerPublicKey() {
                return null;
            }

            @Override
            public void setCustomerPublicKey(String customerPublicKey) {

            }

            @Override
            public Platforms getPlatform() {
                return null;
            }

            @Override
            public void setPlatform(Platforms platform) {

            }

            @Override
            public String getWalletPublicKey() {
                return null;
            }

            @Override
            public void setWalletPublicKey(String walletPublicKey) {

            }

            @Override
            public FermatEnum getMerchandise() {
                return null;
            }

            @Override
            public void setMerchandise(FermatEnum merchandise) {

            }

            @Override
            public CurrencyType getCurrencyType() {
                return null;
            }

            @Override
            public void setCurrencyType(CurrencyType currencyType) {

            }

            @Override
            public String getBankAccount() {
                return null;
            }

            @Override
            public void setBankAccount(String bankAccount) {

            }
        });
        assertThat(cryptoCustomerWalletModuleCryptoCustomerWalletManager.newEmptyCryptoBrokerWalletAssociatedSetting()).isNotNull();
    }
}
