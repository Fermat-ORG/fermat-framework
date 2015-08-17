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

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.24..
 */
public class xmlConversionTest  {

    @Test
    public void fromSkinToXML() throws InvalidParameterException {
        Skin skin = new Skin();
        skin.setId(UUID.randomUUID());
        skin.setName("PruebaSkin");
        skin.setVersion(new Version("1.0.0"));
        skin.setScreenSize(ScreenSize.MEDIUM);
        Resource resourceImagen1 = new Resource("imagen1", "images1.jpg", ResourceType.IMAGE, ResourceDensity.LDPI);
        Resource resourceImagen2 = new Resource("imagen2", "images2.jpg", ResourceType.IMAGE, ResourceDensity.MDPI);
        Resource resourceVideo1 = new Resource("videopresentacion", "video1.mov", ResourceType.VIDEO, ResourceDensity.LDPI);
        Map<String, Resource> resourceMap = new HashMap<>();
        resourceMap.put("imagen1", resourceImagen1);
        resourceMap.put("imagen2", resourceImagen2);
        resourceMap.put("video1", resourceVideo1);
        skin.setLstResources(resourceMap);

        Layout layout = new Layout(UUID.randomUUID(), "layout1");
        Map<String, Layout> layoutMap = new HashMap<>();
        layoutMap.put("layout1", layout);
        skin.setLstLandscapeLayouts(layoutMap);
        skin.setLstPortraitLayouts(layoutMap);
        skin.setNavigationStructureCompatibility(new VersionCompatibility(new Version(1, 0, 0), new Version(2, 0, 0)));



        String xml =XMLParser.parseObject(skin);
        System.out.println(xml);
    }


}
