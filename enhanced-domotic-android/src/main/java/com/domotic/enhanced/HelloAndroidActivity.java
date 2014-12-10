package com.domotic.enhanced;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class HelloAndroidActivity extends Activity {

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(com.domotic.enhanced.R.menu.main, menu);
    return true;
  }

}
