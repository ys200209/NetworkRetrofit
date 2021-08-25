package com.seyeong.networkretrofit

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyGlideApp: AppGlideModule() // 가상의 클래스를 하나 만들고 @GlideModule 애노테이션을 사용하는 코드를 추가해야 Glide가 정상적으로 동작한다.