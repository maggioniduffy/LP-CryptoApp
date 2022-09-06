package com.example.offline_crypto.ui.addcoin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.offline_crypto.databinding.FragmentAddcoinBinding
import com.example.offline_crypto.models.Coin
import com.example.offline_crypto.models.PostCoin
import com.example.offline_crypto.network.Api
import com.example.offline_crypto.ui.today.TodayAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddcoinFragment : Fragment() {

    private var _binding: FragmentAddcoinBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var name = "";
    private var price = "";
    private var price_last_week = "";
    private var symbol = "";

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(AddcoinViewModel::class.java)

        _binding = FragmentAddcoinBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding!!.idEdtName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                name = s.toString()
            }
        })

        _binding!!.idEdtPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                price = s.toString()
            }
        })

        _binding!!.idEdtSymbol.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                symbol = s.toString()
            }
        })

        _binding!!.idEdtPriceLastWeek.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                price_last_week = s.toString()
            }
        })

        _binding!!.idBtnPost.setOnClickListener {
            val postCoin: PostCoin = PostCoin(name,symbol,price,price_last_week, null, null);
            addCoin(postCoin);
        }
        return root;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addCoin(coin: PostCoin) {
        Api.retrofitService.addCoin(coin).enqueue(object : Callback<PostCoin> {
            override fun onFailure(call: Call<PostCoin>, t: Throwable) {
                //onResult(null)
            }
            override fun onResponse( call: Call<PostCoin>, response: Response<PostCoin>) {
                name = "";
                price_last_week = "";
                price = "";
                symbol = "";
                _binding?.idEdtPrice?.text?.clear();
                _binding?.idEdtPriceLastWeek?.text?.clear();
                _binding?.idEdtSymbol?.text?.clear();
                _binding?.idEdtName?.text?.clear();
            }
        })
    }
}