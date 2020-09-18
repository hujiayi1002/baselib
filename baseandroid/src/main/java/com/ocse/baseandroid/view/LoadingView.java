package com.ocse.baseandroid.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.ocse.baseandroid.R;


public class LoadingView extends Dialog {
    Context context;

    public LoadingView(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= LayoutInflater.from(context).inflate(R.layout.loading_view,null);
        setContentView(view);
        ImageView imageView = view.findViewById(R.id.image);
        Animation a = AnimationUtils.loadAnimation(context, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        a.setInterpolator(lin);
        imageView.startAnimation(a);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
