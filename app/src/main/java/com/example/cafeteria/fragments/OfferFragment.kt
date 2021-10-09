package com.example.cafeteria.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafeteria.R
import com.example.cafeteria.adapters.OffersAdapter
import com.example.cafeteria.models.ProductResponse
import com.example.cafeteria.services.ApiClient
import com.example.cafeteria.services.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OfferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OfferFragment : Fragment() {
    lateinit var offerAdapter: OffersAdapter
    private lateinit var offersList:List<ProductResponse>
    lateinit var recyclerView: RecyclerView
    private lateinit var errorLayout: ConstraintLayout
    private lateinit var fragContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragContext = container!!.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorLayout = view.findViewById(R.id.error_layout)
        recyclerView = view.findViewById(R.id.rv_offers)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
    override fun onResume() {
        super.onResume()
        callOffersAPI()
    }
    private fun callOffersAPI(){

        errorLayout.visibility=View.GONE

        val productService: ProductService = ApiClient(fragContext).buildService(ProductService::class.java)
        val requestCall : Call<List<ProductResponse>> = productService.getProductsInOffer()
        requestCall.enqueue(object : Callback<List<ProductResponse>> {
            override fun onResponse(call: Call<List<ProductResponse>>, response: Response<List<ProductResponse>>) {
                if(response.isSuccessful){
                    offersList = response.body()!!
                    offerAdapter = OffersAdapter(fragContext,offersList)
                    recyclerView.adapter=offerAdapter
                    if(offersList.isEmpty()){

                        showErrorLayout("WE ARE SORRY","No available offers right now!")
                    }
                }else{
                    val errorCode:String = when(response.code()){
                        404 -> {
                            "404 not found"
                        }
                        500 -> {
                            "500 server broken"
                        }
                        else ->{
                            "Unknown error!"
                        }
                    }
                    showErrorLayout("ERROR",errorCode)
                }
            }

            override fun onFailure(call: Call<List<ProductResponse>>, t: Throwable) {
                showErrorLayout("Oops...","Network failure, please try again\n $t")
            }

        })
    }
    /**Handle API response errors*/
    private fun showErrorLayout(title:String,message:String){
        if(errorLayout.visibility==View.GONE){
            errorLayout.visibility= View.VISIBLE
        }
        errorLayout.findViewById<TextView>(R.id.tv_error_title).text=title
        errorLayout.findViewById<TextView>(R.id.tv_error_message).text=message
        errorLayout.findViewById<Button>(R.id.btn_error_retry).setOnClickListener {
            callOffersAPI()
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OfferFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OfferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}