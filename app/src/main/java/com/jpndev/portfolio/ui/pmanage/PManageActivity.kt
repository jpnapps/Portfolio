package com.jpndev.portfolio.ui.pmanage

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
import com.jpndev.portfolio.data.util.Resource
import com.jpndev.portfolio.databinding.ActivityPmanageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PManageActivity : AppCompatActivity() {

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
        pitemadapter.setOnItemClickListner {
          /*  val bundle=Bundle().apply {
                putSerializable("selected_item",it)
            }*/
            val intent = Intent(this@PManageActivity, AddPItemActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("selected_item",it)
            startActivity(intent)

/*            val bundle=Bundle().apply {
                putSerializable("selected_item",it)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment,bundle)*/
        }

        binding.addBtn.setOnClickListener{

            viewModel.showAddPItemActivity(activity = this)

        }
        binding.closeDimv.setOnClickListener{

            onBackPressed()

        }
        initRcv()

        viewNewsList()
    }
    override fun onBackPressed() {
        // super.onBackPressed()
        finish()
    }

    private fun viewObservers() {



     /*   viewModel.mld_Progress.observe(this,{response->
            when(response)
            {
                is Resource.Loading->{
                    setLoading()
                    showProgress()
                }
                is Resource.Success->{
                    hideProgress()

                }
                is Resource.HideLoading->{
                    hideProgress()

                }
                is Resource.ShowAlert->{
                    hideProgress()
                    if(BuildConfig.isShowApi || response.isShow) {
                        response.message?.let {
                            showAlertDelay(message = it, bg_color = response.color)
                        }
                    }
                }
                is Resource.Error->{
                    hideProgress()
                    response.message?.let{
                        showErrorAlertDelay(message = it, message2 = it)
                    }
                }
            }

        })*/

    }

 /*   private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.getSearchedNews(country,p0.toString(),page)
                viewSearchNewsList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchedNews(country,p0.toString(),1)
                    viewSearchNewsList()
                }

                return false
            }

        })
        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                initRcv()
                viewNewsList()
                return false
            }

        })
    }

    private fun viewSearchNewsList() {

        viewModel.searchedNews.observe(viewLifecycleOwner,{response->
            when(response)
            {
                is Resource.Loading->{
                    showProgressBar()
                }
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                        pitemadapter.differ.submitList(it.articles.toList())
                        if(it.totalResults%20==0)
                        {
                            pages=it.totalResults/20
                        }
                        else
                        {
                            pages=it.totalResults/20+1
                        }
                        isLastPage=pages==page
                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    response.message?.let{
                        Toast.makeText(activity,"Error Search: "+it, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })
    }*/




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