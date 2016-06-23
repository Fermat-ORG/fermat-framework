package com.bitdubai.sub_app.chat_community.common.popups;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
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
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.Cities;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.ultils.CitiesImpl;
import com.bitdubai.fermat_cht_plugin.layer.sub_app_module.chat_community.developer.bitdubai.version_1.structure.ChatActorCommunityManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantConnectWithExternalAPIException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateBackupFileException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateCountriesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantGetCitiesListException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.City;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.GeolocationManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces.Image;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.adapters.CommunityListAdapter;
import com.bitdubai.sub_app.chat_community.adapters.GeolocationAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roy on 11/06/16.
 */
public class GeolocationDialog extends FermatDialog implements View.OnClickListener {

    //ATTRIBUTES
    private TextView CountryPlace; //Estos van dentro del adapter
    private TextView StatePlace;   //Estos van dentro del adapter
    private EditText searchInput;
    private ChatActorCommunitySubAppModuleManager mChatActorCommunityManager;
    private ListView mListView;
    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private CitiesImpl cityFromList;
    private ImageButton lupaButton;

    //THREAD ATTRIBUTES
    private boolean isRefreshing = false;
    private List<Cities> lstChatUserInformations = new ArrayList<>();
    private GeolocationAdapter adapter;
    private ErrorManager errorManager;
    private LinearLayout emptyView;
    TextView noDatalabel;


    //SETTERS ATTRIBUTES
    String Country;
    String State;
    String Input;



    public GeolocationDialog (Context activity, ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession, ResourceProviderManager resources){
        super(activity, appSession, resources); this.appSession = appSession;
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

        try {

            mChatActorCommunityManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            mChatActorCommunityManager.setAppPublicKey(appSession.getAppPublicKey());

            mListView = (ListView) this.findViewById(R.id.geolocation_view);
            CountryPlace = (TextView) this.findViewById(R.id.country_search);
            StatePlace = (TextView) this.findViewById(R.id.state_search);
            searchInput = (EditText) findViewById(R.id.geolocation_input);

//            lupaButton = (ImageButton) this.findViewById(R.id.lupita_button); TODO Roy: checar cómo hacer el ImageView del layout un botón sin usar ImageButton.

            adapter = new GeolocationAdapter(getContext(), lstChatUserInformations);
            for(Cities cityIterator: mChatActorCommunityManager.getCities("a")){
                adapter.add(cityIterator);
            }
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cityFromList = (CitiesImpl) parent.getItemAtPosition(position);
                }
            });

            onRefresh();
        }catch (CantConnectWithExternalAPIException | CantCreateBackupFileException | CantCreateCountriesListException | CantGetCitiesListException e){
            System.out.println("Exception at Geolocation Dialog");
        }


    }

    public void onClick(View v) {
        int i = v.getId(); //Todo poner la selección del adapter

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
//            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Please wait");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData(searchInput.getText().toString());
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    isRefreshing = false;
                    if (result != null &&
                            result.length > 0) {
//                        progressDialog.dismiss();
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
//                    progressDialog.dismiss();
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
