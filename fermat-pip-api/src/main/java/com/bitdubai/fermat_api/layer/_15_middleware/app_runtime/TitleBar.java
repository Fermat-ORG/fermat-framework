package com.bitdubai.fermat_api.layer._15_middleware.app_runtime;


/**
 * Created by ciencias/MATI on 2/14/15.
 *
 */
public interface TitleBar {

    public String getLabel();

    public void setLabel(String label);

    public void setRuntimeSearchView(SearchView runtimeSearchView);
    public SearchView getRuntimeSearchView();

}
