package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 30/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {

    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    MockErrorManager mockErrorManager=new MockErrorManager();
    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    Database testDatabase;
    @Mock
    DatabaseTable testDatabaseTable;
    @Mock
    DatabaseTableRecord testDatabaseTableRecord;
    @Mock
    DatabaseRecord testDatabaseRecord;
    @Mock
    DeveloperObjectFactory testDeveloperObjectFactory;
    @Mock
    DeveloperDatabase testDeveloperDatabase;
    @Mock
    DeveloperDatabaseTable testDeveloperDatabaseTable;
    @Mock
    DeveloperDatabaseTableRecord testDeveloperDatabaseTableRecord;

    String testId="ExtraUser";
    String testName="Extra User";
    UUID testPluginId=UUID.randomUUID();

    @Test
    public void getDatabaseContentTest_DeveloperObjectFactoryImplemented_DatabaseTableListWithValidContent() throws Exception{

        /**
         * Creating a fake DeveloperDatabase, mocking some interfaces, we can test the DatabaseList return
         */
        List<DeveloperDatabaseTableRecord> resultDatabaseContentList;
        when(testDatabaseRecord.getValue()).thenReturn("V0");
        when(testDatabaseTableRecord.getValues()).thenReturn(Arrays.asList(testDatabaseRecord));
        when(testDeveloperDatabaseTable.getName()).thenReturn(testName);
        when(testDeveloperObjectFactory.getNewDeveloperDatabaseTableRecord(Arrays.asList("V0"))).thenReturn(testDeveloperDatabaseTableRecord);
        when(testDatabase.getTable(testName)).thenReturn(testDatabaseTable);
        when(testDatabaseTable.getRecords()).thenReturn(Arrays.asList(testDatabaseTableRecord));
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testId)).thenReturn(testDatabase);
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        extraUserUserAddonRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        extraUserUserAddonRoot.setId(testPluginId);

        resultDatabaseContentList=extraUserUserAddonRoot.getDatabaseTableContent(testDeveloperObjectFactory, testDeveloperDatabase, testDeveloperDatabaseTable);

        Assertions.assertThat(resultDatabaseContentList).isNotNull();


    }
//@Ignore
    @Test
    public void getDatabaseContestListTest_NullsArguments_ThrowsGenericException() throws Exception{
        /**
         * With null DeveloperObjectFactory argument this method throws an exception
         */
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        extraUserUserAddonRoot.getDatabaseTableContent(null, null, null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void getDatabaseContentListTest_OpenDeveloperObjectFactoryCanGetDatabaseTableList_DatabaseContenteListNotNull() throws Exception {
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList;
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        developerDatabaseTableRecordList= extraUserUserAddonRoot.getDatabaseTableContent(testDeveloperObjectFactory, testDeveloperDatabase, testDeveloperDatabaseTable);
        Assertions.assertThat(developerDatabaseTableRecordList.isEmpty()).isEqualTo(true);
        Assertions.assertThat(mockErrorManager).isNotNull();
    }

}
