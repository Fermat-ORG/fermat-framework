package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.enums.SkinState;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.middleware.wallet_skin.interfaces.WalletSkin</code>
 * indicates the functionality of a WalletSkin
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletSkin {

    /**
     * @return the unique id of the skin
     */
    UUID getId();

    /**
     * @return the id of the skin (can be repeated with different version)
     */
    UUID getSkinId();

    /**
     * @return the name of the skin
     */
    String getName();

    /**
     * @return the alias of the skin
     */
    String getAlias();

    /**
     * @return the state of the skin
     */
    SkinState getState();

    /**
     * @return the designer public key of the translator who is working with this skin
     */
    String getDesignerPublicKey();

    /**
     * @return the version of the skin
     */
    Version getVersion();

    /**
     * @return the version compatibility of the skin
     */
    VersionCompatibility getVersionCompatibility();

    String getPath();

}
