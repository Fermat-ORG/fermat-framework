package com.bitdubai.android_core.app.common.version_1.provisory;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatStructure;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.InstalledApp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mati on 2016.03.11..
 */
//TODO: este no se sabe si va a ser un plugin, pero acá irian las structuras de navegacion y no las installedApps
public class P2PAppsRuntimeManager implements com.bitdubai.fermat_api.layer.dmp_module.P2PAppsRuntimeManager {


    Map<String, InstalledApp> installedApps;

    private FermatStructure lastApp;

    public P2PAppsRuntimeManager() {
        installedApps = new HashMap<>();
        InstalledApp installedApp = new InstalledApp("Tinder", "tinder_public_key", new Version(), com.bitdubai.fermat_wpd.wallet_manager.R.drawable.icon_empresa_tinder, 0, 0, com.bitdubai.fermat_api.AppsStatus.getDefaultStatus(), null);
        installedApps.put("tinder_public_key", installedApp);
        installedApp = new InstalledApp("Airbnb", "Airbnb_public_key", new Version(), com.bitdubai.fermat_wpd.wallet_manager.R.drawable.icon_empresa_aribnb, 0, 0, com.bitdubai.fermat_api.AppsStatus.getDefaultStatus(), null);
        installedApps.put("Airbnb_public_key", installedApp);
        installedApp = new InstalledApp("eBay", "eBay_public_key", new Version(), com.bitdubai.fermat_wpd.wallet_manager.R.drawable.icon_ebay, 0, 0, com.bitdubai.fermat_api.AppsStatus.getDefaultStatus(), null);
        installedApps.put("eBay_public_key", installedApp);
        installedApp = new InstalledApp("Mercado libre", "mercado_libre_public_key", new Version(), com.bitdubai.fermat_wpd.wallet_manager.R.drawable.icon_mercado_libre, 0, 0, com.bitdubai.fermat_api.AppsStatus.getDefaultStatus(), null);
        installedApps.put("mercado_libre_public_key", installedApp);
    }

    @Override
    public void recordNAvigationStructure(FermatStructure fermatStructure) {

    }

    @Override
    public FermatStructure getLastApp() {
        return lastApp;
    }

    @Override
    public FermatStructure getAppByPublicKey(String appPublicKey) {
        return null;
    }

    @Override
    public Set<String> getListOfAppsPublicKey() {
        return installedApps.keySet();
    }
}
