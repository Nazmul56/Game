package com.droidkings.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by nazmul on 4/15/17.
 */


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 856;
    public static final int HIGHT = 480;
    private MainThread thread;
    private Background bg;
    public GamePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder() , this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry  = true ;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.gamebg1));
        bg.setVector(-5);

        //Safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
    public void update()
    {


        bg.update();
    }

    @Override
    public void draw(Canvas canvas) {
        //Scale
        final float scaleFactorX = getWidth() / WIDTH ;
        final float scaleFactorY = getHeight() / HIGHT ;

        if(canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            bg.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }
}
