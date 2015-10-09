package com.example.onzzz.smiledetection;

import android.view.View;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.Color;

import com.google.android.gms.vision.face.Face;



/**
 * Created by onzzz on 9/10/2015.
 */
public class UserView extends View{

    private Bitmap tempBitmap;
    private SparseArray<Face> tempFaces;

    public UserView(Context context, AttributeSet attributes){
        super(context, attributes);
    }

    void setContent(Bitmap bitmap, SparseArray<Face> faces){
        tempBitmap = bitmap;
        tempFaces = faces;
        invalidate();
    }

    private double drawBitmap(Canvas canvas){
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = tempBitmap.getWidth();
        double imageHeight = tempBitmap.getHeight();
        double scale = Math.min(viewWidth/imageWidth, viewHeight/imageHeight);
        Rect destBounds = new Rect(0, 0, (int)(imageWidth*scale), (int)(imageHeight*scale));
        canvas.drawBitmap(tempBitmap, null, destBounds, null);
        return scale;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if ((tempBitmap!=null) && (tempFaces!=null)){
            double scale = drawBitmap(canvas);
            drawRectangle(canvas, scale);
        }
    }

    private void drawRectangle(Canvas canvas, double scale){
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        for (int i=0; i<tempFaces.size(); i++){
            Face face = tempFaces.valueAt(i);
            canvas.drawRect((float)(face.getPosition().x*scale), (float)(face.getPosition().y*scale), (float)((face.getPosition().x+face.getWidth())*scale), (float)((face.getPosition().y+face.getHeight())*scale), paint);
        }
    }







}
