//package com.bitdubai.fermat_android_api.engine;
//
//import android.app.Fragment;
//
//import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
//
//import com.bitdubai.fermat_pip_api.all_definition.sub_app_module.settings.interfaces.SubAppSettings;
//import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
//
///**
// * Created by mati on 2015.08.24..
// */
//public abstract class FermatSubAppFragmentFactory <S extends SubAppsSession,F extends FermatFragmentsEnumType> implements SubAppFragmentFactory<S> {
//
//    protected AbstractFermatFragment fermatFragment;
//
//
//    /**
//     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
//     *
//     * @param code                           the reference used to identify the fragment
//     * @param subAppsSession
//     * @param subAppResourcesProviderManager @return the fragment referenced
//     */
//    @Override
//    public AbstractFermatFragment getFragment(String code, S subAppsSession, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException {
//
//        F fragments = getFermatFragmentEnumType(code);
//
//        fermatFragment = getFermatFragment(fragments);
//
//        fermatFragment.setSubAppsSession(subAppsSession);
//        fermatFragment.setSubAppResourcesProviderManager(subAppResourcesProviderManager);
//        return fermatFragment;
//    }
//
//    public abstract AbstractFermatFragment getFermatFragment(F fragments) throws FragmentNotFoundException;
//
//    public abstract F getFermatFragmentEnumType(String key);
//}
