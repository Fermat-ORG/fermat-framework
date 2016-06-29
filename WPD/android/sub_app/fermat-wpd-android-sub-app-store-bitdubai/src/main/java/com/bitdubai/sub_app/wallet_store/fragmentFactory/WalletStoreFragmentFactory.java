package com.bitdubai.sub_app.wallet_store.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_store.fragments.DetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MainActivityFragment;
import com.bitdubai.sub_app.wallet_store.fragments.MoreDetailsActivityFragment;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSessionReferenceApp;

import static com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentsEnumType.CWP_WALLET_STORE_DETAIL_ACTIVITY;
import static com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentsEnumType.CWP_WALLET_STORE_MAIN_ACTIVITY;
import static com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentsEnumType.CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class WalletStoreFragmentFactory extends FermatFragmentFactory<WalletStoreSubAppSessionReferenceApp, SubAppResourcesProviderManager, WalletStoreFragmentsEnumType> {

    @Override
    public AbstractFermatFragment getFermatFragment(WalletStoreFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == CWP_WALLET_STORE_MAIN_ACTIVITY)
            return MainActivityFragment.newInstance();
        if (fragments == CWP_WALLET_STORE_DETAIL_ACTIVITY)
            return DetailsActivityFragment.newInstance();
        if (fragments == CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY)
            return MoreDetailsActivityFragment.newInstance();

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public WalletStoreFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletStoreFragmentsEnumType.getValue(key);
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
