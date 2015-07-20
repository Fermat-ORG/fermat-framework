package com.bitdubai.fermat_osa_addon.layer.android.location_system.developer.bitdubai.version_1;

import com.bitdubai.fermat_osa_addon.layer.android.location_system.developer.bitdubai.version_1.structure.DeviceLocationManager;
import com.bitdubai.fermat_api.layer.osa_android.LocationSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import android.content.Context;

/**
 * Created by loui on 29/04/15.
 * Modificado Natalia 13/05/2015
 */

/**
 * This addon handles a layer of Device Location representation.
 * Encapsulates all the necessary functions to retrieve the geolocation of the device.
 *
 * * * *
 */
public class DeviceLocationOsAddonRoot implements Addon, LocationSystemOs,Service {


    /**
     * LocationManager Interface member variables.
     */
    private Context context;
    private double lat;
    private double lng;

    public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}



	/**
     * LocationListener Interface member variables.
     */
    private android.location.LocationManager locationManager;
    private android.location.Location deviceLocation;

    public android.location.LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(android.location.LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	public android.location.Location getDeviceLocation() {
		return deviceLocation;
	}

	public void setDeviceLocation(android.location.Location deviceLocation) {
		this.deviceLocation = deviceLocation;
	}

	public LocationManager getLocationSystemManager() {
		return locationSystemManager;
	}

	public void setLocationSystemManager(LocationManager locationSystemManager) {
		this.locationSystemManager = locationSystemManager;
	}

	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public List<EventListener> getListenersAdded() {
		return listenersAdded;
	}

	public void setListenersAdded(List<EventListener> listenersAdded) {
		this.listenersAdded = listenersAdded;
	}



	public static final long              MIN_TIME_BW_UPDATES = 1;
    public static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    public final static long MIN_TIME_INTERVAL = 60 * 1000L;

    private LocationManager locationSystemManager;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;
    private List<EventListener> listenersAdded = new ArrayList<>();


    public int size() {
		return listenersAdded.size();
	}

	public boolean isEmpty() {
		return listenersAdded.isEmpty();
	}

	public boolean contains(Object o) {
		return listenersAdded.contains(o);
	}

	public Iterator<EventListener> iterator() {
		return listenersAdded.iterator();
	}

	public Object[] toArray() {
		return listenersAdded.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return listenersAdded.toArray(a);
	}

	public boolean add(EventListener e) {
		return listenersAdded.add(e);
	}

	public boolean remove(Object o) {
		return listenersAdded.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return listenersAdded.containsAll(c);
	}

	public boolean addAll(Collection<? extends EventListener> c) {
		return listenersAdded.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends EventListener> c) {
		return listenersAdded.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return listenersAdded.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return listenersAdded.retainAll(c);
	}

	public void clear() {
		listenersAdded.clear();
	}

	public boolean equals(Object o) {
		return listenersAdded.equals(o);
	}

	public int hashCode() {
		return listenersAdded.hashCode();
	}

	public EventListener get(int index) {
		return listenersAdded.get(index);
	}

	public EventListener set(int index, EventListener element) {
		return listenersAdded.set(index, element);
	}

	public void add(int index, EventListener element) {
		listenersAdded.add(index, element);
	}

	public EventListener remove(int index) {
		return listenersAdded.remove(index);
	}

	public int indexOf(Object o) {
		return listenersAdded.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return listenersAdded.lastIndexOf(o);
	}

	public ListIterator<EventListener> listIterator() {
		return listenersAdded.listIterator();
	}

	public ListIterator<EventListener> listIterator(int index) {
		return listenersAdded.listIterator(index);
	}

	public List<EventListener> subList(int fromIndex, int toIndex) {
		return listenersAdded.subList(fromIndex, toIndex);
	}

	/**
     * <p>Addon implementation constructor Constructor
     */

   public DeviceLocationOsAddonRoot()
   {
       locationSystemManager = new DeviceLocationManager();
   }

    /**
     *<p>This method sets the android context object for this addon
     *
     * @param context Android context object
     */
    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    /**
     *<p>This method returns an instance of the LocationManager object
     *
     * @return LocationManager object
     */
    public LocationManager getLocationSystem(){
        return this.locationSystemManager;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }



    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

}
