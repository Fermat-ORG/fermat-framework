package popup;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;

import com.bitdubai.fermat_android_api.engine.DesktopHolderClickCallback;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.SpacesItemDecoration;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.desktop.Item;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.manager.R;
import com.bitdubai.sub_app.manager.fragment.session.DesktopSessionReferenceApp;

import java.util.List;

import adapter.DesktopAdapter;

/**
 * Created by mati on 2015.11.27..
 */
public class FolderDialog extends FermatDialog<DesktopSessionReferenceApp, SubAppResourcesProviderManager> {


    private final List<Item> lstItems;
    private final DesktopHolderClickCallback desktopHolderClickCallback;
    private String title;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private DesktopAdapter adapter;


    /**
     * Constructor using Session and Resources
     *
     * @param activity
     * @param fermatSession parent class of walletSession and SubAppSession
     * @param resources     parent class of WalletResources and SubAppResources
     */
    public FolderDialog(Activity activity, DesktopSessionReferenceApp fermatSession, SubAppResourcesProviderManager resources, List<Item> lstItems, DesktopHolderClickCallback desktopHolderClickCallback) {
        super(activity, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
    }

    public FolderDialog(Activity activity, int themeResId, DesktopSessionReferenceApp fermatSession, SubAppResourcesProviderManager resources, String title, List<Item> lstItems, DesktopHolderClickCallback desktopHolderClickCallback) {
        super(activity, themeResId, fermatSession, resources);
        this.lstItems = lstItems;
        this.desktopHolderClickCallback = desktopHolderClickCallback;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.gridView);

        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DesktopAdapter(getActivity(), lstItems, desktopHolderClickCallback, DesktopAdapter.FOLDER);
        recyclerView.setAdapter(adapter);
        int spacingInPixels = 20;
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));


        FermatTextView txt_title = (FermatTextView) findViewById(R.id.txt_title);
        txt_title.setText(title);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.folder_main;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
