package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory.ListData;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseTableContent {

    @Mock
    private ExtraUserUserAddonRoot mockExtraUserAddonRoot = mock(ExtraUserUserAddonRoot.class);
    @Mock
    private List<DeveloperDatabaseTableRecord> testReturnedRecords;
    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory=mock(DeveloperObjectFactory.class);
    private DeveloperDatabase mockDeveloperDatabase=mock(DeveloperDatabase.class);
    private DeveloperDatabaseTable mockDeveloperDatabaseTable=mock(DeveloperDatabaseTable.class);
    @Mock
    ListData testListData = new ListData();
    @Test
    public void TestGetDatabaseTableContent_successful(){

        when(this.mockDeveloperObjectFactory.getNewDeveloperDatabase(
                        Matchers.anyString(), Matchers.anyString())
        ).thenReturn (testListData.getDatabase());

        List<String> FieldNames = new ArrayList<>();
        when(mockDeveloperDatabase.getId()).thenReturn("ExtraUser");
        when(mockDeveloperDatabase.getName()).thenReturn("Extra User");
        when(mockDeveloperDatabaseTable.getFieldNames()).thenReturn(FieldNames);
        when(mockDeveloperDatabaseTable.getName()).thenReturn("Extra User");

        testReturnedRecords=mockExtraUserAddonRoot.getDatabaseTableContent(mockDeveloperObjectFactory,mockDeveloperDatabase,mockDeveloperDatabaseTable);

        Assertions.assertThat(testReturnedRecords).isNotNull();
    }
}