package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;


import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Doodle extends Sprite
{
    private static final String TAG = Doodle.class.getSimpleName();

    private static Doodle singleton;

    private static MainGame game;

    private float dx, dy;
    private static final float jumpSpeed = 2500;
    private static final float gravity = 80;
    private static final float sitTime = 0.3f, shootTime = 0.3f;

    private SensorManager sensorManager;
    private SensorEventListener sensorEventListener;
    private Sensor sensor;

    private float[] initOrientation;

    private enum State
    {
        left(R.mipmap.lik_left), left_sit(R.mipmap.lik_left_sit),
        right(R.mipmap.lik_right), right_sit(R.mipmap.lik_right_sit),
        shoot(R.mipmap.lik_shoot), shoot_sit(R.mipmap.lik_shoot_sit);

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
    private float sitTimer, shootTimer;

    public Doodle()
    {
        super(0, 0, Metrics.size(R.dimen.doodle_width), Metrics.size(R.dimen.doodle_width), R.mipmap.lik_left);

        game = MainGame.getInstance();

        sensorManager = (SensorManager) GameView.view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorEventListener = new UserSensorListner();

        registerListener();
    }

    public static Doodle getInstance()
    {
        if (singleton == null)
        {
            singleton = new Doodle();
        }
        return singleton;
    }

    public static void clear()
    {
        singleton = null;
    }

    public void init(float x, float y)
    {
        this.x = x;
        this.y = y;
        setDstRect(Metrics.size(R.dimen.doodle_width), Metrics.size(R.dimen.doodle_width));
        this.bitmap = BitmapPool.get(R.mipmap.lik_left);

        this.dx = 0;
        this.dy = 0;

        this.state = State.left;
        this.sitTimer = 0.0f;
        this.falling = true;
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
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterListener()
    {
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    public RectF getRect()
    {
        int sizeH = (int)Metrics.size(R.dimen.doodle_height) / 2;
        int sizeW = (int)Metrics.size(R.dimen.doodle_width) / 3;
        RectF tmp = new RectF();
        tmp.set(dstRect.left + sizeW, dstRect.bottom - sizeH, dstRect.right - sizeW, dstRect.bottom);
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

        // 게임 오버
        if (y > Metrics.height)
        {
            GameView.view.endGame();
            GameView.view.changeScene(GameView.Scene.score);
            return;
        }

        // 스프라이트 선택 부분
        if (sitTimer <= 0.f)
        {
            if (shootTimer <= 0.0f)
            {
                changeSprite(this.state.getImageID());
            }
            else
            {
                changeSprite(State.shoot.getImageID());
                shootTimer -= frameTime;
            }
        }

        else    // 앉아있는 스프라이트
        {
            if (shootTimer <= 0.0f)
            {
                if (this.state == State.left)
                {
                    changeSprite(State.left_sit.getImageID());
                }
                else
                {
                    changeSprite(State.right_sit.getImageID());
                }
            }
            else
            {
                changeSprite(State.shoot_sit.getImageID());
                shootTimer -= frameTime;
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

    public void shoot()
    {
        shootTimer = shootTime;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
