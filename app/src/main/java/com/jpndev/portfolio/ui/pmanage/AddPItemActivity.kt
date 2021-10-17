package com.jpndev.portfolio.ui.pmanage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.jpndev.portfolio.utils.JAESUtils
import com.jpndev.portfolio.utils.ToastHandler
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
            it as PItem
            if( it.value1_encrypted)
                it.value1=JAESUtils.decrypt( it.value1)?: "Data formatted"
            if( it.value2_encrypted)
                it.value2=JAESUtils.decrypt( it.value2)?: "Data formatted"
            viewModel.pitem_mld.value=it
            viewModel.action_menu_text="Update"
            viewModel.isUpdate=true
        }?:let{
            viewModel.pitem_mld.value= PItem()
            viewModel.action_menu_text="Save"
            viewModel.isUpdate=false
        }

        //setContentView(R.layout.activity_add_pitem)
        binding.lifecycleOwner=this
        binding.viewmodel=viewModel
     /*   binding.saveBtn.setOnClickListener{
            viewModel.checkExcute()

         //  viewModel.savePItem(PItem(key1=binding.key1Edt.text+"",key2=binding.key2Edt.text+""))
            //nackbar.make( binding.saveBtn,"PItem saved successfully "+viewModel.pitem.value1, Snackbar.LENGTH_LONG).show()
        }*/

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                OkDialog.showDialog(this@AddPItemActivity,MBaseDialog(title = it,close_icon = false)).showsDialog
            //    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
        binding.closeDimv.setOnClickListener{

            onBackPressed()

        }

     /*   binding. copy1Dimv.setOnClickListener {
            var clipboardManager = getSystemService(
                Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val separator = "\n"
            val text =viewModel.pitem_mld.value?.value2
            var clipData = ClipData.newPlainText("  ",text)
            //   clipboardManager!!.primaryClip = clipData
            clipboardManager!!.setPrimaryClip(clipData)
            // jsontest=""
            ToastHandler.newInstance(this@AddPItemActivity).mustShowToast("Copied Json data and cleared")

        }*/
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        finish()
    }

    override fun onDismiss(obj: Any?) {
        onBackPressed()

    }
}