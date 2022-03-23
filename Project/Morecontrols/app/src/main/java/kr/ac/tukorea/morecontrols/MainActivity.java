package kr.ac.tukorea.morecontrols;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkbox = findViewById(R.id.checkbox);
    }

    public void onCheckBox(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(TAG, "CheckBox state: " + cb.isChecked());
    }

    public void onBtnDoIt(View view) {
        Log.d(TAG, "onBtnDoIt, check=" + checkbox.isChecked());
    }
}