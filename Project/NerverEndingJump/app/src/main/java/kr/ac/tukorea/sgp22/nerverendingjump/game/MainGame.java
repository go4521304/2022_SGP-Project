package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.CollisionHelper;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameObject;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Recycle;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;

public class MainGame
{
    private static final String TAG = MainGame.class.getSimpleName();
    private static MainGame singleton;
    public float frameTime;

    public float score;

    private ArrayList<ArrayList<GameObject>> layers = null;
    private Doodle doodle;
    private Background bg;
    private BlockGenerator blockGenerator;
    private MonsterGenerator monsterGenerator;
    private Sprite btn_pause;

    private Paint scorePaint = new Paint();


    public Doodle getDoodle()
    {
        return doodle;
    }

    public enum Layer
    {
        bg, monster, block, player, bullet, system, COUNT
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

        for (int i = 0; i < count; ++i)
        {
            layers.add(new ArrayList<>());
        }
    }

    public void init()
    {
        // 레이어 리스트 추가
        initLayers(Layer.COUNT.ordinal());

        // 플레이어
        doodle = Doodle.getInstance();
        doodle.init(Metrics.width / 2, Metrics.height / 2);
        add(Layer.player, doodle);

        // 배경 추가
        bg = new Background(R.mipmap.background);
        add(Layer.bg, bg);

        // 블록 생성기
        blockGenerator = new BlockGenerator();
        add(Layer.system, blockGenerator);

        // 몬스터 생성기
        monsterGenerator = new MonsterGenerator();
        add(Layer.system, monsterGenerator);

        // 정지버튼
        btn_pause = new Sprite(Metrics.width - (Metrics.size(R.dimen.btn_pause_w) / 2), Metrics.size(R.dimen.btn_pause_h) / 2, Metrics.size(R.dimen.btn_pause_w), Metrics.size(R.dimen.btn_pause_h), R.mipmap.btn_pause);
        add(Layer.system, btn_pause);

        // 스코어
        score = 0;
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(80);

        ///////////////////////// 초기 블록들 /////////////////////////////
        Block block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2, Metrics.height - Metrics.size(R.dimen.block_y_offset));
        add(Layer.block, block);

        block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2 - Metrics.size(R.dimen.block_width) - Metrics.size(R.dimen.block_width), Metrics.height - Metrics.size(R.dimen.block_y_offset) - Metrics.size(R.dimen.block_y_offset));
        add(Layer.block, block);
        block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2 - Metrics.size(R.dimen.block_width), Metrics.height - (Metrics.size(R.dimen.block_y_offset) * 3));
        add(Layer.block, block);
        block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2, Metrics.height - (Metrics.size(R.dimen.block_y_offset) * 4));
        add(Layer.block, block);
        block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2 + Metrics.size(R.dimen.block_width), Metrics.height - (Metrics.size(R.dimen.block_y_offset) * 5));
        add(Layer.block, block);
        block = Block.get(Block.Type.normal.ordinal(), Metrics.width / 2 + (Metrics.size(R.dimen.block_width) * 2), Metrics.height - (Metrics.size(R.dimen.block_y_offset) * 6));
        add(Layer.block, block);
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
        for (ArrayList<GameObject> objects : layers)
        {
            for (GameObject gobj : objects)
            {
                gobj.draw(canvas);
            }
        }
        canvas.drawText(String.valueOf((int) score), Metrics.width/2 - 50, 80, scorePaint);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (CollisionHelper.PointCheck(btn_pause, x, y))
                {
                    GameView.view.changeScene(GameView.Scene.pause);
                }
                else
                {
                    doodle.shoot();
                    add(Layer.bullet, Bullet.get(doodle.getX(), doodle.getY()));
                }
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

    public void remove(Layer layer, GameObject object)
    {
        GameView.view.post(new Runnable()
        {
            @Override
            public void run()
            {
                layers.get(layer.ordinal()).remove(object);
                Recycle.add(object);
            }
        });
    }

    public int blockCount()
    {
        return layers.get(Layer.block.ordinal()).size();
    }

    public ArrayList<GameObject> getGameObject(Layer layer)
    {
        return layers.get(layer.ordinal());
    }

    public float getScrollVal()
    {
        return bg.getScrollVal();
    }
}
