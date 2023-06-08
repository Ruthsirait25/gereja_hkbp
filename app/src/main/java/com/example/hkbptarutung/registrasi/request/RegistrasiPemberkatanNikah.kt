package com.example.hkbptarutung.registrasi.request

import android.widget.EditText
import android.widget.Spinner
import com.example.hkbptarutung.R
import com.example.hkbptarutung.databinding.ActivityRegistrasiPemberkatanNikahBinding
import com.example.hkbptarutung.registrasi.RegistrasiPagePresenter
import com.example.hkbptarutung.utils.FirebaseUtils
import com.example.hkbptarutung.utils.PreferenceUtils
import com.example.hkbptarutung.utils.value

class RegistrasiPemberkatanNikah : BaseRequestPage() {

    val presenter = RegistrasiPagePresenter(this)
    override fun submit() {
        if (PreferenceUtils.isAdmin(this)) {
            docId()?.apply {
                presenter.approveNikah(this)
            }
            return
        }
        presenter.submitNikah(
            nik = findViewById<EditText>(R.id.edt_nik)?.value(),
            fullName = findViewById<EditText>(R.id.edt_nama_jemaat).value(),
            birthDate = findViewById<EditText>(R.id.edt_tanggal_lahir).value(),
            birthPlace = findViewById<EditText>(R.id.edt_tempat_lahir).value(),
            martumpolPlace = findViewById<EditText>(R.id.edt_tempat_martumpol).value(),
            martumpolDate = findViewById<EditText>(R.id.edt_tanggal_martumpol).value(),
            bloodType = findViewById<EditText>(R.id.edt_goldar).value(),
            address = findViewById<EditText>(R.id.edt_alamat).value(),
            phone = findViewById<EditText>(R.id.edt_nomorHP).value(),
        )
    }

    override fun reject() {
        if (PreferenceUtils.isAdmin(this)) {
            docId()?.apply {
                presenter.rejectNikah(this)
            }
            return
        }
    }

    override fun initAdmin() {
        val dbName = FirebaseUtils.getDbByType(this, titleString()) ?: return
        val docId = docId() ?: return
        FirebaseUtils.db().collection(dbName).document(docId).get().addOnSuccessListener {
            uidRequestor = "${it["requestor"]}"
            findViewById<EditText>(R.id.edt_nik).setText("${it["nik"]}")
            findViewById<EditText>(R.id.edt_nama_jemaat).setText("${it["fullName"]}")
            findViewById<EditText>(R.id.edt_tanggal_lahir).setText("${it["birthDate"]}")
            findViewById<EditText>(R.id.edt_tempat_lahir).setText("${it["birthPlace"]}")
            findViewById<EditText>(R.id.edt_goldar).setText("${it["bloodType"]}")
            findViewById<EditText>(R.id.edt_alamat).setText("${it["address"]}")
            findViewById<EditText>(R.id.edt_nomorHP).setText("${it["phone"]}")
            findViewById<EditText>(R.id.edt_tempat_martumpol).setText("${it["martumpol_place"]?:""}")
            findViewById<EditText>(R.id.edt_tanggal_martumpol).setText("${it["martumpol_date"]?:""}")
        }
    }

    override val binding: ActivityRegistrasiPemberkatanNikahBinding
        get() = ActivityRegistrasiPemberkatanNikahBinding.inflate(layoutInflater)
}