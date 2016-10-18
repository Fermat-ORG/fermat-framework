package com.mati.fermat_osa_addon_android_loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_api.FermatContext;
import com.bitdubai.fermat_api.layer.all_definition.util.MfClassUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * Created by MAtias Furszyfer on 2016.06.22..
 */
public class ClassLoaderManager<O extends FermatApplicationSession & FermatContext> {

    private static final String TAG = "ClassLoaderManager";
    O context;

    Map<String,FermatClassLoader> externalLoaders;

    public ClassLoaderManager(O context) {
        this.context = context;
        externalLoaders = new HashMap<>();
    }


    public Object load(String pluginName, Object[] args){
        ClassLoader classLoader = loadAllPlugin(null);

        FermatClassLoader classLoaderManger = new FermatClassLoader(context.getApplicationContext().getClassLoader().getParent(),classLoader,customClassLoader());
        if (!externalLoaders.containsKey("bch")){
            externalLoaders.put("bch",classLoaderManger);

        }
        try {
            Class klass1 = classLoaderManger.loadClass(pluginName);
            Constructor<?> constructor = null;
            if(args!=null){
                if (args.length>0){
                    Class<?>[] paramTypes = MfClassUtils.getTypes(args,classLoaderManger);
                    constructor = klass1.getDeclaredConstructor(paramTypes);
                }else{
                    constructor = klass1.getDeclaredConstructor();
                }
            }else{
                constructor = klass1.getDeclaredConstructor();
            }
            Object myClass = constructor.newInstance();
//            for (Method method : myClass.getClass().getMethods()) {
//                Log.i("App", method.getName());
//            }
            return myClass;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class<?> load(String className,ClassLoader baseClassLoader){
        ClassLoader classLoader = loadAllPlugin(baseClassLoader);

        FermatClassLoader classLoaderManger = new FermatClassLoader(
                (baseClassLoader!=null)?baseClassLoader:context.getApplicationContext().getClassLoader().getParent(),classLoader,customClassLoader());

        try {
            return classLoaderManger.loadClass(className);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ClassLoader getExternalLoader(String name){
        return externalLoaders.get("bch");
    }


    public DexClassLoader loadAllPlugin(ClassLoader baseClassLoader) {
        try {
            Log.d(TAG, "loadAllPlugin Loading plugins");
            AssetManager asset = context.getApplicationContext().getAssets();
            for (String title : asset.list("plugins")) {
                String path = "plugins/" + title;
                File dexInternalStoragePath = context.getApplicationContext().getDir("dex", Context.MODE_PRIVATE);
                dexInternalStoragePath.mkdirs();
                File f = new File(dexInternalStoragePath, title);
                InputStream fis = context.getApplicationContext().getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[0xFF];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fis.close();
                fos.close();

                File optimizedDexPath = context.getApplicationContext().getDir("outdex", Context.MODE_PRIVATE);
                optimizedDexPath.mkdirs();
                DexClassLoader dcl = new DexClassLoader(f.getAbsolutePath(),
                        optimizedDexPath.getAbsolutePath(), null,
                        (baseClassLoader!=null)?baseClassLoader:context.getApplicationContext().getClassLoader()); //AbstractPluginDeveloper.class.getClassLoader());//context.getApplicationContext().getClassLoader().getParent());
                Class clazz = dcl.loadClass("com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.DeveloperBitDubai");
                if(clazz!=null)Log.i(TAG,"loadAllPlugins: "+clazz.getName());else Log.e(TAG,"Clazz null DeveloperBitDubai");
                return dcl;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public FermatClassLoader customClassLoader() {
        try {
            Log.d(TAG, " customClassLoader Loading plugins");
            AssetManager asset = context.getApplicationContext().getAssets();
            for (String title : asset.list("plugins")) {
                String path = "plugins/" + title;
                File dexInternalStoragePath = context.getApplicationContext().getDir("dex", Context.MODE_PRIVATE);
                if (dexInternalStoragePath.exists()) {
                    dexInternalStoragePath.mkdirs();
                }
                File f = new File(dexInternalStoragePath, title);
                InputStream fis = context.getApplicationContext().getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(f);
                byte[] buffer = new byte[0xFF];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fis.close();
                fos.close();

                File optimizedDexPath = context.getApplicationContext().getDir("outdex", Context.MODE_PRIVATE);
                optimizedDexPath.mkdirs();
                DexClassLoader dcl = new DexClassLoader(f.getAbsolutePath(),
                        optimizedDexPath.getAbsolutePath(), null,
                        context.getApplicationContext().getClassLoader().getParent());
//                Class clazz = dcl.loadClass("com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.DeveloperBitDubai");
//                if(clazz!=null)Log.e(TAG,"customLoader: "+clazz.getName());else Log.e(TAG,"Clazz null DeveloperBitDubai");
                FermatClassLoader classLoaderManger = new FermatClassLoader(dcl,context.getBaseClassLoader(),context.getApplicationContext().getClassLoader().getParent());

//                dcl.addClassLoader(mBaseClassLoader);

//                String resPath = context.getApplicationContext().getDir("dex", Context.MODE_PRIVATE).getAbsolutePath() + "/" + title;
//                PLUGIN_LOADER.put(title, dcl);


                return classLoaderManger;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object load(String pluginName, ClassLoader baseClassLoader, Object[] args) {
        FermatClassLoader classLoaderManger = null;
        if (!externalLoaders.containsKey("bch")){
            ClassLoader classLoader = loadAllPlugin(baseClassLoader);
            classLoaderManger = new FermatClassLoader(context.getApplicationContext().getClassLoader().getParent(),classLoader,customClassLoader());
            externalLoaders.put("bch",classLoaderManger);
        }else{
            classLoaderManger = externalLoaders.get("bch");
        }


        try {
            Class klass1 = classLoaderManger.loadClass(pluginName);
            Constructor<?> constructor = null;
            if(args!=null){
                if (args.length>0){
                    Class<?>[] paramTypes = MfClassUtils.getTypes(args,classLoaderManger);
                    constructor = klass1.getDeclaredConstructor(paramTypes);
                }else{
                    constructor = klass1.getDeclaredConstructor();
                }
            }else{
                constructor = klass1.getDeclaredConstructor();
            }
            Object myClass = constructor.newInstance();
//            for (Method method : myClass.getClass().getMethods()) {
//                Log.i("App", method.getName());
//            }
            return myClass;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
