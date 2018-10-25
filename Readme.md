# RainDrop2

### 동작 시현

![](/screenshot/raindrop2.gif)

* RainDrop과 성능을 비교하기 위해 작성 테스트 예제
* View 기반이 아닌 SurfaceView를 사용하여 구현
* View는 onDraw를 시스템에서 호출해주지만, SurfaceView는 프로그래머가 원하는 시점에 화면에 그릴 수 있는 특징이 있다.
* SurfaceView는 더블 버퍼링 기법을 사용하고, SurfaceHolder에서 Surface에 미리 그리고 Surface가 SurfaceView에 그려지게된다.

#### RainDrop 클래스
* RainDrop1 예제와 거의 동일하게 사용되었으므로 여기서는 생략
* RainDrop1 예제 [Go to link](https://github.com/speldipn/raindrop)

#### Stage 클래스

* SurfaceView 클래스를 상속하고 SurfaceHolder.Callback 인터페이스를 구현한다.
* SurfaceView를 상속하면 생성자를 생성해주어야 하고, Callback 인터페이스는 3개의 메소드를 오버라이딩 해야한다.
* surfaceCreated는 **뷰가 만들어질 때 호출**되고, surfaceChanged는 **크기가 바뀔 때**, surfaceDestroyed는 **뷰가 종료될 때** 호출되는 메소드이다.

````java
class Stage extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;

  public Stage(Context context) {
    super(context);
    holder = getHolder();
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    // 스레드를 시작
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    // 스레드를 종료
  }
}

````

#### SurfaceView에서 사용되는 스레드를 생성하는 방법

* 스레드는 SurfaceView를 상속한 클래스의 inner 클래스 형태로 정의를 하고, SurfaceHolder를 사용하여 동기화를 한다.
* SurfaceHolder에서 Canvas를 가져와 미리 만들어놓은 이미지를 뷰에 그린다.

````java
class RenderingThread extends Thread {
        public RenderingThread() {
            Log.d("RenderingThread", "RenderingThread()");
        }

        public void run() {
            Canvas canvas = null;
            while (true) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        // surface to surface view
                        canvas.drawRect(0, 0, screenSizeX, screenSizeY, backPaint);
                    }
                } finally {
                    if (canvas == null) return;
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
````