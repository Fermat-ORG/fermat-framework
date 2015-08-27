package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.BitcoinWalletBasicWalletPluginRoot;

import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.DiscountWalletBasicWalletPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_settings.developer.bitdubai.version_1.WalletSettingsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_publisher.developer.bitdubai.version_1.WalletPublisherModuleModulePluginRootPlugin;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.CryptoWalletWalletModulePluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.DeveloperIdentityPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.identity.publisher.publisher.bitdubai.version_1.PublisherIdentityPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.identity.designer.developer.bitdubai.version_1.IdentityDesignerPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.identity.translator.developer.bitdubai.version_1.IdentityTranslatorPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.WalletFactoryProjectMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_language.developer.bitdubai.version_1.WalletLanguageMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.WalletManagerMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.WalletSkinMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1.WalletStoreModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.crypto_transmission.developer.bitdubai.version_1.CryptoTransmissionNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.template.developer.bitdubai.version_1.TemplateNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1.WalletStatisticsNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.bank_notes_wallet.developer.bitdubai.version_1.BankNotesWalletWalletModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.discount_wallet.developer.bitdubai.version_1.DiscountWalletWalletModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.fiat_over_crypto_wallet.developer.bitdubai.version_1.FiatOverCryptoWalletWalletModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.wallet_module.multi_account_wallet.developer.bitdubai.version_1.MultiAccountWalletWalletModulePluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.WalletAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.bank_notes.developer.bitdubai.version_1.BankNotesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_community.developer.bitdubai.version_1.WalletCommunityNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.request.money_request.developer.bitdubai.version_1.MoneyRequestRequestPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_client.developer.bitdubai.version_1.CloudClientCommunicationChannelPluginRoot;
import com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.CloudServerCommunicationPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_device_user.developer.bitdubai.version_1.IncomingDeviceUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.IncomingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_intra_user.developer.bitdubai.version_1.IncomingIntraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.inter_wallet.developer.bitdubai.version_1.InterWalletTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitsubai.version_1.OutgoingDeviceUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.OutgoingExtraUserTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.OutgoingIntraUserTransactionPluginRoot;


/*

World layer projects on the pipeline:

import com.bitdubai.fermat_dmp_plugin.layer.world.coinbase.developer.bitdubai.version_1.CoinbaseWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.coinapult.developer.bitdubai.version_1.CoinapultWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.blockchain_info.developer.bitdubai.version_1.BlockchainInfoWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.CryptoIndexWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.shape_shift.developer.bitdubai.version_1.ShapeShiftWorldPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.world.location.developer.bitdubai.version_1.LocationWorldPluginRoot;

 */

import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.SubAppRuntimeMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.BankNotesMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.WalletManagerModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.module.developer.developer.bitdubai.version_1.ModuleDeveloperPluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.SubAppResourcesInstalationNetworkServicePluginRoot;
import com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.navigation_structure.developer.bitdubai.version_1.WalletNavigationStructureManagerMiddlewarePluginRoot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 */
public class PluginsIdentityManager implements Serializable{

    private static final Integer AMOUNT_OF_KNOWN_PLUGINS = 66;

    private PlatformFileSystem platformFileSystem;
    private List<UUID> pluginIds = new ArrayList<>();


    public PluginsIdentityManager(PlatformFileSystem platformFileSystem) throws CantInitializePluginsManagerException {

        this.platformFileSystem = platformFileSystem;

        PlatformTextFile platformTextFile;

        try {
            /**
             * First I get the file where all ids are stored. 
             */
            try {
                platformTextFile = platformFileSystem.getFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            } catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                System.err.println("CantCreateFileException: " + cantCreateFileException.getMessage());
                cantCreateFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }

            try {
                platformTextFile.persistToMedia();
            } catch (CantPersistFileException cantLoadFileException) {
                /**
                 * Until we implement some kind of backup of this critical file, there is no possible recovery from this 
                 * situation, 
                 */
                System.err.println("CantLoadFileException: " + cantLoadFileException.getMessage());
                cantLoadFileException.printStackTrace();
                throw new CantInitializePluginsManagerException();
            }

            /**
             * Then I put the content of the file on an Array String.
             */

            String[] stringPluginIds = platformTextFile.getContent().split(";", -1);

            Integer arrayPosition = 0;

            for (String stringPluginId : stringPluginIds) {

                if (stringPluginId != "") {
                    try {
                        pluginIds.add(arrayPosition, UUID.fromString(stringPluginId));
                        arrayPosition++;
                    } catch (IllegalArgumentException e) {
                        /**
                         * This exception occurs when we reach the end of the file. So there is nothing to do here.
                         */
                    }

                }

            }

            /**
             * Now I check if the amount of plugins on file is the same that the amount of plugin implemented
             */

            if (arrayPosition < AMOUNT_OF_KNOWN_PLUGINS) {
                /**
                 * Under this condition, means that since the last time the platform start, new plugins were added to the
                 * platform, and as these new plugins needs new ids, we are going to create one for each of them. 
                 */

                for (int index = arrayPosition; index < AMOUNT_OF_KNOWN_PLUGINS; index++) {

                    UUID newId = UUID.randomUUID();

                    pluginIds.add(index, newId);
                }

                try {
                    savePluginIds(platformTextFile);
                } catch (CantPersistFileException cantPersistFileException) {
                    /**
                     * If I cannot save this file, It means the Plugin Manager cannot start,
                     */
                    String message = CantInitializePluginsManagerException.DEFAULT_MESSAGE;
                    FermatException cause = cantPersistFileException;
                    String context = "Platform Text File Name: " + platformTextFile.toString();
                    String possibleReason = "Check the cause for the reason";
                    throw new CantInitializePluginsManagerException(message, cause, context, possibleReason);
                }

            }

        } catch (FileNotFoundException fileNotFoundException) {
            try {
                platformTextFile = platformFileSystem.createFile("Platform", "PluginsIds", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            } catch (CantCreateFileException cantCreateFileException) {
                /**
                 * This really should never happen. But if it does...
                 */
                String message = CantInitializePluginsManagerException.DEFAULT_MESSAGE;
                FermatException cause = cantCreateFileException;
                String context = "Create File Params: Platform, PluginIds, PRIVATE, PERMANENT";
                String possibleReason = "Check the cause for the reason";
                throw new CantInitializePluginsManagerException(message, cause, context, possibleReason);
            }

            for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

                UUID newId = UUID.randomUUID();

                pluginIds.add(arrayPosition, newId);
            }

            try {
                savePluginIds(platformTextFile);
            } catch (CantPersistFileException cantPersistFileException) {
                /**
                 * If I cannot save this file, It means the Plugin Manager cannot start,
                 */
                String message = CantInitializePluginsManagerException.DEFAULT_MESSAGE;
                FermatException cause = cantPersistFileException;
                String context = "Platform Text File Name: " + platformTextFile.toString();
                String possibleReason = "Check the cause for the reason";
                throw new CantInitializePluginsManagerException(message, cause, context, possibleReason);
            }

        }

    }

    public UUID getPluginId(Plugin plugin, Plugins descriptor) throws PluginNotRecognizedException {

        UUID pluginId = new UUID(0, 0);
        Integer pluginIndex = 0;

        /* Plugin on the pipeline:
        //THIS WILL NEVER WORK, WE CAN'T USE THE 0 FOR A PLUGIN IF THE REST OF VERIFICATION GO AGAINST 0

        if (pluginIndex == 0) {
            try {
                CryptoIndexWorldPluginRoot tryType;
                tryType = (CryptoIndexWorldPluginRoot) plugin;
                pluginIndex = 0;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
        }
*/
        if (plugin instanceof BitcoinCryptoNetworkPluginRoot)
            pluginIndex = 1;

        if (plugin instanceof CloudClientCommunicationChannelPluginRoot)
            pluginIndex = 2;

        if (plugin instanceof DiscountWalletBasicWalletPluginRoot)
            pluginIndex = 3;

        if (plugin instanceof WalletManagerModulePluginRoot)
            pluginIndex = 4;

        if (plugin instanceof WalletRuntimeModulePluginRoot)
            pluginIndex = 5;

        if (plugin instanceof SubAppRuntimeMiddlewarePluginRoot)
            pluginIndex = 6;

        if (plugin instanceof IncomingExtraUserTransactionPluginRoot)
            pluginIndex = 7;

        if (plugin instanceof IncomingIntraUserTransactionPluginRoot)
            pluginIndex = 8;

        if (plugin instanceof InterWalletTransactionPluginRoot)
            pluginIndex = 9;

        //if(plugin instanceof CryptoIndexWorldPluginRoot)
        //    pluginIndex = 22;

        if (plugin instanceof OutgoingDeviceUserTransactionPluginRoot)
            pluginIndex = 11;

        if (plugin instanceof BankNotesMiddlewarePluginRoot)
            pluginIndex = 12;

        if (plugin instanceof BankNotesNetworkServicePluginRoot)
            pluginIndex = 13;

        if (plugin instanceof WalletResourcesNetworkServicePluginRoot)
            pluginIndex = 14;

        if (plugin instanceof WalletStoreNetworkServicePluginRoot)
            pluginIndex = 15;

        if (plugin instanceof WalletContactsMiddlewarePluginRoot)
            pluginIndex = 16;

        if (plugin instanceof WalletCommunityNetworkServicePluginRoot)
            pluginIndex = 17;

        if (plugin instanceof ActorAddressBookCryptoModulePluginRoot)
            pluginIndex = 18;

        if (plugin instanceof IncomingDeviceUserTransactionPluginRoot)
            pluginIndex = 19;

        if (plugin instanceof OutgoingExtraUserTransactionPluginRoot)
            pluginIndex = 20;

        if (plugin instanceof OutgoingIntraUserTransactionPluginRoot)
            pluginIndex = 21;

        //if(plugin instanceof BlockchainInfoWorldPluginRoot)
        //    pluginIndex = 22;

        //if(plugin instanceof CoinapultWorldPluginRoot)
        //    pluginIndex = 23;

        //if(plugin instanceof ShapeShiftWorldPluginRoot)
        //    pluginIndex = 24;


        if (plugin instanceof IncomingCryptoTransactionPluginRoot)
            pluginIndex = 25;

        //if(plugin instanceof CoinbaseWorldPluginRoot)
        //    pluginIndex = 26;

        if (plugin instanceof CloudServerCommunicationPluginRoot)
            pluginIndex = 27;

        if (plugin instanceof WalletFactoryModulePluginRoot)
            pluginIndex = 28;

        if (plugin instanceof BitcoinWalletBasicWalletPluginRoot)
            pluginIndex = 29;

        if (plugin instanceof WalletAddressBookCryptoModulePluginRoot)
            pluginIndex = 30;

        //if(plugin instanceof LocationWorldPluginRoot)
        //    pluginIndex = 31;

        if (plugin instanceof BankNotesWalletWalletModulePluginRoot)
            pluginIndex = 32;

        //if(plugin instanceof CryptoLossProtectedWalletWalletModulePluginRoot)
        //    pluginIndex = 33;


        if (plugin instanceof CryptoWalletWalletModulePluginRoot)
            pluginIndex = 34;

        if (plugin instanceof DiscountWalletWalletModulePluginRoot)
            pluginIndex = 35;

        //if(plugin instanceof FiatOverCryptoLossProtectedWalletWalletModulePluginRoot)
        //    pluginIndex = 36;

        if (plugin instanceof FiatOverCryptoWalletWalletModulePluginRoot)
            pluginIndex = 37;

        if (plugin instanceof MultiAccountWalletWalletModulePluginRoot)
            pluginIndex = 38;

        if (plugin instanceof BitcoinCryptoVaultPluginRoot)
            pluginIndex = 39;

        if (plugin instanceof ActorDeveloperPluginRoot)
            pluginIndex = 40;

        if (plugin instanceof ExtraUserUserAddonRoot)
            pluginIndex = 41;

        if (plugin instanceof WalletFactoryProjectMiddlewarePluginRoot)
            pluginIndex = 42;

        if (plugin instanceof WalletManagerMiddlewarePluginRoot)
            pluginIndex = 43;

        if (plugin instanceof WalletPublisherMiddlewarePluginRoot)
            pluginIndex = 44;

        if (plugin instanceof WalletStoreMiddlewarePluginRoot)
            pluginIndex = 45;

        if (plugin instanceof WalletStatisticsNetworkServicePluginRoot)
            pluginIndex = 46;

        if (plugin instanceof DeveloperIdentityPluginRoot)
            pluginIndex = 47;

        if (plugin instanceof WalletSkinMiddlewarePluginRoot)
            pluginIndex = 48;

        if (plugin instanceof WalletLanguageMiddlewarePluginRoot)
            pluginIndex = 49;

        if (plugin instanceof WalletNavigationStructureManagerMiddlewarePluginRoot)
            pluginIndex = 50;

        if (plugin instanceof IntraUserModulePluginRoot)
            pluginIndex = 51;

        if (plugin instanceof IntraUserActorPluginRoot)
            pluginIndex = 52;

        if (plugin instanceof IntraUserIdentityPluginRoot)
            pluginIndex = 53;

        if (plugin instanceof ModuleDeveloperPluginRoot)
            pluginIndex = 54;

        if (plugin instanceof IdentityTranslatorPluginRoot)
            pluginIndex = 55;

        if (plugin instanceof IdentityDesignerPluginRoot)
            pluginIndex = 56;

        if (plugin instanceof CryptoTransmissionNetworkServicePluginRoot)
            pluginIndex = 57;

        if (plugin instanceof WalletStoreModulePluginRoot)
            pluginIndex = 58;

        if (plugin instanceof TemplateNetworkServicePluginRoot)
            pluginIndex = 59;

        if (plugin instanceof MoneyRequestRequestPluginRoot)
            pluginIndex = 60;

        if (plugin instanceof SubAppResourcesInstalationNetworkServicePluginRoot)
            pluginIndex = 61;

        if (plugin instanceof PublisherIdentityPluginRoot)
            pluginIndex = 62;

        if (plugin instanceof IntraUserNetworkServicePluginRoot)
            pluginIndex = 63;

        if (plugin instanceof WalletSettingsMiddlewarePluginRoot){
            pluginIndex = 64;
        }
        if (plugin instanceof WalletPublisherModuleModulePluginRootPlugin)
            pluginIndex = 65;

        if (pluginIndex > 0)
            return pluginIds.get(pluginIndex);
        else
            throw new PluginNotRecognizedException(PluginNotRecognizedException.DEFAULT_MESSAGE, null, "Plugin Descriptor: " + descriptor.getKey(), null);
    }


    private void savePluginIds(PlatformTextFile platformTextFile) throws CantPersistFileException {

        String fileContent = "";

        for (int arrayPosition = 0; arrayPosition < AMOUNT_OF_KNOWN_PLUGINS; arrayPosition++) {

            fileContent = fileContent + pluginIds.get(arrayPosition).toString() + ";";

        }

        platformTextFile.setContent(fileContent);

        platformTextFile.persistToMedia();
    }

}
