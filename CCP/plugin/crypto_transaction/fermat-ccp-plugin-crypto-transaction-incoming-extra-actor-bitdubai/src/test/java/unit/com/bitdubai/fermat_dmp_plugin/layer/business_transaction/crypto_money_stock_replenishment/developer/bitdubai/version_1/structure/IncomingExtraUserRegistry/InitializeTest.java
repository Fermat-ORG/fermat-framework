package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.exceptions.CantInitializeCryptoRegistryException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserDataBaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.02..
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
    private IncomingExtraUserRegistry testRegistry;

    @Before
    public void setUpId(){
        testId = UUID.randomUUID();
    }

    @Test
    public void Initialize_RegistryProperlySet_MethodSuccesfullyInvoked() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenReturn(mockDatabase);

        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testRegistry).initialize(testId);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void Initialize_NoPluginDatabaseSystemSet_ThrowsCantInitializeCryptoRegistryException() throws Exception{
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        catchException(testRegistry).initialize(testId);
        assertThat(caughtException()).isInstanceOf(CantInitializeCryptoRegistryException.class);
    }

    @Test
    public void Initialize_DatabaseNotFound_MethodSuccesfullyInvoked() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenThrow(new DatabaseNotFoundException(DatabaseNotFoundException.DEFAULT_MESSAGE, null, null, null));
        when(mockPluginDatabaseSystem.createDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testRegistry).initialize(testId);
        assertThat(caughtException()).isNull();
    }

    @Test
    public void testInitialize_DatabaseNotFoundAndCreateDatabaseFailed_ThrowsCantInitializeCryptoRegistryException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenThrow(new DatabaseNotFoundException("MOCK", null, null, null));
        when(mockPluginDatabaseSystem.createDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testRegistry).initialize(testId);
        assertThat(caughtException()).isInstanceOf(CantInitializeCryptoRegistryException.class);
    }

    @Test
    public void Initialize_CantOpenDatabase_ThrowsCantInitializeCryptoRegistryException() throws Exception{
        when(mockPluginDatabaseSystem.openDatabase(testId, IncomingExtraUserDataBaseConstants.INCOMING_EXTRA_USER_DATABASE)).thenThrow(new CantOpenDatabaseException("MOCK", null, null, null));
        testRegistry = new IncomingExtraUserRegistry();
        testRegistry.setErrorManager(mockErrorManager);
        testRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        catchException(testRegistry).initialize(testId);
        assertThat(caughtException()).isInstanceOf(CantInitializeCryptoRegistryException.class);
    }

    

}
