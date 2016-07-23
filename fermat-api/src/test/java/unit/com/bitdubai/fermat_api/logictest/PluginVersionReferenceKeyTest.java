package unit.com.bitdubai.fermat_api.logictest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Test;

public class PluginVersionReferenceKeyTest {

    @Test
    public void Construction_Valid_NotNull() throws Exception {
        PluginVersionReference pvr = new PluginVersionReference(
                Platforms.CRYPTO_CURRENCY_PLATFORM,
                Layers.ACTOR,
                Plugins.BITCOIN_ASSET_VAULT,
                Developers.BITDUBAI,
                new Version()
        );

        //String key = pvr.toKey();
        //System.out.println(pvr.toKey());

        //System.out.println(PluginVersionReference.getByKey(key));
    }


}
