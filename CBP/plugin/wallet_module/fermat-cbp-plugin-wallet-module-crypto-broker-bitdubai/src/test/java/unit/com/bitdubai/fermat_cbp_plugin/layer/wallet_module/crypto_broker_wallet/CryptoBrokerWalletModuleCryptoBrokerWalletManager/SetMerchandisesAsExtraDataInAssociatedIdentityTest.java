package unit.com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker_wallet.CryptoBrokerWalletModuleCryptoBrokerWalletManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyWalletManager;
import com.bitdubai.fermat_cbp_api.layer.actor.crypto_broker.interfaces.CryptoBrokerActorManager;
import com.bitdubai.fermat_cbp_api.layer.actor_connection.crypto_customer.interfaces.CryptoCustomerActorConnectionManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_offline_payment.interfaces.BrokerAckOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_ack_online_payment.interfaces.BrokerAckOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_offline_merchandise.interfaces.BrokerSubmitOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.broker_submit_online_merchandise.interfaces.BrokerSubmitOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_offline_merchandise.interfaces.CustomerAckOfflineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_ack_online_merchandise.interfaces.CustomerAckOnlineMerchandiseManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_offline_payment.interfaces.CustomerOfflinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.customer_online_payment.interfaces.CustomerOnlinePaymentManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.ExposureLevel;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityExtraData;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentityManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.interfaces.CustomerBrokerCloseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_update.interfaces.CustomerBrokerUpdateManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_destock.interfaces.BankMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.bank_money_restock.interfaces.BankMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_destock.interfaces.CashMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.cash_money_restock.interfaces.CashMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_destock.interfaces.CryptoMoneyDestockManager;
import com.bitdubai.fermat_cbp_api.layer.stock_transactions.crypto_money_restock.interfaces.CryptoMoneyRestockManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerWalletModuleCryptoBrokerWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


/**
 * Created by nelsonalfo on 13/07/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetMerchandisesAsExtraDataInAssociatedIdentityTest {
    final String BROKER_IDENTITY_PUBLIC_KEY = "broker_public_key";
    final String WALLET_PUBLIC_KEY = WalletsPublicKeys.CBP_CRYPTO_BROKER_WALLET.getCode();

    CryptoBrokerWalletModuleManager moduleManagerSpy;


    @Before
    public void setUp() {
        moduleManagerSpy = spy(new CryptoBrokerWalletModuleCryptoBrokerWalletManager(
                walletManagerManager,
                cryptoBrokerWalletManager,
                bankMoneyWalletManager,
                customerBrokerSaleNegotiationManager,
                bankMoneyRestockManager,
                cashMoneyRestockManager,
                cryptoMoneyRestockManager,
                cashMoneyWalletManager,
                bankMoneyDestockManager,
                cashMoneyDestockManager,
                cryptoMoneyDestockManager,
                customerBrokerContractSaleManager,
                currencyExchangeProviderFilterManager,
                cryptoBrokerIdentityManager,
                customerBrokerUpdateManager,
                cryptoWalletManager,
                cryptoBrokerActorManager,
                customerOnlinePaymentManager,
                customerOfflinePaymentManager,
                customerAckOnlineMerchandiseManager,
                customerAckOfflineMerchandiseManager,
                brokerAckOfflinePaymentManager,
                brokerAckOnlinePaymentManager,
                brokerSubmitOfflineMerchandiseManager,
                brokerSubmitOnlineMerchandiseManager,
                intraWalletUserIdentityManager,
                matchingEngineManager,
                customerBrokerCloseManager,
                cryptoCustomerActorConnectionManager,
                pluginFileSystem,
                null, broadcaster));
    }

    @Test
    public void updateExtraDataWithThreeWalletsAndThreeDifferentMerchandises() throws Exception {

        // setup
        CryptoBrokerIdentity baseIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY, "testAlias",
                new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10);
        doReturn(baseIdentity).when(moduleManagerSpy).getAssociatedIdentity(WALLET_PUBLIC_KEY);

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.ARGENTINE_PESO));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias", new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10, CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD, ARS");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));


        // setup
        associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.ARGENTINE_PESO));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias", new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10, FiatCurrency.US_DOLLAR, CryptoCurrency.BITCOIN,
                "Merchandises: USD, BTC, ARS");

        expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
    }

    @Test
    public void updateExtraDataWithThreeWalletsAndTwoDifferentMerchandises() throws Exception {

        // setup
        final CryptoBrokerIdentity baseIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY, "testAlias",
                new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10);
        doReturn(baseIdentity).when(moduleManagerSpy).getAssociatedIdentity(WALLET_PUBLIC_KEY);

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        final CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias", new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10, CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
    }

    @Test
    public void updateExtraDataWithTwoWalletsAndTwoDifferentMerchandises() throws Exception {

        // setup
        final CryptoBrokerIdentity baseIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY, "testAlias",
                new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10);
        doReturn(baseIdentity).when(moduleManagerSpy).getAssociatedIdentity(WALLET_PUBLIC_KEY);

        List<CryptoBrokerWalletAssociatedSetting> associatedWallets = new ArrayList<>();
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(CryptoCurrency.BITCOIN));
        associatedWallets.add(new CryptoBrokerWalletAssociatedSettingMock(FiatCurrency.US_DOLLAR));
        doReturn(associatedWallets).when(moduleManagerSpy).getCryptoBrokerWalletAssociatedSettings(WALLET_PUBLIC_KEY);

        // exercise
        CryptoBrokerIdentity updatedIdentity = moduleManagerSpy.setMerchandisesAsExtraDataInAssociatedIdentity();

        // assert
        final CryptoBrokerIdentity expectedIdentity = new CryptoBrokerWalletModuleCryptoBrokerIdentity(BROKER_IDENTITY_PUBLIC_KEY,
                "testAlias", new byte[0], ExposureLevel.PUBLISH, GeoFrequency.NORMAL, 10, CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR,
                "Merchandises: BTC, USD");

        CryptoBrokerIdentityExtraData expectedExtraData = expectedIdentity.getCryptoBrokerIdentityExtraData();
        CryptoBrokerIdentityExtraData updatedExtraData = updatedIdentity.getCryptoBrokerIdentityExtraData();

        assertThat(updatedIdentity.getAccuracy()).isEqualTo(expectedIdentity.getAccuracy());
        assertThat(updatedIdentity.getAlias()).isEqualTo(expectedIdentity.getAlias());
        assertThat(updatedIdentity.getExposureLevel()).isEqualTo(expectedIdentity.getExposureLevel());
        assertThat(updatedIdentity.getFrequency()).isEqualTo(expectedIdentity.getFrequency());
        assertThat(updatedIdentity.getProfileImage()).isEqualTo(expectedIdentity.getProfileImage());
        assertThat(updatedIdentity.getPublicKey()).isEqualTo(expectedIdentity.getPublicKey());
        assertThat(updatedExtraData.getExtraText()).isEqualTo(expectedExtraData.getExtraText());
        assertThat(updatedExtraData.getMerchandise()).isEqualTo(expectedExtraData.getMerchandise());
        assertThat(updatedExtraData.getPaymentCurrency()).isEqualTo(expectedExtraData.getPaymentCurrency());

        verify(cryptoBrokerIdentityManager).updateCryptoBrokerIdentity(eq(updatedIdentity));
    }

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    WalletManagerManager walletManagerManager;

    @Mock
    com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager cryptoBrokerWalletManager;

    @Mock
    BankMoneyWalletManager bankMoneyWalletManager;

    @Mock
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @Mock
    BankMoneyRestockManager bankMoneyRestockManager;

    @Mock
    CashMoneyRestockManager cashMoneyRestockManager;

    @Mock
    CryptoMoneyRestockManager cryptoMoneyRestockManager;

    @Mock
    CashMoneyWalletManager cashMoneyWalletManager;

    @Mock
    BankMoneyDestockManager bankMoneyDestockManager;

    @Mock
    CashMoneyDestockManager cashMoneyDestockManager;

    @Mock
    CryptoMoneyDestockManager cryptoMoneyDestockManager;

    @Mock
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;

    @Mock
    CurrencyExchangeProviderFilterManager currencyExchangeProviderFilterManager;

    @Mock
    CustomerBrokerUpdateManager customerBrokerUpdateManager;

    @Mock
    CryptoWalletManager cryptoWalletManager;

    @Mock
    CryptoBrokerActorManager cryptoBrokerActorManager;

    @Mock
    CustomerOnlinePaymentManager customerOnlinePaymentManager;

    @Mock
    CustomerOfflinePaymentManager customerOfflinePaymentManager;

    @Mock
    CustomerAckOnlineMerchandiseManager customerAckOnlineMerchandiseManager;

    @Mock
    CustomerAckOfflineMerchandiseManager customerAckOfflineMerchandiseManager;

    @Mock
    BrokerAckOfflinePaymentManager brokerAckOfflinePaymentManager;

    @Mock
    BrokerAckOnlinePaymentManager brokerAckOnlinePaymentManager;

    @Mock
    BrokerSubmitOfflineMerchandiseManager brokerSubmitOfflineMerchandiseManager;

    @Mock
    BrokerSubmitOnlineMerchandiseManager brokerSubmitOnlineMerchandiseManager;

    @Mock
    MatchingEngineManager matchingEngineManager;

    @Mock
    CustomerBrokerCloseManager customerBrokerCloseManager;

    @Mock
    IntraWalletUserIdentityManager intraWalletUserIdentityManager;

    @Mock
    CryptoBrokerIdentityManager cryptoBrokerIdentityManager;

    @Mock
    CryptoCustomerActorConnectionManager cryptoCustomerActorConnectionManager;
}
