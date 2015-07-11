package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseList {

    ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory;
    DeveloperObjectFactory developerObjectFactory;
    @Test
    public void testGetDatabaseList() throws Exception {
        List<DeveloperDatabase> databases;
        databases = extraUserDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);

        Assertions.assertThat(databases).isNotEmpty();
    }
    @Test
    public  void testGetDatabaseList_zero_size() throws Exception{
        List<DeveloperDatabase> databases;
        databases = extraUserDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
        int i = databases.size();
        Assertions.assertThat(i).isEqualTo(0);
    }
}