package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.mati.fermat_preference_settings.settings.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsSwithItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.09..
 */
public class ReferenceWalletSettings extends FermatPreferenceFragment<ReferenceWalletSession,WalletResourcesProviderManager> {


    public static ReferenceWalletSettings newInstance() {
        return new ReferenceWalletSettings();
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        List<PreferenceSettingsItem> list = new ArrayList<>();
        list.add(new PreferenceSettingsSwithItem() {
            @Override
            public boolean getSwitchChecked() {
                return true;
            }

            @Override
            public String getText() {
                return "opcion_1";
            }
        });
        list.add(new PreferenceSettingsOpenDialogText() {
            @Override
            public String getText() {
                return "open dialog";
            }

            @Override
            public List<String> getOptionList() {
                List<String> strings = new ArrayList<String>();
                strings.add("opcion1");
                strings.add("opcion2");
                return strings;
            }
        });
        return list;
    }


}
