package com.bitdubai.sub_app.intra_user_community.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.holders.AppNotificationsHolder;
import com.ibm.icu.util.Calendar;

import java.util.Date;
import java.util.List;

/**
 * @author Jose Manuel De Sousa.
 * updated Andres Abreu aabreu1 03/08/2016
 */
public class AppNotificationAdapter extends FermatAdapter<IntraUserInformation, AppNotificationsHolder> {

    private Context context;
    public AppNotificationAdapter(Context context, List<IntraUserInformation> lst) {
        super(context, lst);

        this.context = context;
    }

    @Override
    protected AppNotificationsHolder createHolder(View itemView, int type) {
        return new AppNotificationsHolder(itemView);
    }

    @Override
    protected int getCardViewResource() {
        return R.layout.row_connection_notification;
    }

    @Override
    protected void bindHolder(AppNotificationsHolder holder, IntraUserInformation data, int position) {

        if (data.getCity()!=null||data.getCountry()!=null)
            holder.notification_location.setText(data.getCountry()+", "+data.getCity());
        else holder.notification_location.setText("No Location");

        if (data.getPublicKey() != null) {
            holder.userName.setText(data.getName());
            holder.receptionTime.setText(convertToTimeAgo(data.getContactRegistrationDate()));
            // holder.userLocation.setText(data.getCountry()+" - "+data.getCity());
            if (data.getProfileImage() != null && data.getProfileImage().length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data.getProfileImage(), 0, data.getProfileImage().length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
                holder.userAvatar.setImageDrawable(ImagesUtils.getRoundedBitmap(context.getResources(), bitmap));
            }
        }
    }

    private  String convertToTimeAgo(long time){
        Date date = new Date(time);
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        Date Actualdate = cal.getTime();
        int diff;
        String ago = "";

        if (Actualdate.getYear() == date.getYear()){
            if (Actualdate.getMonth() == date.getMonth()){
                if (Actualdate.getDay() == date.getDay()){
                    if (Actualdate.getHours() == date.getHours()){
                        if (Actualdate.getMinutes() == date.getMinutes()){
                            ago = context.getResources().getString(R.string.notification_time_1); //less than 1 minute
                        }else{
                            diff = Actualdate.getMinutes() - date.getMinutes();
                            if (diff >1)
                                ago = diff+ context.getResources().getString(R.string.notification_time_2); //2 or more minutes
                            else
                                ago = diff+ context.getResources().getString(R.string.notification_time_2);  //1 minute
                        }
                    }else{
                        if(Actualdate.getHours() > date.getHours()) {
                            diff = Actualdate.getHours() - date.getHours();
                            if (diff > 1)
                                ago = diff + context.getResources().getString(R.string.notification_time_3); //2 or more hours
                            else
                                ago = diff + context.getResources().getString(R.string.notification_time_3);  //1 hour
                        }
                    }
                }else{
                    if(Actualdate.getDay() > date.getDay()) {
                        diff = Actualdate.getDay() - date.getDay();
                        if (diff > 1)
                            ago = diff + context.getResources().getString(R.string.notification_time_4); //2 or more days
                        else
                            ago = diff + context.getResources().getString(R.string.notification_time_4);  //1 day
                    }
                }
            }else{
                if(Actualdate.getMonth() > date.getMonth()) {
                    diff = Actualdate.getMonth() - date.getMonth();
                    if (diff > 1)
                        ago = diff + context.getResources().getString(R.string.notification_time_3); //2 or more Month
                    else
                        ago = diff + context.getResources().getString(R.string.notification_time_3);  //1 Month
                }
            }
        }
        else{
            if(Actualdate.getYear() > date.getYear()){
                diff = Actualdate.getYear() - date.getYear();
                if (diff > 1)
                    ago = diff + context.getResources().getString(R.string.notification_time_5); //2 or more years
                else
                    ago = diff + context.getResources().getString(R.string.notification_time_5);  //1 year
            }
        }
        return  ago;    //sdf.format(date);
    }

    public int getSize() {
        if (dataSet != null)
            return dataSet.size();
        return 0;
    }
}
