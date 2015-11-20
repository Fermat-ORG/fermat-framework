package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 09/09/15.
 */
public class GetClassesFullPathTest {

    private WalletResourcesNetworkServicePluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new WalletResourcesNetworkServicePluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}
