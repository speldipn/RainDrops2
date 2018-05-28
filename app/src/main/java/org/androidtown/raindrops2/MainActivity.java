package org.androidtown.raindrops2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

  FrameLayout layout;
  Switch rainDropOnOff;
  Stage surface;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 화면사이즈 정보를 가져온다.
    DisplayMetrics metrics = getResources().getDisplayMetrics();
    int stageWidth = metrics.widthPixels;
    int stageHeight = metrics.heightPixels;

    surface = new Stage(this, stageWidth, stageHeight);
    layout = findViewById(R.id.layout);
    layout.addView(surface);

    rainDropOnOff = findViewById(R.id.switch1);
    rainDropOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
          surface.start();
        } else {
          surface.stop();
        }
      }
    });

  }
}
