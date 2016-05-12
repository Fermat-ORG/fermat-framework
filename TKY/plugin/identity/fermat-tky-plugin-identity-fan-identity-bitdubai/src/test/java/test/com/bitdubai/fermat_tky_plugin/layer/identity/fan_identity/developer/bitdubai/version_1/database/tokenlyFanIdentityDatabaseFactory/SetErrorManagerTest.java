package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDatabaseFactory;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class SetErrorManagerTest {
    @Mock
    ErrorManager errorManager;


    @Test
    public void setErrorManagerTest(){

        TokenlyFanIdentityDatabaseFactory tokenlyFanIdentityDatabaseFactory = Mockito.mock(TokenlyFanIdentityDatabaseFactory.class);

        doCallRealMethod().when(tokenlyFanIdentityDatabaseFactory).setErrorManager(errorManager);

    }
}
