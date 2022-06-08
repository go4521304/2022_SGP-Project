package kr.ac.tukorea.sgp22.nerverendingjump.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.game.MainGame;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor accelerometer, magnetic;

    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];
    private float[] initOrientation;

    private double timestamp;
    private double dt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        sensorEventListener = new UserSensorListner();

        accelOutput = null;
        magOutput = null;
        initOrientation = null;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListener);
        if (GameView.view != null)
        {
            GameView.view.pauseGame();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        if (GameView.view != null)
        {
            GameView.view.resumeGame();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(sensorEventListener);
        GameView.view = null;
        MainGame.clear();
    }

    public class UserSensorListner implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                accelOutput = event.values;
            else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                magOutput = event.values;

            if (accelOutput != null && magOutput != null)
            {
                float[] R = new float[9];
                float[] I = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, accelOutput, magOutput);
                if (success)
                {
                    SensorManager.getOrientation(R, orientation);
                    if (initOrientation == null)
                    {
                        initOrientation = new float[orientation.length];
                        System.arraycopy(orientation, 0, initOrientation, 0, orientation.length);
                    }

                    MainGame.getInstance().getDoodle().setDirection((orientation[1] - initOrientation[1]) * 1000);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
    }
}