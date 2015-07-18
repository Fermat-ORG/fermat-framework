package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory.ListData;
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
    private ExtraUserUserAddonRoot mockExtraUserAddonRoot = mock(ExtraUserUserAddonRoot.class);
    @Mock
    private List<DeveloperDatabaseTable> mockListDeveloperDatabaseTable;
    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory;
    private DeveloperDatabase mockDeveloperDatabase;
    private DeveloperDatabaseTable mockDeveloperDatabaseTable;
    ListData testListData = new ListData();
    @Test
    public void TestGetDatabaseTableList_successful() throws Exception{


        when(mockExtraUserAddonRoot.
                getDatabaseTableList(Matchers.<DeveloperObjectFactory>any(),Matchers.<DeveloperDatabase>any()))
                .thenReturn(testListData.getDatabaseTableList());

        mockListDeveloperDatabaseTable=mockExtraUserAddonRoot.getDatabaseTableList(mockDeveloperObjectFactory,mockDeveloperDatabase);

        Assertions.assertThat(mockListDeveloperDatabaseTable).isNotNull();
    }
}