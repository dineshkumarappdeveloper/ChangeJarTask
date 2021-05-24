package com.ahzimat.changejar.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ahzimat.changejar.R
import com.ahzimat.changejar.databinding.FragmentDashboardBinding
import com.ahzimat.changejar.utils.PaginationScrollListener
import com.ahzimat.changejar.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
   // lateinit var dashboardViewModel: DashboardViewModel
    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private lateinit var galleryAdapter: GalleryAdapter

    private val binding get() = _binding!!


    private var lastLoadedPageNumber = 1
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        galleryAdapter = GalleryAdapter()

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.recyclerView.adapter = galleryAdapter

        val layoutmanager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recyclerView.layoutManager = layoutmanager

        binding.recyclerView.addOnScrollListener(object: PaginationScrollListener(layoutmanager){
            override fun loadMoreItems() {
                if(!isLoading){loadData(lastLoadedPageNumber)}
            }
        })
        loadData(lastLoadedPageNumber)
        return root
    }

    private fun loadData(lastLoadedPageNumber: Int) {

        dashboardViewModel.res(lastLoadedPageNumber).observe(viewLifecycleOwner, Observer {
            when(it.status){
                    Status.SUCCESS->{
                        this.lastLoadedPageNumber = lastLoadedPageNumber+1
                        it.data.let { imagesData ->
                            //closeProgressBar()
                            isLoading = false
                            if(imagesData.isNullOrEmpty()){
                                if(imagesData == null){
                                    Toast.makeText(requireContext(),"Try again (API limit exceeded)",
                                        Toast.LENGTH_SHORT).show()
                                }else{
                                    isLastPage = true
                                }
                            }else{
                                isLastPage = false
                                try{
                                    galleryAdapter.updateList(imagesData)
                                }catch (ex:Exception){
                                    //galleryAdapter.updateList(imagesData)
                                }

                            }
                        }
                    }
                    Status.ERROR->{
                        Toast.makeText(requireContext(),"Try again (API limit exceeded)", Toast.LENGTH_SHORT).show()
                        isLoading = false
                       // closeProgressBar()

                    }
                    Status.LOADING->{
                       // showLoadingProgressBar(false)
                        isLoading = true
                    }
                }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}