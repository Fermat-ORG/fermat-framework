package unit.com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContructorTest extends TestCase {

    DeveloperBitDubai developerBitDubai;

    @Test
    public void testConstructor_NotNull() throws Exception {
        developerBitDubai = new DeveloperBitDubai();
        assertNotNull(developerBitDubai);
    }
}
