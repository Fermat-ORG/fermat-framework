package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletProviderSettingImpl;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.QuoteImpl;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import unit.common.ParentTestClass;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


/**
 * Created by José Vilchez on 22/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetQuoteTest extends ParentTestClass {
    private List<CryptoBrokerWalletProviderSetting> associatedProviders;
    private ArrayList<CurrencyExchangeRateProviderManager> exchangeRateProviderManagers;

    @Mock
    CurrencyExchangeProviderFilterManager providerFilter;

    @Override
    public void setUp() {
        super.setUp();
        cryptoBrokerWalletDatabaseDaoSpy.setProviderFilter(providerFilter);
        associatedProviders = new ArrayList<>();
        exchangeRateProviderManagers = new ArrayList<>();

    }

    @Test
    public void getQuote() throws Exception {
        final float salePrice = 1150.0f;
        final float purchasePrice = 1050.0f;
        final float frozenAvailableBalance = 0.0f;
        final ExchangeRateProviderManagerMock usdVefProviderManager = new ExchangeRateProviderManagerMock(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR, salePrice, purchasePrice);
        final UUID usdVefProviderId = usdVefProviderManager.getProviderId();


        associatedProviders.add(new CryptoBrokerWalletProviderSettingImpl(usdVefProviderId, FiatCurrency.US_DOLLAR.getCode(), FiatCurrency.VENEZUELAN_BOLIVAR.getCode()));
        doReturn(associatedProviders).when(cryptoBrokerWalletDatabaseDaoSpy).getCryptoBrokerWalletProviderSettings();

        exchangeRateProviderManagers.add(usdVefProviderManager);
        when(providerFilter.getProviderReferencesFromCurrencyPair(new CurrencyPairImpl(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR))).thenReturn(exchangeRateProviderManagers);

        when(providerFilter.getProviderReference(usdVefProviderId)).thenReturn(usdVefProviderManager);

        doReturn(frozenAvailableBalance).when(cryptoBrokerWalletDatabaseDaoSpy).getBalanceFrozenByMerchandise(FiatCurrency.US_DOLLAR, null, BalanceType.AVAILABLE, salePrice);


        final Quote resultQuote = cryptoBrokerWalletDatabaseDaoSpy.getQuote(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR);


        final Quote expectedQuote = new QuoteImpl(FiatCurrency.US_DOLLAR, FiatCurrency.VENEZUELAN_BOLIVAR, salePrice, 0.0f);
        assertThat(resultQuote).isNotNull();
        assertThat(resultQuote.getMerchandise()).isEqualTo(expectedQuote.getMerchandise());
        assertThat(resultQuote.getFiatCurrency()).isEqualTo(expectedQuote.getFiatCurrency());
        assertThat(resultQuote.getPriceReference()).isEqualTo(expectedQuote.getPriceReference());
        assertThat(resultQuote.getQuantity()).isEqualTo(expectedQuote.getQuantity());
    }
}
