package com.tutorkomputer.uangkaskotlin.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.tutorkomputer.uangkaskotlin.data.Constant
import com.tutorkomputer.uangkaskotlin.data.model.KasModel

class DatabaseHelper : SQLiteOpenHelper {


    companion object {
        const val TAG = "DatabaseHelper"
        const val DB_NAME = "uangKasv.kotlin"
        const val DB_VERSION = 1
        const val TABLE_NAME = "transaksi"
        const val TRANSAKSI_ID = "transaksi_id"
        const val STATUS = "status"
        const val JUMLAH = "jumlah"
        const val KETERANGAN = "keterangan"
        const val TANGGAL = "tanggal"
    }

    private val createTable = "CREATE TABLE " + TABLE_NAME + "(" +
            TRANSAKSI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            STATUS + " TEXT," +
            JUMLAH + " TEXT," +
            KETERANGAN + " TEXT," +
            TANGGAL + " DATETIME DEFAULT CURRENT_DATE" +
            ")"

    var context: Context? = null
    var db: SQLiteDatabase


    constructor(context: Context) : super(context,
        DB_NAME, null,
        DB_VERSION
    ){
        this.context = context
        db = this.writableDatabase
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL( createTable )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun insertData(status:String, jumlah:String, keterangan:String) : Long {

        var values = ContentValues()
        values.put(STATUS, status)
        values.put(JUMLAH, jumlah)
        values.put(KETERANGAN, keterangan)

        val transaksi_id = db.insert(TABLE_NAME, "", values)

        //db.close()
        return transaksi_id
    }

    fun getData() : List<KasModel>{
        val kas = ArrayList<KasModel>()

        val strsql = "SELECT * FROM $TABLE_NAME ORDER BY $TRANSAKSI_ID DESC"

        val cursor : Cursor = db.rawQuery(strsql,null)
        cursor.moveToFirst()
        for(i in 0 until  cursor.count){
            cursor.moveToPosition(i)
            kas.add(
                KasModel(
                cursor.getInt(cursor.getColumnIndex(TRANSAKSI_ID)),
                cursor.getString(cursor.getColumnIndex(STATUS)),
                cursor.getLong(cursor.getColumnIndex(JUMLAH)),
                cursor.getString(cursor.getColumnIndex(KETERANGAN)),
                cursor.getString(cursor.getColumnIndex(TANGGAL))
             ))
        }

        return kas
    }


    fun getTotal(){
        val strSql =" SELECT SUM($JUMLAH) AS total," +
                "(SELECT SUM ($JUMLAH) FROM $TABLE_NAME WHERE $STATUS ='MASUK') AS masuk," +
                "(SELECT SUM ($JUMLAH) FROM $TABLE_NAME WHERE $STATUS ='KELUAR') AS keluar" +
                 " FROM $TABLE_NAME"

        val cursor :Cursor =db.rawQuery(strSql,null)
        cursor.moveToFirst()


        Log.e("_pemasukan",cursor.getLong(cursor.getColumnIndex("masuk")).toString())
        Log.e("_pengeluaran",cursor.getLong(cursor.getColumnIndex("keluar")).toString())


        Constant.masuk  =cursor.getLong(cursor.getColumnIndex("masuk"))
        Constant.keluar =cursor.getLong(cursor.getColumnIndex("keluar"))
    }

    fun updateData(id:Int ,stts:String, jml:Long,ket:String,tgl:String){
        var values = ContentValues()
        values.put(STATUS, stts)
        values.put(JUMLAH, jml)
        values.put(KETERANGAN, ket)
        values.put(TANGGAL, tgl)


        db.update(TABLE_NAME,values,"$TRANSAKSI_ID='$id'",null)
    }


    fun DeleteData(id:Int){
        db.delete(TABLE_NAME,"$TRANSAKSI_ID='$id'",null)
    }


}