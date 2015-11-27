package com.bitdubai.fermat_android_api.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;

/**
 * Created by Matias Furszyfer on 2015.10.18..
 */
public abstract class FermatDialog <S extends FermatSession,R extends ResourceProviderManager> extends Dialog {


    private final Activity activity;
    private S fermatSession;

    private R resources;


    private ViewInflater viewInflater;


    /**
     *  Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources parent class of WalletResources and SubAppResources
     */
    public FermatDialog(Activity activity,S fermatSession,R resources) {
        super(activity);
        this.activity = activity;
        this.fermatSession = fermatSession;
        this.resources = resources;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(setWindowFeacture());
            setContentView(setLayoutId());
        }catch (Exception e){
            getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH,e);
            Toast.makeText(getOwnerActivity(), "Oooops! recovering from system error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *
     * Set the layout content view
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * Window feacture
     * @return
     */
    protected abstract int setWindowFeacture();

    /**
     *
     * @return Session
     */
    public S getSession(){
        return fermatSession;
    }

    /**
     *
     *
     * @return resoruces instance
     */

    public R getResources(){
        return resources;
    }

    /**
     *
     * @return error manager from session
     */
    public ErrorManager getErrorManager(){
       return fermatSession.getErrorManager();
    }

    protected void changeApp(Engine emgine,String fermatAppToConnectPublicKey, Object[] objects) {
        getFermatScreenSwapper().connectWithOtherApp(emgine,fermatAppToConnectPublicKey, objects);
    }
    protected FermatScreenSwapper getFermatScreenSwapper(){
        return (FermatScreenSwapper) activity;
    }

    protected Activity getActivity(){
        return activity;
    }
}
