package com.mati.toolbar_widgets.search_view;

/**
 * Created by mati on 2016.02.16..
 */
public interface SearchViewCallback {

    void onCloseView();

    void onTextChange(final CharSequence s, int start, int before, int count);

    void onErrorOccur(Exception e);

}
