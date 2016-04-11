package com.bitdubai.android_core.app.common.version_1.dialogs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.04.06..
 */
public class WelcomeDialogsData {

    public static List<WelcomeDialogItem> getWelcomeDialogData(){

        List<WelcomeDialogItem> list = new ArrayList<>();


        WelcomeDialogItem welcomeDialogItemFirst = new WelcomeDialogItem(
                "Welcome To Fermat! 1",
                "Lorem ipsum dolor sit ametan massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mu",
                "mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante",
                "metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorpe",
                1

        );

        WelcomeDialogItem welcomeDialogItemSecond = new WelcomeDialogItem(
                "Welcome To Fermat! 2",
                "Lorem ipsum dolor sit ametan massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mu",
                "mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante",
                "metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorpe",
                2

        );

        WelcomeDialogItem welcomeDialogItemThird = new WelcomeDialogItem(
                "Welcome To Fermat! 3",
                "Lorem ipsum dolor sit ametan massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mu",
                "mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante",
                "metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorpe",
                3

        );

        WelcomeDialogItem welcomeDialogItemFourth = new WelcomeDialogItem(
                "Welcome To Fermat! 4",
                "Lorem ipsum dolor sit ametan massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mu",
                "mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante",
                "metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorpe",
                4
        );

        list.add(welcomeDialogItemFirst);
        list.add(welcomeDialogItemSecond);
        list.add(welcomeDialogItemThird);
        list.add(welcomeDialogItemFourth);

        return list;

    }

}
