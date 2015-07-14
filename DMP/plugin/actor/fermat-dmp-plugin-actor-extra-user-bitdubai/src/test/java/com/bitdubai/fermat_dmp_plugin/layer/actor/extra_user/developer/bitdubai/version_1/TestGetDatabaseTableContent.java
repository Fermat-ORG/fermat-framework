package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseTableContent {
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabase developerDatabase;
    DeveloperDatabaseTable developerDatabaseTable;
    @Ignore
    @Test
    public void testGetDatabaseTableContent() throws Exception {
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        developerDatabaseTableRecordList=extraUserUserAddonRoot.getDatabaseTableContent(developerObjectFactory,developerDatabase,developerDatabaseTable);
        Assertions.assertThat(developerDatabaseTableRecordList.isEmpty()).isEqualTo(false);
    }
    @Ignore
    @Test
    public void testGetDatabaseTableContent_Not_Exceptions() throws Exception{
        Exception exception = null;
        try {
            extraUserUserAddonRoot.getDatabaseTableContent(developerObjectFactory,developerDatabase,developerDatabaseTable);
        } catch (Exception e) {
            e.printStackTrace();
            exception=e;
        }
        Assertions.assertThat(exception).isNull();
    }
}