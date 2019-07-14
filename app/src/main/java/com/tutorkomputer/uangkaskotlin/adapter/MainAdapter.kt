package com.tutorkomputer.uangkaskotlin.adapter

import android.app.Activity
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tutorkomputer.uangkaskotlin.R
import com.tutorkomputer.uangkaskotlin.Utils.Converter
import com.tutorkomputer.uangkaskotlin.data.model.KasModel

class MainAdapter (val activity: Activity, kasModel: List<KasModel>): BaseAdapter() {

    var kasModel = ArrayList<KasModel>()
    init {
        this.kasModel = kasModel as ArrayList<KasModel>
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val view : View?
       val infalter = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       view =  infalter.inflate(R.layout.adapter_main, null)

       val textStatus : TextView =view.findViewById(R.id.textStatus)
       val textJumlah : TextView =view.findViewById(R.id.textJumlah)
       val textTanggal : TextView =view.findViewById(R.id.textTanggal)
       val textKeterangan : TextView =view.findViewById(R.id.textKeterangan)

        textStatus.text     =kasModel[position].status
        textJumlah.text     =Converter.rupiahformat(kasModel[position].jumlah.toString())
        textKeterangan.text =kasModel[position].keterangan
        textTanggal.text    =Converter.dateFormat(kasModel[position].tanggal)

        when(kasModel[position].status){
            "MASUK" -> textStatus.setTextColor(ContextCompat.getColor(activity,R.color.colorBlue))
            "KELUAR" -> textStatus.setTextColor(ContextCompat.getColor(activity,R.color.colorYellow))
        }

       return view
    }

    override fun getItem(position: Int): Any {
          return position
    }

    override fun getItemId(position: Int): Long {
         return position.toLong()
    }

    override fun getCount(): Int {

        return kasModel.size
    }
}