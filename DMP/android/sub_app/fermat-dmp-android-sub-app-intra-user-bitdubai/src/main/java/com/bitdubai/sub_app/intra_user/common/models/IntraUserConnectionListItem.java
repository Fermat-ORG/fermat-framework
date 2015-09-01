package com.bitdubai.sub_app.intra_user.common.models;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.intra_user.bitdubai.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Matias Furszyfer on 2015.08.31..
 */
public class IntraUserConnectionListItem implements Item{

    private String name;
    private String profilePhrase;
    private byte[] profileImage;

    //esto puede ser un enum con los distintos estados de la conexion
    private String connectionStatus;


    public IntraUserConnectionListItem(String name, String profilePhrase, byte[] profileImage, String connectionStatus) {
        this.name = name;
        this.profilePhrase = profilePhrase;
        this.profileImage = profileImage;
        this.connectionStatus = connectionStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhrase() {
        return profilePhrase;
    }

    public void setProfilePhrase(String profilePhrase) {
        this.profilePhrase = profilePhrase;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isConnection() {
        return true;
    }


    public static ArrayList<IntraUserConnectionListItem> getTestData(Resources resources) {
        ArrayList<IntraUserConnectionListItem> lst = new ArrayList<IntraUserConnectionListItem>();
        Drawable d=resources.getDrawable(R.drawable.celine_profile_picture); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();
        IntraUserConnectionListItem intraUserConnectionListItem = new IntraUserConnectionListItem("Matias Furszyfer","keep moving forward",bitmapdata,"connect");
        lst.add(intraUserConnectionListItem);
        return lst;


    }
}
