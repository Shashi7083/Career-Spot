package com.intern_job.notification.activity.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.intern_job.notification.activity.model.homeRecyclerModel
import com.intern_job.notification.activity.model.topcompanyModel


class HomeDBHelper(context :Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){


    companion object{
       private const val DATABASE_VERSION=1
        private const val DATABASE_NAME="Company"
        private const val TABLE1_NAME="HomeCompany"
        private  const val TABLE2_NAME="TopCompany"
        private const val COLUMN_ID="SNO"
        private const val  COLUMN_COMPANY ="COMPANY"
        private const val  COLUMN_BATCH="BATCH"
        private const val COLUMN_URL="URL"
        private const val COLUMN_IMG="IMG"
        private const val COLUMN_SECTOR="SECTOR"
    }
    override fun onCreate(p0: SQLiteDatabase?) {

        val createHomeTable = ("CREATE TABLE $TABLE1_NAME($COLUMN_ID INT,$COLUMN_COMPANY TEXT,$COLUMN_BATCH TEXT,$COLUMN_URL TEXT)")


        p0?.execSQL(createHomeTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE1_NAME")
        p0!!.execSQL("DROP TABLE IF EXISTS $TABLE2_NAME")
        onCreate(p0)
    }



    //FOR TABLE 1 HOME TABLE
    fun insertCompany(c:homeRecyclerModel) :Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_ID,c.sno)
        contentValues.put(COLUMN_COMPANY,c.company)
        contentValues.put(COLUMN_BATCH,c.batch)
        contentValues.put(COLUMN_URL,c.url)

        val success = db.insert(TABLE1_NAME,null,contentValues)
        db.close()
        return success

    }

    @SuppressLint("Range")
    fun getAllHomeCompany():ArrayList<homeRecyclerModel>{
        val compList :ArrayList<homeRecyclerModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE1_NAME"
        val db = this.readableDatabase

        val cursor:Cursor?

        try{
            cursor = db.rawQuery(selectQuery,null)

        }
        catch (e : Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id : Int
        var company : String
        var batch : String
        var url :String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("SNO"))
                company = cursor.getString(cursor.getColumnIndex("COMPANY"))
                batch = cursor.getString(cursor.getColumnIndex("BATCH"))
                url = cursor.getString(cursor.getColumnIndex("URL"))

                val com = homeRecyclerModel(id,company,batch,url)
                compList.add(com)

            }while(cursor.moveToNext())
        }

        return compList

    }

    fun deleteAllHomeData() {
        val db = this.writableDatabase
        val query = "DELETE FROM $TABLE1_NAME"

     db.execSQL(query)
        db.close()
    }



}