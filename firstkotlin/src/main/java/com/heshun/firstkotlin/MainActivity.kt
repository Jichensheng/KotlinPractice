package com.heshun.firstkotlin

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.heshun.firstkotlin.customer.FlowLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_love->{
                val intent:Intent=Intent(this,MainActivity2::class.java)
                startActivity(intent)
            }
            R.id.tv_learning->{
                startActivity(Intent(this,RedBookActivity::class.java))
            }
        }
    }

    private var mImageView: ImageView? = null
    private var tv_love:TextView?=null
    private var tv_learning:TextView?=null
    var fl_flow: FlowLayout?=null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }
    fun initView(){
        tv_love=findViewById(R.id.tv_love) as TextView
        tv_love!!.setOnClickListener(this)
        tv_learning=findViewById(R.id.tv_learning) as TextView
        tv_learning!!.setOnClickListener(this)
        fl_flow=findViewById(R.id.fl_flow) as FlowLayout
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
        mImageView = findViewById(R.id.iv_image) as ImageView
        try {
            val inputStream = assets.open("aa.jpg")

            //获得图片的宽、高
            val tmpOptions = BitmapFactory.Options()
            tmpOptions.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, tmpOptions)
            val width = tmpOptions.outWidth
            val height = tmpOptions.outHeight

            //设置显示图片的中心区域
            val bitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.RGB_565
            val bitmap = bitmapRegionDecoder.decodeRegion(Rect(width / 2 - 100, height / 2 - 100, width / 2 + 100, height / 2 + 100), options)
            (mImageView as ImageView).setImageBitmap(bitmap)


        } catch (e: IOException) {
            e.printStackTrace()
        }


    }
}
