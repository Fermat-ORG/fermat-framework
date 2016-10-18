package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

/**
 * ChatHolder ViewHolder
 *
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 06/09/2016.
 *
 * @author  lnacosta
 * @version 1.0
 */
public class ChatHolder extends FermatViewHolder implements RecyclerView.OnCreateContextMenuListener {

    public ImageView imageTick;
    public ImageView image;
    public TextView  contactname;
    public TextView  lastmessage;
    public TextView  dateofmessage;
    public TextView  tvnumber;

    private ReferenceAppFermatSession<ChatManager> appSession;
    private Context context;
    private ChatListAdapter adapter;

    /**
     * Constructor
     *
     * @param item
     */
    public ChatHolder(View item, ReferenceAppFermatSession<ChatManager> appSession, Context context, ChatListAdapter adapter) {
        super(item);

        imageTick     = (ImageView) item.findViewById(R.id.imagetick);
        image         = (ImageView) item.findViewById(R.id.image);
        contactname   = (TextView)  item.findViewById(R.id.tvtitle);
        lastmessage   = (TextView)  item.findViewById(R.id.tvdesc);
        dateofmessage = (TextView)  item.findViewById(R.id.tvdate);
        tvnumber      = (TextView)  item.findViewById(R.id.tvnumber);

        this.appSession = appSession;
        this.context = context;
        this.adapter = adapter;

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.chat_list_context_menu, menu);

        try {
            // Set the info of chat selected in session
            appSession.setData(ChatSessionReferenceApp.CHAT_DATA, appSession.getModuleManager().getChatByChatId(adapter.getItem(getAdapterPosition()).getChatId()));
        } catch (CantGetChatException e) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

    }
}