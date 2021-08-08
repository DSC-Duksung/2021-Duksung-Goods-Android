package com.example.duksunggoodsplatform_2021_android.category

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.duksunggoodsplatform_2021_android.R
import kotlinx.android.synthetic.main.fragment_all.*

class StationaryFragment : Fragment() {

    lateinit var recyclerAdapter: RecyclerAdapter
    val datas = mutableListOf<Data>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clothes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
    }



    @SuppressLint("NotifyDataSetChanged")
    fun initRecycler() {
        recyclerAdapter = RecyclerAdapter(requireActivity())
        recyclerView.adapter = recyclerAdapter


        datas.apply {
            add(Data(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원"))
            add(Data(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원"))
            add(Data(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원"))
            add(Data(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원"))
            add(Data(photo = R.drawable.towel, name = "덕성여대 버건디 수건", price = "12,000원"))

            recyclerAdapter.datas = datas
            recyclerAdapter.notifyDataSetChanged()

        }
    }
}