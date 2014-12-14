package com.domotic.enhanced.activity;

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

  @ViewById(R.id.editText)
  EditText testEditText;

  @Click(R.id.buttonRaw)
  void raw() {
    String raw = testEditText.getText().toString();
    EnhancedDomotic.execRaw(null, raw);
  }

  @Click(R.id.buttonOn)
  void turnOn() {
    // TODO
  }

  @Click(R.id.buttonOff)
  void turnOff() {
    // TODO
  }

}
