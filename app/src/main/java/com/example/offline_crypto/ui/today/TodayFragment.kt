package com.example.offline_crypto.ui.today

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.offline_crypto.databinding.FragmentHomeBinding
import com.example.offline_crypto.isOnline
import com.example.offline_crypto.models.Coin
import com.example.offline_crypto.network.Api
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayFragment() : Fragment() {
    lateinit var data: MutableList<Coin>
    private var _binding: FragmentHomeBinding? = null
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: TodayAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view: View = binding.root
        manager = LinearLayoutManager(view.context)
        getAllData()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAllData() {
        if (isOnline(this.requireContext())) {
            this.onlineRender();
        } else {
            this.offlineRender();
        }
    }

    private fun onlineRender(){
        Api.retrofitService.getAllData().enqueue(object : Callback<List<Coin>> {
            override fun onResponse(
                call: Call<List<Coin>>,
                response: Response<List<Coin>>
            ) {
                if (response.isSuccessful) {
                    binding.recyclerViewCurrent.apply {
                        data = response.body() as MutableList<Coin>
                        checkImage(data);
                        myAdapter = TodayAdapter(data)
                        layoutManager = manager
                        adapter = myAdapter
                        myAdapter.setItems(data)
                        saveOnDatabase()
                    }
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun checkImage(coins: MutableList<Coin>){
        for (coin in coins) {
            if(coin.image == "" || coin.image == null) {
                coin.image = "app/src/main/res/mipmap-xhdpi/default_coin.png";
            }
        }
    }

    private fun saveOnDatabase() {
        for (coin in data){
            db.collection("coins").document(coin.id)
                .set(coin)
                .addOnSuccessListener {
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot successfully written!"
                    )
                }
                .addOnFailureListener { e ->
                    Log.w(
                        ContentValues.TAG,
                        "Error writing document",
                        e
                    )
                }
        }
    }

    private fun offlineRender(){

        val docRef = db.collection("coins")
        val source = Source.CACHE
        docRef.orderBy("ranking").get(source).addOnSuccessListener { documents ->
            if (documents != null) {
                binding.recyclerViewCurrent.apply {
                    val auxlist = getCoinsFromDatabase(documents);
                    data = auxlist as MutableList<Coin>
                    myAdapter = TodayAdapter(data)
                    layoutManager = manager
                    adapter = myAdapter
                    myAdapter.setItems(data)
                }
            }
            else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

    private fun getCoinsFromDatabase(documents : QuerySnapshot): ArrayList<Coin> {
        val auxlist = ArrayList<Coin>();
        for (document in documents){
            val aux = document.data.values
            val prop: Coin = Coin(
                horizontal = false,
                image = aux.elementAt(1) as String,
                symbol = aux.elementAt(2) as String,
                price = aux.elementAt(3) as String,
                price_last_week = aux.elementAt(4) as String,
                name = aux.elementAt(5) as String,
                id = aux.elementAt(7) as String,
                selected = true,
                ranking = aux.elementAt(6) as String
            )
            auxlist.add(prop)
        }
        return auxlist;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}