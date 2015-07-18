package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
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
public class TestGetDatabaseTableList {
    @Mock
    private ExtraUserDeveloperDatabaseFactory mockExtraUserDeveloperDatabaseFactory=mock(ExtraUserDeveloperDatabaseFactory.class);
    @Mock
    private DatabaseFactory mockDatabaseFactory=mock(DatabaseFactory.class);
    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory =mock(DeveloperObjectFactory.class);
    @Mock
    List<DeveloperDatabaseTable> mockLisTables;

    ListData testListData = new ListData();
    @Test
    public void Test_GetDatabaseTable_successful() throws Exception{

      when(mockExtraUserDeveloperDatabaseFactory.
                getDatabaseTableList(Matchers.<DeveloperObjectFactory>any()))
         .thenReturn(testListData.getDatabaseTableList());

       mockLisTables=mockExtraUserDeveloperDatabaseFactory.getDatabaseTableList(this.mockDeveloperObjectFactory);

        Assertions.assertThat(mockLisTables).isNotNull();

    }
}