package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.wizard_pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Created by Lozadaa on 20/01/16.
*/
 public class WizardTwoStepBroadcastFragment extends AbstractFermatFragment
 {
 private static final String TAG = "WizardTwoStepBroadcastFragment";


 // Fermat Managers
 private ChatManager walletManager;
 private ErrorManager errorManager;



     public static WizardTwoStepBroadcastFragment newInstance() {
        return new WizardTwoStepBroadcastFragment();
     }

 @Override
     public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

     ChatModuleManager moduleManager = ((ChatSession) appSession).getModuleManager();
     try {
         walletManager = moduleManager.getChatManager();
     } catch (CHTException e) {
         errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
     }
     errorManager = appSession.getErrorManager();


 }

     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cht_wizard_broadcast_one_step, container, false);
         ShowDialogWelcome();

     return layout;
     }

     public void ShowDialogWelcome(){
         PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                 .setBody(R.string.cht_chat_body_broadcast_step_one)
                 .setSubTitle(R.string.cht_chat_subtitle_broadcast_step_one)
                 .setTextFooter(R.string.cht_chat_footer_broadcast_step_one)
                 .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                 .setBannerRes(R.drawable.cht_banner)
                 .setIconRes(R.drawable.chat_subapp)
                 .build();
         presentationDialog.show();
     }

     @Override
     public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
         menu.add(0, ChtConstants.CHT_BROADCAST_NEXT_STEP, 0, "help")
                 .setTitle("Finish")
                 .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         int id = item.getItemId();
         if(id == ChtConstants.CHT_BROADCAST_NEXT_STEP){

         }
         return super.onOptionsItemSelected(item);
     }

     private void saveSettingAndGoNextStep() {
         changeActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_TWO_DETAIL);
        /*try {
           walletSetting = walletManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(automaticRestock);
            walletManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());
            appSession.setData(CryptoBrokerWalletSession.CONFIGURED_DATA, true);
            // TODO Solo para testing, eliminar despues
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
           } catch (FermatException ex) {
            Toast.makeText(WizardFirstStepBroadcastFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
             errorManager.reportUnexpectedWalletException(
                     Wallets.CBP_CRYPTO_BROKER_WALLET,
                     UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                     ex);
            }
        }*/


     }


 }