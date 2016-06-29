package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.io.InputStream;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 03/09/15.
 */
public class GetInputStreamTest {

    private InputStream inputStreamTest;
    HTTPJson httpJson = new HTTPJson();

    @Test
    public void TestGetInputStream_successful() throws Exception {
        inputStreamTest = httpJson.getInputStream("https://www.google.com");
        Assertions.assertThat(inputStreamTest).isNotNull();
    }

    public void TestGetInputStream_ThrowCantGetInputStream() throws Exception {
        catchException(httpJson).getInputStream("");
        assertThat(caughtException()).isNotNull();
    }
}
