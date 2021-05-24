package com.ahzimat.changejar.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahzimat.changejar.model.ImagesDataItem
import com.ahzimat.changejar.repository.MainRepository
import com.ahzimat.changejar.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class DashboardViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {

    private val _res = MutableLiveData<Resource<List<ImagesDataItem>>>()
    val res : LiveData<Resource<List<ImagesDataItem>>>  get() = _res


    fun getImages(pageNumber:Int) = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepository.getImages("${pageNumber}").let {
            if(it.isSuccessful){
                _res.postValue(Resource.success(it.body()))
            }else{
                _res.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun res(lastLoadedPageNumber: Int): LiveData<Resource<List<ImagesDataItem>>> {
        getImages(lastLoadedPageNumber)
        return _res
    }


}