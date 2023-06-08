package com.example.hkbptarutung.registrasi.request

import android.widget.EditText
import android.widget.Spinner
import androidx.viewbinding.ViewBinding
import com.example.hkbptarutung.R
import com.example.hkbptarutung.databinding.ActivityRegistrasiPageBinding
import com.example.hkbptarutung.registrasi.RegistrasiPagePresenter
import com.example.hkbptarutung.utils.FirebaseUtils
import com.example.hkbptarutung.utils.PreferenceUtils
import com.example.hkbptarutung.utils.showAlert
import com.example.hkbptarutung.utils.showToast
import com.example.hkbptarutung.utils.value

class RegistrasiSidi : BaseRequestPage() {

    var presenter = RegistrasiPagePresenter(this)

    override fun submit() {
        if (PreferenceUtils.isAdmin(this)) {
            docId()?.apply {
                presenter.approveSidi(this)
            }
            return
        }
        presenter.submitSidi(
            nik = findViewById<EditText>(R.id.edt_nik)?.value(),
            fullName = findViewById<EditText>(R.id.edt_nama_Lengkap)?.value(),
            dadName = findViewById<EditText>(R.id.edt_nama_ayah)?.value(),
            momName = findViewById<EditText>(R.id.edt_nama_ibu)?.value(),
            birthPlace = findViewById<EditText>(R.id.edt_Tempat_Lahir)?.value(),
            birthDate = findViewById<EditText>(R.id.edt_tanggal_lahir)?.value(),
            gender = findViewById<Spinner>(R.id.sp_jekel).selectedItem.toString(),
            baptisDate = findViewById<EditText>(R.id.edt_tanggal_baptis).value(),
            sidiDate = findViewById<EditText>(R.id.edt_tanggal_sidi).value(),
        )
    }

    override fun reject() {
        if (PreferenceUtils.isAdmin(this)) {
            docId()?.apply {
                presenter.rejectSidi(this)
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
            findViewById<EditText>(R.id.edt_nama_Lengkap).setText("${it["fullName"]}")
            findViewById<EditText>(R.id.edt_nama_ayah).setText("${it["dad"]}")
            findViewById<EditText>(R.id.edt_nama_ibu).setText("${it["mom"]}")
            findViewById<EditText>(R.id.edt_Tempat_Lahir).setText("${it["birthPlace"]}")
            findViewById<EditText>(R.id.edt_tanggal_lahir).setText("${it["birthDate"]}")
            findViewById<EditText>(R.id.edt_tanggal_baptis).setText("${it["baptisDate"]}")
            findViewById<EditText>(R.id.edt_tanggal_sidi).setText("${it["sidiDate"]}")
            val gender = if (it["gender"] == getString(R.string.female)) 1 else 0
            findViewById<Spinner>(R.id.sp_jekel).setSelection(gender)
        }
    }

    override val binding: ViewBinding
        get() = ActivityRegistrasiPageBinding.inflate(layoutInflater)
}