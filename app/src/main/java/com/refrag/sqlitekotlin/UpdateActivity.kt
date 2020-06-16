package com.refrag.sqlitekotlin

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import kotlinx.android.synthetic.main.activity_update.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class UpdateActivity : AppCompatActivity() {
    private lateinit var dateFormat: SimpleDateFormat

    private var title: String = ""
    private lateinit var times: String
    private lateinit var location: String
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)

        getAndSetIntentData()

        val ab = supportActionBar
        ab?.setTitle(title)

        date_picker_toggle_barang.setOnClickListener { showDateDialog() }
        btn_update.setOnClickListener {
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            title = et_barangName_update.getText().toString().trim { it <= ' ' }
            times = tv_barangTime_update.getText().toString().trim { it <= ' ' }
            location = et_barangLoc_update.getText().toString().trim({ it <= ' ' })
            myDB.updateData(id, title, times, location, imageViewToByte(imageView))

            var intent = Intent()
            setResult(1,intent)
            finish()
        }
        btn_image.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, AddActivity.REQUEST_CODE_GALLERY)
        }
        btn_delete.setOnClickListener { confirmDialog() }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == AddActivity.REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val uri = data.data
            try {
                assert(uri != null)
                val inputStream: InputStream = uri?.let { getContentResolver().openInputStream(it) }!!
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imageView.setImageBitmap(bitmap)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun bytetoBitmap(data: ByteArray): Bitmap? {
        return BitmapFactory.decodeByteArray(
            data, 0,
            data.size
        )
    }

    fun getAndSetIntentData() {
        if (intent.hasExtra("id") && intent.hasExtra("title") &&
            intent.hasExtra("time") && intent.hasExtra("location")
        ) {
            id = intent.getStringExtra("id")
            title = intent.getStringExtra("title")
            times = intent.getStringExtra("time")
            location = intent.getStringExtra("location")
            imageView.setImageBitmap(MyDatabaseHelper(this).fetchAnImage(id))
            et_barangName_update!!.setText(title)
            tv_barangTime_update.setText(times)
            et_barangLoc_update.setText(location)
        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show()
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
                tv_barangTime_update!!.text = dateFormat!!.format(newDate.time)
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

    fun confirmDialog() {
        val builder =
            AlertDialog.Builder(this)
        builder.setTitle("Delete $title ?")
        builder.setMessage("Are you sure you want to delete $title ?")
        builder.setPositiveButton(
            "Yes"
        ) { dialogInterface, i ->
            val myDB = MyDatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRow(id)
            finish()
        }
        builder.setNegativeButton(
            "No"
        ) { dialogInterface, i -> }
        builder.create().show()
    }

    fun imageViewToByte(image: ImageView): ByteArray? {
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

}