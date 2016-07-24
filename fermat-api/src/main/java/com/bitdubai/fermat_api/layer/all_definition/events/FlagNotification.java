package com.bitdubai.fermat_api.layer.all_definition.events;


import java.util.Observable;

/**
 * Created by Matias Furszyfer on 2015.09.03..
 */
public class FlagNotification extends Observable {

    private boolean active;

    public FlagNotification() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        setChanged();
        notifyObservers();
        this.active = false;
    }
}
