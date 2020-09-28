package com.example.myloginsystem.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myloginsystem.MainActivity;
import com.example.myloginsystem.NoInternetActivity;
import com.example.myloginsystem.R;
import com.example.myloginsystem.User;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsFragment extends Fragment {

    final String TAG = "DetailFragment";
    TextView usernameText, emailText, phoneText;
    ImageView profilePic, uploadImage;
    Button logoutBtn;
    String imgURL = "";
    Bitmap bitmap;


    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        usernameText = view.findViewById(R.id.nameText);
        emailText = view.findViewById(R.id.emailText);
        phoneText = view.findViewById(R.id.phoneText);
        profilePic = view.findViewById(R.id.profilePics);
        uploadImage = view.findViewById(R.id.uploadPics);
        logoutBtn = view.findViewById(R.id.logoutBtn);

        populateView();

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageURL();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        return view;
    }

    public void Logout(){
        MainActivity.prefConfig.writeLoginStatus(false);
        MainActivity.prefConfig.writeUserId("User");
        MainActivity.prefConfig.writeToken("Token");
        ((MainActivity)getActivity()).setViewPager(0);
    }

    public void populateView(){
        String id = MainActivity.prefConfig.readId();
        String token = MainActivity.prefConfig.readToken();
        Call<User> call = MainActivity.apiInterface.userDetails(id, token);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null){
                    String username = response.body().getUsername();
                    String email = response.body().getEmail();
                    imgURL = response.body().getImage();

                    usernameText.setText(username);
                    emailText.setText(email);
                    HandleImage(imgURL);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Intent intent = new Intent(getActivity(), NoInternetActivity.class);
                startActivity(intent);

            }
        });
    }

    public void HandleImage(String imgURL){

        Picasso.get()
                .load("http://192.168.0.123:5000/img/"+ imgURL)
                .error(R.drawable.ic_round_face_24)
                .into(profilePic);
    }

    public void uploadProfilePics(String path){
        File file = new File(path);
        String id = MainActivity.prefConfig.readId();
        String token = MainActivity.prefConfig.readToken();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        RequestBody image = RequestBody.create(MediaType.parse("text/plain"), "image");

        Call call = MainActivity.apiInterface.uploadPics(token, parts, image);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getActivity(), "Undone", Toast.LENGTH_LONG).show();
            }
        });

        populateView();
    }

    public void getImageURL(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select A Picture"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null){
            Uri path = data.getData();
            Toast.makeText(getActivity(), path.toString(), Toast.LENGTH_LONG).show();

            uploadProfilePics(path.toString());
        }
    }
}
