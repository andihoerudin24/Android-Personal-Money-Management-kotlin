package com.tutorkomputer.uangkaskotlin.activity

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.tutorkomputer.uangkaskotlin.App
import com.tutorkomputer.uangkaskotlin.R
import com.tutorkomputer.uangkaskotlin.Utils.Converter
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {

    var tanggal:String? =null
    var status:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        var bundle: Bundle = intent.extras
        Log.e("hasilintent",bundle.getString(getString(R.string.intent_keterangan)))

        status=bundle.getString(getString(R.string.intent_status))
        when(status){
            "MASUK" -> Radiomasuk.isChecked=true
            "KELUAR" -> Radiokeluar.isChecked=true
        }

        radioStatus.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.Radiomasuk -> status = "MASUK"
                R.id.Radiokeluar -> status = "KELUAR"
            }
            Log.e("_logstatus",status)
        }

        editjumlah.setText(bundle.getString(getString(R.string.intent_jumlah)))
        editketerangan.setText(bundle.getString(getString(R.string.intent_keterangan)))
        tanggal=bundle.getString(getString(R.string.intent_tanggal))
        edittanggal.setText(Converter.dateFormat(tanggal.toString()))

        buttonsimpan.setOnClickListener {
            if (status.isNullOrBlank() || editjumlah.text.isNullOrBlank() || editketerangan.text.isNullOrBlank()|| tanggal.isNullOrBlank() ){
                Toast.makeText(applicationContext, "Isi Data Dengan Benar",Toast.LENGTH_SHORT).show()
            }else{
              App.db!!.updateData(bundle.getString(getString(R.string.intent_id)).toInt(),
                  status.toString(),editjumlah.text.toString().toLong(),editketerangan.text.toString(),tanggal.toString())
                Toast.makeText(applicationContext, "Perubahan Berhasil Di simpan",Toast.LENGTH_SHORT).show()
                finish()

            }
        }


        var calender= Calendar.getInstance()
        var Y= calender.get(Calendar.YEAR)
        var M= calender.get(Calendar.MONTH)
        var D= calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog. OnDateSetListener { view, year, month, dayOfMonth ->
            tanggal="$year-${Converter.decimalFormat(month+1)}-${Converter.decimalFormat(dayOfMonth)}"

            Log.e("tanggal",tanggal)
            edittanggal.setText(Converter.dateFormat(tanggal.toString()))
        },Y,M,D)

        edittanggal.setOnClickListener {
            datePickerDialog.show()
        }

        supportActionBar!!.setTitle("Edit Data")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

}
