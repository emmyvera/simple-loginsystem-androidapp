package com.example.myloginsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

public class RegisterFragment extends Fragment {

    final String TAG = "RegFragment";

    ImageView backBtn;
    EditText usernameEdit, emailEdit, passwordEdit, confirmPasswordEdit, phoneEdit;
    Button regBtn;

    public RegisterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        backBtn = view.findViewById(R.id.backBtn);
        usernameEdit = view.findViewById(R.id.inputUsername);
        emailEdit = view.findViewById(R.id.inputEmail);
        passwordEdit = view.findViewById(R.id.inputPassword);
        confirmPasswordEdit = view.findViewById(R.id.inputCPassword);
        phoneEdit = view.findViewById(R.id.inputPhone);
        regBtn = view.findViewById(R.id.regButton);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setViewPager(0);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
            }
        });
        return view;
    }

    public void performRegistration(){
        String username = usernameEdit.getText().toString();
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String cPassword = confirmPasswordEdit.getText().toString();
        String phone = phoneEdit.getText().toString();

        Call<User> call = MainActivity.apiInterface.performRegistration(username, email, password, cPassword, phone);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.i(TAG, "onResponse: ");
                if (response.body() != null){
                    Log.i(TAG, "onResponse: "+response.toString());
                    MainActivity.prefConfig.displayToast(response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent intent = new Intent(getActivity(), NoInternetActivity.class);
                startActivity(intent);
            }
        });

        usernameEdit.setText("");
        emailEdit.setText("");
        passwordEdit.setText("");
        confirmPasswordEdit.setText("");
        phoneEdit.setText("");
    }

}
