package com.example.myloginsystem.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myloginsystem.MainActivity;
import com.example.myloginsystem.NoInternetActivity;
import com.example.myloginsystem.R;
import com.example.myloginsystem.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    final String TAG = "LogFragment";
    public TextView regText;
    public EditText usernameEdit, passwordEdit;
    public Button loginBtn;



    public LoginFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        regText = view.findViewById(R.id.regText);
        usernameEdit = view.findViewById(R.id.inputUsername);
        passwordEdit = view.findViewById(R.id.inputPassword);
        loginBtn = view.findViewById(R.id.logButton);

        regText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setViewPager(1);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformLogin();
            }
        });



        return view;
    }

    public void PerformLogin(){
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        Call<User> call = MainActivity.apiInterface.performLogin(username,password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null){
                    Log.i(TAG, "onResponse: "+response.body().getId());
                    Log.i(TAG, "onResponse: "+response.body().getToken());
                    MainActivity.prefConfig.displayToast(response.body().getId());
                    MainActivity.prefConfig.displayToast(response.body().getToken());

                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeUserId(response.body().getId());
                    MainActivity.prefConfig.writeToken(response.body().getToken());
                    ((MainActivity)getActivity()).setViewPager(2);

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent intent = new Intent(getActivity(), NoInternetActivity.class);
                startActivity(intent);
            }
        });
    }

}
