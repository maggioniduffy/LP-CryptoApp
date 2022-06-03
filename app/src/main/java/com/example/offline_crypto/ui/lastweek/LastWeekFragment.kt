package com.example.offline_crypto.ui.lastweek

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
import com.example.offline_crypto.databinding.FragmentLastweekBinding
import com.example.offline_crypto.isOnline
import com.example.offline_crypto.models.Property
import com.example.offline_crypto.network.Api
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LastWeekFragment : Fragment() {
    lateinit var data: MutableList<Property>
    private var _binding: FragmentLastweekBinding? = null
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: LastWeekAdapter;
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
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        db.firestoreSettings = settings
        _binding = FragmentLastweekBinding.inflate(inflater, container, false)
        val root: View = binding.recyclerViewCurrent
        manager = LinearLayoutManager(root.context)
        getAllData()
        return root

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getAllData() {
        if (isOnline(this.requireContext())) {
            Api.retrofitService.getAllData().enqueue(object : Callback<List<Property>> {
                override fun onResponse(
                    call: Call<List<Property>>,
                    response: Response<List<Property>>
                ) {
                    if (response.isSuccessful) {
                        binding.recyclerViewCurrent.apply {
                            data = response.body() as MutableList<Property>
                            myAdapter = LastWeekAdapter(data)
                            layoutManager = manager
                            adapter = myAdapter
                            myAdapter.setItems(data)
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
                    }
                }

                override fun onFailure(call: Call<List<Property>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        } else {

            val docRef = db.collection("coins")
            val source = Source.CACHE
            Log.println(Log.ASSERT, "doc", docRef.toString())
            docRef.orderBy("ranking").get(source).addOnSuccessListener { documents ->
                if (documents != null) {
                    binding.recyclerViewCurrent.apply {
                        val auxlist = ArrayList<Property>();
                        for (document in documents){
                            //val aux = document.toObject<Property>()
                            val aux = document.data.values
                            Log.d(TAG, "aux-: ${aux}")
                            val prop: Property = Property(
                                horizontal = false,
                                image = aux.elementAt(1) as String,
                                symbol = aux.elementAt(2) as String,
                                price = aux.elementAt(3) as String,
                                price_last_week = aux.elementAt(4) as String,
                                name = aux.elementAt(5) as String,
                                id = aux.elementAt(7) as String,
                                selected = true,
                                ranking = aux.elementAt(6) as String,
                            )
                            Log.d(TAG, "data-: ${prop}")
                            auxlist.add(prop)
                        }
                        data = auxlist as MutableList<Property>
                        myAdapter = LastWeekAdapter(data)
                        layoutManager = manager
                        adapter = myAdapter
                        myAdapter.setItems(data)
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}