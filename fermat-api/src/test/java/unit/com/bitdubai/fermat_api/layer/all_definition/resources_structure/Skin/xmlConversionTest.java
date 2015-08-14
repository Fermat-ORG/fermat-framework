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
import junit.framework.TestResult;

import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/*
import org.junit.Test;

import java.io.StringReader;
import java.util.Map;
import java.util.UUID;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;*/

/**
 * Created by lnacosta on 2015.07.24..
 */
public class xmlConversionTest extends TestCase {

/*
    public void testcreateResult() {
        Map<String,Resource> lstRecursosPortrait = new HashMap<String,Resource>();
        lstRecursosPortrait.put("person1", new Resource(UUID.randomUUID(), "person1", "person1.png", ResourceType.IMAGE, ResourceDensity.MDPI));

        Map<String,Resource> lstRecursosLandscape = new HashMap<String,Resource>();
        lstRecursosLandscape.put("person1", new Resource(UUID.randomUUID(), "person1", "person1.png", ResourceType.IMAGE, ResourceDensity.MDPI));

        Map<String,Layout> lstLayoutsPortrait = new HashMap<String,Layout>();
        lstLayoutsPortrait.put("wallets_bitcoin_fragment_balance", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_balance.xml"));
        lstLayoutsPortrait.put("wallets_bitcoin_fragment_transactions", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_transactions.xml"));

        Map<String,Layout> lstLayoutsLandscape = new HashMap<String,Layout>();
        lstLayoutsLandscape.put("wallets_bitcoin_fragment_balance", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_balance.xml"));
        lstLayoutsLandscape.put("wallets_bitcoin_fragment_transactions", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_transactions.xml"));

        Version minVersion = new Version(1,0,0);

        Version maxVersion = new Version(1,0,0);

        VersionCompatibility compatibility = null;
        try {

            compatibility = new VersionCompatibility(minVersion,maxVersion);

        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }


        Skin skin = new Skin(UUID.randomUUID(),"basic_wallet_default",new Version(1,1,1),compatibility, ScreenSize.MEDIUM,lstRecursosPortrait,lstRecursosLandscape,lstLayoutsPortrait,lstLayoutsLandscape);

        String xml = XMLParser.parseObject(skin);

        System.out.println(xml);
    }
*/
    /*    @Test
    public void testXmlToClassStructureAndInverseConversion() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<skin id=\"e2864045-d0bb-4254-88a0-4ce53b99fb34\">");
            stringBuffer.append("<name>skin1</name>");
            stringBuffer.append(" <resources>");
            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e4\" resourceType=\"layout\">");
            stringBuffer.append("<name>main</name>");
            stringBuffer.append("<fileName>main.xml</fileName>");
            stringBuffer.append("</resource>");
            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e5\" resourceType=\"layout\">");
            stringBuffer.append("<name>main1</name>");
            stringBuffer.append("<fileName>main1.xml</fileName>");
            stringBuffer.append("</resource>");
            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e3\" resourceType=\"layout\">");
            stringBuffer.append("<name>main2</name>");
            stringBuffer.append("<fileName>main2.xml</fileName>");
            stringBuffer.append("</resource>");
            stringBuffer.append("</resources>");
            stringBuffer.append("<version>1.0.0</version>");
            stringBuffer.append("</skin>");

            StringReader reader = new StringReader(stringBuffer.toString());

            RuntimeInlineAnnotationReader.cachePackageAnnotation(Skin.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Skin.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Skin skin = (Skin) jaxbUnmarshaller.unmarshal(reader);

            StringBuilder skinBuilder = new StringBuilder();
            skinBuilder.append("I'm a skin, these are my attributes: \n");
            skinBuilder.append("\nID: "+skin.getId());
            skinBuilder.append("\nName: "+skin.getName());
            skinBuilder.append("\nVersion: "+skin.getVersion());
            skinBuilder.append("\n\nThese are my resources: \n\n");
            for (Map.Entry<UUID, FermatResource> resource : skin.getResources().entrySet()) {
                skinBuilder.append("  Resource: ID: " + resource.getValue().getId() + "\n");
                skinBuilder.append("            Name: " + resource.getValue().getName() + "\n");
                skinBuilder.append("            FileName: " + resource.getValue().getFileName() + "\n");
                skinBuilder.append("            ResourceType: " + resource.getValue().getResourceType() + "\n");
            }
            System.out.println(skinBuilder.toString());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(skin, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
