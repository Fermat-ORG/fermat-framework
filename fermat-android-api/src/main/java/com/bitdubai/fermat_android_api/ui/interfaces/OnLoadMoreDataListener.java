package com.bitdubai.fermat_android_api.ui.interfaces;

/**
 * Interface that can be used to fetch more data to setted in a list view.
 * You can use this if yoy want to implement infinite scrolling or pagination in a {@link android.widget.ListView}
 * or a {@link android.support.v7.widget.RecyclerView}
 * <p/>
 * Create by Nelson Ramirez (nelsonalfo@gmail.com) on 22/06/2016
 */
public interface OnLoadMoreDataListener {
    /**
     * Here you can put the login to retrieve more data to fill your {@link android.widget.ListView}
     * or {@link android.support.v7.widget.RecyclerView}
     *
     * @param page            the page to load
     * @param totalItemsCount the total item count
     */
    void onLoadMoreData(int page, int totalItemsCount);
}
