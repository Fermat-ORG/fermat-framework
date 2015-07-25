package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;

import java.util.Arrays;
import java.util.UUID;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;



import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 24/07/15.
 */
public class StartTest {

    @Mock
    ExtraUserUserAddonRoot extraUserUserAddonRoot=new ExtraUserUserAddonRoot();
    Database mockDatabase= Mockito.mock(Database.class);
    @Mock
    DatabaseFactory mockDatabaseFactory;
    DatabaseTable mockDatabaseTable=Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord=Mockito.mock(DatabaseTableRecord.class);
    MockErrorManager mockErrorManager;
    @Mock
    ExtraUserDatabaseFactory mockExtraUserDatabaseFactory;
    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;
    UUID pluginId;
    String testUserName;

    /**
     * Create an int that false ServiceStatus in startTest_callingMethod_getServiceNonStartedStatus()
     * */
    private int getAnyInteger(int elementsInList, int indexRejected){

        int index= (int) (elementsInList*Math.random());
        if(index==indexRejected){

            return getAnyInteger(elementsInList,indexRejected);

        }
        return index;

    }

    @Before
    public void setUp() throws Exception{

        testUserName="Test User Name";
        pluginId= UUID.randomUUID();

        MockitoAnnotations.initMocks(this);

        mockExtraUserDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(ExtraUserDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, "ExtraUser")).thenReturn(mockDatabase);

        //when(mockPluginDatabaseSystem.createDatabase(pluginId, "ExtraUser")).thenReturn(mockDatabase);
        when(mockExtraUserDatabaseFactory.createDatabase(pluginId)).thenReturn(mockDatabase);
        mockErrorManager=new MockErrorManager();
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        extraUserUserAddonRoot.setId(pluginId);
        extraUserUserAddonRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);

    }

    @Test
    public void startTest_callingMethod_getServiceNonStartedStatus() throws Exception{

        int randomServiceStatusIndex;
        ServiceStatus[] serviceStatusArray=ServiceStatus.values();
        int startedIndex= Arrays.binarySearch(serviceStatusArray, ServiceStatus.STARTED);
        randomServiceStatusIndex=getAnyInteger(serviceStatusArray.length, startedIndex);
        when(extraUserUserAddonRoot.getStatus()).thenReturn(ServiceStatus.values()[randomServiceStatusIndex]);
        extraUserUserAddonRoot.start();
        ServiceStatus serviceStatus=extraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isNotEqualTo(ServiceStatus.STARTED);

    }

    @Test
    public void startTest_ExtraUserUserAddonRootInicializate_GetsStartedStatus() throws Exception {

        /**
         * Back the mock the correct return
         */

        when(extraUserUserAddonRoot.getStatus()).thenReturn(ServiceStatus.STARTED);
        extraUserUserAddonRoot.start();
        ServiceStatus serviceStatus=extraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);

    }

    @Test(expected=CantStartPluginException.class)
    public void startTest_ExtraUserUserAddonRootCanNotInicializate_throwsAnException() throws Exception {

        when(mockPluginDatabaseSystem.openDatabase(pluginId, "ExtraUser")).thenThrow(new DatabaseNotFoundException());
        when(mockPluginDatabaseSystem.createDatabase(pluginId, "ExtraUser")).thenThrow(new CantCreateDatabaseException());
        ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();
        testExtraUserUserAddonRoot.setErrorManager(mockErrorManager);
        testExtraUserUserAddonRoot.setId(pluginId);
        testExtraUserUserAddonRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testExtraUserUserAddonRoot.start();
        System.out.println(mockErrorManager.getReportedException());

    }


}
