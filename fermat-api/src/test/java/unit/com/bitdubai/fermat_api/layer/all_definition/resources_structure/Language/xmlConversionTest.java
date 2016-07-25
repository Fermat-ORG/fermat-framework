package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.24..
 */
public class xmlConversionTest {
    @Test
    public void fromClassToXml() {
        Language language = new Language("Default", Languages.LATIN_AMERICAN_SPANISH, new Version(1, 0, 0));
        language.addString("boton_aceptar", "Accept");
        language.addString("boton_atras", "Back");
        language.addString("tab_balance_titulo", "Balance");

        String xml = XMLParser.parseObject(language);
        System.out.println(xml);

        Language language2 = null;
        XMLParser.parseXML(xml, language2);

        Assert.assertNotNull(xml);

    }

}
