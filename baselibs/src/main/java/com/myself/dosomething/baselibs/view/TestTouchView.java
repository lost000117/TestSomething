package com.myself.dosomething.baselibs.view;
/*
 *create by mac_hou on 2021/8/31 17:12
 *
 *
 *
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.myself.dosomething.baselibs.R;
import com.myself.dosomething.baselibs.baseutil;

import java.util.LinkedList;

import androidx.annotation.Nullable;

public class TestTouchView extends View {
    public TestTouchView(Context context) {
        super(context);init();
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);init();
    }

    public TestTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init();
    }

    LinkedList< LinkedList<PointF>> allpoints;
    LinkedList<PointF> points;
    Paint paint;
    Bitmap colors;
    private void init(){
        allpoints=new LinkedList<>();

        paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(baseutil.dpsize*10);
        paint.setStrokeCap(Paint.Cap.ROUND);
        colors = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.colorful);
        paint.setColor(colors.getPixel(0,0));

    }

    float downx,downy;
    long downtime=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_DOWN){
            points=new LinkedList<>();
            downx=event.getX();
            downy=event.getY();
            downtime=System.currentTimeMillis();
            allpoints.add(points);
            points.add(new PointF(event.getX(),event.getY()));
        }else if (event.getAction()==MotionEvent.ACTION_MOVE){
            PointF end=points.getLast();
            if (end.x!=event.getX()&&event.getY()!=end.y){
                points.add(new PointF(event.getX(),event.getY()));
            }

        }else if (event.getAction()==MotionEvent.ACTION_UP){
            if (Math.abs(downx-event.getX())<10
                    &&Math.abs(downy-event.getY())<10
                    &&System.currentTimeMillis()-downtime>1000){
                clear();
            }else {
                Canvas canvas=new Canvas(showbit);
                dodraw(canvas);
            }

        }
        invalidate();
        return true;
    }

    public void clear(){
        if (points!=null)
        points.clear();
        if (points!=null)
        allpoints.clear();
        showbit=null;
        lastpos=0;
        paint.setColor(colors.getPixel(0,0));
    }


    Bitmap showbit;

    int lastpos=0;
    void dodraw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        if (allpoints.size()==0)return;
        lastpos=0;
        for (LinkedList<PointF> points: allpoints) {
            if (points.size()==0)return;
            PointF lastpointF=points.get(0);
            canvas.drawPoint(lastpointF.x,lastpointF.y,paint);
            int all=colors.getWidth();
            if (points.size()>1)
                for (int i = 1; i < points.size(); i++) {
                    PointF pointF=points.get(i);
                    int c=colors.getPixel((lastpos+i)%all,0);
                    paint.setColor(c);
                    canvas.drawLine(
                            lastpointF.x,lastpointF.y,
                            pointF.x,pointF.y,
                            paint
                    );
                    lastpointF=pointF;
                }
            lastpos+=points.size();
        }
        points=null;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (showbit==null&&canvas.getWidth()>10&&canvas.getHeight()>10){
            showbit=Bitmap.createBitmap(canvas.getWidth(),canvas.getHeight(), Bitmap.Config.ARGB_8888);
        }
        canvas.drawBitmap(showbit,0,0,null);
        if (points==null||points.size()==0)return;
        PointF lastpointF=points.get(0);
        canvas.drawPoint(lastpointF.x,lastpointF.y,paint);
        int all=colors.getWidth();
        if (points.size()>1)
        for (int i = 1; i < points.size(); i++) {
            PointF pointF=points.get(i);
            int c=colors.getPixel((lastpos+i)%all,0);
            paint.setColor(c);
            canvas.drawLine(
                    lastpointF.x,lastpointF.y,
                    pointF.x,pointF.y,
                    paint
            );
            lastpointF=pointF;
        }

    }

    public static int rgb(float red, float green, float blue) {
        return 0xff000000 |
                ((int) (red   * 255.0f + 0.5f) << 16) |
                ((int) (green * 255.0f + 0.5f) <<  8) |
                (int) (blue  * 255.0f + 0.5f);
    }
}
