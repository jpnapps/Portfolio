package com.jpndev.newsapiclient.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.jpndev.portfolio.data.model.PItem
import com.jpndev.portfolio.databinding.RcvItemPitemBinding
import com.jpndev.portfolio.ui.pmanage.PManageViewModel
import com.jpndev.portfolio.utils.AESUtils


class PItemAdapter():RecyclerView.Adapter<PItemAdapter.MyViewHolder>() {
/*
    private val tvList = ArrayList<Article>()

    fun setList(tvShows:List<Article>){
         tvList.clear()
         tvList.addAll(tvShows)
    }
*/

    private val callback =object:DiffUtil.ItemCallback<PItem>(){
        override fun areItemsTheSame(oldItem: PItem, newItem: PItem): Boolean {

            return oldItem.key1==newItem.key1
        }

        override fun areContentsTheSame(oldItem: PItem, newItem: PItem): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = RcvItemPitemBinding.inflate( LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
      //  return tvList.size

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= differ.currentList.get(position)
       holder.bind(item)
    }

      var viewModel: PManageViewModel? = null


    fun setViewModels( temp: PManageViewModel) {


         viewModel=temp
    }




inner class MyViewHolder(val binding: RcvItemPitemBinding):
RecyclerView.ViewHolder(binding.root){

   fun bind(item: PItem){
        binding.key1Txv.text = item.key1
        binding.value1Ctxv.text = item.value1
       binding.key2Ctxv.text = item.key2

       viewModel?.getUseCase()?.logsource?.addLog("List before ="+ item.value2)
       try {
           item.value2 = AESUtils.decrypt(item.value2) ?: "Data formatted"
       }catch(e:Exception) {
          // item.value2 =
       }
       viewModel?.getUseCase()?.logsource?.addLog("List  after= "+ item.value2)


       binding.value2Ctxv.text = item.value2
        //val posterURL = "https://image.tmdb.org/t/p/w500"+tvShow.posterPath
      /*  Glide.with(binding.imgDimv.context)
            .load(item.urlToImage)
            .into(binding.imgDimv)
*/
       binding.root.setOnClickListener{OnItemClickListener?.let{
                      it(item)
       }}

   }

}

    private var OnItemClickListener:((PItem)->Unit)?=null

    fun setOnItemClickListner(listner:(PItem)->Unit){

        OnItemClickListener=listner
    }

}