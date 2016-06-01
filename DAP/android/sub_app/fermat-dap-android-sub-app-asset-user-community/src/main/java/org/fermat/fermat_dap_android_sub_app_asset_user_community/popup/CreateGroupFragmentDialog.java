package org.fermat.fermat_dap_android_sub_app_asset_user_community.popup;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;

import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Group;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantUpdateAssetUserGroupException;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;


/**
 * Created by Penny on 01/08/16.
 */

public class CreateGroupFragmentDialog extends Dialog implements
        View.OnClickListener {

    private static AssetUserCommunitySubAppModuleManager manager;
    public Activity activity;
    public Dialog d;
    private Group group;
    Button save_group_btn;
    Button cancel_btn;
    AutoCompleteTextView group_name;

    public CreateGroupFragmentDialog(Activity a, AssetUserCommunitySubAppModuleManager manager, Group group) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        CreateGroupFragmentDialog.manager = manager;
        this.group = group;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
    }

    private void setUpScreenComponents() {

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.create_group_dialog);

            save_group_btn = (Button) findViewById(R.id.save_group_btn);
            cancel_btn = (Button) findViewById(R.id.cancel_btn);
            group_name = (AutoCompleteTextView) findViewById(R.id.group_name);
            if (group != null) {
                group_name.setText(group.getGroupName());
            }

            cancel_btn.setOnClickListener(this);
            save_group_btn.setOnClickListener(this);


            //getWindow().setBackgroundDrawable(new ColorDrawable(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            dismiss();
        } else if (i == R.id.save_group_btn) {
            if (group != null) {
                editGroup();
            } else {
                saveGroup();
            }
        }
    }

    /**
     * create group and save it into database
     */
    private void saveGroup() {
        try {
            String name = group_name.getText().toString();

            if (!name.equals("")) {
                manager.createAssetUserGroup(name);
                Toast.makeText(activity.getApplicationContext(), "Group saved!", Toast.LENGTH_SHORT).show();
                dismiss();


            } else {
                Toast.makeText(activity.getApplicationContext(), "Please enter a valid group name...", Toast.LENGTH_SHORT).show();
            }
        } catch (CantCreateAssetUserGroupException e) {
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error-" + e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(activity.getApplicationContext(), "Oooops! recovering from system error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void editGroup() {

        String name = group_name.getText().toString();
        group.setGroupName(name);

        if (!name.equals("")) {
            try {
                manager.renameGroup(group);
            } catch (CantUpdateAssetUserGroupException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Can't rename the group", Toast.LENGTH_SHORT).show();
            } catch (RecordsNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(activity.getApplicationContext(), "Group not found!", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(activity.getApplicationContext(), "Group renamed.", Toast.LENGTH_SHORT).show();
            dismiss();

        } else {
            Toast.makeText(activity.getApplicationContext(), "Please enter a valid group name...", Toast.LENGTH_SHORT).show();
        }
    }

    public Group getGroup() {
        return group;
    }

}