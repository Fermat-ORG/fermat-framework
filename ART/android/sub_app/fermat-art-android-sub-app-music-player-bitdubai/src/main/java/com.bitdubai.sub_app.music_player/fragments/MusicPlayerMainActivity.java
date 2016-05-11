package com.bitdubai.sub_app.music_player.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.music_player.MusicPlayerPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.sub_app.music_player.session.MusicPlayerSession;

/**
 * Created by Miguel Payarez on 08/04/16.
 */
public class MusicPlayerMainActivity extends AbstractFermatFragment {

    //FermatManager
    private MusicPlayerSession musicPlayerSession;
    private MusicPlayerModuleManager musicPlayermoduleManager;
    private MusicPlayerPreferenceSettings musicPlayerSettings;
    private ErrorManager errorManager;


    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            musicPlayerSession = ((MusicPlayerSession) appSession);
            musicPlayermoduleManager = musicPlayerSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START MUSIC PLAYER");

            try {
                musicPlayerSettings = musicPlayermoduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                musicPlayerSettings = null;
            }

            if (musicPlayerSettings == null) {
                musicPlayerSettings = new MusicPlayerPreferenceSettings();
                musicPlayerSettings.setIsPresentationHelpEnabled(true);
                try {
                    musicPlayermoduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), musicPlayerSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.ART_MUSIC_PLAYER, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }


    }

/*    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (toolbar.getMenu() != null) toolbar.getMenu();
    }*/

    public static MusicPlayerMainActivity newInstance() {
        return new MusicPlayerMainActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //     configureToolbar();
        //     getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);

        /*view= inflater.inflate(R.layout.tky_fan_wallet_activity,container,false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    */

        return view;
    }

}





