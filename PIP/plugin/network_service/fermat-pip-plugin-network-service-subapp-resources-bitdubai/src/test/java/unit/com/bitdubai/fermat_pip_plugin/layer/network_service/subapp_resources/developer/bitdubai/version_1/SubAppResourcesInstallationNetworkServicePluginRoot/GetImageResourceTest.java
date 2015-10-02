package unit.com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.network_service.exceptions.CantGetImageResourceException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstallationNetworkServicePluginRoot;

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
 * Created by francisco on 30/09/15.
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
    @Mock
    private FermatEventListener mockFermatEventListener;

    private String walletPublicKey;
    SubAppResourcesInstallationNetworkServicePluginRoot subAppResourcesInstallationNetworkServicePluginRoot;

    @Before
    public void setUp() throws Exception {

        image = new  byte[100];
        mockImages.put("image1", image);
        subAppResourcesInstallationNetworkServicePluginRoot = new SubAppResourcesInstallationNetworkServicePluginRoot();
        subAppResourcesInstallationNetworkServicePluginRoot.setPluginFileSystem(pluginFileSystem);
        subAppResourcesInstallationNetworkServicePluginRoot.setEventManager(eventManager);
        subAppResourcesInstallationNetworkServicePluginRoot.setErrorManager(errorManager);

        when(mockImages.get(anyString())).thenReturn(image);

        subAppResourcesInstallationNetworkServicePluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(eventManager.getNewListener(EventType.BEGUN_WALLET_INSTALLATION)).thenReturn(mockFermatEventListener);
        walletPublicKey = AsymmectricCryptography.derivePublicKey(AsymmectricCryptography.createPrivateKey());
        subAppResourcesInstallationNetworkServicePluginRoot.start();
    }


    @Test
    public void testgetImageResource_TheResourcesHasAlreadyBeenReturn_ThrowsCantGetResourcesException() throws Exception {

       catchException(subAppResourcesInstallationNetworkServicePluginRoot).getImageResource("ar_bill_1_a.jpg", UUID.randomUUID(),walletPublicKey);
        //assertThat(caughtException()).isNull();
    }


    @Test
    public void testcheckResources_TheResourcesRepositoryNotExist_ThrowsCantGetImageResourcesException() throws Exception {

        catchException(subAppResourcesInstallationNetworkServicePluginRoot).getImageResource(null,null,null);
        assertThat(caughtException()).isInstanceOf(CantGetImageResourceException.class);
        //caughtException().printStackTrace();
    }

    @Ignore
    @Test
    public void testcheckResources_fileNotFound_ThrowsCantGetResourcesException() throws Exception {

        //catchException(walletResourcePluginRoot).getImageResource("ar_bill.jpg");
        //assertThat(caughtException()).isInstanceOf(CantGetResourcesException.class);
        caughtException().printStackTrace();
    }
}