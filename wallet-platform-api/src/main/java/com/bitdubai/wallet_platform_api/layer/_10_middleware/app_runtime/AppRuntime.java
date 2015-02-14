package com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime;

/**
 * Created by ciencias on 2/14/15.
 */
public interface AppRuntime {
    
    public App getApp (Apps app);
    
    public Apps getLastApp ();

    public SubApps getLastSubApp ();

    public Wallets getLastWallet ();
    
    public Activities getLasActivity ();
    
    public Fragments getLastFragment ();
    
}
