package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 10/09/15.
 */
public class GetClassesFullPathTest {
    private WalletContactsMiddlewarePluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new WalletContactsMiddlewarePluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}


