package com.example.hkbptarutung.utils

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hkbptarutung.LoginPage
import com.example.hkbptarutung.R
import com.example.hkbptarutung.registrasi.request.RegistrasiBaptis
import com.example.hkbptarutung.registrasi.request.RegistrasiCalonMempelai
import com.example.hkbptarutung.registrasi.request.RegistrasiJemaatBaru
import com.example.hkbptarutung.registrasi.request.RegistrasiPemberkatanNikah
import com.example.hkbptarutung.registrasi.request.RegistrasiPindahHuria
import com.example.hkbptarutung.registrasi.request.RegistrasiSidi


fun EditText.value() = this.text.toString()

fun Context.showToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun Context.showAlert(title: String = "", msg: String, onYes: () -> Unit) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(msg)
        setPositiveButton("Ya") { _, _ ->
            onYes.invoke()
        }
    }.create().show()
}

fun emptyString() = ""

fun Context.showPromptAlert(onSubmit: (value: String) -> Unit) {
    val alert = AlertDialog.Builder(this)

//    alert.setTitle("Title")
    alert.setMessage("Masukkan label yang diinginkan")

// Set an EditText view to get user input

// Set an EditText view to get user input
    val input = EditText(this)
    alert.setView(input)

    alert.setPositiveButton("Ok") { _, _ ->
        val value: String = input.value()
        onSubmit.invoke(value)
    }

    alert.setNegativeButton("Cancel"
    ) { _, _ ->
        // Canceled.
    }

    alert.show()
}

fun AppCompatActivity.sessionExpired() {
    showToast("sesi anda telah habis")
    startActivity(Intent(this, LoginPage::class.java))
    finishAffinity()
}

fun Context.convertActivity(input: String): Class<*> {
    val java : Class<*> = RegistrasiBaptis::class.java
    when(input) {
        getString(R.string.registrasi_sidi) -> {
            return RegistrasiSidi::class.java
        }
        getString(R.string.registrasi_martupol) -> {
            return RegistrasiCalonMempelai::class.java
        }
        getString(R.string.registrasi_pindah_huria) -> {
            return RegistrasiPindahHuria::class.java
        }
        getString(R.string.registrasi_jemaat_baru) -> {
            return RegistrasiJemaatBaru::class.java
        }
        getString(R.string.registrasi_pemberkatan_nikah) -> {
            return RegistrasiPemberkatanNikah::class.java
        }
    }
    return java
}

fun View.visibleGone(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}