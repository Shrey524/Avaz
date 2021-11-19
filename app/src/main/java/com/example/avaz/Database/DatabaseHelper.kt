package com.example.avaz.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.avaz.models.SortedApiData

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ListDatabase"
        private val TABLE_DISH = "DishTable"
        private val KEY_IMG_DISH = "img_dish"
        private val KEY_NAME = "name"
        private val KEY_SELECTED = "selected"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val createDishTable = ("CREATE TABLE $TABLE_DISH($KEY_IMG_DISH TEXT,$KEY_NAME TEXT,$KEY_SELECTED INT)")
        db?.execSQL(createDishTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_DISH")
        onCreate(db)
    }


    //method to insert data
    fun addDish(sortedApiData: SortedApiData):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IMG_DISH, sortedApiData.img_dish)
        contentValues.put(KEY_NAME, sortedApiData.name)
        contentValues.put(KEY_SELECTED,sortedApiData.selected)
        // Inserting Row
        val success = db.insert(TABLE_DISH, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to read data
    fun viewDishes():List<SortedApiData>{
        val empList:ArrayList<SortedApiData> = ArrayList<SortedApiData>()
        val selectQuery = "SELECT  * FROM $TABLE_DISH"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var dish: String
        var dishName: String
        var selected: Boolean
        if (cursor.moveToFirst()) {
            do {
                dish = cursor.getString(cursor.getColumnIndexOrThrow("img_dish"))
                dishName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                selected = cursor.getInt(cursor.getColumnIndexOrThrow("selected")) != 0

                val emp= SortedApiData(dishName,dish,selected)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
    //method to update data
    fun updateDish(sortedApiData: SortedApiData):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_IMG_DISH, sortedApiData.img_dish)
        contentValues.put(KEY_NAME, sortedApiData.name) // EmpModelClass Name
        contentValues.put(KEY_SELECTED, sortedApiData.selected ) // EmpModelClass Email

        // Updating Row
        val success = db.update(TABLE_DISH, contentValues,"img_dish="+sortedApiData.img_dish,null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
    //method to delete data
    fun deleteDish(sortedApiData: SortedApiData){
        val db = this.writableDatabase

        // Deleting Row
        db.delete(TABLE_DISH,"$KEY_IMG_DISH = ?", arrayOf(sortedApiData.img_dish))

        db.close() // Closing database connection
    }
}