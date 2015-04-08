//package com.smartx.cookies.smartx.test;
//
//import android.test.ActivityInstrumentationTestCase2;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.smartx.cookies.smartx.R;
//import com.smartx.cookies.smartx.changeInfo;
//import com.smartx.cookies.smartx.myAPI;
//
//import models.User;
//import retrofit.Callback;
//import retrofit.RestAdapter;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//
///**
// * Created by ahmad on 08/04/15.
// */
//public class UnitTest extends ActivityInstrumentationTestCase2<changeInfo> {
//    EditText emailtxt;
//    EditText phonetxt;
//    Button changeInfoB;
//    String email;
//    String phone;
//    String originalPass;
//    int userID;
//    String ENDPOINT ;
//private changeInfo myInfo;
//
//public UnitTest() {
//        super(changeInfo.class);
//        }
//protected void setUp() throws Exception {
//        super.setUp();
//        myInfo = getActivity();
//
//    changeInfoB = (Button) myInfo.findViewById(R.id.changeInfoB);
//    emailtxt = (EditText) myInfo.findViewById(R.id.email);
//    phonetxt = (EditText) myInfo.findViewById(R.id.phone);
//    userID= myInfo.getUserID();
//    originalPass= myInfo.getOriginalPass();
//    ENDPOINT = myInfo.getENDPOINT();
//
//}
//
//    public void testPreconditions() {
//        assertNotNull("myActivity is null", myInfo);
//        }
//    public void testchangeInfoSuccess() throws Exception {
//        myInfo.runOnUiThread(new Runnable() {
//            public void run() {
//                emailtxt.setText("email@test.com");
//                 phonetxt.setText("9565632323");
//                email= emailtxt.getText().toString();
//                phone = phonetxt.getText().toString();
//            }
//        });
//        myInfo.changeInfo(myInfo.getWindow().getDecorView());
//        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
//        myAPI api = adapter.create(myAPI.class);
//        api.changeInfo(userID + "", email, originalPass, phone, new Callback<User>() {
//            @Override
//            public void success(User s, Response response) {
//                assertEquals("email@test.com", s.getEmail());
//
//                assertEquals("9565632323", s.getPhone());
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//    }
//    public void testChangeInfoFailure() throws Exception {
//        myInfo.runOnUiThread(new Runnable() {
//            public void run() {
////                emailtxt.setText("");
//            }
//        });
//        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
//        myAPI api = adapter.create(myAPI.class);
//        api.changeInfo(userID + "",email, originalPass, phone, new Callback<User>() {
//            @Override
//            public void success(User s, Response response) {
//                myInfo.changeInfo(myInfo.getWindow().getDecorView());
//
//                assertEquals("",s.getEmail());
//            }
//
//
//
//            @Override
//            public void failure(RetrofitError error) {
//            }
//        });
//    }
//
//public void testFilter() throws Exception {
//
//
//
//        }
//
//
//}