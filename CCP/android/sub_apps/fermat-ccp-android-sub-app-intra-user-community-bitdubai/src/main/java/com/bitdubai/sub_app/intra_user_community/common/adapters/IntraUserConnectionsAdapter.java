package com.bitdubai.sub_app.intra_user_community.common.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.sub_app.intra_user_community.common.UtilsFuncs;
import com.bitdubai.sub_app.intra_user_community.common.models.IntraUserConnectionListItem;
import com.bitdubai.sub_app.intra_user_community.R;

import java.util.ArrayList;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 */
public class IntraUserConnectionsAdapter extends FermatAdapter<IntraUserConnectionListItem, IntraUserConnectionsAdapter.IntraUserItemViewHolder> {



    private boolean addButtonVisible=false;

    protected IntraUserConnectionsAdapter(Context context) {
        super(context);
    }

    public IntraUserConnectionsAdapter(Context context, ArrayList<IntraUserConnectionListItem> dataSet) {
        super(context, dataSet);

    }

    /**
     * Create a new holder instance
     *
     * @param itemView View object
     * @param type     int type
     * @return ViewHolder
     */
    @Override
    protected IntraUserItemViewHolder createHolder(View itemView, int type) {
        //preguntar esto del type
        return new IntraUserItemViewHolder(itemView);
    }

    /**
     * Get custom layout to use it.
     *
     * @return int Layout Resource id: Example: R.layout.row_item
     */
    @Override
    protected int getCardViewResource() {
        return R.layout.intra_user_connection_item_with_buttons;
    }            //holder.imageView_profile_connection.setImageDrawable(new RoundedDrawable(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length), holder.imageView_profile_connection));


    /**
     * Bind ViewHolder
     *
     * @param holder   ViewHolder object
     * @param data     Object data to render
     * @param position position to render
     */
    @Override
    protected void bindHolder(IntraUserItemViewHolder holder, IntraUserConnectionListItem data, int position) {
        holder.txtView_profile_name.setText(data.getName());
        if(data.getProfileImage()!=null){
            holder.imageView_profile_connection.setImageBitmap(UtilsFuncs.getRoundedShape(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));
        }else{
            holder.imageView_profile_connection.setImageBitmap(UtilsFuncs.getRoundedShape(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length)));
            //holder.imageView_profile_connection.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length), holder.imageView_profile_connection));

        }
        holder.txtView_profile_phrase.setText(data.getProfilePhrase());
        holder.txtView_profile_status.setText(data.getConnectionStatus());

        if(addButtonVisible){
            holder.imageView_add_connection.setVisibility(View.VISIBLE);
            holder.imageView_add_connection.setImageResource(R.drawable.ic_action_add_person_grey);
            holder.imageView_add_connection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "add connection", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            holder.imageView_add_connection.setVisibility(View.INVISIBLE);
        }
        holder.imageView_chat.setImageResource(R.drawable.ic_action_chat);
        holder.imageView_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Chat", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class IntraUserItemViewHolder extends FermatViewHolder {
        ImageView imageView_profile_connection;
        FermatTextView txtView_profile_name;
        FermatTextView txtView_profile_phrase;
        FermatTextView txtView_profile_status;
        ImageView imageView_add_connection;
        ImageView imageView_chat;

        protected IntraUserItemViewHolder(View itemView) {
            super(itemView);

            imageView_profile_connection = (ImageView) itemView.findViewById(R.id.imageView_profile_connection);
            txtView_profile_name = (FermatTextView) itemView.findViewById(R.id.txtView_profile_name);
            txtView_profile_phrase = (FermatTextView) itemView.findViewById(R.id.txtView_profile_phrase);
            txtView_profile_status = (FermatTextView) itemView.findViewById(R.id.txtView_profile_status);
            imageView_chat = (ImageView) itemView.findViewById(R.id.imageView_chat);
            imageView_add_connection = (ImageView) itemView.findViewById(R.id.imageView_add_connection);



        }
    }




    public void setAddButtonVisible(boolean addButtonVisible) {
        this.addButtonVisible = addButtonVisible;
    }

}
