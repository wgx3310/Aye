package reid.aye.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v13.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;

import java.util.Random;

import reid.aye.R;

public class SplashActivity extends BaseActivity {

    private ViewPropertyAnimator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TypedArray ar = getResources().obtainTypedArray(R.array.splash_imgs);
        int len = ar.length();
        int[] resIds = new int[len];
        for (int i = 0; i < len; i++)
            resIds[i] = ar.getResourceId(i, 0);
        ar.recycle();

        int index = new Random().nextInt(2);

        ImageView img = (ImageView) findViewById(R.id.splash_img);
        img.setImageResource(resIds[index]);

        mAnimator = img.animate().scaleX(1.13f).scaleY(1.13f).setDuration(4000)
                .setStartDelay(1000).withEndAction(()->{
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                });
        mAnimator.start();
    }

    @Override
    protected void onDestroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
        }
        super.onDestroy();
    }
}
