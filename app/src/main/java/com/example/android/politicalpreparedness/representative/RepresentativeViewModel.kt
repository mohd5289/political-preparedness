package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Representative
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RepresentativeViewModel(val api: CivicsApiService): ViewModel() {

    //TODO: Establish live data for representatives and address
    val representatives: MutableLiveData<List<com.example.android.politicalpreparedness.representative.model.Representative>> =   MutableLiveData()
    var state: MutableLiveData<String> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()

    //TODO: Create function to fetch representatives from API from a provided address
   // var client: CivicsApiService = CivicsApi.retrofitService

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

   // private val _representatives = MutableLiveData<List<Representative>>()
   // val representatives: MutableLiveData<List<Representative>>
     //   get() = _representatives

    fun findRepresentatives(address: String) {
        viewModelScope.launch {
            try {
                val (offices, officials) = api.getRepresentativesAsync(address).await()
                representatives.value
                representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }
            } catch (e: HttpException) {
                error.value = e.localizedMessage
            }
        }
    }

    fun toFormattedString(line1: String, line2: String, city: String, state: String, zip: String): String {
        var output = line1.plus("\n")
        if (!line2.isNullOrEmpty()) output = output.plus(line2).plus("\n")
        output = output.plus("$city, $state $zip")
        return output
    }


    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
