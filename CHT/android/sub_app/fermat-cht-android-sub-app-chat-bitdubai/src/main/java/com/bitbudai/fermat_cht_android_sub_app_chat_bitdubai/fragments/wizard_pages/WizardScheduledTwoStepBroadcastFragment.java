package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.wizard_pages;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.DialogGetDatePicker;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.DialogGetTimePicker;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lozadaa on 20/01/16.
 */
public class WizardScheduledTwoStepBroadcastFragment extends AbstractFermatFragment
{
    private static final String TAG = "WizardTwoStepBroadcastFragment";


    // Fermat Managers
    private ChatManager walletManager;
    private ErrorManager errorManager;
    String DateSelect,TimeSelect;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    ArrayList<String> datelist = new ArrayList<String>();
    ArrayList<String> timelist = new ArrayList<String>();
    private ChatSessionReferenceApp chatSession;
    private ChatManager chatManager;

    public static WizardTwoStepBroadcastFragment newInstance() {
        return new WizardTwoStepBroadcastFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatSession=((ChatSessionReferenceApp) appSession);
        chatManager= chatSession.getModuleManager();
        ChatManager moduleManager = ((ChatSessionReferenceApp) appSession).getModuleManager();
        //TODO:Revisar esto
//        try {
//            walletManager = moduleManager.getChatManager();
//        } catch (CHTException e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }
        errorManager = appSession.getErrorManager();
        //Obtain chatSettings  or create new chat settings if first time opening chat platform
        chatSettings = null;
        try {
            chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
            //chatSettings = (ChatPreferenceSettings) moduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            //chatSettings = moduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            chatSettings = null;
        }

        if (chatSettings == null) {
            chatSettings = new ChatPreferenceSettings();
            chatSettings.setIsPresentationHelpEnabled(true);
            try {
                chatManager.persistSettings(appSession.getAppPublicKey(), chatSettings);
            } catch (Exception e) {
                if (errorManager != null)
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cht_wizard_broadcast_two_step_scheduled, container, false);
        ShowDialogWelcome();
        final Spinner dateSpinner = (Spinner) layout.findViewById(R.id.fecha);
        dateSpinner.setDropDownWidth(0);
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        String date_now = df.format(Calendar.getInstance().getTime());
        datelist.add(date_now);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity()
                ,android.R.layout.simple_spinner_item,datelist);
        dateSpinner.setAdapter(arrayAdapter);
        dateSpinner.setDropDownHorizontalOffset(0);
        dateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogGetDatePicker getDateDialog = new DialogGetDatePicker(getActivity(), appSession, null);
                getDateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (getDateDialog.isGetDatePick()) {
                            DateSelect = getDateDialog.getDatePick();
                            datelist.clear();
                            datelist.add(getDateDialog.getDatePick());
                        }
                    }
                });
            }
        });

        final Spinner timeSpinner = (Spinner) layout.findViewById(R.id.hora);
        dateSpinner.setDropDownWidth(0);
        DateFormat dff = new SimpleDateFormat("HH:mm:ss");
        String date_noww = df.format(Calendar.getInstance().getTime());
        timelist.add(date_now);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(getActivity()
                ,android.R.layout.simple_spinner_item,timelist);
        dateSpinner.setAdapter(arrayAdapter2);
        dateSpinner.setDropDownHorizontalOffset(0);
        dateSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogGetTimePicker getTimeDialog = new DialogGetTimePicker(getActivity(), appSession, null);
                getTimeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (getTimeDialog.isGetTimeSelect()) {
                            TimeSelect = getTimeDialog.getTimeSelect();
                            timelist.clear();
                            timelist.add(getTimeDialog.getTimeSelect());
                        }
                    }
                });
            }
        });

        return layout;
    }

    public void ShowDialogWelcome(){
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
                    .setBody(R.string.cht_chat_body_broadcast_step_one)
                    .setSubTitle(R.string.cht_chat_subtitle_broadcast_step_one)
                    .setTextFooter(R.string.cht_chat_footer_broadcast_step_one)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cht_banner)
                    .setIconRes(R.drawable.chat_subapp)
                    .build();
            presentationDialog.show();
        }catch (Exception e){

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, ChtConstants.CHT_BROADCAST_NEXT_STEP, 0, "CREATE")
                .setTitle("CREATE")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case ChtConstants.CHT_BROADCAST_NEXT_STEP:
                saveSettingAndGoNextStep();
                break;
            case android.R.id.home:
                changeActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL,appSession.getAppPublicKey());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSettingAndGoNextStep() {
        //TODO: AÑADIR SAVESETTINGS
        changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST,appSession.getAppPublicKey());

    }


}