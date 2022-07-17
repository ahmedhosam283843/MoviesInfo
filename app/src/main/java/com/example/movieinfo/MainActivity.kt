package com.example.movieinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    val imgs = listOf<Int>(
        androidx.customview.R.drawable.notification_bg, androidx.constraintlayout.widget.R.drawable.abc_ab_share_pack_mtrl_alpha,
        androidx.viewpager.R.drawable.notification_bg, androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.appcompat.R.drawable.abc_ic_go_search_api_material,
        androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.transition.R.drawable.abc_btn_check_to_on_mtrl_000, androidx.constraintlayout.widget.R.drawable.abc_spinner_textfield_background_material,
        androidx.constraintlayout.widget.R.drawable.notification_bg, androidx.appcompat.R.drawable.abc_edit_text_material)
    lateinit var s1 : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}