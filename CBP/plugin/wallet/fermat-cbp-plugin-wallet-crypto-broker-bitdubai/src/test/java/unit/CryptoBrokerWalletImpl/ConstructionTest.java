package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private UUID pluginId = UUID.randomUUID();
    private ErrorManager errorManager = new ErrorManager(){

        @Override
        public void reportUnexpectedPlatformException(PlatformComponents exceptionSource, UnexpectedPlatformExceptionSeverity unexpectedPlatformExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedPluginException(Plugins exceptionSource, UnexpectedPluginExceptionSeverity unexpectedPluginExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedWalletException(Wallets exceptionSource, UnexpectedWalletExceptionSeverity unexpectedWalletExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedAddonsException(Addons exceptionSource, UnexpectedAddonsExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedSubAppException(SubApps exceptionSource, UnexpectedSubAppExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedUIException(UISource exceptionSource, UnexpectedUIExceptionSeverity unexpectedAddonsExceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedPluginException(PluginVersionReference exceptionSource, UnexpectedPluginExceptionSeverity exceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedAddonsException(AddonVersionReference exceptionSource, UnexpectedAddonsExceptionSeverity exceptionSeverity, Exception exception) {

        }

        @Override
        public void reportUnexpectedEventException(FermatEvent exceptionSource, Exception exception) {

        }
    };
    private PluginDatabaseSystem pluginDatabaseSystem = new PluginDatabaseSystem() {
        @Override
        public Database openDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {
            return null;
        }

        @Override
        public void deleteDatabase(UUID ownerId, String databaseName) throws CantOpenDatabaseException, DatabaseNotFoundException {

        }

        @Override
        public Database createDatabase(UUID ownerId, String databaseName) throws CantCreateDatabaseException {
            return null;
        }
    };
    private PluginFileSystem pluginFileSystem = new PluginFileSystem() {
        @Override
        public PluginTextFile getTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginTextFile createTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public boolean isTextFileExist(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws Exception {
            return false;
        }

        @Override
        public PluginBinaryFile getBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException, CantCreateFileException {
            return null;
        }

        @Override
        public PluginBinaryFile createBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException {
            return null;
        }

        @Override
        public void deleteTextFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }

        @Override
        public void deleteBinaryFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws CantCreateFileException, FileNotFoundException {

        }

        @Override
        public String getAppPath() {
            return null;
        }
    };
    private CurrencyExchangeProviderFilterManager providerFilter = new CurrencyExchangeProviderFilterManager() {
        @Override
        public Map<UUID, String> getProviderNames() throws CantGetProviderInfoException {
            return null;
        }

        @Override
        public Map<UUID, String> getProviderNamesListFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderInfoException {
            return null;
        }

        @Override
        public CurrencyExchangeRateProviderManager getProviderReference(UUID providerId) throws CantGetProviderException {
            return null;
        }

        @Override
        public Collection<CurrencyExchangeRateProviderManager> getProviderReferencesFromCurrencyPair(CurrencyPair currencyPair) throws CantGetProviderException {
            return null;
        }
    };


    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoBrokerWalletImpl cryptoBrokerWallet = new CryptoBrokerWalletImpl(
                this.errorManager,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.pluginId,
                this.providerFilter,
                Mockito.any(Broadcaster.class)
        );
        assertThat(cryptoBrokerWallet).isNotNull();
    }
}
