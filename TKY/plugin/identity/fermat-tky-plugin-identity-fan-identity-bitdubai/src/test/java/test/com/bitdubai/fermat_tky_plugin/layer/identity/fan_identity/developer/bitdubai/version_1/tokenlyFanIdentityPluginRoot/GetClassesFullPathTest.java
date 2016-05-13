package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetClassesFullPathTest {

    @Test
    public void getClassesFullPathTest() {
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        List<String> returnedClasses = new ArrayList<String>();

        returnedClasses.add("test");

        when(tokenlyFanIdentityPluginRoot.getClassesFullPath()).thenReturn(returnedClasses);

    }
}
