package unit.com.bitdubai.fermat_core.PluginsIdentityManager;

import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lnacosta on 2015.08.28..
 */
public class ContentFileTest extends TestCase {
    @Test
    public void testBuildingFile() throws Exception {
        String fileContent = "";

        for (Plugins plugin : Plugins.values())
            fileContent = new StringBuilder().append(fileContent).append(plugin.getCode()).append(";").append(UUID.randomUUID()).append("\\|").toString();

        String[] stringPluginIdPairs = fileContent.split("\\|");

        Map<Plugins, UUID> pluginIdsMap = new HashMap<>();
        for (String stringPluginIdPair : stringPluginIdPairs) {
            if (!stringPluginIdPair.equals("")) {
                String[] pluginIdPair = stringPluginIdPair.split(";");
                pluginIdsMap.put(Plugins.getByCode(pluginIdPair[0]), UUID.fromString(pluginIdPair[1]));
                System.out.println(Plugins.getByCode(pluginIdPair[0]));

                System.out.println(UUID.fromString(pluginIdPair[1]));

            }
        }
    }
}
