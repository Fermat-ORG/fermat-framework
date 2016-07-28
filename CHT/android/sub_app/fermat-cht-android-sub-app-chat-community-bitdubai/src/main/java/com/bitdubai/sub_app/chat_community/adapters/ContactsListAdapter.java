package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.common.popups.ContactDialog;
import com.bitdubai.sub_app.chat_community.filters.ContactsFilter;
import com.bitdubai.sub_app.chat_community.holders.ContactsListHolder;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * ContactsListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public class ContactsListAdapter
        extends FermatAdapter<ChatActorCommunityInformation, ContactsListHolder>
        implements Filterable {

    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private ChatActorCommunitySubAppModuleManager moduleManager;
    List<ChatActorCommunityInformation> filteredData;
    private String filterString;
    private String cityAddress;
    private String stateAddress;
    private String countryAddress;

    public ContactsListAdapter(Context context, List<ChatActorCommunityInformation> dataSet,
                               ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession,
                               ChatActorCommunitySubAppModuleManager moduleManager) {
        super(context, dataSet);
        this.appSession = appSession;
        this.moduleManager = moduleManager;
    }

    @Override
    protected ContactsListHolder createHolder(View itemView, int type) {
        return new ContactsListHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.cht_comm_connection_list_item;
    }

    @Override
    protected void bindHolder(ContactsListHolder holder, ChatActorCommunityInformation data, int position) {
        if (data.getPublicKey() != null) {
            holder.friendName.setText(data.getAlias());
            if (data.getImage() != null && data.getImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                holder.friendAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            } else
                holder.friendAvatar.setImageResource(R.drawable.cht_comm_icon_user);

            if (data.getLocation() != null) {
                if (data.getState().equals("null") || data.getState().equals("") || data.getState().equals("state"))
                    stateAddress = "";
                else stateAddress = new StringBuilder().append(data.getState()).append(" ").toString();
                if (data.getCity().equals("null") || data.getState().equals("") || data.getCity().equals("city"))
                    cityAddress = "";
                else cityAddress = new StringBuilder().append(data.getCity()).append(" ").toString();
                if (data.getCountry().equals("null") || data.getState().equals("") || data.getCountry().equals("country"))
                    countryAddress = "";
                else countryAddress = data.getCountry();
                if (stateAddress.equalsIgnoreCase("") && cityAddress.equalsIgnoreCase("") && countryAddress.equalsIgnoreCase("")) {
                    holder.location.setText("Not Found");
                } else
                    holder.location.setText(new StringBuilder().append(cityAddress).append(stateAddress).append(countryAddress).toString());
            } else
                holder.location.setText("Not Found");

            if (data.getProfileStatus() == ProfileStatus.ONLINE)
                holder.location.setTextColor(Color.parseColor("#47BF73"));//Verde no brillante
            else if (data.getProfileStatus() == ProfileStatus.OFFLINE)
                holder.location.setTextColor(Color.RED);
            else if (data.getProfileStatus() == ProfileStatus.UNKNOWN)
                holder.location.setTextColor(Color.BLACK);

            final ChatActorCommunityInformation dat = data;
            holder.friendAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContactDialog contact = new ContactDialog(context, appSession, null);
                    contact.setProfileName(dat.getAlias());
                    if (dat.getLocation() != null) {
                        if (dat.getState().equals("null") || dat.getState().equals(""))
                            stateAddress = "";
                        else stateAddress = new StringBuilder().append(dat.getState()).append(" ").toString();
                        if (dat.getCity().equals("null") || dat.getCity().equals(""))
                            cityAddress = "";
                        else cityAddress = new StringBuilder().append(dat.getCity()).append(" ").toString();
                        if (dat.getCountry().equals("null") || dat.getCountry().equals(""))
                            countryAddress = "";
                        else countryAddress = dat.getCountry();
                        if (stateAddress.equalsIgnoreCase("") && cityAddress.equalsIgnoreCase("") && countryAddress.equalsIgnoreCase("")) {
                            contact.setCountryText("Not Found");
                        } else
                            contact.setCountryText(new StringBuilder().append(cityAddress).append(stateAddress).append(countryAddress).toString());
                    } else
                        contact.setCountryText("Not Found");

                    ByteArrayInputStream bytes = new ByteArrayInputStream(dat.getImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    contact.setProfilePhoto(bmd.getBitmap());
                    contact.show();
                }
            });
        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }

    public void setData(List<ChatActorCommunityInformation> data) {
        this.filteredData = data;
    }

    public Filter getFilter() {
        return new ContactsFilter(dataSet, this);
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    public String getFilterString() {
        return filterString;
    }
}
