package com.example.duksunggoodsplatform_2021_android.category

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.duksunggoodsplatform_2021_android.R
import com.example.duksunggoodsplatform_2021_android.api.ApiRetrofitClient
import com.example.duksunggoodsplatform_2021_android.api.MyApplication
import com.example.duksunggoodsplatform_2021_android.category.model.ModelCategoryItemListData
import com.example.duksunggoodsplatform_2021_android.goodsEx.ModelItemLikesChangeData
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


// fragment_all, clothes, stationary, etc.xml와 category_item.xml을 이어주는 역할

//class RecyclerAdapter(private val items: ArrayList<Data>) :
//
//    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
//
//    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
//
//        private var view: View = v
//        fun bind(listener: View.OnClickListener, item: Data) {
//            view.imageView.setImageDrawable(item.photo)
//            view.name.text = item.name
//            view.price.text = item.price.toString()
//        }
//
//    }
//
//    override fun getItemCount() = items.size
//
//    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
//
//        val item = items[position]
//
//        val listener = View.OnClickListener { it ->
//            Toast.makeText(it.context, "Clicked:" + item.name, Toast.LENGTH_SHORT).show()
//        }
//        holder.apply {
//            bind(listener, item)
//            itemView.tag = item
//        }
////
////        holder.image.setImageResource(R.drawable.star_clicked)
////        holder.name.setText("타이틀입니다")
////        holder.price.setText("세부사항 입니다")
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
//            RecyclerAdapter.ViewHolder {
//        var inflaterView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.category_item, parent, false)
//
//        return RecyclerAdapter.ViewHolder(inflaterView)
//    }
//
//
//}



//새로운 코드

class RecyclerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var datas = mutableListOf<CategoryItemData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtName: TextView = itemView.findViewById(R.id.name)
        private val txtPrice: TextView = itemView.findViewById(R.id.price)
        private val imgProfile: ImageView = itemView.findViewById(R.id.imageView)
        private val interestedbutton: Button = itemView.findViewById(R.id.starbutton)
        private val progressbar: ProgressBar = itemView.findViewById(R.id.progressBar)
        private val progressCount: TextView = itemView.findViewById(R.id.progressCount)
        private val progressPercentage: TextView = itemView.findViewById(R.id.progressPertantage)
        private val remainingTime: TextView = itemView.findViewById(R.id.remainingTime)


        fun bind(item: CategoryItemData) {

            txtName.text = item.name
            val formatter = DecimalFormat("###,###")
            val priceFormatted = formatter.format(item.price)
            txtPrice.text = priceFormatted.plus("원")

            Glide.with(itemView).load(item.photo).into(imgProfile)

            if(item.like) {
                interestedbutton.setBackgroundResource(R.drawable.star_clicked)
            }

            // 별 버튼 클릭시
            interestedbutton.setOnClickListener{
                //postItemLike(item.id)

                //별 클릭 시
                if(item.like) {
                    Toast.makeText(MyApplication.applicationContext(), "관심목록에 존재하는 상품입니다.", Toast.LENGTH_SHORT).show()
//                    interestedbutton.setBackgroundResource(R.drawable.star_nonclicked)
//                    Toast.makeText(MyApplication.applicationContext(), "관심을 취소합니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(MyApplication.applicationContext(), "관심목록에 존재 하지 않는 상품입니다", Toast.LENGTH_SHORT).show()
//                    interestedbutton.setBackgroundResource(R.drawable.star_clicked)
//                    Toast.makeText(MyApplication.applicationContext(), "관심목록에 추가입니다", Toast.LENGTH_SHORT).show()
                }

            }

            // Progressbar
            val progressNum = ( item.currentNum.toDouble() / item.entireNum.toDouble() ) * 100
            progressbar.setProgress(progressNum.toInt())
            progressPercentage.text = progressNum.toInt().toString() + "%"

            // 수요조사 진행 개수
            progressCount.text = item.currentNum.toString() + "/" + item.entireNum.toString() + "개"

            // Remaining Time
            remainingTime.text = item.remainingDate.toString()+"일 남음"


        }


    }

    val apiService = ApiRetrofitClient.apiService
    fun postItemLike(itemId: Int) {
        val responseData = MutableLiveData <ModelItemLikesChangeData>()

        apiService.postItemLikesChange(itemId = itemId)
            .enqueue(object : retrofit2.Callback<ModelItemLikesChangeData> {
                override fun onResponse(
                    call: Call<ModelItemLikesChangeData>,
                    response: Response<ModelItemLikesChangeData>
                ) {
                    responseData.value = response.body()

                    val status = responseData.value?.status
                    if(status == "OK"){
                        val data = responseData.value?.data

                        if(data != null){
                            Log.d("jh", "like data response : ${data}")
                        }
                    }
                }

                override fun onFailure(call: Call<ModelItemLikesChangeData>, t: Throwable) {
                    Log.e("jh", "getCategoryItem fail..")
                }
        })
    }



}