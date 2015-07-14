package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetLogLevelByClass implements  LogManagerForDevelopers{
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabase developerDatabase;
    DeveloperDatabaseTable developerDatabaseTable;
    @Ignore
    @Test
    public void testGetLogLevelByClass() throws Exception {
        LogLevel logLevel;
        logLevel=extraUserUserAddonRoot.getLogLevelByClass("");
        Assertions.assertThat(logLevel).isNotNull();
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

    }
}