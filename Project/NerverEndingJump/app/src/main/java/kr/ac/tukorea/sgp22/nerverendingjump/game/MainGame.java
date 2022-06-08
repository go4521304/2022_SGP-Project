package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;

public class MainGame
{
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    public float frameTime;

    private ArrayList<ArrayList<GameObject>> layers;
    private Doodle doodle;
    private Background bg;

    public Doodle getDoodle()
    {
        return doodle;
    }

    public enum Layer
    {
        bg, monster, block, player, bullet, ui, COUNT
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

    private void initLayers(int count)
    {
        layers = new ArrayList<>();

        for (int i = 0; i< count; ++i)
        {
            layers.add(new ArrayList<>());
        }
    }
    
    public void init()
    {
        // 레이어 리스트 추가
        initLayers(Layer.COUNT.ordinal());

        // 플레이어
        float doodleY = Metrics.height - Metrics.size(R.dimen.doodle_y_offset);
        doodle = new Doodle(Metrics.width/2, doodleY);
        add(Layer.player, doodle);
        
        // 배경 추가
        bg = new Background(R.mipmap.background);
        add(Layer.bg, bg);

        ///////////////////////// 테스트용 블록들 /////////////////////////////
        Block block = new Block((int)(Math.random() % Block.Type.COUNT.ordinal()), Metrics.width / 2, Metrics.height - 200, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        add(Layer.block, block);

        block = new Block((int)(Math.random() % Block.Type.COUNT.ordinal()), Metrics.width / 2 - 400, Metrics.height - 750, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        add(Layer.block, block);

        block = new Block((int)(Math.random() % Block.Type.COUNT.ordinal()), Metrics.width / 2 + 300, Metrics.height - 1000, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        add(Layer.block, block);

        block = new Block((int)(Math.random() % Block.Type.COUNT.ordinal()), Metrics.width / 2 - 400, Metrics.height - 1400, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        add(Layer.block, block);

        block = new Block((int)(Math.random() % Block.Type.COUNT.ordinal()), Metrics.width / 2, Metrics.height - 2000, Metrics.size(R.dimen.block_width), Metrics.size(R.dimen.block_height));
        add(Layer.block, block);
        ///////////////////////////////////////////////////////////////////
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

    public void SetDirection(double Direction)
    {
        if (Direction > 0)
            doodle.setDirection(1);
        else if (Direction < 0)
            doodle.setDirection(-1);
    }


    public float getScrollVal() { return bg.getScrollVal(); }
}
