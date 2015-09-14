package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import android.content.Context;


import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


import java.util.logging.ErrorManager;

import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 11/09/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class SetterGetterTest
    {

        /**
         * DealsWithEvents Interface member variables.
         */
        @Mock
        private Context mockContext;

        @Mock
        private Object mockObjectContext;

        /**
         * DealsWithErrors interface Mocked
         */
        @Mock
        private ErrorManager mockErrorManager;

        /**
         * PluginDatabaseSystem Interface member variables.
         */
        @Mock
        private PluginDatabaseSystem pluginDatabaseSystem;
        @Mock
        private PlatformDatabaseSystem platformDatabaseSystem;

        private DataBaseSystemAndroidAddonRoot pluginRoot;


        @Before
        public void setUp() throws Exception {


        pluginRoot = new DataBaseSystemAndroidAddonRoot();
        pluginRoot.setContext(mockContext);


    }

        @Test
        public void setContextTest() throws Exception {

        catchException(pluginRoot).setContext(mockContext);
        assertThat(caughtException()).isNull();

        assertThat(pluginRoot.getContext()).isEqualTo(mockContext);

    }

        @Test
        public void setContextObjectTest() throws Exception {

        catchException(pluginRoot).setContext(mockObjectContext);
        assertThat(caughtException()).isNull();

    }

        @Test
        public void getterTest() throws Exception {

        assertThat(pluginRoot.getPlatformDatabaseSystem()).isNotNull();
        assertThat(pluginRoot.getPluginDatabaseSystem()).isNotNull();
      }


    }

