package com.bitdubai.sub_app.chat_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.exceptions.CantCreateAddressException;
import com.bitdubai.fermat_pip_api.layer.external_api.geolocation.interfaces.Address;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.holders.ContactsListHolder;

import java.util.List;

/**
 * ContactsListAdapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */

public class ContactsListAdapter
        extends FermatAdapter<ChatActorCommunityInformation, ContactsListHolder> {

    private ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession;
    private ChatActorCommunitySubAppModuleManager moduleManager;

    public ContactsListAdapter(Context context, List<ChatActorCommunityInformation> dataSet,
                               ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> appSession,
                               ChatActorCommunitySubAppModuleManager moduleManager) {
        super(context, dataSet);
        this.appSession=appSession;
        this.moduleManager=moduleManager;
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
            }else
                holder.friendAvatar.setImageResource(R.drawable.cht_comm_icon_user);

            Address address= null;
            if(data.getLocation() != null ){
                try {
                    address = moduleManager.getAddressByCoordinate(data.getLocation().getLatitude(), data.getLocation().getLongitude());
                }catch(CantCreateAddressException e){
                    address = null;
                }
            }
            if (address!=null)
                holder.location.setText(address.getCity() + " " + address.getState() + " " + address.getCountry());//TODO: put here location
            else
                holder.location.setText("Searching...");//TODO: put here location

        }
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
