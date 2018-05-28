package org.androidtown.raindrops2;

import android.graphics.Paint;

import java.util.Random;

public class RainDrop extends Thread {

  private final float RADIUS_MIN = 10f;
  private final float RADIUS_MAX = 30f;
  private final int SPEED_MIN = 20;
  private final int SPEED_MAX = 30;

  private float radius;
  private int speed;
  private int x;
  private int y;
  private int cx;
  private int cy;
  private boolean isStop;

  public Paint paint = new Paint();
  Random random = null;

  public RainDrop(int cx, int cy) {
    random = new Random(System.currentTimeMillis());
    this.cx = cx;
    this.cy = cy;
    setX(cx);
    setY(cy);
    setRadius();
    setSpeed();
    this.isStop = true;
  }

  @Override
  public void run() {
    while (true) {
      if (!isStop) {
        y += speed;
        sleep(20);
        if (this.y > cy) {
          reInit();
        }
      } else {
        sleep(50);
      }
    }
  }

  private void sleep(int msec) {
    try {
      Thread.sleep(msec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public float getRadius() {
    return radius;
  }

  private void setRadius() {
    this.radius = RADIUS_MIN + (random.nextFloat() * RADIUS_MAX); // 0.x ~ 9.x
  }

  private void setSpeed() {
    this.speed = random.nextInt(SPEED_MAX) + SPEED_MIN;
  }


  public int getY() {
    return y;
  }

  private void setY(int cy) {
    this.y = 0;
  }

  public int getX() {
    return x;
  }

  private void setX(int cx) {
    x = random.nextInt(cx);
    if (x < (int) radius) {
      x += (int) radius;
    } else if ((x + (int) radius) >= cx) {
      x -= (int) radius;
    }
    // TODO radius처리가 x설정보다 우선시 되어야 제대로 동작함.
  }

  private void reInit() {
    setRadius();
    setSpeed();
    setX(cx);
    setY(cy);
  }

  public void doRun(boolean isRun) {
    if (isRun) {
      isStop = false;
    } else {
      isStop = true;
    }
  }
}
