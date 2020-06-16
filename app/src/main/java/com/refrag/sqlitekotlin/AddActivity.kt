package com.refrag.sqlitekotlin

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_add.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    companion object{
         val REQUEST_CODE_CAMERA = 1
         val REQUEST_CODE_GALLERY = 2
    }

    private lateinit var dateFormat : SimpleDateFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)
        
        date_picker_toggle_barang.setOnClickListener { showDateDialog() }

        btn_image.setOnClickListener {
            ActivityCompat.requestPermissions(
                this@AddActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_GALLERY
            )
        }

        btn_save!!.setOnClickListener {
            try {
                val myDb = MyDatabaseHelper(this@AddActivity)
                myDb.addBook(
                    et_barangName!!.text.toString().trim(),
                    tv_barangTime!!.text.toString().trim (),
                    et_barangLoc.text.toString().trim(),
                    imageViewToByte(iv_barang!!)
                )
                var intent = Intent()
                setResult(99,intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showDateDialog() {
        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        val newCalendar = Calendar.getInstance()
        /**
         * Initiate DatePicker dialog
         */
        /**
         * Set Calendar untuk menampung tanggal yang dipilih
         */
        /**
         * Update TextView dengan tanggal yang kita pilih
         */
        val datePickerDialog = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                val newDate = Calendar.getInstance()
                newDate[year, monthOfYear] = dayOfMonth
                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tv_barangTime!!.text = dateFormat!!.format(newDate.time)
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
            } else {
                Toast.makeText(
                    getApplicationContext(),
                    "You don't have permission to access file location!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                assert(uri != null)
                val inputStream: InputStream = uri?.let { getContentResolver().openInputStream(it) }!!
                val bitmap = BitmapFactory.decodeStream(inputStream)
                iv_barang!!.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    fun imageViewToByte(image: ImageView): ByteArray? {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}
