package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 10/09/15.
 */
public class GetLogLevelByClassTest {
    private WalletContactsMiddlewarePluginRoot pluginRoot;

    @Test
    public void getLogLevelByClassTest() throws CantStartPluginException {

        pluginRoot = new WalletContactsMiddlewarePluginRoot();

        assertThat(pluginRoot.getLogLevelByClass("WalletContactsMiddlewarePluginRoot")).isNull();
    }
}

