package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import android.content.Context;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.DataBaseSystemAndroidAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;
import java.util.logging.ErrorManager;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 11/09/15.
 */
public class SetterGetterTest
    {

        /**
         * DealsWithEvents Interface member variables.
         */
        @Mock
        Context mockContext;

        @Mock
        Object mockObjectContext;

        /**
         * DealsWithErrors interface Mocked
         */
        @Mock
        private ErrorManager mockErrorManager;

        /**
         * PluginDatabaseSystem Interface member variables.
         */
        @Mock
        PluginDatabaseSystem pluginDatabaseSystem;
        @Mock
        PlatformDatabaseSystem platformDatabaseSystem;

        private DataBaseSystemAndroidAddonRoot pluginRoot;


        UUID pluginId;

        @Before
        public void setUp() throws Exception {

        pluginId= UUID.randomUUID();


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

        assertThat(pluginRoot.getPlatformDatabaseSystem()).isEqualTo(platformDatabaseSystem);
        assertThat(pluginRoot.getPluginDatabaseSystem()).isEqualTo(pluginDatabaseSystem);
      }


    }

