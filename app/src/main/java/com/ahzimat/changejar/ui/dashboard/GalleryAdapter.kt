package com.ahzimat.changejar.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.ahzimat.changejar.R
import com.ahzimat.changejar.model.ImagesDataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import javax.inject.Inject

class GalleryAdapter @Inject constructor() : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
    private var categoryList = mutableListOf<ImagesDataItem>()
    private var mClickListener: CategorySelectedListener? = null
    private var selectedPosition = -1
    private lateinit var context: Context

    init {
        setHasStableIds(true)
    }

    fun setOnCategoryCLickListener(mClickListener:CategorySelectedListener){
        this.mClickListener=mClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_images, parent, false)
        val myViewHolder=ViewHolder(view)
        return myViewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData=categoryList.get(position)



        Glide
            .with(context)
            .load(currentData.urls.small)
            //.centerCrop() // or other transformation: fitCenter(), circleCrop(), etc
            .thumbnail(Glide.with(context).load(currentData.urls.thumb))
           // .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(android.R.drawable.arrow_up_float)
            .transform(RoundedCorners(8))
            .error(android.R.drawable.ic_delete)
            .into(holder.imageView)


        holder.imageView.setOnClickListener {
            Toast.makeText(context,currentData.id,Toast.LENGTH_SHORT).show()
        }

    }



    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView = itemView.findViewById<AppCompatImageView>(R.id.imageView)


    }

    interface CategorySelectedListener {
        fun onCategorySelected(categoryItem: ImagesDataItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    companion object {
        private val TAG = "FaqActivityAda"
    }


    fun updateList(data: List<ImagesDataItem>) {
        if(this.categoryList.size > 0){
            var size = this.categoryList.size
            data.forEach {  this.categoryList.add(it)  }
            notifyItemRangeChanged(size, this.categoryList.size)
        }else{
            this.categoryList=data as MutableList<ImagesDataItem>
            selectedPosition = -1
            notifyDataSetChanged()
        }


    }

}
