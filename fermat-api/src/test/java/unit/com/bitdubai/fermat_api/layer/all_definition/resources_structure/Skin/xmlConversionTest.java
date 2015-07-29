package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatResource;

import junit.framework.TestCase;
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
