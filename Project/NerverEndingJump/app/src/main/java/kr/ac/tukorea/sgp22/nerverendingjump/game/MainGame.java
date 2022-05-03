package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;

public class MainGame
{
    private static MainGame singleton;
    public float frameTime;

    private ArrayList<ArrayList<GameObject>> layers;
    private Doodle doodle;

    public enum Layer
    {
        bullet, monster, player, foothold, ui, COUNT
    }

    public static MainGame getInstance()
    {
        if (singleton == null)
        {
            singleton = new MainGame();
        }
        return singleton;
    }

    public static void clear()
    {
        singleton = null;
    }
    
    public void init()
    {
        initLayers(Layer.COUNT.ordinal());

        float doodleY = Metrics.height - Metrics.size(R.dimen.doodle_y_offset);
        doodle = new Doodle(Metrics.width/2, doodleY);
        add(Layer.player, doodle);
    }

    private void initLayers(int count)
    {
        layers = new ArrayList<>();

        for (int i = 0; i< count; ++i)
        {
            layers.add(new ArrayList<>());
        }
    }

    public void update(int elapsed)
    {
        frameTime = elapsed * 1e-9f;
        for (ArrayList<GameObject> objects : layers)
        {
            for (GameObject obj : objects)
            {
                obj.update();
            }
        }
    }

    public void draw(Canvas canvas)
    {
        for (ArrayList<GameObject> objects: layers)
        {
            for (GameObject gobj : objects)
            {
                gobj.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                doodle.setDirection(x, y);
                return true;

            case MotionEvent.ACTION_UP:
                doodle.stopMove();
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
}
