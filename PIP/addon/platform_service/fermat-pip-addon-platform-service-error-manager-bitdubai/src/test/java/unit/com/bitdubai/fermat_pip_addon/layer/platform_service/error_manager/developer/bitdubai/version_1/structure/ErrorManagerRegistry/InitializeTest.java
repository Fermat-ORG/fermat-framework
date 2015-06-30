package unit.com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerRegistry;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;

import org.mockito.Mock;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerDatabaseConstants;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.structure.ErrorManagerRegistry;

import org.junit.Test;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class InitializeTest {

    @Mock
    private PlatformDatabaseSystem mockPlatformDatabaSystem = mock(PlatformDatabaseSystem.class);

    @Mock
    private Database mockDatabase = mock(Database.class);

    @Test
    public void Initialize_ValidPlatformDatabase_CreatesDatabase() throws Exception{
        
        when(mockPlatformDatabaSystem.openDatabase(ErrorManagerDatabaseConstants.EXCEPTION_DATABASE_NAME)).thenReturn(mockDatabase);

        ErrorManagerRegistry testRegistry = new ErrorManagerRegistry();
        testRegistry.setPlatformDatabaseSystem(mockPlatformDatabaSystem);
        testRegistry.initialize();
        assertThat(testRegistry).isNotNull();
    }

    @Test
    public void Initialize_DatabaseDoesNotExist_CreatesDatabase() throws Exception{
        when(mockPlatformDatabaSystem.openDatabase(ErrorManagerDatabaseConstants.EXCEPTION_DATABASE_NAME)).thenThrow(new DatabaseNotFoundException());

        ErrorManagerRegistry testRegistry = new ErrorManagerRegistry();
        testRegistry.setPlatformDatabaseSystem(mockPlatformDatabaSystem);
        catchException(testRegistry).initialize();

        assertThat(caughtException()).isInstanceOf(NullPointerException.class);
    }

}
