package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PluginLicensorInterfaceImplementationTest extends TestCase {

    DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp() throws Exception {
        developerBitDubai = new DeveloperBitDubai();
    }

    @Test
    public void testGetAmountToPay_NotNull() throws Exception {
        int amountToPay = developerBitDubai.getAmountToPay();
        assertNotNull(amountToPay);
    }

    @Test
    public void testGetCryptoCurrency_NotNull() throws Exception {
        CryptoCurrency cryptoCurrency = developerBitDubai.getCryptoCurrency();
        assertNotNull(cryptoCurrency);
    }

    @Test
    public void testGetAddress_NotNull() throws Exception {
        String address = developerBitDubai.getAddress();
        assertNotNull(address);
    }

    @Test
    public void testGetTimePeriod_NotNull() throws Exception {
        TimeFrequency timePeriod = developerBitDubai.getTimePeriod();
        assertNotNull(timePeriod);
    }
}
