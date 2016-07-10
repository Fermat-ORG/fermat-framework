package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.InputStream;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 03/09/15.
 */
public class GetBufferedReaderTest {


    private InputStream inputStreamTest, inputStreamExceptionTest;
    private BufferedReader bufferedReaderTest;
    @Mock
    private HTTPJson httpJson = new HTTPJson();

    @Before
    public void setValues() throws Exception {
        inputStreamTest = httpJson.getInputStream("https://www.google.com");

    }

    @Test
    public void TestGetBufferedReader_successful() throws Exception {

        bufferedReaderTest = httpJson.getBufferedReader(inputStreamTest);
        assertThat(bufferedReaderTest).isNotNull();
    }

    public void TestGetBufferedReader_ThrowsCantGetBufferedReader() throws Exception {
        catchException(httpJson).getBufferedReader(null);
        assertThat(caughtException()).isNotNull();
    }
}
