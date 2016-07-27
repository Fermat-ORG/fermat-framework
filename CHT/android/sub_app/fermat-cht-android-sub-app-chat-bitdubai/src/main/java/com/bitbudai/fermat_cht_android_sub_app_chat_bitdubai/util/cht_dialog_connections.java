package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.UUID;


/**
 * Created by Lozadaa on 05/03/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 16/03/16.
 */
public class cht_dialog_connections extends FermatDialog<ReferenceAppFermatSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    private final Activity activity;
    private static final String TAG = "cht_dialog_connections";
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings> settingsManager;
    private ChatSessionReferenceApp chatSession;
    public List<ContactConnection> contacts;
    ArrayList<String> contactname=new ArrayList<String>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();
    private List<ContactConnection> contactConnectionList;
    Boolean act_vista = false;
    ListView list;
    private AdapterCallbackContacts mAdapterCallback;
    FermatTextView txt_title,txt_body;
    TextView text;
    FermatButton btn_yes,btn_no;
    Button btn_add, btn_cancel;
    com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.DialogConnectionListAdapter adapter;
    public cht_dialog_connections(Activity activity, ReferenceAppFermatSession referenceAppFermatSession, SubAppResourcesProviderManager resources,
                                  ChatManager chatManager, AdapterCallbackContacts mAdapterCallback) {
        super(activity, referenceAppFermatSession, null);
        this.activity = activity;
        this.chatManager = chatManager;
        this.mAdapterCallback = mAdapterCallback;

    }

    public static interface AdapterCallbackContacts extends com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no.AdapterCallbackContacts {
        void onMethodCallbackContacts();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            chatSession=((ChatSessionReferenceApp) getSession());
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=getSession().getErrorManager();

        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
        text=(TextView) findViewById(R.id.text);
        list = (ListView) findViewById(R.id.list);

        setUpListeners();

        try {
            //final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("Please wait");
           // progressDialog.setCancelable(false);
           // progressDialog.show();
            text.setText("Please wait...");
            FermatWorker worker = new FermatWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    return getMoreData();
                }
            };
            worker.setContext(getActivity());
            worker.setCallBack(new FermatWorkerCallBack() {
                @SuppressWarnings("unchecked")
                @Override
                public void onPostExecute(Object... result) {
                    if (result != null && result.length > 0) {
                        //progressDialog.dismiss();
                        if (getActivity() != null) {
                            contactConnectionList = (List<ContactConnection>) result[0];
                            for (ContactConnection con : contactConnectionList) {
                                if (!con.getAlias().isEmpty() &&
                                        !con.getContactId().equals("") &&
                                        !con.getProfileImage().equals("")) {
                                    try {
                                        ByteArrayInputStream bytes = new ByteArrayInputStream(con.getProfileImage());
                                        BitmapDrawable bmd = new BitmapDrawable(bytes);
                                        if (bmd.getBitmap().getWidth() != 0) {
                                            contactname.add(con.getAlias());
                                            contactid.add(con.getContactId());
                                            contacticon.add(bmd.getBitmap());
                                        }
                                    } catch (Exception e) {
                                        //errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                        Log.i("CHT add contacts", "se ha ignorado contacto mal creado.");
                                    }
                                }
                            }

                            adapter = new com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.DialogConnectionListAdapter(getActivity(), contactname, contacticon, contactid, errorManager);
                            list.setAdapter(adapter);
//                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                    try {
//                                        Contact contactexist = chatSession.getSelectedContactToUpdate();
//                                        if (contactexist != null) {
//                                            if (contactexist.getRemoteActorPublicKey().equals("CONTACTTOUPDATE_DATA")) {
//                                                UUID contactidnew = contactexist.getContactId();
//                                                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                                                contactexist = null;//chatManager.getContactByContactId(contactid.get(position));
//                                                Chat chat = chatManager.getChatByChatId((UUID) getSession().getData("chatid"));
//                                                chat.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
//                                                chatManager.saveChat(chat);
//                                                Contact contactnew = new ContactImpl();
//
//                                                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                                                contactnew = null;//chatManager.getContactByContactId(contactidnew);
//                                                contactnew.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
//                                                contactnew.setAlias(contactexist.getAlias());
//                                                contactnew.setRemoteName(contactexist.getRemoteName());
//                                                contactnew.setRemoteActorType(contactexist.getRemoteActorType());
//                                                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                                                //chatManager.saveContact(contactnew);
//                                                Contact deleteContact;
//                                                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
////                                                for (int i = 0; i < chatManager.getContacts().size(); i++) {
////                                                    deleteContact = chatManager.getContacts().get(i);
////                                                    if (deleteContact.getRemoteName().equals("Not registered contact")) {
////                                                        if (deleteContact.getContactId().equals(contactidnew)) {
////                                                            chatManager.deleteContact(deleteContact);
////                                                        }
////                                                    }
////                                                }
//                                                //chatManager.deleteContact(contactexist);
//                                                getSession().setData(com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp.CONTACTTOUPDATE_DATA, null);
//                                                getSession().setData("whocallme", "contact");
//                                                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                                                getSession().setData(com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp.CONTACT_DATA, null);//chatManager.getContactByContactId(contactidnew));
//                                                Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
//                                                //changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, getSession().getAppPublicKey());
//                                                dismiss();
//                                            }
//                                        } else {
//                                            final int pos = position;
//                                            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                                            final ContactConnection contactConn = null;//chatManager.getContactConnectionByContactId(contactid.get(pos));
//
//                                            if (contactConn.getRemoteName() != null) {
//                                                final com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no customAlert = new com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no(getActivity(), getSession(), null, contactConn, mAdapterCallback);
//                                                customAlert.setTextBody("Do you want to add " + contactConn.getRemoteName() + " to your Contact List?");
//                                                customAlert.setTextTitle("Add connections");
//                                                customAlert.setType("add-connections");
//                                                customAlert.show();
//                                                customAlert.setOnDismissListener(new OnDismissListener() {
//                                                    @Override
//                                                    public void onDismiss(DialogInterface dialog) {
//                                                        if (customAlert.getStatusAddContact() == true) {
//                                                            act_vista = true;
//                                                        }
//                                                    }
//                                                });
//
//                                            } else {
//                                                dismiss();
//                                                //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//
//                                            }
//                                        }
//                                    } catch (CantSaveChatException e) {
//                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                    //} catch (CantDeleteContactException e) {
//                                    //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                    //} catch (CantSaveContactException e) {
//                                    //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                    //} catch (CantGetContactException e) {
//                                    //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                    } catch (Exception e) {
//                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                    }
//                                }
//                            });
                            final EditText inputSearch = (EditText) findViewById(R.id.SearchFilterAddContacts);
                            inputSearch.addTextChangedListener(new TextWatcher() {

                                @Override
                                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                                    // When user changed the Text
                                    cht_dialog_connections.this.adapter.getFilter().filter(cs);

                                }

                                @Override
                                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                              int arg3) {
                                    // TODO Auto-generated method stub

                                }

                                @Override
                                public void afterTextChanged(Editable arg0) {
                                    // TODO Auto-generated method stub
                                }
                            });

                            if (contactConnectionList.isEmpty()) {
                                showEmpty(true, text);
                            } else {
                                showEmpty(false, text);
                            }
                        }
                    } else {
                        showEmpty(true, text);
                    }
                }

                @Override
                public void onErrorOccurred(Exception ex) {
                    //progressDialog.dismiss();
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            });
            worker.execute();
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public boolean getAct(){
        return act_vista;
        }

    protected int setLayoutId() {
            return R.layout.cht_dialog_connections;
    }

    private void setUpListeners() {
      //  btn_add.setOnClickListener(this);
        }

    public void onClick(View v) {
        int id = v.getId();
        /*if (id == R.id.btn_add) {
            dismiss();
        }*/
    }
    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    private synchronized List<ContactConnection> getMoreData() {
        List<ContactConnection> dataSet = new ArrayList<>();

        try {
            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
            List<ContactConnection> result = null;//chatManager.discoverActorsRegistered();//moduleManager.listWorldCryptoBrokers(moduleManager.getSelectedActorIdentity(), MAX, offset);
            dataSet.addAll(result);
            //offset = dataSet.size();
        }catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        return dataSet;
    }

    public void showEmpty(boolean show, TextView text) {
        if (show &&
                (text.getVisibility() == View.GONE || text.getVisibility() == View.INVISIBLE)) {
            text.setVisibility(View.VISIBLE);
            text.setText("No Connections");
            if (adapter != null)
                adapter.refreshEvents(null, null, null);
        } else if (!show && text.getVisibility() == View.VISIBLE) {
            text.setVisibility(View.GONE);
        }
    }
//
//    public void showEmpty(boolean show, View emptyView) {
//        Animation anim = AnimationUtils.loadAnimation(getActivity(),
//                show ? android.R.anim.fade_in : android.R.anim.fade_out);
//        if (show &&
//                (emptyView.getShowAsAction() == View.GONE || emptyView.getShowAsAction() == View.INVISIBLE)) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.VISIBLE);
//            if (adapter != null)
//                adapter.refreshEvents(null, null, null);
//        } else if (!show && emptyView.getShowAsAction() == View.VISIBLE) {
//            emptyView.setAnimation(anim);
//            emptyView.setVisibility(View.GONE);
//        }
//    }

}
