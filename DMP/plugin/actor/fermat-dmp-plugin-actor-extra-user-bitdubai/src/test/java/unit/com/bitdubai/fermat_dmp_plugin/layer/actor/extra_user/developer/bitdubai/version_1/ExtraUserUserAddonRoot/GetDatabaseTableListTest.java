package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;


import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 21/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest {

    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();

    @Mock
    DeveloperDatabase testDeveloperDatabase;
    @Mock
    DeveloperDatabaseTable testDeveloperDataBaseTable;
    @Mock
    DeveloperDatabaseTableRecord testDeveloperDatabaseTableRecord;
    @Mock
    DeveloperObjectFactory testDeveloperObjectFactory;

    String testId="Id";
    String testName="UserRegistry";

    // TODO NOT WORKING
    @Ignore
    public void getDataBaseTableListTest_DeveloperObjectFactoryImplemented__DatabaseTableListWithValidContent(){

        List<DeveloperDatabaseTable> resultList;
        /**
         * Creating a fake DeveloperDatabase, mocking some interfaces, we can test the DatabaseList return
         */
        List<String> testFieldNames= Arrays.asList("F0", "F1","F2");
        List<String> testColumnNames= Arrays.asList("Id","Name","TimeStamp");
        List<String> testValues= Arrays.asList("V0","V1","V2");
        when(testDeveloperDataBaseTable.getFieldNames()).thenReturn(testFieldNames);
        when(testDeveloperDataBaseTable.getName()).thenReturn(testName);
        when(testDeveloperObjectFactory.getNewDeveloperDatabase(testName, testId)).thenReturn(testDeveloperDatabase);
        when(testDeveloperObjectFactory.getNewDeveloperDatabaseTable(testName, testColumnNames)).thenReturn(testDeveloperDataBaseTable);
        when(testDeveloperObjectFactory.getNewDeveloperDatabaseTableRecord(testValues)).thenReturn(testDeveloperDatabaseTableRecord);
        resultList= extraUserUserAddonRoot.getDatabaseTableList(testDeveloperObjectFactory, testDeveloperDatabase);


        Assertions.assertThat(resultList).contains(testDeveloperDataBaseTable);

    }


    @Test
    public void getDatabaseTableListTest_NullsArguments_ThrowsGenericException() throws Exception{
        /**
         * With null DeveloperObjectFactory argument this method throws an exception
         */
        MockErrorManager mockErrorManager=new MockErrorManager();
        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        extraUserUserAddonRoot.getDatabaseTableList(null, null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }


    @Test
    public void getDatabaseTableListTest_OpenDeveloperObjectFactoryCanGetDatabaseTableList_DatabaseTableListNotNull() throws Exception {
        List<DeveloperDatabaseTable> developerDatabaseTableList;

        developerDatabaseTableList= extraUserUserAddonRoot.getDatabaseTableList(testDeveloperObjectFactory,testDeveloperDatabase);
        Assertions.assertThat(developerDatabaseTableList.isEmpty()).isEqualTo(false);
    }

}
