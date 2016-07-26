package unit.com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by rodrigo on 9/10/15.
 */
public class GenerateString {

    @Test
    public void parseSkin() {
        identityTest designerIdentity = new identityTest();
        designerIdentity.setPublicKey("asdasd");
        designerIdentity.setAlias("asd");
        Skin skin = new Skin();
        skin.setId(UUID.randomUUID());
        skin.setName("SkinTest");
        skin.setScreenSize(ScreenSize.MEDIUM);

        //skin.setDesigner(designerIdentity);
        skin.setSize(100);
        skin.setVersion(new Version("1.0.0"));

        String skinXml = XMLParser.parseObject(skin);
        Assert.assertNotNull(skinXml);
        System.out.println(skinXml);

        Skin skin2 = new Skin();
        skin2 = (Skin) XMLParser.parseXML(skinXml, skin2);
        Assert.assertNotNull(skin2);
        System.out.println(skin2.getName());
    }


}
