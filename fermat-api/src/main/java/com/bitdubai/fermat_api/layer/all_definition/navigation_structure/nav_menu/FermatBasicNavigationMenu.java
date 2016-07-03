package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.nav_menu;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.SideMenu;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.06.07..
 */
public class FermatBasicNavigationMenu extends SideMenu implements Serializable {

    private FermatView header;
    private FermatBasicNavigationMenuBody body;
    private FermatView footer;


    public FermatBasicNavigationMenu() {
    }

    public FermatBasicNavigationMenu(FermatView header, FermatBasicNavigationMenuBody body, FermatView footer) {
        this.header = header;
        this.body = body;
        this.footer = footer;
    }

    public FermatView getHeader() {
        return header;
    }

    public void setHeader(FermatView header) {
        this.header = header;
    }

    public FermatBasicNavigationMenuBody getBody() {
        return body;
    }

    public void setBody(FermatBasicNavigationMenuBody body) {
        this.body = body;
    }

    public FermatView getFooter() {
        return footer;
    }

    public void setFooter(FermatView footer) {
        this.footer = footer;
    }
}
