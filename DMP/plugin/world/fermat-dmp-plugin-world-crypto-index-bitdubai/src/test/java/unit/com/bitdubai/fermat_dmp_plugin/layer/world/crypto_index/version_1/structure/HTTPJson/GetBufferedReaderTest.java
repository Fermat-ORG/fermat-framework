package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exception.CantGetBufferedReader;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.exception.CantGetInputStream;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;
import com.googlecode.catchexception.CatchException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 03/09/15.
 */
public class GetBufferedReaderTest {


   private InputStream inputStreamTest, inputStreamExceptionTest;
   private BufferedReader bufferedReaderTest;
    @Mock
    private HTTPJson httpJson= new HTTPJson();

    @Before
    public void setValues() throws Exception {
       inputStreamTest =httpJson.getInputStream("https://www.google.com");

    }
    @Test
    public void TestGetBufferedReader_successful() throws Exception {

        bufferedReaderTest = httpJson.getBufferedReader(inputStreamTest);
        assertThat(bufferedReaderTest).isNotNull();
    }

    public void TestGetBufferedReader_ThrowsCantGetBufferedReader() throws Exception{
        catchException(httpJson).getBufferedReader(null);
        assertThat(caughtException()).isNotNull();
    }
}
