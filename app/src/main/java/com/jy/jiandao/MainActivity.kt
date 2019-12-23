package com.jy.jiandao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mr.k.libmvp.widget.LoadingView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        showLoading.setOnClickListener {
            val viewGroup: ViewGroup = findViewById(android.R.id.content);
            val loadingView = LayoutInflater.from(this).inflate(
                R.layout.mvp_layout_loading,
                viewGroup,
                false
            ) as LoadingView;

            loadingView.showMode(LoadingView.MODE_TRANSPARENT_BACKGROUND)
            loadingView.onError()
            
        }
    }

}
