//package com.smartx.cookies.smartx;
//
///**
// * Created by zamzamy on 3/11/15.
// */
//public class ServiceGenerator {
//
//    public ServiceGenerator(){
//
//    }
//
//    public static <S> S createService(Class<S> ServiceClass, String baseUrl){
//
//        RestAdapter builder = new RestAdapter.Builder()
//                .setEndpoint(baseUrl)
//                .setClient(new OkClient(new OkHttpClient()));
//
//        RestAdapter adapter = builder.build();
//
//        return adapter.create(serviceClass);
//
//    }
//}
