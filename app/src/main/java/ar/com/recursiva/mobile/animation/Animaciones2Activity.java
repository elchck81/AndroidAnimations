package ar.com.recursiva.mobile.animation;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;

public class Animaciones2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animaciones2);
        ImageView mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();
        ImageView mImageViewEmptying = (ImageView) findViewById(R.id.imageview_animation_list_emptying);
        ((AnimationDrawable) mImageViewEmptying.getBackground()).start();
        final CheckedTextView mImageViewSelector = (CheckedTextView) findViewById(R.id.imageview_animated_selector);

        mImageViewSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewSelector.setActivated(!mImageViewSelector.isActivated());
            }
        });
    }
}
