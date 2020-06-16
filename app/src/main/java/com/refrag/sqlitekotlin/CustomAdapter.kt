package com.refrag.sqlitekotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_item.view.*
import java.io.ByteArrayOutputStream

class CustomAdapter(val activity : Activity,
                    val barang_id : List<String>,
                    val barang_title : List<String>,
                    val barang_time : List<String>,
                    val barang_location : List<String>,
                    val barang_gambar : List<Bitmap>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(activity).inflate(R.layout.row_item, parent, false)
        return MyViewHolder(inflater);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.barang_title_txt.setText(barang_title.get(position))
        holder.barang_time_txt.setText(barang_time.get(position))
        holder.barang_location_txt.setText(barang_location.get(position))
        holder.iv.setImageBitmap(barang_gambar[position]);
        Log.w("TEST_NULL", Integer.toString(position)+" data = "+barang_gambar.get(position).toString());

        holder.mainLayout.setOnClickListener{
            var intent =  Intent(activity, UpdateActivity::class.java)
            intent.putExtra("id", barang_id[position])
            intent.putExtra("title", barang_title[position])
            intent.putExtra("time", barang_time[position])
            intent.putExtra("location", barang_location[position])
            activity.startActivityForResult(intent,1)
        }
    }

    override fun getItemCount():Int {
        return barang_id.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

          var  barang_title_txt : TextView = itemView.tv_barangName
          var  barang_time_txt : TextView = itemView.findViewById(R.id.tv_barangTime)
          var  barang_location_txt : TextView = itemView.findViewById(R.id.tv_barangLoc)
          var mainLayout : RelativeLayout = itemView.rl_main
          var iv: ImageView = itemView.findViewById(R.id.iv_barang)

    }
}