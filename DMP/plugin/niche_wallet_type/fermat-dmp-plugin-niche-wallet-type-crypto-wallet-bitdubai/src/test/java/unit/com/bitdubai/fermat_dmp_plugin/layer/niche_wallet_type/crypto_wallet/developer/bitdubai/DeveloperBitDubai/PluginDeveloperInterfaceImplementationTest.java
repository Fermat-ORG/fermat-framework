package unit.com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.DeveloperBitDubai;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PluginDeveloperInterfaceImplementationTest extends TestCase {

    DeveloperBitDubai developerBitDubai;

    @Before
    public void setUp() throws Exception {
        developerBitDubai = new DeveloperBitDubai();
    }

    @Test
    public void testGetPlugin_NotNull() throws Exception {
        Plugin plugin = developerBitDubai.getPlugin();
        assertNotNull(plugin);
    }
}
