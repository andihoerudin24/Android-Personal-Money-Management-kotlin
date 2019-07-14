package com.tutorkomputer.uangkaskotlin.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import com.tutorkomputer.uangkaskotlin.App
import com.tutorkomputer.uangkaskotlin.R
import com.tutorkomputer.uangkaskotlin.Utils.Converter
import com.tutorkomputer.uangkaskotlin.adapter.MainAdapter
import com.tutorkomputer.uangkaskotlin.data.Constant
import com.tutorkomputer.uangkaskotlin.data.model.KasModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_menu.*

class  MainActivity : AppCompatActivity() {

    var kasList =ArrayList<KasModel>()

    companion object{
        var intent: Intent?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData();getTotal()
    }

    fun getData(){
        kasList.clear()
        listView.adapter=null
        kasList.addAll(App.db!!.getData())

        val adapter =MainAdapter(this,kasList)

        listView.adapter = adapter


        listView.setOnItemClickListener { parent, view, position, id ->


            Constant.transaksi_id=kasList[position].transaksi_id

            intent = Intent(this,EditActivity::class.java)
            intent.putExtra(getString(R.string.intent_id),kasList[position].transaksi_id.toString())
            intent.putExtra(getString(R.string.intent_status),kasList[position].status)
            intent.putExtra(getString(R.string.intent_jumlah),kasList[position].jumlah.toString())
            intent.putExtra(getString(R.string.intent_keterangan),kasList[position].keterangan)
            intent.putExtra(getString(R.string.intent_tanggal),kasList[position].tanggal)

            Log.e("_keterangan",kasList[position].keterangan)
            MenuDialog()

        }
    }

    fun getTotal(){
        App.db!!.getTotal()
        textMasuk.text=Converter.rupiahformat( Constant.masuk.toString())
        textKeluar.text=Converter.rupiahformat(Constant.keluar.toString())
        textSaldo.text ="Rp" +Converter.rupiahformat  (
            (Constant.masuk - Constant.keluar).toString())
    }


    fun MenuDialog(){
        val dialog =Dialog(this)
        dialog.setContentView(R.layout.dialog_menu)
        dialog.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)

        dialog.textEdit.setOnClickListener {
           // startAc tivity(Intent(this,EditActivity::class.java))
            startActivity(intent)
            dialog.dismiss()


        }
        dialog.textHapus.setOnClickListener {
            alertDialog()
            dialog.dismiss()
        }

        dialog.show()
    }


    fun alertDialog(){
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Konfrimasi")
        builder.setMessage("Yakin Untuk Menghapus Transaksi ini")
        builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
            App.db!!.DeleteData(Constant.transaksi_id!!)
            Toast.makeText(applicationContext,"Transaksi Berhasil Di Hapus",Toast.LENGTH_SHORT).show()
            getData();  getTotal()
            dialog.dismiss()
        })
        builder.setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.show()
    }

     

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
