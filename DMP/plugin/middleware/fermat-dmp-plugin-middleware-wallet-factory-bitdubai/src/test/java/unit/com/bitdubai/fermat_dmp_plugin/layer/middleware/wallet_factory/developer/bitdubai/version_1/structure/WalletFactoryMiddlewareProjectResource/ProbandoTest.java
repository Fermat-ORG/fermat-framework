package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MainMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.MenuItem;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.StatusBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Tab;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TabStrip;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Wallet;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectLanguage;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectProposal;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectResource;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.interfaces.WalletFactoryProjectSkin;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProject;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectLanguage;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectProposal;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectSkin;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;

public class ProbandoTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSkinsMarshalUnmarshal() throws Exception {
        try {
            File file = new File("/home/lnacosta/Desktop/skins.xml");

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));
            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectSkin que = (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(file);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            System.out.println(que.getName() + que.getHash());
            jaxbMarshaller.marshal(que, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProposalMarshalUnmarshal() throws Exception {
        try {
            File file = new File("/home/lnacosta/Desktop/skins.xml");

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectSkin que = (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(file);

            List<WalletFactoryProjectSkin> skins = new ArrayList<>();
            skins.add(que);
            skins.add(que);

            WalletFactoryMiddlewareProjectProposal proposal = new WalletFactoryMiddlewareProjectProposal("soyunapropuesta", FactoryProjectState.DISMISSED, null, skins, null);

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectProposal.class.getPackage(), new XmlSchemaMine(""));
            jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectProposal.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(proposal, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProposalMarshalUnmarshal3() throws Exception {
        try {
            File file = new File("/home/lnacosta/Desktop/skins.xml");

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectSkin que = (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(file);

            List<WalletFactoryProjectSkin> skins = new ArrayList<>();
            skins.add(que);
            skins.add(que);

            List<WalletFactoryProjectLanguage> languages = new ArrayList<>();
            languages.add(new WalletFactoryMiddlewareProjectLanguage("hungaro.xml", Languages.AMERICAN_ENGLISH));
            languages.add(new WalletFactoryMiddlewareProjectLanguage("alfredo.xml", Languages.AMERICAN_ENGLISH));

            WalletFactoryMiddlewareProjectProposal proposal = new WalletFactoryMiddlewareProjectProposal("soyunapropuesta", FactoryProjectState.DISMISSED, null, skins, languages);

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectProposal.class.getPackage(), new XmlSchemaMine(""));
            jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectProposal.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);


            Unmarshaller jaxbunmarshaller213 = jaxbContext.createUnmarshaller();
            WalletFactoryMiddlewareProjectProposal what = (WalletFactoryMiddlewareProjectProposal) jaxbunmarshaller213.unmarshal(new File("/home/lnacosta/Desktop/proposal.xml"));

            System.out.println("*****asd: - " + what.getAlias());

            for (WalletFactoryProjectSkin lan : what.getSkins()) {
                for (WalletFactoryProjectResource sao : lan.getResources()) {
                    System.out.println("*****asd: - " + sao.getName());
                    System.out.println("*****asd: - " + sao.getResourceType());
                }
            }
            Marshaller jaxbmarshaller213 = jaxbContext.createMarshaller();
            jaxbmarshaller213.marshal(what, System.out);


            jaxbMarshaller.marshal(proposal, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProposalMarshalUnmarshal2() throws Exception {
        try {
            File file = new File("/home/lnacosta/Desktop/proposal.xml");

            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectProposal.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectProposal.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectProposal que = (WalletFactoryMiddlewareProjectProposal) jaxbUnmarshaller.unmarshal(file);

            System.out.println("*****asd: - " + que.getAlias());

            for (WalletFactoryProjectLanguage lan : que.getLanguages()) {
                System.out.println("*****asd: - " + lan.getType());
            }

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(que, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WalletFactoryMiddlewareProjectSkin getSkin(String name) {
        List<WalletFactoryProjectResource> resources = new ArrayList<>();

        WalletFactoryProjectResource res = new WalletFactoryMiddlewareProjectResource(name+"imagen1.png", "imagen1.png", ResourceType.IMAGE);
        resources.add(res);
        res = new WalletFactoryMiddlewareProjectResource(name+"fuente1.BLABLA", "fuente1.BLABLA", ResourceType.FONT_STYLE);
        resources.add(res);
        res = new WalletFactoryMiddlewareProjectResource(name+"layout1.xml", "layout1.xml", ResourceType.LAYOUT);
        resources.add(res);

        return new WalletFactoryMiddlewareProjectSkin(name, "as5a5s4da6s4das", resources);
    }

    private String getSkinXml() {
        try {
            WalletFactoryMiddlewareProjectSkin skin = getSkin("skin1");
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(skin, outputStream);

            return outputStream.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getProposalXml() {
        try {
            WalletFactoryMiddlewareProjectProposal proposal = getProposal("proposal1");
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectProposal.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectProposal.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(proposal, outputStream);

            return outputStream.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WalletFactoryMiddlewareProject getProject(String name) {
        List<WalletFactoryProjectProposal> proposals = new ArrayList<>();
        proposals.add(getProposal("mati rosa"));
        proposals.add(getProposal("mati verde"));
        proposals.add(getProposal("mati azul"));
        proposals.add(getProposal("mati amarillo"));

        return new WalletFactoryMiddlewareProject(name, "soy una developer public key", proposals);
    }

    private String getProjectXml() {
        try {
            WalletFactoryMiddlewareProjectProposal proposal = getProposal("proposal1");
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProject.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProject.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(proposal, outputStream);

            return outputStream.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private WalletFactoryMiddlewareProjectProposal getProposal(String alias) {
        List<WalletFactoryProjectSkin> skins = new ArrayList<>();
        skins.add(getSkin("mati rosa"));
        skins.add(getSkin("mati azul"));
        skins.add(getSkin("mati verde"));

        List<WalletFactoryProjectLanguage> languages = new ArrayList<>();
        languages.add(new WalletFactoryMiddlewareProjectLanguage("hungaro.xml", Languages.AMERICAN_ENGLISH));
        languages.add(new WalletFactoryMiddlewareProjectLanguage("alfredo.xml", Languages.AMERICAN_ENGLISH));

        return new WalletFactoryMiddlewareProjectProposal(alias, FactoryProjectState.DRAFT, null, skins, languages);
    }

    @Test
    public void testgetSkinXml() throws Exception {
        try {
            WalletFactoryMiddlewareProjectSkin skin = getSkin("skin1");
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(skin, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetSkinStructureFromXmlString() throws Exception {
        try {
            String strinxml = getSkinXml();

            StringReader reader = new StringReader(strinxml);
            RuntimeInlineAnnotationReader.cachePackageAnnotation(WalletFactoryMiddlewareProjectSkin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(WalletFactoryMiddlewareProjectSkin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            WalletFactoryMiddlewareProjectSkin que = (WalletFactoryMiddlewareProjectSkin) jaxbUnmarshaller.unmarshal(reader);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(que, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSkinGetSkinXml() throws Exception {
        WalletFactoryMiddlewareProjectSkin walletFactoryMiddlewareProjectSkin = new WalletFactoryMiddlewareProjectSkin();
        System.out.println(walletFactoryMiddlewareProjectSkin.getSkinXml(getSkin("WALTERFIERRO")));
    }

    @Test
    public void testSkinGetSkinFromXml() throws Exception {
        WalletFactoryMiddlewareProjectSkin walletFactoryMiddlewareProjectSkin = new WalletFactoryMiddlewareProjectSkin();
        System.out.println(walletFactoryMiddlewareProjectSkin.getSkinFromXml(getSkinXml()));
    }

    @Test
    public void testGetProposalXml() throws Exception {
        WalletFactoryProjectProposal walletFactoryMiddlewareProjectProposal = new WalletFactoryMiddlewareProjectProposal();
        System.out.println(walletFactoryMiddlewareProjectProposal.getProposalXml(getProposal("WALTERFIERRO")));
    }

    @Test
    public void testGetProposalFromXML() throws Exception {
        WalletFactoryProjectProposal walletFactoryMiddlewareProjectProposal = new WalletFactoryMiddlewareProjectProposal();
        System.out.println(walletFactoryMiddlewareProjectProposal.getProposalFromXml(getProposalXml()));
    }

    @Test
    public void testGetProjectXML() throws Exception {
        WalletFactoryProject walletFactoryProject = new WalletFactoryMiddlewareProject();
        System.out.println(walletFactoryProject.getProjectXml(getProject("WALTERFIERRO")));
    }

    private TitleBar getTittleBar(String nombre) {
        SearchView searchView = new SearchView("hola", "tal.png", "que");
        return new TitleBar(nombre, "#FFFFFF", nombre+"SOYUNAIMAGEN.png", searchView);
    }

    private SideMenu getSideMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("rober", "icon1.png", Activities.CWP_SHELL_LOGIN));
        menuItems.add(new MenuItem("logout", "logout.png", Activities.CWP_SHELL_LOGIN));
        return new SideMenu(menuItems);
    }

    private MainMenu getMainMenu() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("asdar223", "icon1.png", Activities.CWP_WALLET_ADULTS_ALL_REQUEST_SEND));
        menuItems.add(new MenuItem("324qwsaf", "asdasg4rwww.png", Activities.CWP_WALLET_ADULTS_ALL_REFFILS));
        return new MainMenu(menuItems);
    }

    private TabStrip getTabStrip() {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab("holaquetal", Fragments.CWP_SHELL_LOGIN));
        tabs.add(new Tab("bienyvos", Fragments.CWP_SHOP_MANAGER_MAIN));
        return new TabStrip(1, 2, 3, 4, 5, 6, "#456878", "#456878", "#456878",tabs);
    }

    private StatusBar getStatusBar() {
        return new StatusBar("#456878", false);
    }

    private Wallet getNavigationStructure() {
        Map<Activities, Activity> activitiesActivityMap = new HashMap<>();

        Activity activity = new Activity();
        activity.setType(Activities.CWP_SHELL_LOGIN);
        activity.setTitleBar(getTittleBar("titlebar1"));
        activity.setSideMenu(getSideMenu());
        activity.setMainMenu(getMainMenu());
        activity.setTabStrip(getTabStrip());
        activity.setStatusBar(getStatusBar());
        activitiesActivityMap.put(Activities.CWP_SHELL_LOGIN, activity);

        Activity activity2 = new Activity();
        activity2.setType(Activities.CWP_SHOP_MANAGER_MAIN);
        activity2.setTitleBar(getTittleBar("titlebar2"));
        activity2.setColor("#AA2222");
        activitiesActivityMap.put(Activities.CWP_SHOP_MANAGER_MAIN, activity2);

        return new Wallet(Wallets.CWP_WALLET_RUNTIME_WALLET_ADULTS_ALL_BITDUBAI, activitiesActivityMap);
    }

    private String getNavigationStructureXml() {
        try {
            Wallet wallet = getNavigationStructure();
            RuntimeInlineAnnotationReader.cachePackageAnnotation(Wallet.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Writer outputStream = new StringWriter();
            jaxbMarshaller.marshal(wallet, outputStream);

            return outputStream.toString();
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testGetNavigationStructure() {
        try {
            RuntimeInlineAnnotationReader.cachePackageAnnotation(Wallet.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            Wallet navigationStructure = getNavigationStructure();
            jaxbMarshaller.marshal(navigationStructure, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetNavigationStructure2() {
        try {
            String strinxml = getNavigationStructureXml();

            StringReader reader = new StringReader(strinxml);
            RuntimeInlineAnnotationReader.cachePackageAnnotation(Wallet.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Wallet.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Wallet que = (Wallet) jaxbUnmarshaller.unmarshal(reader);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(que, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}