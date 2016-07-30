package ChatIdentityManagerImpl;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedAddonsExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPlatformExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.AddonVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformComponents;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
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
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantAcceptConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantCancelConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantConfirmException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDenyConnectionRequestException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantDisconnectException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentitiesException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantExposeIdentityException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantListPendingConnectionRequestsException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.interfaces.ChatSearch;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionInformation;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatConnectionRequest;
import com.bitdubai.fermat_cht_api.layer.actor_network_service.utils.ChatExposingData;
import com.bitdubai.fermat_cht_plugin.layer.identity.chat.developer.bitdubai.version_1.structure.ChatIdentityManagerImpl;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantCreateNewDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetDeviceUserListException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantSetImageException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.IncorrectUserOrPasswordException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.LoginFailedException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;

import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by Miguel Rincon on 4/15/16.
 */
public class ConstructionTest {

    private final PluginDatabaseSystem mPluginDatabaseSystem = new PluginDatabaseSystem() {
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
    private final UUID mPluginId = UUID.randomUUID();
    private final ErrorManager mErrorManager = new ErrorManager() {
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
    private final DeviceUserManager mDeviceUserManager = new DeviceUserManager() {
        @Override
        public String createNewDeviceUser(String alias, String password) throws CantCreateNewDeviceUserException {
            return null;
        }

        @Override
        public String createNewDeviceUser(String alias, String password, byte[] personalImage) throws CantCreateNewDeviceUserException {
            return null;
        }

        @Override
        public List<DeviceUser> getAllDeviceUsers() throws CantGetDeviceUserListException {
            return null;
        }

        @Override
        public DeviceUser getDeviceUser(String publicKey) throws CantGetDeviceUserException {
            return null;
        }

        @Override
        public DeviceUser getLoggedInDeviceUser() throws CantGetLoggedInDeviceUserException {
            return null;
        }

        @Override
        public void login(String publicKey, String password) throws LoginFailedException, IncorrectUserOrPasswordException {

        }

        @Override
        public void logout() {

        }

        @Override
        public void setPersonalImage(byte[] personalImage) throws CantSetImageException {

        }
    };
    private final PluginFileSystem mPluginFileSystem = new PluginFileSystem() {
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
    private final ChatManager mChatManager = new ChatManager() {
        @Override
        public void exposeIdentity(ChatExposingData chatExposingData) throws CantExposeIdentityException {

        }

        @Override
        public void updateIdentity(ChatExposingData chatExposingData) throws CantExposeIdentityException {

        }

        @Override
        public void exposeIdentities(Collection<ChatExposingData> chatExposingDataList) throws CantExposeIdentitiesException {

        }

        @Override
        public ChatSearch getSearch() {
            return null;
        }

        @Override
        public void requestConnection(ChatConnectionInformation chatConnectionInformation) throws CantRequestConnectionException {

        }

        @Override
        public void disconnect(UUID requestId) throws CantDisconnectException, ConnectionRequestNotFoundException {

        }

        @Override
        public void denyConnection(UUID requestId) throws CantDenyConnectionRequestException, ConnectionRequestNotFoundException {

        }

        @Override
        public void cancelConnection(UUID requestId) throws CantCancelConnectionRequestException, ConnectionRequestNotFoundException {

        }

        @Override
        public void acceptConnection(UUID requestId) throws CantAcceptConnectionRequestException, ConnectionRequestNotFoundException {

        }

        @Override
        public List<ChatConnectionRequest> listPendingConnectionNews(Actors actorType) throws CantListPendingConnectionRequestsException {
            return null;
        }

        @Override
        public List<ChatConnectionRequest> listPendingConnectionUpdates() throws CantListPendingConnectionRequestsException {
            return null;
        }

        @Override
        public void confirm(UUID requestId) throws CantConfirmException, ConnectionRequestNotFoundException {

        }
    };

    @Test
    public void contructionWithValidParametersNewObjectCreated() {
        ChatIdentityManagerImpl chatIdentityManager = new ChatIdentityManagerImpl(
                this.mPluginDatabaseSystem,
                this.mPluginId,
                this.mErrorManager,
                this.mDeviceUserManager,
                this.mPluginFileSystem,
                this.mChatManager
        );
        assertThat(chatIdentityManager).isNotNull();
    }

}
