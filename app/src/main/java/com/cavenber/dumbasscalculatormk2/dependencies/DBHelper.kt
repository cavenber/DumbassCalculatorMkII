package com.cavenber.dumbasscalculatormk2.dependencies

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(
    context.applicationContext,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "dumbass_calculator.db"
        private const val DATABASE_VERSION = 4

        const val OLD_TABLE = "AnswerLog"
        const val TABLE_CALCULATION_LOG = "CalculationLog"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE $TABLE_CALCULATION_LOG (
                    _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    program     TEXT    NOT NULL,
                    variables    TEXT    NOT NULL,
                    answerVar   TEXT    NOT NULL,
                    answer      TEXT    NOT NULL
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $OLD_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CALCULATION_LOG")
        onCreate(db)
    }

    fun saveAnswer(
        program: String,
        variables: String,
        answerVar: String,
        answer: String
    ) {
        val db = writableDatabase

        db.beginTransaction()
        try {
            val logValues = ContentValues().apply {
                put("program", program)
                put("variables", variables)
                put("answerVar", answerVar)
                put("answer", answer)
            }

            db.insert(TABLE_CALCULATION_LOG, null, logValues)
            db.setTransactionSuccessful()

        } finally {
            db.endTransaction()
        }
    }

    fun deleteAllAnswer() {
        val db = writableDatabase

        db.beginTransaction()
        try {
            db.delete(TABLE_CALCULATION_LOG, null, null)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getMostRecentAnswer() : String {
        val db = readableDatabase

        db.query(
            TABLE_CALCULATION_LOG,
            null,
            null,
            null,
            null,
            null,
            "_id DESC"
        ).use { cursor ->
            if (cursor.moveToFirst())
                return cursor.getString(cursor.getColumnIndexOrThrow("answer")).toString()
            else return ""
        }
    }

    fun getAllCalculationLogs() : List<CalculationLogEntry> {
        val list = mutableListOf<CalculationLogEntry>()
        val db = readableDatabase

        db.query(
            TABLE_CALCULATION_LOG,
            null,
            null,
            null,
            null,
            null,
            "_id DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                list.add(
                    CalculationLogEntry(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                        program = cursor.getString(cursor.getColumnIndexOrThrow("program")),
                        variables = cursor.getString(cursor.getColumnIndexOrThrow("variables")),
                        answerVar = cursor.getString(cursor.getColumnIndexOrThrow("answerVar")),
                        answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"))
                    )
                )
            }
        }

        return list
    }

    fun getMostRecentCalculationLog() : CalculationLogEntry {
        val db = readableDatabase

        db.query(
            TABLE_CALCULATION_LOG,
            null,
            null,
            null,
            null,
            null,
            "_id DESC"
        ).use { cursor ->
            if (cursor.moveToFirst())
                return CalculationLogEntry(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                    program = cursor.getString(cursor.getColumnIndexOrThrow("program")),
                    variables = cursor.getString(cursor.getColumnIndexOrThrow("variables")),
                    answerVar = cursor.getString(cursor.getColumnIndexOrThrow("answerVar")),
                    answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"))
                )
            else
                return CalculationLogEntry(-1, "", "", "", "")
        }
    }
}