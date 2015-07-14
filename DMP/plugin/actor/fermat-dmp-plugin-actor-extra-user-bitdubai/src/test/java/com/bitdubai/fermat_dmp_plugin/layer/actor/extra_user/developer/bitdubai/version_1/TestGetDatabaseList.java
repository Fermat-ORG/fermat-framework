package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseList {
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabase developerDatabase;
    DeveloperDatabaseTable developerDatabaseTable;
    @Ignore
    @Test
    public void testGetDatabaseList() throws Exception {
        List<DeveloperDatabase> developerDatabaseList = null;
        developerDatabaseList=extraUserUserAddonRoot.getDatabaseList(developerObjectFactory);

        Assertions.assertThat(developerDatabaseList.isEmpty()).isEqualTo(false);
    }
    @Ignore
    @Test
    public void testGetDatabaseList_Not_Exception() throws Exception{
        Exception exception=null;
        try {
            extraUserUserAddonRoot.getDatabaseList(developerObjectFactory);
        } catch (Exception e) {
            e.printStackTrace();
            exception=e;
        }
        Assertions.assertThat(exception).isNull();
    }

}