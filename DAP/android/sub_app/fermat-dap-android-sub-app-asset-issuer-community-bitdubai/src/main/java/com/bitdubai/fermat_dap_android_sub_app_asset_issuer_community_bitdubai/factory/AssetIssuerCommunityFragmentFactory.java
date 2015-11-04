package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.fragments.HomeFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.settings.AssetIssuerSubAppSettings;

import org.apache.commons.lang.NotImplementedException;

/**
 * Created by francisco on 21/10/15.
 */
public class AssetIssuerCommunityFragmentFactory extends FermatSubAppFragmentFactory<AssetIssuerCommunitySubAppSession, AssetIssuerSubAppSettings, AssetIssuerCommunityFragmentEnumTypes> {


    @Override
    public FermatFragment getFermatFragment(AssetIssuerCommunityFragmentEnumTypes fragments) throws FragmentNotFoundException {
        FermatFragment fragment = null;
        switch (fragments) {
            case DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN:
                fragment = HomeFragment.newInstance();
                break;
            default:
                throw new NotImplementedException("this fragment is not implemented yet...");
        }
        return fragment;
    }

    @Override
    public AssetIssuerCommunityFragmentEnumTypes getFermatFragmentEnumType(String key) {
        return AssetIssuerCommunityFragmentEnumTypes.getValue(key);
    }
}
