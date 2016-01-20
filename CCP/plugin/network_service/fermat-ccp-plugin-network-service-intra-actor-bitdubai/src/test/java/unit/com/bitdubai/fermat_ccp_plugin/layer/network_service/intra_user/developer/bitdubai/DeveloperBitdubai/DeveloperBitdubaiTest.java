package unit.com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.DeveloperBitdubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.DeveloperBitDubai;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
/**
 * Created by Joaquin C. on 18/12/15.
 */
public class DeveloperBitdubaiTest {
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
