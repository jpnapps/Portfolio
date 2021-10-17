package com.jpndev.newsapiclient.presentation

import android.view.LayoutInflater
import android.view.View
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
import com.jpndev.portfolio.utils.JAESUtils
import com.jpndev.portfolio.utils.LogUtils


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
     fun getItems(): MutableList<PItem> {
        //  return tvList.size

        return differ.currentList
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item= differ.currentList.get(position)
       holder.bind(item)
    }

     private var viewModel: PManageViewModel? = null


    fun setViewModel( temp: PManageViewModel) {


         viewModel=temp
    }




inner class MyViewHolder(val binding: RcvItemPitemBinding):
RecyclerView.ViewHolder(binding.root){

   fun bind(item: PItem){
        binding.key1Txv.text = item.key1

       binding.key2Ctxv.text = item.key2

       binding.value1Ctxv.text="xxxxxxxxxxxxxxxxxx"

       binding.value2Ctxv.text="xxxxxxxxxxxxxxxxxx"

       binding.showRlay.visibility= View.GONE
       if(( item.value1_encrypted) || (item.value2_encrypted))
       {
           binding.showRlay.visibility= View.VISIBLE
           binding.showRlay.setOnClickListener{OnItemClickListener?.let{
               it(item)
           }}
       }



       if(!item.value1_encrypted)
       {
           viewModel?.getUseCase()?.logsource?.addLog("List v1 before ="+ item.value1)
           try {
               item.value1 = JAESUtils.decrypt(item.value1) ?: "Data formatted"
           }catch(e:Exception) {
               // item.value2 =
               viewModel?.getUseCase()?.logsource?.addLog("List v1 exce ="+ e.message)
           }
           viewModel?.getUseCase()?.logsource?.addLog("List v1 after= "+ item.value1)



           binding.value1Ctxv.text = item.value1
       }

       if(!item.value2_encrypted)
       {
       viewModel?.getUseCase()?.logsource?.addLog("List v2 before ="+ item.value2)
       try {
           item.value2 = JAESUtils.decrypt(item.value2) ?: "Data formatted"
       }catch(e:Exception) {
          // item.value2 =
           viewModel?.getUseCase()?.logsource?.addLog("List v2 exce ="+ e.message)
       }
       viewModel?.getUseCase()?.logsource?.addLog("List  v2after= "+ item.value2)


           binding.value2Ctxv.text = item.value2
       }



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