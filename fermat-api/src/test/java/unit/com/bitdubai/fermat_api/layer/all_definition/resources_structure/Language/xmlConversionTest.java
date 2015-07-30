package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;

import junit.framework.TestCase;

import org.junit.Test;
/*
import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
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
public class xmlConversionTest extends TestCase {
  /*  @Test
    public void testXmlToClassStructure() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<language id=\"31c5724c-876e-4a5b-8bcd-62d63ad033e4\" type=\"AMERICAN_ENGLISH\">");
            stringBuffer.append("<name>english.xml</name>");
            stringBuffer.append("<version>1.0.0</version>");
            stringBuffer.append("<strings>");
            stringBuffer.append("<string>");
            stringBuffer.append("<name>menuItem1</name>");
            stringBuffer.append("<value>item1</value>");
            stringBuffer.append("</string>");
            stringBuffer.append("<string>");
            stringBuffer.append(" <name>menuItem2</name>");
            stringBuffer.append(" <value>item2</value>");
            stringBuffer.append("</string>");
            stringBuffer.append("</strings>");
            stringBuffer.append("</language>");

            StringReader reader = new StringReader(stringBuffer.toString());

            RuntimeInlineAnnotationReader.cachePackageAnnotation(Language.class.getPackage(), new XmlSchemaMine(""));

            JAXBContext jaxbContext = JAXBContext.newInstance(Language.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Language language = (Language) jaxbUnmarshaller.unmarshal(reader);

            StringBuilder languageBuilder = new StringBuilder();
            languageBuilder.append("I'm a language, these are my attributes: \n");
            languageBuilder.append("ID: "+language.getId()+"\n");
            languageBuilder.append("Name: " +language.getName()+"\n");
            languageBuilder.append("Type: "+language.getType()+"\n");
            languageBuilder.append("Version: " + language.getVersion()+"\n");
            languageBuilder.append("Strings:\n");
            for (Map.Entry entry : language.getStrings().entrySet()) {
                languageBuilder.append("  Name: "+entry.getKey());
                languageBuilder.append(" - Value: "+entry.getValue()+"\n");
            }
            System.out.println(languageBuilder.toString());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            jaxbMarshaller.marshal(language, System.out);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
