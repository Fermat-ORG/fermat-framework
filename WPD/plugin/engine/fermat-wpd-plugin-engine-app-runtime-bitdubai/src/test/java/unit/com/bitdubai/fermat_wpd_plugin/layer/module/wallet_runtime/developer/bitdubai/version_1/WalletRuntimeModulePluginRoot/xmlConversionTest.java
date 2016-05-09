//package unit.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;
//
//import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.AppNavigationStructure;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
//import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
//import com.bitdubai.fermat_api.layer.all_definition.util.Version;
//import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
//import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
//
//import junit.framework.TestCase;
//import junit.framework.TestResult;
//
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
///*
//import org.junit.Test;
//
//import java.io.StringReader;
//import java.util.Map;
//import java.util.UUID;
//
//import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
//import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
//import ae.javax.xml.bind.JAXBContext;
//import ae.javax.xml.bind.Marshaller;
//import ae.javax.xml.bind.Unmarshaller;*/
//
///**
// * Created by Matias Furszyfer
// */
//
//public class xmlConversionTest extends TestCase {
//
//// TODO COMMENTED: NOT COMPILING.
//    public void testCreateNAvigationStructure() {
////        Activity runtimeActivity;
////        Fragment runtimeFragment;
////        AppNavigationStructure runtimeWalletNavigationStructure;
////        TitleBar runtimeTitleBar;
////        SideMenu runtimeSideMenu;
////        MainMenu runtimeMainMenu;
////        MenuItem runtimeMenuItem;
////        TabStrip runtimeTabStrip;
////        StatusBar runtimeStatusBar;
////
////        Tab runtimeTab;
////
////        String publicKey;
////
////        runtimeWalletNavigationStructure = new AppNavigationStructure();
////        publicKey="reference_wallet";
////        runtimeWalletNavigationStructure.setPublicKey(publicKey);
////        //listWallets.put(publicKey, runtimeWalletNavigationStructure);
////
////        runtimeActivity= new Activity();
////        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN);
////        runtimeActivity.setColor("#8bba9e");
////        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
////        runtimeWalletNavigationStructure.setStartActivity(runtimeActivity.getType());
////
////        runtimeTitleBar = new TitleBar();
////        runtimeTitleBar.setLabel("Fermat Bitcoin Reference Wallet");
////
////        runtimeActivity.setTitleBar(runtimeTitleBar);
////        runtimeActivity.setColor("#72af9c");
////        //runtimeActivity.setColor("#d07b62");
////
////
////        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
////        runtimeStatusBar.setColor("#72af9c");
////
////        runtimeActivity.setStatusBar(runtimeStatusBar);
////
////
////        runtimeTabStrip = new TabStrip();
////
////        runtimeTabStrip.setTabsColor("#8bba9e");
////
////        runtimeTabStrip.setTabsTextColor("#FFFFFF");
////
////        runtimeTabStrip.setTabsIndicateColor("#72af9c");
////
////        runtimeTab = new Tab();
////        runtimeTab.setLabel("Balance");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
////        runtimeTabStrip.addTab(runtimeTab);
////
////        /*runtimeTab = new Tab();
////        runtimeTab.setLabel("Send");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
////        runtimeTabStrip.addTab(runtimeTab);
////
////        runtimeTab = new Tab();
////        runtimeTab.setLabel("Receive");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
////        runtimeTabStrip.addTab(runtimeTab);
////
////        runtimeTab = new Tab();
////        runtimeTab.setLabel("Transactions");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
////        runtimeTabStrip.addTab(runtimeTab);
////
////
////        runtimeTab = new Tab();
////        runtimeTab.setLabel("Money request");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST);
////        runtimeTabStrip.addTab(runtimeTab);
////
////        */
////
////        runtimeTab = new Tab();
////        runtimeTab.setLabel("Contacts");
////        runtimeTab.setFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
////        runtimeTabStrip.addTab(runtimeTab);
////
////
////
////
////
////        runtimeTabStrip.setDividerColor(0x72af9c);
////        //runtimeTabStrip.setBackgroundColor("#72af9c");
////        runtimeActivity.setTabStrip(runtimeTabStrip);
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_BALANCE, runtimeFragment);
////
////
////        /*
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_SEND,runtimeFragment);
////
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_RECEIVE,runtimeFragment);
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS,runtimeFragment);
////
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_MONEY_REQUEST, runtimeFragment);
////
////        */
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_CONTACTS, runtimeFragment);
////
////
////        /**
////         * Transaction Activity
////         */
////
////        runtimeActivity= new Activity();
////        runtimeActivity.setType(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_TRANSACTIONS);
////        runtimeActivity.setColor("#8bba9e");
////        runtimeWalletNavigationStructure.addActivity(runtimeActivity);
////
////        runtimeTitleBar = new TitleBar();
////        runtimeTitleBar.setLabel("Fermat Bitcoin Reference Wallet");
////        runtimeActivity.setTitleBar(runtimeTitleBar);
////        runtimeActivity.setColor("#72af9c");
////        //runtimeActivity.setColor("#d07b62");
////
////
////        runtimeStatusBar = new com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar();
////        runtimeStatusBar.setColor("#72af9c");
////
////        runtimeActivity.setStatusBar(runtimeStatusBar);
////
////        runtimeActivity.setStartFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
////
////        runtimeFragment = new Fragment();
////        runtimeFragment.setType(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS);
////        runtimeActivity.addFragment(Fragments.CWP_WALLET_RUNTIME_WALLET_BITCOIN_ALL_BITDUBAI_TRANSACTIONS, runtimeFragment);
////
////
////
////        String xml = XMLParser.parseObject(runtimeWalletNavigationStructure);
////
////        System.out.println(xml);
//    }
//
////    @Test
////    public void testCreateSkin(){
////
////
////
////
////            try {
////
////                Version version = new Version(1,0,0);
////                VersionCompatibility versionCompatibility = new VersionCompatibility(version,version);
////
////                Map<String, Resource> resources  =new HashMap<String, Resource>();
////
////                //public Resource(UUID id, String name, String fileName, ResourceType resourceType,ResourceDensity resourceDensity) {
////
////
////                Resource resource = new Resource(UUID.randomUUID(),"personIcon","person1.png",ResourceType.IMAGE,ResourceDensity.MDPI);
////
////                resources.put(resource.getName(),resource);
////
////                Map<String, Layout> portraitLayouts = new HashMap<String, Layout>();
////
////                Layout layout_portrait_1 = new Layout(UUID.randomUUID(),"reference_wallet_contact_fragment.xml","reference_wallet_contact_fragment");
////
////                portraitLayouts.put(layout_portrait_1.getName(),layout_portrait_1);
////
////                Map<String, Layout> landscapeLayouts = new HashMap<String, Layout>();
////
////                Layout layout_landscape_1 = new Layout(UUID.randomUUID(),"reference_wallet_contact_fragment.xml","reference_wallet_contact_fragment");
////
////                landscapeLayouts.put(layout_landscape_1.getName(), layout_landscape_1);
////
////                Skin skin = new Skin(UUID.randomUUID(),"reference_wallet",version,versionCompatibility,resources,portraitLayouts,landscapeLayouts);
////
////                System.out.println(XMLParser.parseObject(skin));
////
////            } catch (InvalidParameterException e) {
////                e.printStackTrace();
////            }
////
////    }
//
//    /*    @Test
//    public void testXmlToClassStructureAndInverseConversion() {
//        try {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("<skin id=\"e2864045-d0bb-4254-88a0-4ce53b99fb34\">");
//            stringBuffer.append("<name>skin1</name>");
//            stringBuffer.append(" <resources>");
//            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e4\" resourceType=\"layout\">");
//            stringBuffer.append("<name>main</name>");
//            stringBuffer.append("<fileName>main.xml</fileName>");
//            stringBuffer.append("</resource>");
//            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e5\" resourceType=\"layout\">");
//            stringBuffer.append("<name>main1</name>");
//            stringBuffer.append("<fileName>main1.xml</fileName>");
//            stringBuffer.append("</resource>");
//            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e3\" resourceType=\"layout\">");
//            stringBuffer.append("<name>main2</name>");
//            stringBuffer.append("<fileName>main2.xml</fileName>");
//            stringBuffer.append("</resource>");
//            stringBuffer.append("</resources>");
//            stringBuffer.append("<version>1.0.0</version>");
//            stringBuffer.append("</skin>");
//
//            StringReader reader = new StringReader(stringBuffer.toString());
//
//            RuntimeInlineAnnotationReader.cachePackageAnnotation(Skin.class.getPackage(), new XmlSchemaMine(""));
//
//            JAXBContext jaxbContext = JAXBContext.newInstance(Skin.class);
//
//            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//
//            Skin skin = (Skin) jaxbUnmarshaller.unmarshal(reader);
//
//            StringBuilder skinBuilder = new StringBuilder();
//            skinBuilder.append("I'm a skin, these are my attributes: \n");
//            skinBuilder.append("\nID: "+skin.getId());
//            skinBuilder.append("\nName: "+skin.getName());
//            skinBuilder.append("\nVersion: "+skin.getVersion());
//            skinBuilder.append("\n\nThese are my resources: \n\n");
//            for (Map.Entry<UUID, FermatResource> resource : skin.getResources().entrySet()) {
//                skinBuilder.append("  Resource: ID: " + resource.getValue().getId() + "\n");
//                skinBuilder.append("            Name: " + resource.getValue().getName() + "\n");
//                skinBuilder.append("            FileName: " + resource.getValue().getFileName() + "\n");
//                skinBuilder.append("            ResourceType: " + resource.getValue().getResourceType() + "\n");
//            }
//            System.out.println(skinBuilder.toString());
//
//            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//
//            jaxbMarshaller.marshal(skin, System.out);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }*/
//}
