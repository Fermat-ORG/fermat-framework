package com.bitdubai.android_core.app.common.version_1.communication.server_system_broker.structure;

import android.os.Parcel;
import android.os.Parcelable;
import com.bitdubai.fermat_api.module_object_creator.FermatModuleObject;
import com.bitdubai.fermat_api.module_object_creator.FermatModuleObjectInterface;

import java.io.Serializable;

/**
 * Created by mati on 2016.04.18..
 */
public class FermatModuleObjectWrapper implements Parcelable {

    private FermatModuleObjectInterface object;

    public FermatModuleObjectWrapper(FermatModuleObject object) {
        this.object = object;
    }

    protected FermatModuleObjectWrapper(Parcel in) {
        Serializable moduleObjectSerializable = in.readSerializable();
        this.object = new FermatModuleObject(moduleObjectSerializable);
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


    public Object getObject() {
        return object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(object.writeToSerializable());
    }
}
