package unit.com.bitdubai.desktop.sub_app_manager.SubAppDesktopFragment;

import com.bitdubai.desktop.sub_app_manager.util.CbpSubAppListGenerator;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.InstalledSubApp;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 22/09/15.
 */
public class CreateSubAppsList {
    private List<InstalledSubApp> subAppList;

    @Before
    public void setUp() throws Exception {
        subAppList = null;
    }

    @Test
    public void returnListOfInstalledSubApps() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();
        assertThat(subAppList).isInstanceOf(List.class);
    }

    @Test
    public void oneInstalledSubAppIsCryptoBrokerIdentity() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();

        InstalledSubApp actualSubApp = getInstalledSubAppInSubAppListField(SubApps.CBP_CRYPTO_BROKER_IDENTITY);
        assertThat(actualSubApp).isNotNull();
    }

    @Test
    public void oneInstalledSubAppIsCryptoBrokerCommunity() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();

        InstalledSubApp actualSubApp = getInstalledSubAppInSubAppListField(SubApps.CBP_CRYPTO_BROKER_COMMUNITY);
        assertThat(actualSubApp).isNotNull();
    }

    @Test
    public void oneInstalledSubAppIsCryptoCustomersIdentity() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();

        InstalledSubApp actualSubApp = getInstalledSubAppInSubAppListField(SubApps.CBP_CRYPTO_CUSTOMER_IDENTITY);
        assertThat(actualSubApp).isNotNull();
    }

    @Test
    public void oneInstalledSubAppIsCryptoCustomerCommunity() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();

        InstalledSubApp actualSubApp = getInstalledSubAppInSubAppListField(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY);
        assertThat(actualSubApp).isNotNull();
    }

    @Test
    public void oneInstalledSubAppIsCustomers() {
        subAppList = CbpSubAppListGenerator.instance.createSubAppsList();

        InstalledSubApp actualSubApp = getInstalledSubAppInSubAppListField(SubApps.CBP_CUSTOMERS);
        assertThat(actualSubApp).isNotNull();
    }

    private InstalledSubApp getInstalledSubAppInSubAppListField(SubApps subAppType) {
        InstalledSubApp actualSubApp = null;

        for (InstalledSubApp subApp : subAppList) {
            if (subApp.getSubAppType() == subAppType) {
                actualSubApp = subApp;
                break;
            }
        }
        return actualSubApp;
    }

}