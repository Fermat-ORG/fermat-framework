package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.fragments.IssuerCommunityHomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by francisco on 21/10/15.
 */
public class AssetIssuerCommunityFragmentFactory extends FermatFragmentFactory<AssetIssuerCommunitySubAppSession, SubAppResourcesProviderManager, AssetIssuerCommunityFragmentEnumTypes> {


    @Override
    public FermatFragment getFermatFragment(AssetIssuerCommunityFragmentEnumTypes fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN:
                return IssuerCommunityHomeFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-sub-app-asset-issuer-community", "fragment not found");
        }
    }

    @Override
    public AssetIssuerCommunityFragmentEnumTypes getFermatFragmentEnumType(String key) {
        return AssetIssuerCommunityFragmentEnumTypes.getValue(key);
    }
}
