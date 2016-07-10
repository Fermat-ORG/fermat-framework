package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesNetworkServicePluginRoot;

import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 30/09/15.
 */
public class GetClassesFullPathTest {
    //
    private SubAppResourcesNetworkServicePluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new SubAppResourcesNetworkServicePluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}