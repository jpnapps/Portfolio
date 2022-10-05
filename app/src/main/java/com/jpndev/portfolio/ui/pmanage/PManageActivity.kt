package com.jpndev.portfolio.ui.pmanage

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jpndev.portfolio.ui.pmanage.PManageViewModel
import com.jpndev.portfolio.ui.pmanage.PManageViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.jpndev.newsapiclient.presentation.PItemAdapter
import com.jpndev.portfolio.MainActivity
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.databinding.ActivityPmanageBinding
import com.jpndev.portfolio.utils.AESUtils
import com.jpndev.portfolio.utils.JAESUtils
import com.jpndev.portfolio.utils.ToastHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class  PManageActivity : AppCompatActivity() {

    @Inject
    lateinit var  factory: PManageViewModelFactory
    lateinit var viewModel: PManageViewModel
    private lateinit var binding: ActivityPmanageBinding




    @Inject
    lateinit var pitemadapter: PItemAdapter

    var country:String="US"
    var page :Int=1
    var pages:Int=0
    var isLoading =false
    var isScrolling=false
    var isLastPage=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPmanageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this,factory).get(PManageViewModel::class.java)
       // setContentView(R.layout.activity_pmanage)

        binding.viewmodel=viewModel
        pitemadapter.setViewModel(viewModel)
        AESUtils.logsource=  viewModel?.getUseCase()?.logsource

        pitemadapter.setOnItemClickListner {

           /* viewModel.setBioPrompt(this@PManageActivity,{
                val intent = Intent(this@PManageActivity, AddPItemActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("selected_item",it)
                startActivity(intent)


            })*/
            viewModel.setBioAuth(this@PManageActivity,{
                val intent = Intent(this@PManageActivity, AddPItemActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("selected_item",it)
                startActivity(intent)})

        }

        binding.addBtn.setOnClickListener{

            viewModel.showAddPItemActivity(activity = this)


        }

        binding.backupBtn.setOnClickListener{
            viewModel.setBioAuth(this@PManageActivity,{
                configBackup()}

            )





        }
        binding.closeDimv.setOnClickListener{

           // onBackPressed()
            viewModel.showLifeCycleActivity(activity = this)
        }
        initRcv()

        viewNewsList()
    }

    private fun configBackup() {
        val list=pitemadapter.getItems()
        var temp=""
        list?.let{
            for (item in it) {
                temp=temp+item.key1+": "+getValueString(item.value1_encrypted,item.value1,"Data formatted") + "\n"
                temp=temp+item.key2+": "+getValueString(item.value2_encrypted,item.value2,"Data formatted") + "\n"
            }
        }
        var clipboardManager = getSystemService(
            Context.CLIPBOARD_SERVICE) as ClipboardManager?


        var clipData = ClipData.newPlainText("BackUp  ",temp)
        clipboardManager!!.setPrimaryClip(clipData)
        ToastHandler.newInstance(this@PManageActivity).mustShowToast("Copied data")
    }

    private fun getValueString(value1Encrypted: Boolean, value1: String, temp: String): String {
      //   var text=""
         return if(value1Encrypted)  { encryptJp(AESUtils.decrypt(value1) ?: temp) } else value1

    }

    private fun encryptJp(text: String): String {
        var text_enc=text
        text_enc=text.replace("rajpal","hashmsd",true)
        text_enc=text.replace("Rajpal","Hashmsd",true)
        text_enc=text_enc.replace("#","@",true)
        return text_enc

    }

    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }




    private fun viewNewsList() {
        // viewModel.getNewsHeadLines(country,page)
        viewModel.getSavedPItems().observe(this,{
            pitemadapter.differ.submitList(it)
        })

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = pitemadapter.differ.currentList[position]
                viewModel.deletePItem(article)
               /* view?.let {
                    Snackbar.make(it,"Deleted Successfully", Snackbar.LENGTH_LONG)
                        .apply {
                            setAction("Undo"){
                                viewModel.savePItem(article)
                            }
                            show()
                        }
                }*/

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rcv)
        }
    }


    private fun initRcv() {
        //  pitemadapter= NewsAdapter()
        binding.rcv.apply {
            layoutManager = LinearLayoutManager(this@PManageActivity)
            adapter=pitemadapter
            addOnScrollListener(this@PManageActivity.onScrollListner)
        }

    }

    private fun hideProgressBar() {
        isLoading=false
        binding.progressBar.visibility= View.GONE
    }

    private fun showProgressBar() {
        isLoading=true
        binding.progressBar.visibility= View.VISIBLE
    }

    private val onScrollListner = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val lm=binding.rcv.layoutManager as LinearLayoutManager
            val size_current_list=   lm.itemCount
            val visible_items=lm.childCount
            val toppos= lm.findFirstVisibleItemPosition()
            val hasReachedtoEnd=toppos+visible_items>=size_current_list

            if(!isLastPage && hasReachedtoEnd && isScrolling && !isLoading)
            {
                page++
                viewModel.getSavedPItems()
                isScrolling=false
            }
        }

    }

}