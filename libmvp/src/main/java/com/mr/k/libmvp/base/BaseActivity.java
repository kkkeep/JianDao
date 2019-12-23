package com.mr.k.libmvp.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.mr.k.libmvp.manager.MvpFragmentManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/*
 * created by Cherry on 2019-12-20
 **/
public class BaseActivity extends RxAppCompatActivity {



    protected void showToast(@StringRes int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();

    }


}
