package com.jpndev.portfolio.ui.pmanage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.beeone.techbank.sign.kyc.sumsub.AddPItemViewModel
import com.beeone.techbank.sign.kyc.sumsub.AddPItemViewModelFactory
import com.beeone.techbank.sign.kyc.sumsub.PManageViewModel
import com.beeone.techbank.sign.kyc.sumsub.PManageViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.databinding.ActivityAddPitemBinding
import com.jpndev.portfolio.databinding.ActivityPmanageBinding
import javax.inject.Inject


class AddPItemActivity : AppCompatActivity() {
    @Inject
    lateinit var  factory: AddPItemViewModelFactory
    lateinit var viewModel: AddPItemViewModel
    private lateinit var binding: ActivityAddPitemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPitemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this,factory).get(AddPItemViewModel::class.java)
        //setContentView(R.layout.activity_add_pitem)

        binding.saveBtn.setOnClickListener{

           var item = PItem()

            viewModel.savePItem(PItem(key1=binding.key1Edt.text+"",key2=binding.key2Edt.text+""))
            Snackbar.make(view,"PItem saved successfully ", Snackbar.LENGTH_LONG).show()
        }
    }
}