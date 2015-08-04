package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Franklin Marcano 03/08/15.
 */
public class GetPluginTest {
    @Test
    public void getPluginTest_objectInstantiated_returnsValidPlugin() throws Exception{

        DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();
        Assertions.assertThat(testDeveloperBitDubai.getPlugin())
                .isNotNull()
                .isInstanceOf(Plugin.class);

    }

}
