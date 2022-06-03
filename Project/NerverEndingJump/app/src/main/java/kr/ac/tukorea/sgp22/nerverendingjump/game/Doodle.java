package kr.ac.tukorea.sgp22.nerverendingjump.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp22.nerverendingjump.framework.GameView;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Metrics;
import kr.ac.tukorea.sgp22.nerverendingjump.R;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.Sprite;
import kr.ac.tukorea.sgp22.nerverendingjump.framework.BitmapPool;

public class Doodle extends Sprite
{
    private static final String TAG = Doodle.class.getSimpleName();
    private static final ArrayList<Bitmap> doodleImage = new ArrayList<>();

    private float dx, dy = 0;
    private final float jumpSpeed = 2500;
    private final float gravity = 80;

    private enum State
    {
        left, left_sit, right, right_sit, COUNT
    }

    private boolean falling;
    private float sitTimer;

    public Doodle(float x, float y)
    {
        super(x, y, R.dimen.doodle_radius, R.mipmap.lik_left);

        if (doodleImage.isEmpty())
        {
            doodleImage.add(State.left.ordinal(), BitmapPool.get(R.mipmap.lik_left));
            doodleImage.add(State.left_sit.ordinal(), BitmapPool.get(R.mipmap.lik_left_sit));
            doodleImage.add(State.right.ordinal(), BitmapPool.get(R.mipmap.lik_right));
            doodleImage.add(State.right_sit.ordinal(), BitmapPool.get(R.mipmap.lik_right_sit));
        }

        falling = true;
    }

    @Override
    public RectF getRect()
    {
        int size = (int)Metrics.size(R.dimen.doodle_radius) / 2;
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
    }

    public void setDirection(int Direction)
    {
        dx = Metrics.size(R.dimen.doodle_x_speed);
        if (Direction == -1)
        {
            dx = -dx;
        }
    }

    public void stopMove()
    {
        this.dx = 0;
    }

    public void jumping()
    {
        dy = -jumpSpeed;
    }

    public boolean isFalling() { return falling; }

    @Override
    public void update()
    {
        float frameTime = MainGame.getInstance().frameTime;

        // 캐릭터 Y축 이동
        dy += gravity;
        float t_dy = dy * frameTime;

        //////////////////////////////////////
        // 테스트용 y위치
//        float doodleY = Metrics.height - Metrics.size(R.dimen.doodle_y_offset);
//        // 이걸 충돌 감지하고 뛰도록 바꿔야함
//        if (y + t_dy >= doodleY)
//        {
//            dy = -jumpSpeed;
//            sitTimer = 0.3f;
//        }
        //////////////////////////////////////

        // 아래로 떨어지는 중인지 체크
        falling = dy >= 0;

        // 스프라이트 선택 부분
        if (sitTimer <= 0.f)
        {
            if (dx < 0)
            {
                this.bitmap = doodleImage.get(State.left.ordinal());
            }
            else if (dx > 0)
            {
                this.bitmap = doodleImage.get(State.right.ordinal());
            }
        }
        
        else    // 앉아있는 스프라이트
        {
            if (dx < 0)
            {
                this.bitmap = doodleImage.get(State.left_sit.ordinal());
            }
            else if (dx > 0)
            {
                this.bitmap = doodleImage.get(State.right_sit.ordinal());
            }

            sitTimer -= frameTime;
        }
        y += t_dy;
        dstRect.offset(0, t_dy);

        if (dx != 0)
        {
            // 캐릭터 X축 이동
            float t_dx = dx * frameTime;
            if ((t_dx < 0 && 30 <= dstRect.left) || (t_dx > 0 && dstRect.right <= Metrics.width - 25))
            {
                x += t_dx;
                dstRect.offset(t_dx, 0);
            }
            else
            {
                dx = 0;
            }
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
