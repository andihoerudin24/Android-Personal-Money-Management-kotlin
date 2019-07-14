package com.tutorkomputer.uangkaskotlin.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tutorkomputer.uangkaskotlin.App
import com.tutorkomputer.uangkaskotlin.R
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    var  status : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        radioStatus.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.Radiomasuk -> status = "MASUK"
                R.id.Radiokeluar -> status = "KELUAR"
            }
            Log.e("_logstatus",status)
        }

        buttonsimpan.setOnClickListener {
            if (status.isNullOrBlank() || editjumlah.text.isNullOrBlank() || editketerangan.text.isNullOrBlank()){
                Toast.makeText(applicationContext, "Isi Data Dengan Benar",Toast.LENGTH_SHORT).show()
            }else{
               var id= App.db!!.insertData(status, editjumlah.text.toString(), editketerangan.text.toString())

                Log.e("_log_id",id.toString())
                if (id > 0){
                    Toast.makeText(applicationContext, "Transaksi ok",Toast.LENGTH_SHORT).show()
                    finish()
                  }
            }
        }

        supportActionBar!!.setTitle("Tambah Baru")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
