package kr.ac.tukorea.sgp2022.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp2022.dragonflight.framework.CollisionChecker;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Metrics;
import kr.ac.tukorea.sgp2022.dragonflight.R;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameObject;
import kr.ac.tukorea.sgp2022.dragonflight.framework.GameView;
import kr.ac.tukorea.sgp2022.dragonflight.framework.Recyclable;
import kr.ac.tukorea.sgp2022.dragonflight.framework.RecycleBin;

public class MainGame
{
    private static final String TAG = MainGame.class.getSimpleName();
    private Paint collisionPaint;
    public Score score;

    public static MainGame getInstance()
    {
        if (singleton == null)
        {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame()
    {
    }

    private static MainGame singleton;

    private static final int BALL_COUNT = 10;
    //private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<ArrayList<GameObject>> layers;
    private Fighter fighter;

    public enum Layer
    {
        bg1, bullet, enemy, player, bg2, ui, controller, COUNT
    }

    public static void clear()
    {
        singleton = null;
    }

    public void init()
    {
        //objects.clear();

        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());


        float fighterY = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(Metrics.width / 2, fighterY);
        add(Layer.player, fighter);

        score = new Score();
        add(Layer.ui, score);

        add(Layer.bg1, new HorzScrollBackground(R.mipmap.bg_city, Metrics.size(R.dimen.bg_speed_city)));
        add(Layer.bg2, new HorzScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.bg_speed_cloud)));

        collisionPaint = new Paint();
        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
    }

    private void initLayers(int count)
    {
        layers = new ArrayList<>();
        for (int i = 0; i < count; ++i)
        {
            layers.add(new ArrayList<>());
        }
    }

    public void update(int elapsedNanos)
    {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        for (ArrayList<GameObject> objects: layers)
        {
            for (GameObject gobj : objects)
            {
                gobj.update();
            }
        }
    }

    public ArrayList<GameObject> objectsAt(Layer layer)
    {
        return layers.get(layer.ordinal());
    }

    public void draw(Canvas canvas)
    {
        for (ArrayList<GameObject> objects: layers)
        {
            for (GameObject gobj : objects)
            {
                gobj.draw(canvas);
//            if (gobj instanceof BoxCollidable)
//            {
//                RectF rect = ((BoxCollidable) gobj).getBoundingRect();
//                canvas.drawRect(rect, collisionPaint);
//            }
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);

                return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject)
    {
        GameView.view.post(new Runnable()
        {
            @Override
            public void run()
            {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject)
    {
        GameView.view.post(new Runnable()
        {
            @Override
            public void run()
            {
                for (ArrayList<GameObject> objects : layers)
                {
                    boolean removed = objects.remove(gameObject);
                    if (!removed) continue;
                    if (gameObject instanceof Recyclable)
                    {
                        RecycleBin.add((Recyclable) gameObject);
                    }
                    break;
                }
            }
        });
    }

    public int objectCount()
    {
        int count = 0;
        for (ArrayList<GameObject> objects: layers)
        {
            count += objects.size();
        }
        return count;
    }
}
