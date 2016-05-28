package com.fermat.clelia.loadinganimationdialog.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;


/**
 * Created by Clelia López on 4/14/16
 */
public class BlurBuilder {
    private static final float BLUR_RADIUS = 7;

    public static Bitmap blurView(View view) {
        return blurBitmap(view.getContext(), getScreenshot(view, view.getContext()));
    }

    public static Bitmap blurBitmap(Context context, Bitmap bitmap) {
        Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript renderScript = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        Allocation input = Allocation.createFromBitmap(renderScript, inputBitmap);
        Allocation output = Allocation.createFromBitmap(renderScript, outputBitmap);
        theIntrinsic.setInput(input);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.forEach(output);
        output.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static Bitmap fastblur(Bitmap bitmap, int radius) {
        Bitmap output = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);

        if (radius<1)
            return null;

        int w = output.getWidth();
        int h = output.getHeight();
        int wm = w-1;
        int hm = h-1;
        int wh = w*h;
        int div = radius+radius+1;
        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum,gsum,bsum,x,y,i,p,p1,p2,yp,yi,yw;
        int vmin[] = new int[Math.max(w,h)];
        int vmax[] = new int[Math.max(w,h)];
        int pix[] = new  int[w*h];

        output.getPixels(pix, 0, w, 0,0,w, h);

        int dv[]=new int[256*div];
        for (i=0;i<256*div;i++)
            dv[i]=(i/div);

        yw=yi=0;

        for (y=0;y<h;y++) {
            rsum = gsum = bsum = 0;
            for(i=-radius; i<=radius; i++) {
                p=pix[yi+Math.min(wm,Math.max(i,0))];
                rsum += (p & 0xff0000)>>16;
                gsum += (p & 0x00ff00)>>8;
                bsum += p & 0x0000ff;
            }
            for (x=0; x<w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                if(y == 0) {
                    vmin[x]=Math.min(x+radius+1,wm);
                    vmax[x]=Math.max(x-radius,0);
                }
                p1 = pix[yw+vmin[x]];
                p2 = pix[yw+vmax[x]];
                rsum += ((p1 & 0xff0000)-(p2 & 0xff0000))>>16;
                gsum += ((p1 & 0x00ff00)-(p2 & 0x00ff00))>>8;
                bsum += (p1 & 0x0000ff)-(p2 & 0x0000ff);
                yi++;
            }
            yw += w;
        }

        for (x=0; x<w; x++){
            rsum = gsum = bsum = 0;
            yp =- radius*w;
            for(i=-radius; i<=radius; i++){
                yi = Math.max(0,yp)+x;
                rsum += r[yi];
                gsum += g[yi];
                bsum += b[yi];
                yp += w;
            }
            yi = x;
            for (y=0; y<h; y++){
                pix[yi]=0xff000000 | (dv[rsum]<<16) | (dv[gsum]<<8) | dv[bsum];
                if(x == 0) {
                    vmin[y]=Math.min(y+radius+1,hm)*w;
                    vmax[y]=Math.max(y-radius,0)*w;
                }
                p1 =x+ vmin[y];
                p2= x+ vmax[y];

                rsum += r[p1]-r[p2];
                gsum += g[p1]-g[p2];
                bsum +=b [p1]-b[p2];

                yi += w;
            }
        }

        output.setPixels(pix,0, w,0,0,w,h);

        return output;
    }

    public static Bitmap getScreenshot(View view, Context context) {
        int width = view.getWidth();
        int height = view.getHeight();

        Point point = NavigationBarTools.getNavigationBarSize(context);
        if(point != null)
            height = view.getHeight() - point.y;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}