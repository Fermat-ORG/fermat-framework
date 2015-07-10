package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.List;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetClassesFullPatch {
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    @Test
    public void testGetClassesFullPath() throws Exception {
        List<String> returnedClasses;
        returnedClasses = extraUserUserAddonRoot.getClassesFullPath();

        Assertions.assertThat(returnedClasses.isEmpty()).isEqualTo(false);
    }
}