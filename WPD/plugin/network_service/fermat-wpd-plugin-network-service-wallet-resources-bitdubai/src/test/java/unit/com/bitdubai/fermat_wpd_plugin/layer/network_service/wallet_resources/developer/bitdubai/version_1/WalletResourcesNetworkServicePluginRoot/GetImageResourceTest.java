package unit.com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;


import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


/**
 * Created by natalia on 03/07/15.
 */

/**
        * The Resource Repository  is not created, then test return error for all case.
        */
@RunWith(MockitoJUnitRunner.class)
public class GetImageResourceTest extends TestCase {

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    ErrorManager errorManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    PluginFileSystem pluginFileSystem;


    /**
     * DealWithEvents Iianterface member variables.
     */
    @Mock
    EventManager eventManager;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;


    @Mock
    private Map<String, byte[]> mockImages;

    private byte[] image;

    WalletResourcesNetworkServicePluginRoot walletResourcePluginRoot;

    @Before
    public void setUp() throws Exception {

        image = new  byte[100];
        mockImages.put("image1", image);
        walletResourcePluginRoot = new WalletResourcesNetworkServicePluginRoot();
        walletResourcePluginRoot.setPluginFileSystem(pluginFileSystem);
        walletResourcePluginRoot.setEventManager(eventManager);
        walletResourcePluginRoot.setErrorManager(errorManager);

        when(mockImages.get(anyString())).thenReturn(image);

        walletResourcePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        walletResourcePluginRoot.start();
    }


    @Test
    public void testgetImageResource_TheResourcesHasAlreadyBeenReturn_ThrowsCantGetResourcesException() throws Exception {

        catchException(walletResourcePluginRoot).getImageResource("ar_bill_1_a.jpg", UUID.randomUUID());
        assertThat(caughtException()).isNull();

    }

    @Ignore
    @Test
    public void testcheckResources_TheResourcesRepositoryNotExist_ThrowsCantGetResourcesException() throws Exception {

        catchException(walletResourcePluginRoot).getImageResource("ar_bill_1_a.jpg",null);
        assertThat(caughtException()).isInstanceOf(CantGetResourcesException.class);
        caughtException().printStackTrace();
    }

    @Ignore
    @Test
    public void testcheckResources_fileNotFound_ThrowsCantGetResourcesException() throws Exception {

        //catchException(walletResourcePluginRoot).getImageResource("ar_bill.jpg");
        //assertThat(caughtException()).isInstanceOf(CantGetResourcesException.class);
        caughtException().printStackTrace();
    }
}
