package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;


import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoDataBaseConstants;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;
/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeTest {
    @Mock
    private ErrorManager mockErrorManager;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;


    private UUID testId;

    private IncomingCryptoRegistry mockRegistry;

    @Before
    public void setUpId(){
        testId = UUID.randomUUID();
    }

    @Test
    public void Initialize_RegistryProperlySet_MethodSuccesfullyInvoked() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);

        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setErrorManager(mockErrorManager);
        mockRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(mockRegistry).initialize(testId);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void Initialize_NoPluginDatabaseSystemSet_ThrowsCantInitializeCryptoRegistryException() throws Exception{
        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setErrorManager(mockErrorManager);
        catchException(mockRegistry).initialize(testId);
        assertThat(caughtException()).isNotNull();//isInstanceOf(CantInitializeCryptoRegistryException.class);
    }

    @Test
    public void Initialize_DatabaseNotFound_MethodSuccesfullyInvoked() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenThrow(new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, null, null));
        when(mockPluginDatabaseSystem.createDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setErrorManager(mockErrorManager);
        mockRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(mockRegistry).initialize(testId);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void Initialize_DatabaseNotFoundAndCreateDatabaseFailed_MethodSuccesfullyInvoked() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenThrow(new DatabaseNotFoundException("MOCK", null, null, null));
        when(mockPluginDatabaseSystem.createDatabase(testId, IncomingCryptoDataBaseConstants.INCOMING_CRYPTO_DATABASE)).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));
        mockRegistry = new IncomingCryptoRegistry();
        mockRegistry.setErrorManager(mockErrorManager);
        mockRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(mockRegistry).initialize(testId);
        assertThat(caughtException()).isInstanceOf(CantInitializeCryptoRegistryException.class);
    }

}

