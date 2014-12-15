package com.domotic.enhanced.activity;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import android.app.Activity;
import android.widget.EditText;

import com.domotic.enhanced.R;
import com.enhanced.domotic.EnhancedDomotic;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main)
public class MainActivity extends Activity {
  
  @Bean
  MainConfig config;

  @ViewById(R.id.editText)
  EditText testEditText;

  @Click(R.id.buttonRaw)
  void raw() {
    String raw = testEditText.getText().toString();
    EnhancedDomotic.raw(null, raw);
  }

  @Click(R.id.buttonOn)
  void turnOn() {
    // TODO
    EnhancedDomotic.<String>raw(config, "*1*1*21##");
  }

  @Click(R.id.buttonOff)
  void turnOff() {
    // TODO
    test();
  }
  
  @Background
  void test() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 41)
      .requestCommand();
  }

}
