package unit.com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.WalletNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rodrigo on 8/14/15.
 */
public class xmlTests {

    @Test
    public void fromObjectToXml(){
        WalletNavigationStructure navigationStructure = new WalletNavigationStructure();
        navigationStructure.setPublicKey(new ECCKeyPair().getPublicKey());

        Activity startActivity = new Activity();
        startActivity.setType(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_COUPONS);
        MenuItem menuItem = new MenuItem("Item1", "icono1", Activities.CWP_WALLET_ADULTS_ALL_MONTHLY_DISCOUNT);
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(menuItem);
        MainMenu mainMenu = new MainMenu(menuItems);
        startActivity.setMainMenu(mainMenu);
        navigationStructure.setStartActivity(Activities.CWP_SHELL_LOGIN);
        Map<Activities, Activity> activities = new HashMap<>();
        activities.put(Activities.CWP_SHELL_LOGIN, startActivity);
        navigationStructure.setActivities(activities);


        String xml =XMLParser.parseObject(navigationStructure);
        System.out.println(xml);
    }
}
