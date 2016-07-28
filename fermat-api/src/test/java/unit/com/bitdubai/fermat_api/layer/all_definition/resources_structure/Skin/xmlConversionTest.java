package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Created by rodrigo on 2015.07.24..
 */
public class xmlConversionTest extends TestCase {

    public void testcreateResult() {

//        RuntimeSubApp runtimeSubApp = new RuntimeSubApp();
//        runtimeSubApp.setType(SubApps.CWP_SHELL);
//        TitleBar runtimeTitleBar;
//        SideMenu runtimeSideMenu;
//        OptionsMenu runtimeMainMenu;
//        MenuItem runtimeMenuItem;
//        TabStrip runtimeTabStrip;
//        StatusBar statusBar;
//
//        Tab runtimeTab;
//
//
//        //wallet factory app
//        runtimeSubApp = new RuntimeSubApp();
//        runtimeSubApp.setType(SubApps.CWP_WALLET_FACTORY);
//        runtimeApp.addSubApp(runtimeSubApp);
//        listSubApp.put(SubApps.CWP_WALLET_FACTORY, runtimeSubApp);
//
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_MAIN);
//        runtimeActivity.setColor("#b46a54");
//        //runtimeActivity.setStatusBarColor("");
//
//        statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
//        statusBar.setColor("#b46a54");
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Wallet Factory");
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        runtimeTabStrip = new TabStrip();
//        runtimeTabStrip.setTabsColor("#d07b62");
//        runtimeTabStrip.setTabsTextColor("#FFFFFF");
//        runtimeTabStrip.setTabsIndicateColor("#b46a54");
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Developer Projects");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_MANAGER);
//
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Wallet Projects");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_PROJECTS);
//
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("EditMode");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_FACTORY_EDIT_MODE);
//
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeActivity.setTabStrip(runtimeTabStrip);
//        runtimeSubApp.addActivity(runtimeActivity);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_MANAGER);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_MANAGER, runtimeFragment);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_PROJECTS);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_PROJECTS, runtimeFragment);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_FACTORY_EDIT_MODE);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_FACTORY_EDIT_MODE, runtimeFragment);
//
//    /* Adding WizardTypes */
//        Wizard runtimeWizard = new Wizard();
//        // step 1 wizard create from scratch
//        WizardPage runtimeWizardPage = new WizardPage();
//        runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_1);
//        runtimeWizard.addPage(runtimeWizardPage);
//        //step 2 wizard
//        runtimeWizardPage = new WizardPage();
//        runtimeWizardPage.setType(WizardPageTypes.CWP_WALLET_FACTORY_CREATE_STEP_2);
//        runtimeWizard.addPage(runtimeWizardPage);
//            /* Adding wizard */
//        runtimeActivity.addWizard(WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT, runtimeWizard);
//
//        /**
//         *  Edit Activity
//         */
//        runtimeActivity = new Activity();
//        runtimeActivity.setType(Activities.CWP_WALLET_FACTORY_EDIT_WALLET);
//        runtimeActivity.setColor("#b46a54");
//
//        statusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
//        statusBar.setColor("#b46a54");
//
//        runtimeTitleBar = new TitleBar();
//        runtimeTitleBar.setLabel("Edit Wallet");
//        runtimeActivity.setTitleBar(runtimeTitleBar);
//
//        runtimeTabStrip = new TabStrip();
//
//        runtimeTabStrip.setTabsColor("#8bba9e");
//
//        runtimeTabStrip.setTabsTextColor("#FFFFFF");
//
//        runtimeTabStrip.setTabsIndicateColor("#72af9c");
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Balance");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTab = new Tab();
//        runtimeTab.setLabel("Contacts");
//        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
//        runtimeTabStrip.addTab(runtimeTab);
//
//        runtimeTabStrip.setDividerColor(0x72af9c);
//        //runtimeTabStrip.setBackgroundColor("#72af9c");
//        runtimeActivity.setTabStrip(runtimeTabStrip);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE, runtimeFragment);
//
//        runtimeFragment = new Fragment();
//        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
//        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS, runtimeFragment);
//
//
//        runtimeSubApp.addActivity(runtimeActivity);


        /**End Wallet Factory*/

        Skin skin = new Skin();

        skin.setId(UUID.randomUUID());

        skin.setName("default");

        skin.setScreenSize(ScreenSize.MEDIUM);

        skin.setVersion(new Version(1, 0, 0));

        Map<String, Layout> layoutHashMap = new HashMap<String, Layout>();
        Layout layout;

        layout = new Layout(UUID.randomUUID(), "payment_request_base.xml", "payment_request_base");

        layoutHashMap.put("payment_request_base", layout);

        layout = new Layout(UUID.randomUUID(), "pending_request_row.xml", "pending_request_row");

        layoutHashMap.put("pending_request_row", layout);

        layout = new Layout(UUID.randomUUID(), "pending_request_row2.xml", "pending_request_row2");

        layoutHashMap.put("pending_request_row2", layout);


        skin.setPortraitLayouts(layoutHashMap);

        Map<String, Resource> resourceHashMap = new HashMap<String, Resource>();

        Resource resource;

        resource = new Resource();

        resource.setId(UUID.randomUUID());
        resource.setName("person1");
        resource.setFileName("person1.png");
        resource.setResourceDensity(ResourceDensity.MDPI);
        resource.setResourceType(ResourceType.IMAGE);

        resourceHashMap.put("person1", resource);

        skin.setResources(resourceHashMap);

        try {
            skin.setNavigationStructureCompatibility(new VersionCompatibility(new Version(1, 0, 0), new Version(1, 0, 0)));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }


        System.out.println(XMLParser.parseObject(skin));

    }

}
