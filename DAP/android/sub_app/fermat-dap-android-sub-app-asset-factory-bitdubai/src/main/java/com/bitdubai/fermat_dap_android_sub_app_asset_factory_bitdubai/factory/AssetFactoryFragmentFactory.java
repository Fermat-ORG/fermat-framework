package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments.AssetEditorFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments.EditableAssetsFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments.PublishedAssetsFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.settings.AssetFactorySettings;

/**
 * AssetFactoryFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class AssetFactoryFragmentFactory extends FermatSubAppFragmentFactory<AssetFactorySession, AssetFactorySettings, AssetFactoryFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(AssetFactoryFragmentsEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT:
                return EditableAssetsFragment.newInstance();
            case DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT:
                return PublishedAssetsFragment.newInstance();
            case DAP_SUB_APP_ASSET_EDITOR_ACTIVITY:
                return AssetEditorFragment.newInstance(EditableAssetsFragment.getAssetForEdit());
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }

    @Override
    public AssetFactoryFragmentsEnumType getFermatFragmentEnumType(String key) {
        return AssetFactoryFragmentsEnumType.getValue(key);
    }
}
