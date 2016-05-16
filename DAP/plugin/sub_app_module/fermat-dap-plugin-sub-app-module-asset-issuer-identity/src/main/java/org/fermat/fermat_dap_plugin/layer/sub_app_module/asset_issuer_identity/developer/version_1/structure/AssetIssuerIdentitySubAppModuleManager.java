package org.fermat.fermat_dap_plugin.layer.sub_app_module.asset_issuer_identity.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantCreateNewIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantGetAssetIssuerIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantListAssetIssuersException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantUpdateIdentityAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantCreateNewRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantGetRedeemPointIdentitiesException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantUpdateIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_identity.IssuerIdentitySettings;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_identity.interfaces.AssetIssuerIdentityModuleManager;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * Created by nerio on 14/5/2016.
 */
public class AssetIssuerIdentitySubAppModuleManager extends ModuleManagerImpl<IssuerIdentitySettings> implements AssetIssuerIdentityModuleManager, Serializable {

    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final IdentityAssetIssuerManager identityAssetIssuerManager;

    public AssetIssuerIdentitySubAppModuleManager(IdentityAssetIssuerManager identityAssetIssuerManager, PluginFileSystem pluginFileSystem, UUID pluginId) {
        super(pluginFileSystem, pluginId);

        this.identityAssetIssuerManager = identityAssetIssuerManager;
        this.pluginFileSystem    = pluginFileSystem;
        this.pluginId            = pluginId;
    }

    @Override
    public List<IdentityAssetIssuer> getIdentityAssetIssuersFromCurrentDeviceUser() throws CantListAssetIssuersException {
        return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
    }

    @Override
    public IdentityAssetIssuer getIdentityAssetIssuer() throws CantGetAssetIssuerIdentitiesException {
        return identityAssetIssuerManager.getIdentityAssetIssuer();
    }

    @Override
    public IdentityAssetIssuer createNewIdentityAssetIssuer(String alias, byte[] profileImage) throws CantCreateNewIdentityAssetIssuerException {
        return identityAssetIssuerManager.createNewIdentityAssetIssuer(alias, profileImage);
    }

    @Override
    public void updateIdentityAssetIssuer(String identityPublicKey, String identityAlias, byte[] profileImage) throws CantUpdateIdentityAssetIssuerException {
        identityAssetIssuerManager.updateIdentityAssetIssuer(identityPublicKey, identityAlias, profileImage);
    }

    @Override
    public boolean hasIntraIssuerIdentity() throws CantListAssetIssuersException {
        return identityAssetIssuerManager.hasIntraIssuerIdentity();
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException {
        try {
            List<IdentityAssetIssuer> identities = identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
            return (identities == null || identities.isEmpty()) ? null : identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser().get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        identityAssetIssuerManager.createNewIdentityAssetIssuer(name, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
