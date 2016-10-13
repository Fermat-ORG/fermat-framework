package unit.com.bitdubai.reference_wallet.crypto_broker_wallet.util;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.NavigationStructureFactory;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by nelson on 29/09/15.
 */
public class NavigationStructureFactoryTest {

    @Test
    public void testCreateNavigationStructure() throws Exception {
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();
        assertThat(navigationStructure).isNotNull();

        int expectedActivities = 4;
        assertThat(navigationStructure.getActivities()).hasSize(expectedActivities);

        Activity startActivity = navigationStructure.getStartActivity();
        assertThat(startActivity).isNotNull();

        Activities activityType = Activities.CBP_CRYPTO_BROKER_WALLET_HOME;
        assertThat(startActivity.getType()).isNotNull().isEqualTo(activityType);
        assertThat(startActivity.getActivityType()).isNotNull().isEqualTo(activityType.getCode());
    }

    @Test
    public void testHomeActivityIsWellConfigured() {
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();

        Activities activityType = Activities.CBP_CRYPTO_BROKER_WALLET_HOME;
        Activity activity = navigationStructure.getActivity(activityType);

        assertThat(activity.getType()).isNotNull().isEqualTo(activityType);
        assertThat(activity.getActivityType()).isNotNull().isEqualTo(activityType.getCode());
        assertThat(activity.getFragments()).isNotNull().hasSize(6);
        assertThat(activity.getTitleBar()).isNotNull();
        assertThat(activity.getStatusBar()).isNotNull();

        TabStrip tabStrip = activity.getTabStrip();

        assertThat(tabStrip).isNotNull();
        assertThat(tabStrip.getTabs()).isNotNull().hasSize(3);
    }

    @Test
    public void testDealsActivityIsWellConfigured() {
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();

        Activities activityType = Activities.CBP_CRYPTO_BROKER_WALLET_CONTRACTS_HISTORY;
        Activity activity = navigationStructure.getActivity(activityType);

        assertThat(activity.getType()).isNotNull().isEqualTo(activityType);
        assertThat(activity.getActivityType()).isNotNull().isEqualTo(activityType.getCode());
        assertThat(activity.getFragments()).isNotNull().hasSize(2);
        assertThat(activity.getTitleBar()).isNotNull();
        assertThat(activity.getStatusBar()).isNotNull();
    }

    @Test
    public void testStockPreferenceActivityIsWellConfigured() {
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();

        Activities activityType = Activities.CBP_CRYPTO_BROKER_WALLET_EARNINGS;
        Activity activity = navigationStructure.getActivity(activityType);

        assertThat(activity.getType()).isNotNull().isEqualTo(activityType);
        assertThat(activity.getActivityType()).isNotNull().isEqualTo(activityType.getCode());
        assertThat(activity.getFragments()).isNotNull().hasSize(1);
        assertThat(activity.getTitleBar()).isNotNull();
        assertThat(activity.getStatusBar()).isNotNull();
    }

    @Test
    public void testSettingsActivityIsWellConfigured() {
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();

        Activities activityType = Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS;
        Activity activity = navigationStructure.getActivity(activityType);

        assertThat(activity.getType()).isNotNull().isEqualTo(activityType);
        assertThat(activity.getActivityType()).isNotNull().isEqualTo(activityType.getCode());
        assertThat(activity.getFragments()).isNotNull().hasSize(1);
        assertThat(activity.getTitleBar()).isNotNull();
        assertThat(activity.getStatusBar()).isNotNull();
    }

    @Test
    public void testSideBarMenuIsWellConfigured(){
        AppNavigationStructure navigationStructure = NavigationStructureFactory.createNavigationStructure();
        Activity startActivity = navigationStructure.getStartActivity();
        List<MenuItem> menuItems = startActivity.getSideMenu().getMenuItems();

        assertThat(menuItems).isNotNull().hasSize(5);
        assertThat(menuItems.get(0).getLinkToActivity()).isEqualTo(Activities.CBP_CRYPTO_BROKER_WALLET_HOME);
    }

    @Test
    public void testGetNavigationStructureAsXmlString() throws Exception {
        String xmlString = NavigationStructureFactory.getNavigationStructureAsXmlString();
        assertThat(xmlString).isNotNull();

        System.out.println(xmlString);
    }
}