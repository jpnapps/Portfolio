package com.jpndev.portfolio.ui.pmanage

import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModel
import com.jpndev.portfolio.ui.pmanage.AddPItemViewModelFactory

import com.google.android.material.snackbar.Snackbar
import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.databinding.ActivityAddPitemBinding
import com.jpndev.portfolio.databinding.ActivityPmanageBinding
import com.jpndev.portfolio.ui.alert.MBaseDialog
import com.jpndev.portfolio.ui.alert.OkDialog
import com.jpndev.portfolio.ui.interfaces.OnDismissListner
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddPItemActivity : AppCompatActivity(),OnDismissListner {
    @Inject
    lateinit var  factory: AddPItemViewModelFactory
    lateinit var viewModel: AddPItemViewModel
    private lateinit var binding: ActivityAddPitemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPitemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item= intent.getSerializableExtra("selected_item")

        viewModel= ViewModelProvider(this,factory).get(AddPItemViewModel::class.java)
        item?.let{
            viewModel.pitem= it as PItem
            viewModel.action_menu_text="Update"
            viewModel.isUpdate=true
        }
        //setContentView(R.layout.activity_add_pitem)
        binding.lifecycleOwner=this
        binding.viewmodel=viewModel
        binding.saveBtn.setOnClickListener{
            viewModel.checkExcute()

         //  viewModel.savePItem(PItem(key1=binding.key1Edt.text+"",key2=binding.key2Edt.text+""))
            //nackbar.make( binding.saveBtn,"PItem saved successfully "+viewModel.pitem.value1, Snackbar.LENGTH_LONG).show()
        }

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                OkDialog.showDialog(this@AddPItemActivity,MBaseDialog(title = it,close_icon = false)).showsDialog
            //    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.closeDimv.setOnClickListener{

            onBackPressed()

        }
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        finish()
    }

    override fun onDismiss(obj: Any?) {
        onBackPressed()

    }
}