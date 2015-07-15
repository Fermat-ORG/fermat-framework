package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseTableContent {

    ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory;
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabaseTable developerDatabaseTable;
/**
 * If returnRecord returns a list not empty
 * **/
    @Ignore
    @Test
    public void testGetDatabaseTableContent() throws Exception {
        List<DeveloperDatabaseTableRecord> returnedRecords;
        returnedRecords=extraUserDeveloperDatabaseFactory.
                getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        Assertions.assertThat(returnedRecords.isEmpty()).isEqualTo(false);
    }
    /**
     * If returnRecord fails, it should return an empty list
     * **/
    @Ignore
    @Test
    public  void testGetDatabaseTableContent_isEmpty() throws Exception{
        List<DeveloperDatabaseTableRecord> returnedRecords = new ArrayList<DeveloperDatabaseTableRecord>();
        returnedRecords=extraUserDeveloperDatabaseFactory.
                getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);

        Assertions.assertThat(returnedRecords.isEmpty()).isEqualTo(true);
    }
    @Ignore
    @Test
    public  void testGetDatabaseTableContent_Exception() throws Exception{
        Exception exception=null;
        try {
            extraUserDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory,developerDatabaseTable);
        } catch (Exception e) {
            e.printStackTrace();
            exception=e;
        }
        Assertions.assertThat(exception).isNull();
    }
}