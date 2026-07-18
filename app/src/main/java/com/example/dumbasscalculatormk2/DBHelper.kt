package com.example.dumbasscalculatormk2

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
        private const val DATABASE_VERSION = 3

        const val TABLE_ANSWER_LOG = "AnswerLog"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
                CREATE TABLE $TABLE_ANSWER_LOG (
                    _id         INTEGER PRIMARY KEY AUTOINCREMENT,
                    program     TEXT    NOT NULL,
                    equation    TEXT    NOT NULL,
                    answerVar   TEXT    NOT NULL,
                    answer      TEXT    NOT NULL
                )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ANSWER_LOG")
        onCreate(db)
    }

    fun saveAnswer(
        program: String,
        equation: String,
        answerVar: String,
        answer: String
    ) {
        val db = writableDatabase

        db.beginTransaction()
        try {
            val logValues = ContentValues().apply {
                put("program", program)
                put("equation", equation)
                put("answerVar", answerVar)
                put("answer", answer)
            }

            db.insert(TABLE_ANSWER_LOG, null, logValues)
            db.setTransactionSuccessful()

        } finally {
            db.endTransaction()
        }
    }

    fun deleteAllAnswer() {
        val db = writableDatabase

        db.beginTransaction()
        try {
            db.delete(TABLE_ANSWER_LOG, null, null)
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getMostRecentAnswer() : String {
        val db = readableDatabase

        db.query(
            TABLE_ANSWER_LOG,
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

    fun getAllAnswerLogs() : List<AnswerLogEntry> {
        val list = mutableListOf<AnswerLogEntry>()
        val db = readableDatabase

        db.query(
            TABLE_ANSWER_LOG,
            null,
            null,
            null,
            null,
            null,
            "_id DESC"
        ).use { cursor ->
            while (cursor.moveToNext()) {
                list.add(
                    AnswerLogEntry(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                        program = cursor.getString(cursor.getColumnIndexOrThrow("program")),
                        equation = cursor.getString(cursor.getColumnIndexOrThrow("equation")),
                        answerVar = cursor.getString(cursor.getColumnIndexOrThrow("answerVar")),
                        answer = cursor.getString(cursor.getColumnIndexOrThrow("answer"))
                    )
                )
            }
        }

        return list
    }
}