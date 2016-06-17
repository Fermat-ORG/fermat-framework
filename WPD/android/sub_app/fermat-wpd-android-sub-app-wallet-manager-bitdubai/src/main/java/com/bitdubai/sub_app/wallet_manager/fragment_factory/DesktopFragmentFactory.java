package com.bitdubai.sub_app.wallet_manager.fragment_factory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.wallet_manager.fragment.CommunitiesExpandibleFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopP2PApssFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopSocialApssFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.FermatNetworkSettings;
import com.bitdubai.sub_app.wallet_manager.fragment.settings.ExportImportSeedFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.settings.MoreSettingsFragment;
import com.bitdubai.sub_app.wallet_manager.fragment.welcome_wizard.WelcomeWizardFragment;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSessionReferenceApp;


/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class DesktopFragmentFactory extends FermatFragmentFactory<DesktopSessionReferenceApp, ResourceProviderManager,DesktopFragmentsEnumType> {


    @Override
    public Fragment getFermatFragment(DesktopFragmentsEnumType fragments) throws FragmentNotFoundException {

        Fragment abstractFermatFragment = null;

        switch (fragments){
            case DESKTOP_MAIN:
                abstractFermatFragment = DesktopFragment.newInstance();
                break;
            case SETTINGS:
                abstractFermatFragment = FermatNetworkSettings.newInstance();
                break;
            case DESKTOP_P2P_MAIN:
                abstractFermatFragment = DesktopP2PApssFragment.newInstance();
                break;
            case DESKTOP_SOCIAL_MAIN:
                abstractFermatFragment = DesktopSocialApssFragment.newInstance();
                break;
            case COMMUNITIES_FRAGMENT:
                abstractFermatFragment = CommunitiesExpandibleFragment.newInstance();
                break;
            // Welcome wizard
            case WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT:
            case WELCOME_WIZARD_SECOND_SCREEN_FRAGMENT:
            case WELCOME_WIZARD_THIRD_SCREEN_FRAGMENT:
            case WELCOME_WIZARD_FOURTH_SCREEN_FRAGMENT:
                abstractFermatFragment = WelcomeWizardFragment.newInstance();
                break;
            case SETTINGS_IMPORT_KEY:
                abstractFermatFragment = ExportImportSeedFragment.newInstance(1);
                break;
            case SETTINGS_EXPORT_KEY:
                abstractFermatFragment = ExportImportSeedFragment.newInstance(0);
                break;
            case MORE_SETTINGS:
                abstractFermatFragment = MoreSettingsFragment.newInstance();
                break;
            default:
                abstractFermatFragment = DesktopFragment.newInstance();
        }


        return abstractFermatFragment;

    }

    @Override
    public DesktopFragmentsEnumType getFermatFragmentEnumType(String key) {
        return DesktopFragmentsEnumType.getValue(key);
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
