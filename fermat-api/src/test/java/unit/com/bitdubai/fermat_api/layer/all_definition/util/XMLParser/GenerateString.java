package unit.com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerIdentity;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.Designer;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by rodrigo on 9/10/15.
 */
public class GenerateString {

    @Test
    public void parseSkin(){
        Skin skin = new Skin();
        skin.setId(UUID.randomUUID());
        skin.setName("SkinTest");
        skin.setScreenSize(ScreenSize.MEDIUM);
        DesignerIdentity designerIdentity= new DesignerIdentity() {
            @Override
            public String getAlias() {
                return "alias";
            }

            @Override
            public String getPublicKey() {
                return "pk";
            }

            @Override
            public String createMessageSignature(String mensage) throws CantSingMessageException {
                return "signature";
            }
        };
        skin.setDesigner(designerIdentity);
        skin.setSize(100);
        skin.setVersion(new Version("1.0.0"));

        String skinXml = XMLParser.parseObject(skin);
        Assert.assertNotNull(skinXml);
        System.out.println(skinXml);
    }
}
