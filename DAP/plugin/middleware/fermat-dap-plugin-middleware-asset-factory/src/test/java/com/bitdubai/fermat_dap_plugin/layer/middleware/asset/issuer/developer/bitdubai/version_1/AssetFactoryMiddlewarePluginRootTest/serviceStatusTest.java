package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.AssetFactoryMiddlewarePluginRootTest;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;

import junit.framework.TestCase;

import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.AssetFactoryMiddlewarePluginRoot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by franklin on 22/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class serviceStatusTest extends TestCase{
    @Mock
    Database database;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    private UUID testPluginId;
    private AssetFactoryMiddlewarePluginRoot assetFactoryMiddlewarePluginRoot;

    @Before
    public void setUp(){
        assetFactoryMiddlewarePluginRoot = new AssetFactoryMiddlewarePluginRoot();
        assetFactoryMiddlewarePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        testPluginId = UUID.randomUUID();
    }

    @Test
    public void createdTest(){
        org.junit.Assert.assertEquals(ServiceStatus.CREATED, assetFactoryMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void startedTest() throws CantStartPluginException {
        try{
            when(pluginDatabaseSystem.openDatabase(testPluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME)).thenReturn(database);
            assetFactoryMiddlewarePluginRoot.start();
        }
        catch (CantStartPluginException exception)
        {
            org.junit.Assert.assertNotNull(exception);
        }
        catch (Exception exception){
            org.junit.Assert.assertNotNull(exception);
        }
        org.junit.Assert.assertEquals(ServiceStatus.STARTED, assetFactoryMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void pausedTest(){
        assetFactoryMiddlewarePluginRoot.resume();
        org.junit.Assert.assertEquals(ServiceStatus.PAUSED, assetFactoryMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void resumedTest(){
        assetFactoryMiddlewarePluginRoot.resume();
        org.junit.Assert.assertEquals(ServiceStatus.STARTED, assetFactoryMiddlewarePluginRoot.getStatus());
    }

    @Test
    public void stopedTest(){
        assetFactoryMiddlewarePluginRoot.resume();
        org.junit.Assert.assertEquals(ServiceStatus.STARTED, assetFactoryMiddlewarePluginRoot.getStatus());
    }
}
