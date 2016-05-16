package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_plugin.layer.crypto_module.crypto_address_book.developer.bitdubai.version_1.CryptoAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

/**
 * Created by natalia on 07/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private Database mockDatabase;

    CryptoAddressBookCryptoModulePluginRoot  cryptoAddressBookCryptoModulePluginRoot;

    private UUID pluginId;

    @Before
    public void setUp() throws Exception{

        pluginId = UUID.randomUUID();
        cryptoAddressBookCryptoModulePluginRoot = new CryptoAddressBookCryptoModulePluginRoot();

        cryptoAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        cryptoAddressBookCryptoModulePluginRoot.setId(UUID.randomUUID());

        cryptoAddressBookCryptoModulePluginRoot.setLogManager(logManager);

        cryptoAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);


        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);


    }

    @Test
    public void testValidStart_TrowsCantStartPluginException() throws Exception {

        cryptoAddressBookCryptoModulePluginRoot.start();
        Assert.assertEquals(cryptoAddressBookCryptoModulePluginRoot.getStatus(), ServiceStatus.STARTED);
    }

    /**
     * I will simulate an error saving the vault to verify the exception.
     * @throws CantStartPluginException
     */
    @Test
    public void testStartWithError() throws Exception {

        cryptoAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(null);
        catchException(cryptoAddressBookCryptoModulePluginRoot).start();
        assertThat(caughtException()).isNotNull().isInstanceOf(CantStartPluginException.class);
    }

    @Test
    public void stopTest() throws Exception {


        cryptoAddressBookCryptoModulePluginRoot.start();
        cryptoAddressBookCryptoModulePluginRoot.stop();
       Assert.assertEquals(cryptoAddressBookCryptoModulePluginRoot.getStatus(), ServiceStatus.STOPPED);
    }

    @Test
    public void pauseTest() throws Exception {

        cryptoAddressBookCryptoModulePluginRoot.start();
        cryptoAddressBookCryptoModulePluginRoot.pause();
      Assert.assertEquals(cryptoAddressBookCryptoModulePluginRoot.getStatus(), ServiceStatus.PAUSED);
    }



    @Test
    public void resumeTest() throws Exception {

        cryptoAddressBookCryptoModulePluginRoot.resume();
        Assert.assertEquals(cryptoAddressBookCryptoModulePluginRoot.getStatus(), ServiceStatus.STARTED);
    }
}
