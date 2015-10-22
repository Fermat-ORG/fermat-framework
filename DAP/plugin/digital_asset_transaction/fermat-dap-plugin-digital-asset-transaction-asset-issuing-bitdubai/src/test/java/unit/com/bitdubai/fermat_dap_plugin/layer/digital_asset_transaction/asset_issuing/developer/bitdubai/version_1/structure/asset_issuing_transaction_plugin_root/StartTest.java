package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.asset_issuing_transaction_plugin_root;

import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by frank on 22/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    AssetIssuingTransactionPluginRoot pluginRoot;

    @Before
    void setUp() {
        pluginRoot = new AssetIssuingTransactionPluginRoot();
    }

    @Test
    public void test1() {
        pluginRoot.start();
    }
}
