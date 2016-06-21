package com.bitdubai.sub_app.chat_community.common.popups;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SearchView;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.adapters.GeolocationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 11/06/16.
 */
public class GeolocationDialog extends FermatDialog implements View.OnClickListener {

    //ATTRIBUTES
    private TextView CountryPlace; //Estos van dentro del adapter
    private TextView StatePlace;   //Estos van dentro del adapter
    private SearchView SeachInput;
    private ChatActorCommunityManager mChatActorCommunityManager;

    //THREAD ATTRIBUTES
    private boolean isRefreshing = false;
//    private SwipeRefreshLayout swipeRefresh;
//    private DeviceLocation location = null;
//    private double distance = 0;
//    private String al;
//    private static final int MAX = 6;
//    private int offset = 0;
    private ArrayList<Cities> lstChatUserInformations;
    private GeolocationAdapter adapter;
    private ErrorManager errorManager;
    private LinearLayout emptyView;
//    ImageView noData;
    TextView noDatalabel;
//    private View rootView;

    //SETTERS ATTRIBUTES
    String Country;
    String State;
    String Input;



    public GeolocationDialog (Context activity, FermatSession referenceAppFermatSession, ResourceProviderManager resources){
        super(activity, referenceAppFermatSession, resources);
    }

    public void setCountryPlace(String country){
        Country = country;
    }

    public void setStatePlace (String state){
        State = state;
    }

    public void setSeachInput (String input){
        Input = input;
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //TODO Instaciar el geolocation_recycler_view
        CountryPlace = (TextView) this.findViewById(R.id.country_search);
        StatePlace = (TextView) this.findViewById(R.id.state_search);


    }

    public void onClick(View v) {
        int i = v.getId();

    }

    protected int setLayoutId() {
        return R.layout.cht_comm_geolocation_dialog;
    }

    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    public void onRefresh(){
        if (!isRefreshing) {
            isRefreshing = true;
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData(SeachInput.getText());
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    isRefreshing = false;
//                    if ()
//                        swipeRefresh.setRefreshing(false);
                    if (result != null &&
                            result.length > 0) {
                        progressDialog.dismiss();
                        if (getContext()!= null && adapter != null) {
                            lstChatUserInformations = (ArrayList<Cities>) result[0];
                            adapter.changeDataSet(lstChatUserInformations);
                            if (lstChatUserInformations.isEmpty()) {
                                showEmpty(true, emptyView);
                            } else {
                                showEmpty(false, emptyView);
                            }
                        }
                    } else
                        showEmpty(true, emptyView);
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    progressDialog.dismiss();
                    isRefreshing = false;
                    if (getActivity() != null)
                        errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(ex));
                }
            });
            worker.execute();
        }
    }

    private synchronized List<Cities> getMoreData(String filter) {
        System.out.println("****************** GETMORE DATA SYNCHRONIZED ENTERING");
        List<Cities> dataSet = new ArrayList<>();

        try {
           List<Cities> result = mChatActorCommunityManager.getCities(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("****************** GETMORE DATA SYNCRHONIZED SALIO BIEN: ");
        return dataSet;
    }

    public void showEmpty(boolean show, View emptyView) {

        Animation anim = AnimationUtils.loadAnimation(getContext(),
                show ? android.R.anim.fade_in : android.R.anim.fade_out);
        if (show) {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.VISIBLE);
            noDatalabel.setAnimation(anim);
            noDatalabel.setVisibility(View.VISIBLE);
            if (adapter != null)
                adapter.changeDataSet(null);
        } else {
            emptyView.setAnimation(anim);
            emptyView.setVisibility(View.GONE);
            emptyView.setBackgroundResource(0);
            noDatalabel.setAnimation(anim);
            noDatalabel.setVisibility(View.GONE);
            ColorDrawable bgcolor = new ColorDrawable(Color.parseColor("#F9F9F9"));
            emptyView.setBackground(bgcolor);
        }
    }

}
