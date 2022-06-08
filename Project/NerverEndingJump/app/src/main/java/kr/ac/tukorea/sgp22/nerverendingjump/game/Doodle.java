package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;


import kr.ac.tukorea.sgp22.nerverendingjump.app.MainActivity;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Doodle extends Sprite
{
    private static final String TAG = Doodle.class.getSimpleName();

    private static final MainGame game = MainGame.getInstance();

    private float dx, dy = 0;
    private final float jumpSpeed = 2500;
    private final float gravity = 80;
    private final float sitTime = 0.2f;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;

    private float[] initOrientation;

    private enum State
    {
        left(R.mipmap.lik_left), left_sit(R.mipmap.lik_left_sit),
        right(R.mipmap.lik_right), right_sit(R.mipmap.lik_right_sit);

        private final int image;
        State(int value)
        {
            image = value;
        }

        public int getImageID()
        {
            return image;
        }
    }

    private State state;

    private boolean falling;
    private float sitTimer;

    public Doodle(float x, float y)
    {
        super(x, y, Metrics.size(R.dimen.doodle_width), Metrics.size(R.dimen.doodle_width), R.mipmap.lik_left);

        sensorManager = (SensorManager) GameView.view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorEventListener = new UserSensorListner();

        state = State.left;
        sitTimer = 0.0f;
        falling = true;

        registerListener();
    }

    public class UserSensorListner implements SensorEventListener
    {
        @Override
        public void onSensorChanged(SensorEvent event)
        {
            if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR)
            {
                if (event.values[0] != 0.0 && event.values[1] != 0.0 && event.values[2] != 0.0)
                {
                    if (initOrientation == null)
                    {
                        initOrientation = new float[event.values.length];
                        System.arraycopy(event.values, 0, initOrientation, 0, event.values.length);
                    }

                    setDirection(event.values[1] - initOrientation[1]);

                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {

        }
    }

    public void registerListener()
    {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public RectF getRect()
    {
        int size = (int)Metrics.size(R.dimen.doodle_height) / 2;
        RectF tmp = new RectF();
        tmp.set(dstRect.left, dstRect.bottom - size, dstRect.right, dstRect.bottom);
        return tmp;
    }

    public void setDirection(float tx, float ty)
    {
        dx = Metrics.size(R.dimen.doodle_x_speed);
        if (tx < Metrics.width / 2)
        {
            dx = -dx;
        }

        this.state = dx < 0 ? State.left : State.right;
    }

    public void setDirection(float Direction)
    {
        dx = Direction * Metrics.size(R.dimen.doodle_x_speed);
        this.state = Direction < 0 ? State.left : State.right;
    }

    public void stopMove()
    {
        this.dx = 0;
    }

    public void jumping()
    {
        sitTimer = sitTime;
        dy = -jumpSpeed;
    }

    public boolean isFalling() { return falling; }

    @Override
    public void update()
    {
        float frameTime = game.frameTime;

        // 캐릭터 Y축 이동
        dy += gravity;
        float t_dy = dy * frameTime;

        // 아래로 떨어지는 중인지 체크
        falling = dy >= 0;

        y += t_dy + game.getScrollVal();
        dstRect.offset(0, t_dy + game.getScrollVal());

        // 스프라이트 선택 부분
        if (sitTimer <= 0.f)
        {
            this.bitmap = BitmapPool.get(this.state.getImageID());
        }

        else    // 앉아있는 스프라이트
        {
            if (this.state == State.left)
            {
                this.bitmap = BitmapPool.get(State.left_sit.getImageID());
            }
            else
            {
                this.bitmap = BitmapPool.get(State.right_sit.getImageID());
            }

            sitTimer -= frameTime;
        }

        if (dx != 0)
        {
            // 캐릭터 X축 이동
            float t_dx = dx * frameTime;
            x += t_dx;
            if (x < 0)
            {
                x = Metrics.width;
            }
            else if (x > Metrics.width)
            {
                x = 0;
            }
            setDstRect(Metrics.size(R.dimen.doodle_width), Metrics.size(R.dimen.doodle_height));
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
