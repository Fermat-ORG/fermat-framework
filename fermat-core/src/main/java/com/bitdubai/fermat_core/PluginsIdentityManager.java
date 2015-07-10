package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.CantInitializePluginsManagerException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginNotRecognizedException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.BitcoinWalletBasicWalletPluginRoot;

import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.discount_wallet.developer.bitdubai.version_1.DiscountWalletBasicWalletPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.WalletFactoryMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.WalletManagerMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.WalletPublisherMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.WalletStoreMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_statistics.developer.bitdubai.version_1.WalletStatisticsNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.bank_notes_wallet.developer.bitdubai.version_1.BankNotesWalletNicheWalletTypePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.CryptoWalletNicheWalletTypePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.discount_wallet.developer.bitdubai.version_1.DiscountWalletNicheWalletTypePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.fiat_over_crypto_wallet.developer.bitdubai.version_1.FiatOverCryptoWalletNicheWalletTypePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.multi_account_wallet.developer.bitdubai.version_1.MultiAccountWalletNicheWalletTypePluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.WalletAddressBookCryptoModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.bank_notes.developer.bitdubai.version_1.BankNotesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_community.developer.bitdubai.version_1.WalletCommunityNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.WalletResourcesNetworkServicePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.WalletStoreNetworkServicePluginRoot;
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
import com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.AppRuntimeMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.bank_notes.developer.bitdubai.version_1.BankNotesMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.WalletContactsMiddlewarePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_manager.developer.bitdubai.version_1.WalletManagerModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.engine.wallet_runtime.developer.bitdubai.version_1.WalletRuntimeModulePluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.BitcoinCryptoNetworkPluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;
import com.bitdubai.fermat_pip_plugin.layer.identity.developer.developer.bitdubai.version_1.DeveloperIdentityPluginRoot;
import com.fermat_dmp_plugin.layer.module.wallet_factory.developer.bitdubai.version_1.WalletFactoryModulePluginRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 02.02.15.
 */
public class PluginsIdentityManager {

    private PlatformFileSystem platformFileSystem;
    private final Integer AMOUNT_OF_KNOWN_PLUGINS = 48;
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

    public UUID getPluginId(Plugin plugin) throws PluginNotRecognizedException {

        UUID pluginId = new UUID(0, 0);
        Integer pluginIndex = 0;

        /* Plugin on the pipeline:


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

        if (pluginIndex == 0) {
            try {
                BitcoinCryptoNetworkPluginRoot tryType;
                tryType = (BitcoinCryptoNetworkPluginRoot) plugin;
                pluginIndex = 1;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                CloudClientCommunicationChannelPluginRoot tryType;
                tryType = (CloudClientCommunicationChannelPluginRoot) plugin;
                pluginIndex = 2;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                DiscountWalletBasicWalletPluginRoot tryType;
                tryType = (DiscountWalletBasicWalletPluginRoot) plugin;
                pluginIndex = 3;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }
        if (pluginIndex == 0) {
            try {
                WalletManagerModulePluginRoot tryType;
                tryType = (WalletManagerModulePluginRoot) plugin;
                pluginIndex = 4;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }
        if (pluginIndex == 0) {
            try {
                WalletRuntimeModulePluginRoot tryType;
                tryType = (WalletRuntimeModulePluginRoot) plugin;
                pluginIndex = 5;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                AppRuntimeMiddlewarePluginRoot tryType;
                tryType = (AppRuntimeMiddlewarePluginRoot) plugin;
                pluginIndex = 6;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                IncomingExtraUserTransactionPluginRoot tryType;
                tryType = (IncomingExtraUserTransactionPluginRoot) plugin;
                pluginIndex = 7;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try {
                IncomingIntraUserTransactionPluginRoot tryType;
                tryType = (IncomingIntraUserTransactionPluginRoot) plugin;
                pluginIndex = 8;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try {
                InterWalletTransactionPluginRoot tryType;
                tryType = (InterWalletTransactionPluginRoot) plugin;
                pluginIndex = 9;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

                /* Plugin on the pipeline:

        if (pluginIndex == 0) {
            try {
                CryptoIndexWorldPluginRoot tryType;
                tryType = (CryptoIndexWorldPluginRoot) plugin;
                pluginIndex = 10;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 /
            }
        }
*/
        if (pluginIndex == 0) {
            try {
                OutgoingDeviceUserTransactionPluginRoot tryType;
                tryType = (OutgoingDeviceUserTransactionPluginRoot) plugin;
                pluginIndex = 11;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                BankNotesMiddlewarePluginRoot tryType;
                tryType = (BankNotesMiddlewarePluginRoot) plugin;
                pluginIndex = 12;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                BankNotesNetworkServicePluginRoot tryType;
                tryType = (BankNotesNetworkServicePluginRoot) plugin;
                pluginIndex = 13;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletResourcesNetworkServicePluginRoot tryType;
                tryType = (WalletResourcesNetworkServicePluginRoot) plugin;
                pluginIndex = 14;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletStoreNetworkServicePluginRoot tryType;
                tryType = (WalletStoreNetworkServicePluginRoot) plugin;
                pluginIndex = 15;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletContactsMiddlewarePluginRoot tryType;
                tryType = (WalletContactsMiddlewarePluginRoot) plugin;
                pluginIndex = 16;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletCommunityNetworkServicePluginRoot tryType;
                tryType = (WalletCommunityNetworkServicePluginRoot) plugin;
                pluginIndex = 17;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                ActorAddressBookCryptoModulePluginRoot tryType;
                tryType = (ActorAddressBookCryptoModulePluginRoot) plugin;
                pluginIndex = 18;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                IncomingDeviceUserTransactionPluginRoot tryType;
                tryType = (IncomingDeviceUserTransactionPluginRoot) plugin;
                pluginIndex = 19;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                OutgoingExtraUserTransactionPluginRoot tryType;
                tryType = (OutgoingExtraUserTransactionPluginRoot) plugin;
                pluginIndex = 20;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                OutgoingIntraUserTransactionPluginRoot tryType;
                tryType = (OutgoingIntraUserTransactionPluginRoot) plugin;
                pluginIndex = 21;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

                /* Plugin on the pipeline:

        if (pluginIndex == 0) {
            try {
                BlockchainInfoWorldPluginRoot tryType;
                tryType = (BlockchainInfoWorldPluginRoot) plugin;
                pluginIndex = 22;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
        }


        if (pluginIndex == 0) {
            try {
                CoinapultWorldPluginRoot tryType;
                tryType = (CoinapultWorldPluginRoot) plugin;
                pluginIndex = 23;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
        }

        if (pluginIndex == 0) {
            try {
                ShapeShiftWorldPluginRoot tryType;
                tryType = (ShapeShiftWorldPluginRoot) plugin;
                pluginIndex = 24;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
                */

        if (pluginIndex == 0) {
            try {
                IncomingCryptoTransactionPluginRoot tryType;
                tryType = (IncomingCryptoTransactionPluginRoot) plugin;
                pluginIndex = 25;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

/*
        if (pluginIndex == 0) {
            try {
                CoinbaseWorldPluginRoot tryType;
                tryType = (CoinbaseWorldPluginRoot) plugin;
                pluginIndex = 26;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
        }
*/

        if (pluginIndex == 0) {
            try {
                CloudServerCommunicationPluginRoot tryType;
                tryType = (CloudServerCommunicationPluginRoot) plugin;
                pluginIndex = 27;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletFactoryModulePluginRoot tryType;
                tryType = (WalletFactoryModulePluginRoot) plugin;
                pluginIndex = 28;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                BitcoinWalletBasicWalletPluginRoot tryType;
                tryType = (BitcoinWalletBasicWalletPluginRoot) plugin;
                pluginIndex = 29;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletAddressBookCryptoModulePluginRoot tryType;
                tryType = (WalletAddressBookCryptoModulePluginRoot) plugin;
                pluginIndex = 30;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

    /*
        if (pluginIndex == 0) {
            try {
                LocationWorldPluginRoot tryType;
                tryType = (LocationWorldPluginRoot) plugin;
                pluginIndex = 31;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 *
            }
        }*/

        if (pluginIndex == 0) {
            try {
                BankNotesWalletNicheWalletTypePluginRoot tryType;
                tryType = (BankNotesWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 32;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try {
                //CryptoLossProtectedWalletNicheWalletTypePluginRoot tryType;
                // tryType = (CryptoLossProtectedWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 33;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex == 0) {
            try {
                CryptoWalletNicheWalletTypePluginRoot tryType;
                tryType = (CryptoWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 34;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                DiscountWalletNicheWalletTypePluginRoot tryType;
                tryType = (DiscountWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 35;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                //  FiatOverCryptoLossProtectedWalletNicheWalletTypePluginRoot tryType;
                //  tryType = (FiatOverCryptoLossProtectedWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 36;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                FiatOverCryptoWalletNicheWalletTypePluginRoot tryType;
                tryType = (FiatOverCryptoWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 37;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                MultiAccountWalletNicheWalletTypePluginRoot tryType;
                tryType = (MultiAccountWalletNicheWalletTypePluginRoot) plugin;
                pluginIndex = 38;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                BitcoinCryptoVaultPluginRoot tryType;
                tryType = (BitcoinCryptoVaultPluginRoot) plugin;
                pluginIndex = 39;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                ActorDeveloperPluginRoot tryType;
                tryType = (ActorDeveloperPluginRoot) plugin;
                pluginIndex = 40;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                ExtraUserUserAddonRoot tryType;
                tryType = (ExtraUserUserAddonRoot) plugin;
                pluginIndex = 41;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletFactoryMiddlewarePluginRoot tryType;
                tryType = (WalletFactoryMiddlewarePluginRoot) plugin;
                pluginIndex = 42;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletManagerMiddlewarePluginRoot tryType;
                tryType = (WalletManagerMiddlewarePluginRoot) plugin;
                pluginIndex = 43;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletPublisherMiddlewarePluginRoot tryType;
                tryType = (WalletPublisherMiddlewarePluginRoot) plugin;
                pluginIndex = 44;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletStoreMiddlewarePluginRoot tryType;
                tryType = (WalletStoreMiddlewarePluginRoot) plugin;
                pluginIndex = 45;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                WalletStatisticsNetworkServicePluginRoot tryType;
                tryType = (WalletStatisticsNetworkServicePluginRoot) plugin;
                pluginIndex = 46;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }

        if (pluginIndex == 0) {
            try {
                DeveloperIdentityPluginRoot tryType;
                tryType = (DeveloperIdentityPluginRoot) plugin;
                pluginIndex = 47;
            } catch (Exception e) {
                /**
                 * If this fails, is because this is not the index for this plug in.
                 */
            }
        }


        if (pluginIndex > 0) {

            return pluginIds.get(pluginIndex);

        } else {
            throw new PluginNotRecognizedException();
        }

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
