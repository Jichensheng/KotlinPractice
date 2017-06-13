package com.heshun.firstkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView(){
        tv_kotlinx.text = "你好，kotlin！"
        Thread(Runnable { tv_kotlinx.text = "不错，我是在非UI线程里" }).start()
        tv_kotlinx.setOnClickListener {Toast.makeText(this@MainActivity,"nihao",Toast.LENGTH_SHORT).show()}

        val mutList:MutableList<String> = mutableListOf()
        val list:List<String> = mutList
        for(i in 0..120){
            mutList.add("第$i 天")
        }
        rv_list.adapter=RvListAdapter(this,list)
        rv_list.layoutManager = LinearLayoutManager(this)

    }
}
