package com.bitdubai.reference_wallet.fan_wallet.fragmentFactory;


import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.fan_wallet.fragments.FollowingFragment;
import com.bitdubai.reference_wallet.fan_wallet.fragments.SongFragment;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;

/**
 * Created by Miguel Payarez on 15/03/16.
 */
public class FanWalletFragmentFactory extends FermatFragmentFactory<FanWalletSession,WalletResourcesProviderManager,FanWalletEnumtype> {
    @Override
    protected AbstractFermatFragment getFermatFragment(FanWalletEnumtype fragments) throws FragmentNotFoundException {
        if (fragments == null) {
            throw createFragmentNotFoundException(null);
        }

        if (fragments.equals(FanWalletEnumtype.TKY_FAN_WALLET_SONGS_TAB_FRAGMENT))
        {
            return SongFragment.newInstance();
        }

        if (fragments.equals(FanWalletEnumtype.TKY_FAN_WALLET_FOLLOWING_TAB_FRAGMENT))
        {
            return FollowingFragment.newInstance();
        }


        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public FanWalletEnumtype getFermatFragmentEnumType(String key) {
        return FanWalletEnumtype.getValues(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }


}
