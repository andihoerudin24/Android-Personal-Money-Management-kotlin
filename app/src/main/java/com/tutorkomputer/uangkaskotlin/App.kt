package com.tutorkomputer.uangkaskotlin

import android.app.Application
import android.util.Log
import com.tutorkomputer.uangkaskotlin.data.db.DatabaseHelper

class App : Application() {

    companion object {
       var db : DatabaseHelper? = null
    }


    override fun onCreate() {
        super.onCreate()

        db = DatabaseHelper(this)


        Log.e("_logapp","Hallo")
    }
}