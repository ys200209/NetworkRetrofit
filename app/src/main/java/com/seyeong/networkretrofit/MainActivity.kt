package com.seyeong.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.seyeong.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter() // CustomAdapter 어댑터 선언
        binding.recyclerView.adapter = adapter // 어댑터 연결

        binding.recyclerView.layoutManager = LinearLayoutManager(this) // 리니어 레이아웃 매니저 연결

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build() // Github의 도메인 주소와 JSON 데이터를 앞에서 생성한 Repository 클래스의 컬렉션으로 변환해주는 컨버터를 입력한 뒤 build 호출.

        binding.buttonRequest.setOnClickListener { // 클릭 리스너
            // 정의한 인터페이스를 파라미터로 넘겨주면 실행 가능한 서비스 객체를 생성해서 반환해준다.
            val githubService = retrofit.create(GithubService::class.java)

            githubService.users().enqueue(object: Callback<Repository> { // 비동기 통신으로 데이터를 가져오는 enqueue() 메서드. 호출되면 통신이 시작됨.
                // 콜백 인터페이스의 필수 메서드들을 구현함.
                override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                    adapter.userList = response.body() as Repository
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Repository>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}

interface GithubService { // 레트로핏 인터페이스는 호출 방식, 주소, 데이터 등을 지정한다.
    @GET("users/Kotlin/repos") // Github의 도메인을 제외한 요청 주소 설정.
    fun users(): Call<Repository> // 리턴 타입은 Call<List<데이터 클래스>> 형태로 작성한다.
    // 레트로핏은 이렇게 만들어진 인터페이스에 지정된 방식으로 서버와 통신하고 데이터를 가져온다.
}