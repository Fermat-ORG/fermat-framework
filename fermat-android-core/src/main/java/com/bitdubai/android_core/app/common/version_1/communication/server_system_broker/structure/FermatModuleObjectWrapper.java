package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.04.18..
 */
public class FermatModuleObjectWrapper implements Parcelable,Serializable {

    private String objectRequestId;
    private Serializable object;
    private boolean isLargeData = false;
    private Exception e;

    public FermatModuleObjectWrapper(Serializable object,boolean isLargeData,String objectRequestId) {
        this.object = object;
        this.isLargeData = isLargeData;
        this.objectRequestId = objectRequestId;
    }

    public FermatModuleObjectWrapper(String objectRequestId, Serializable object, boolean isLargeData, Exception e) {
        this.objectRequestId = objectRequestId;
        this.object = object;
        this.isLargeData = isLargeData;
        this.e = e;
    }

    public FermatModuleObjectWrapper(Serializable object) {
        this.object = object;
    }

    protected FermatModuleObjectWrapper(Parcel in) {
        Serializable moduleObjectSerializable = in.readSerializable();
//        this.object = new FermatModuleObject(moduleObjectSerializable);
        this.object = moduleObjectSerializable;
        this.e = (Exception) in.readSerializable();
        this.isLargeData = in.readByte() != 0;
    }

    public static final Creator<FermatModuleObjectWrapper> CREATOR = new Creator<FermatModuleObjectWrapper>() {
        @Override
        public FermatModuleObjectWrapper createFromParcel(Parcel in) {
            return new FermatModuleObjectWrapper(in);
        }

        @Override
        public FermatModuleObjectWrapper[] newArray(int size) {
            return new FermatModuleObjectWrapper[size];
        }
    };

    public boolean isLargeData() {
        return isLargeData;
    }

    public Object getObject() {
        return object;
    }

    public String getObjectRequestId() {
        return objectRequestId;
    }

    public Exception getE() {
        return e;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try{
            dest.writeSerializable(object);
            dest.writeSerializable(e);
            dest.writeByte((byte) (isLargeData ? 1 : 0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        String o = (object!=null)?object.toString():"null";
        return "FermatModuleObjectWrapper{" +
                "object=" +o+
                '}';
    }
}
