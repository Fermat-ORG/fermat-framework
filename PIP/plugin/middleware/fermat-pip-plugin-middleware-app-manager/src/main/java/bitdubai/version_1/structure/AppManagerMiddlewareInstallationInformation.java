package bitdubai.version_1.structure;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationInformation;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.AppManagerMiddlewareInstallationInformation</code>
 * is the implementation of WalletInstallationInformation.
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppManagerMiddlewareInstallationInformation implements WalletInstallationInformation {


    private UUID catalogId;
    private UUID skinId;
    private UUID languageId;


    /**
     * WalletInstallationInformation Interface implementation.
     */

    /**
     * This method returns the identifier of the wallet to install
     */
    @Override
    public UUID getWalletCatalogId() {

        return this.catalogId;
    }

    /**
     * This method tells us the identifier of the selected skin to install
     */
    @Override
    public UUID getSkinId() {
        return this.skinId;
    }

    /**
     * This method gives us the identifier of the language selected to install
     */
    @Override
    public UUID getLanguageId() {
        return this.languageId;
    }
}
