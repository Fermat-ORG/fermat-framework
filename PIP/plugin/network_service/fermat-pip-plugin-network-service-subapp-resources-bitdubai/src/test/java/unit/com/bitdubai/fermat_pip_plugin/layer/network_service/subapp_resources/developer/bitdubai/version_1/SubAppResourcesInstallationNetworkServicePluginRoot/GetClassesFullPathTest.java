package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 30/09/15.
 */
public class GetClassesFullPathTest {
//
    private SubAppResourcesInstallationNetworkServicePluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new SubAppResourcesInstallationNetworkServicePluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}