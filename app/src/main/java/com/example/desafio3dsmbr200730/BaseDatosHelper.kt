package com.example.desafio3dsmbr200730

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.File

class BaseDatosHelper(contexto: Context) : SQLiteOpenHelper(contexto, NOMBRE_DB, null, 1) {

    companion object {
        private const val NOMBRE_DB = "tstudent.db"
    }

    init {
        copiarBaseDatosDesdeAssets(contexto)
    }

    private fun copiarBaseDatosDesdeAssets(contexto: Context) {
        val rutaDestino = contexto.getDatabasePath(NOMBRE_DB)
        if (!rutaDestino.exists()) {
            rutaDestino.parentFile?.mkdirs()
            val inputStream = contexto.assets.open(NOMBRE_DB)
            val outputStream = FileOutputStream(rutaDestino)
            val buffer = ByteArray(1024)
            var largo: Int
            while (inputStream.read(buffer).also { largo = it } > 0) {
                outputStream.write(buffer, 0, largo)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // No crear nada aquí porque la base ya viene precargada desde assets
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí podrías gestionar actualizaciones futuras
    }

    fun obtenerValorT(gradosLibertad: Int, alfa: Double): Double? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT valor_t FROM tabla_t_student WHERE grados_libertad = ? AND alfa = ?",
            arrayOf(gradosLibertad.toString(), alfa.toString())
        )
        return if (cursor.moveToFirst()) {
            val valor = cursor.getDouble(0)
            cursor.close()
            valor
        } else {
            cursor.close()
            null
        }
    }

    fun obtenerAlfaDesdeT(gradosLibertad: Int, valorT: Double): Double? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT alfa FROM tabla_t_student WHERE grados_libertad = ? ORDER BY ABS(valor_t - ?) ASC LIMIT 1",
            arrayOf(gradosLibertad.toString(), valorT.toString())
        )
        return if (cursor.moveToFirst()) {
            val alfa = cursor.getDouble(0)
            cursor.close()
            alfa
        } else {
            cursor.close()
            null
        }
    }
}