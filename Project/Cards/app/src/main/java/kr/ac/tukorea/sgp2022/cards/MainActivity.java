package kr.ac.tukorea.sgp2022.cards;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int[] BUTTON_IDS = new int[]{
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33
    };

    private int[] resIds = new int[]{
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_kd, R.mipmap.card_qh,
            R.mipmap.card_as, R.mipmap.card_2c, R.mipmap.card_3d, R.mipmap.card_4h,
            R.mipmap.card_5s, R.mipmap.card_jc, R.mipmap.card_kd, R.mipmap.card_qh
    };

    private ImageButton previousButton;
    private int flips;
    private TextView scoreTextView;
    private int countFlips;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById(R.id.socreTextview);

        startGame();
    }

    private void startGame()
    {
        Random random = new Random();
        for (int i = 0; i<resIds.length; ++i)
        {
            int t = random.nextInt(resIds.length);
            int id = resIds[i];
            resIds[i] = resIds[t];
            resIds[t] = id;
        }

        for (int i = 0; i < BUTTON_IDS.length; ++i)
        {
            int resId = resIds[i];
            ImageButton btn = findViewById(BUTTON_IDS[i]);
            btn.setTag(resId);
            btn.setVisibility(View.VISIBLE);
            btn.setImageResource(R.mipmap.card_blue_back);
        }
        countFlips = 0;
        setScore(0);
    }

    public void onBtnRestart(View view)
    {
        Log.d(TAG, "onBtnRestart");
        askRetry();
    }

    private void askRetry()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.restart)
                .setMessage(R.string.restart_alert_msg)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        startGame();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .create()
                .show();
    }

    public void onBtnCard(View view)
    {
        ImageButton imageButton = (ImageButton) view;
        if (imageButton == previousButton)
        {
            Log.d(TAG, "Same Button");
            Toast.makeText(this,
                    R.string.same_card_toast,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        int btnIndex = findButtonIndex(imageButton.getId());
        Log.d(TAG, "onBtnCard: " + btnIndex);

        int prevResId = 0;
        if (previousButton != null)
        {
            prevResId = (Integer) previousButton.getTag();
        }

        int resId = (Integer) imageButton.getTag();
        if (resId != prevResId)
        {
            imageButton.setImageResource(resId);
            if (previousButton != null)
            {
                previousButton.setImageResource(R.mipmap.card_blue_back);
            }

            previousButton = imageButton;
            setScore(flips + 1);
        } else
        {
            imageButton.setVisibility(View.INVISIBLE);
            previousButton.setVisibility(View.INVISIBLE);

            countFlips += 2;

            previousButton = null;
        }

        if (countFlips == BUTTON_IDS.length)
        {
            askRetry();
        }
    }

    private void setScore(int flips)
    {
        this.flips = flips;

        Resources res = getResources();
        String fmt = res.getString(R.string.flips_fmt);
        String text = String.format(fmt, flips);
        scoreTextView.setText(text);
    }

    private int findButtonIndex(int id)
    {
        for (int i = 0; i < BUTTON_IDS.length; ++i)
        {
            if (BUTTON_IDS[i] == id)
                return i;
        }
        return -1;
    }
}