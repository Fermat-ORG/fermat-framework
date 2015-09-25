package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.enums.SkinState;
import com.bitdubai.fermat_api.layer.ccp_middleware.wallet_skin.interfaces.WalletSkin;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_skin.developer.bitdubai.version_1.structure.WalletSkinMiddlewareWalletSkin</code>
 * implements the functionality of a WalletSKin<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletSkinMiddlewareWalletSkin implements WalletSkin {

    private UUID id;

    private UUID skinId;

    private String name;

    private String alias;

    private SkinState state;

    private String designerPublicKey;

    private Version version;

    private VersionCompatibility versionCompatibility;

    private String path;

    public WalletSkinMiddlewareWalletSkin(UUID id, UUID skinId, String name, String alias, String path, SkinState state, String designerPublicKey, Version version, VersionCompatibility versionCompatibility) {
        this.id = id;
        this.skinId = skinId;
        this.name = name;
        this.alias = alias;
        this.state = state;
        this.path = path;
        this.designerPublicKey = designerPublicKey;
        this.version = version;
        this.versionCompatibility = versionCompatibility;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getSkinId() {
        return skinId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public SkinState getState() {
        return state;
    }

    @Override
    public String getDesignerPublicKey() {
        return designerPublicKey;
    }

    @Override
    public Version getVersion() {
        return version;
    }

    @Override
    public VersionCompatibility getVersionCompatibility() {
        return versionCompatibility;
    }

    @Override
    public String getPath() {
        return path + name;
    }
}
