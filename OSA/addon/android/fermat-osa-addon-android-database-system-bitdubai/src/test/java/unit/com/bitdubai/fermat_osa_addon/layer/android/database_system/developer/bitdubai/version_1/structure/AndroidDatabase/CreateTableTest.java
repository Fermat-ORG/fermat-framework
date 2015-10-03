package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.app.Activity;
import android.content.Context;
import android.support.v13.BuildConfig;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.apache.tools.ant.taskdefs.condition.And;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.robolectric.Shadows.shadowOf;
import unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.CustomBuildConfig;

/**
 * Created by jorgegonzalez on 2015.06.27..
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class)
public class CreateTableTest {

    private Activity mockActivity;
    private Context mockContext;

    private AndroidDatabase testDatabase;
    private UUID testOwnerId;
    private String testDatabaseName = "testDatabase";

    private String testTableName = "testTable";
    private DatabaseTableFactory testTableFactory;

    @Before
    public  void setUpDatabase() throws Exception {
        mockActivity = Robolectric.setupActivity(Activity.class);
        mockContext = shadowOf(mockActivity).getApplicationContext();
        testOwnerId = UUID.randomUUID();
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, testDatabaseName);
        testDatabase.createDatabase(testDatabaseName);
    }

    @Before
    public void setUpTableFactory(){
        testTableFactory = new AndroidDatabaseTableFactory(testTableName);
        testTableFactory.addColumn("testColumn1", DatabaseDataType.INTEGER, 0, false);
        testTableFactory.addColumn("testColumn2", DatabaseDataType.STRING, 10, false);
    }

    @Test
    public void CreateTableWithOwnerId_CorrectTableFactory_TableIsAddedAndCanBeGet() throws Exception{
        testDatabase.createTable(testOwnerId, testTableFactory);
        assertThat(testDatabase.getTable(testTableName).isTableExists()).isTrue();
    }

    @Test
    public void CreateTableWithOwnerId_TheOwnerUUIDIsDifferent_MethodInvokedSuccessfully() throws Exception{
        catchException(testDatabase).createTable(UUID.randomUUID(), testTableFactory);
        assertThat(caughtException()).isInstanceOf(InvalidOwnerIdException.class);

    }

    @Test
    public void CreateTableWithoutOwnerId_TheOwnerUUIDIsDifferent_MethodInvokedSuccessfully() throws Exception{
        testDatabase.createTable(testTableFactory);
        assertThat(testDatabase.getTable(testTableName)).isNotNull();
    }

    @Test
    public void CreateTable_NullTableFactory_ThrowCantCreateTableException() throws Exception{
        catchException(testDatabase).createTable(testOwnerId, null);
        assertThat(caughtException()).isInstanceOf(CantCreateTableException.class);

    }


    @Test
    public void CreateTable_InvalidOwnerId_ThrowCantCreateTableException() throws Exception{
        testDatabase = new AndroidDatabase(mockContext, testOwnerId, "DataBase2");
        testDatabase.createDatabase("DataBase2");
        catchException(testDatabase).createTable(UUID.randomUUID(), testTableFactory);
        assertThat(caughtException()).isInstanceOf(InvalidOwnerIdException.class);

    }

}
