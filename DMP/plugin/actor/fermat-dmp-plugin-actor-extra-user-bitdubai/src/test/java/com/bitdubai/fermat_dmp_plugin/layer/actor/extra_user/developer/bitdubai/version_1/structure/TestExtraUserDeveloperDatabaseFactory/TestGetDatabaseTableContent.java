package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseTableContent {

    @Mock
    private ExtraUserDeveloperDatabaseFactory mockExtraUserDeveloperDatabaseFactory=mock(ExtraUserDeveloperDatabaseFactory.class);
    @Mock
    private List<DeveloperDatabaseTableRecord> testReturnedRecords;
    @Mock
    private DatabaseTable selectedTable;
    private DeveloperObjectFactory mockDeveloperObjectFactory= mock(DeveloperObjectFactory.class);
    private DeveloperDatabaseTable mockDeveloperDatabaseTable=mock(DeveloperDatabaseTable.class);

   ListData testListData = new ListData();
    @Test
    public void testGetDatabaseTableConent() throws Exception{

        when(this.mockDeveloperObjectFactory.getNewDeveloperDatabase(
        Matchers.anyString(), Matchers.anyString())
        ).thenReturn (testListData.getDatabase());

        testReturnedRecords=mockExtraUserDeveloperDatabaseFactory.
        getDatabaseTableContent(mockDeveloperObjectFactory,mockDeveloperDatabaseTable);

        Assertions.assertThat(testReturnedRecords).isNotNull();
    }
}