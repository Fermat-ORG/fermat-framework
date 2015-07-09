package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseTableList {

    ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory;
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabaseTable developerDatabaseTable;

    @Test
    public void testGetDatabaseTableList() throws Exception {


        List<DeveloperDatabaseTable> databases = extraUserDeveloperDatabaseFactory.
                getDatabaseTableList
                (developerObjectFactory);

        Assertions.assertThat(databases.isEmpty()).isEqualTo(false);
    }
}