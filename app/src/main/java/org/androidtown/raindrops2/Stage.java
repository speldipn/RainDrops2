package org.androidtown.raindrops2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class Stage extends SurfaceView implements SurfaceHolder.Callback {

  private static final int RAINDROPS_MAX = 200;

  Context mContext;
  SurfaceHolder mHolder;
  RenderingThread mRThread;
  List<RainDrop> rainDrops;
  Paint rainPaint;
  Paint backPaint;
  float screenSizeX;
  float screenSizeY;
  private boolean isStart;

  public Stage(Context context, int cx, int cy) {
    super(context);
    mContext = context;
    isStart = false;
    screenSizeX = cx;
    screenSizeY = cy;
    backPaint = new Paint();
    backPaint.setColor(Color.WHITE);
    rainPaint = new Paint();
    rainPaint.setColor(Color.BLUE);
    rainDrops = new ArrayList<>();
    // 물방울 생성
    for (int i = 0; i < RAINDROPS_MAX; ++i) {
      RainDrop rainDrop = new RainDrop((int)screenSizeX, (int)screenSizeY);
      rainDrop.start();
      rainDrops.add(rainDrop);
    }
    mHolder = getHolder();
    mHolder.addCallback(this);
    mRThread = new Stage.RenderingThread();
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    mRThread.start();
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    try {
      mRThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  class RenderingThread extends Thread {

    public RenderingThread() {
      Log.d("RenderingThread", "RenderingThread()");
    }

    public void run() {
      Log.d("RenderingThread", "run()");
      Canvas canvas = null;

      while (true) {
        canvas = mHolder.lockCanvas();
        try {
          synchronized (mHolder) {
            canvas.drawRect(0, 0, screenSizeX, screenSizeY, backPaint);
            for(int i = 0; i < rainDrops.size(); ++i) {
              RainDrop rainDrop = rainDrops.get(i);
              if(isStart) {
                canvas.drawCircle(rainDrop.getX(), rainDrop.getY(), rainDrop.getRadius(), rainPaint);
              }
            }
          }
        } finally {
          if (canvas == null) return;
          mHolder.unlockCanvasAndPost(canvas);
        }
      }
    }
  }

  public void start() {
    if(!isStart)isStart = true;
    for (int i = 0; i < RAINDROPS_MAX; ++i) {
      RainDrop rainDrop = rainDrops.get(i);
      rainDrop.doRun(true);
    }
  }

  public void stop() {
    for (int i = 0; i < RAINDROPS_MAX; ++i) {
      RainDrop rainDrop = rainDrops.get(i);
      rainDrop.doRun(false);
    }
  }
}