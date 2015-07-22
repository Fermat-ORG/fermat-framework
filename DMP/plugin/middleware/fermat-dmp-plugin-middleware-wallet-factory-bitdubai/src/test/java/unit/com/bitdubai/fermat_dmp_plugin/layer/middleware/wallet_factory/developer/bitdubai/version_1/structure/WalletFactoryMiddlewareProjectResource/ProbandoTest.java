package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;
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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


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

        WalletFactoryProjectResource res = new WalletFactoryMiddlewareProjectResource(name+"imagen1.png", ResourceType.IMAGE);
        resources.add(res);
        res = new WalletFactoryMiddlewareProjectResource(name+"fuente1.BLABLA", ResourceType.FONT_STYLE);
        resources.add(res);
        res = new WalletFactoryMiddlewareProjectResource(name+"layout1.xml", ResourceType.LAYOUT);
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
}