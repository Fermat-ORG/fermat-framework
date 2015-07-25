package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import com.googlecode.catchexception.CatchException;

import java.util.Arrays;
import java.util.List;
import org.fest.assertions.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 21/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDabaseListTest {

    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    @Mock
    DeveloperObjectFactory testDeveloperObjectFactory;
    @Mock
    DeveloperDatabase testDeveloperDatabase;
    @Mock
    DeveloperDatabaseTable testDeveloperDataBaseTable;
    @Mock
    DeveloperDatabaseTableRecord testDeveloperDatabaseTableRecord;
    @Mock
    ExtraUserUserAddonRoot testExtraUserUserAddonRoot;
    String testId="ExtraUser";
    String testName="Extra User";

    @Test
    public void getDatabaseList_DeveloperObjectFactoryImplemented_DatabaseListWithValidContent() throws Exception{

        /**
         * Creating a fake DeveloperDatabase, mocking some interfaces, we can test the DatabaseList return
         */
        List<String> testFieldNames= Arrays.asList("F0","F1");
        List<String> testColumnNames= Arrays.asList("C0","C1");
        List<String> testValues= Arrays.asList("V0","V1");
        when(testDeveloperDataBaseTable.getFieldNames()).thenReturn(testFieldNames);
        when(testDeveloperObjectFactory.getNewDeveloperDatabase(testName, testId)).thenReturn(testDeveloperDatabase);
        when(testDeveloperObjectFactory.getNewDeveloperDatabaseTable(testName, testColumnNames)).thenReturn(testDeveloperDataBaseTable);
        when(testDeveloperObjectFactory.getNewDeveloperDatabaseTableRecord(testValues)).thenReturn(testDeveloperDatabaseTableRecord);
        List<DeveloperDatabase> resultList=extraUserUserAddonRoot.getDatabaseList(testDeveloperObjectFactory);

        Assertions.assertThat(resultList).contains(testDeveloperDatabase);

    }

    @Test
    public void getDatabaseList_NullDeveloperObjectFactory_ThrowsGenericException() throws Exception{

        MockErrorManager mockErrorManager=new MockErrorManager();

        extraUserUserAddonRoot.setErrorManager(mockErrorManager);
        extraUserUserAddonRoot.getDatabaseList(null);

    }

     @Test
     public void getDatabaseList_OpenDeveloperObjectFactoryCanGetDatabaseList_DatabaseListNotNull() throws Exception {
        List<DeveloperDatabase> developerDatabaseList = null;
        //Mocking DeveloperObjectFactory
        developerDatabaseList=extraUserUserAddonRoot.getDatabaseList(testDeveloperObjectFactory);

        Assertions.assertThat(developerDatabaseList.isEmpty()).isEqualTo(false);
    }

}
