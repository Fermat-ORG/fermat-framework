package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;

/*
import org.junit.Test;

import java.io.File;
import java.io.StringReader;
import java.util.Map;

import ae.com.sun.xml.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
import ae.com.sun.xml.bind.v2.model.annotation.XmlSchemaMine;
import ae.javax.xml.bind.JAXBContext;
import ae.javax.xml.bind.Marshaller;
import ae.javax.xml.bind.Unmarshaller;
*/

/**
 * Created by lnacosta on 2015.07.24..
 */
public class xmlConversionTest {
  /*  @Test
    public void testXmlToClassStructureAndInverseConversion() {
        try {
            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("<resource id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e4\" resourceType=\"layout\">");
            stringBuffer.append("<name>main</name>");
            stringBuffer.append("<fileName>main.xml</fileName>");
            stringBuffer.append("</resource>");

            StringReader reader = new StringReader(stringBuffer.toString());

            RuntimeInlineAnnotationReader.cachePackageAnnotation(Resource.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Resource.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Resource resource = (Resource) jaxbUnmarshaller.unmarshal(reader);

            StringBuilder resourceBuilder = new StringBuilder();
            resourceBuilder.append("I'm a resource, these are my attributes: \n");
            resourceBuilder.append("ID: " + resource.getId() + "\n");
            resourceBuilder.append("Name: " + resource.getName() + "\n");
            resourceBuilder.append("FileName: " + resource.getFileName() + "\n");
            resourceBuilder.append("ResourceType: " + resource.getResourceType() + "\n");
            System.out.println(resourceBuilder.toString());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(resource, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
