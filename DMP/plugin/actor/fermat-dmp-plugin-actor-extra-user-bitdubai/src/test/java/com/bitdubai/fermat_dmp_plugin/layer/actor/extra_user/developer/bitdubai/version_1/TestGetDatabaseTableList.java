package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 * Modify by Manuel on 19/07/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class TestGetDatabaseTableList {
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


    @Test
    public void testGetDatabaseTableList() throws Exception {
        List<DeveloperDatabaseTable> developerDatabaseTableList=null;

        developerDatabaseTableList= extraUserUserAddonRoot.getDatabaseTableList(testDeveloperObjectFactory,testDeveloperDatabase);
        Assertions.assertThat(developerDatabaseTableList.isEmpty()).isEqualTo(false);
    }

    @Test
    public void testDataBaseTableList_interface_implementation(){

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
        resultList= extraUserUserAddonRoot.getDatabaseTableList(testDeveloperObjectFactory,testDeveloperDatabase);

        Assertions.assertThat(resultList).contains(testDeveloperDataBaseTable);

    }


    @Test
    public void testGetDatabaseTableList_With_Exceptions() throws Exception{
        /**
         * With nulls arguments this method throws an exception
         */
        extraUserUserAddonRoot.getDatabaseTableList(null,null);
    }
}