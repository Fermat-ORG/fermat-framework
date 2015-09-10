package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 10/09/15.
 */
public class DeveloperBitDubaiTest extends TestCase {

    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void getPluging() {
        assertThat(developTest.getPlugin()).isInstanceOf(WalletContactsMiddlewarePluginRoot.class);
    }
}
