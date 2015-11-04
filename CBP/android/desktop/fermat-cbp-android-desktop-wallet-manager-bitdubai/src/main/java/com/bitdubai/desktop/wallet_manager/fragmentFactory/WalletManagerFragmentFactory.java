package com.bitdubai.desktop.wallet_manager.fragmentFactory;

import com.bitdubai.desktop.wallet_manager.fragments.MainFragment;
import com.bitdubai.desktop.wallet_manager.preference_settings.WalletManagerPreferenceSettings;
import com.bitdubai.desktop.wallet_manager.session.WalletManagerSubAppSession;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

import static com.bitdubai.desktop.wallet_manager.fragmentFactory.WalletManagerFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class WalletManagerFragmentFactory extends FermatSubAppFragmentFactory<WalletManagerSubAppSession, WalletManagerPreferenceSettings, WalletManagerFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(WalletManagerFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == MAIN_FRAGMET) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public WalletManagerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletManagerFragmentsEnumType.getValue(key);
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
