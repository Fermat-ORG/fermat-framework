package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by francisco on 03/09/15.
 */
public class GetInputStreamTest {

    private InputStream inputStreamTest;
    HTTPJson httpJson = new HTTPJson();

    @Test
    public void TestGetInputStream_successful() throws Exception{
        inputStreamTest=httpJson.getInputStream("https://www.google.com");
        Assertions.assertThat(inputStreamTest).isNotNull();
    }
}
