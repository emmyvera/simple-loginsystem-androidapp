package com.example.myloginsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myloginsystem.Config.PrefConfig;
import com.example.myloginsystem.Fragment.DetailsFragment;
import com.example.myloginsystem.Fragment.LoginFragment;
import com.example.myloginsystem.Fragment.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;
    private SectionStatePagerAdapter sectionStatePagerAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        viewPager = findViewById(R.id.viewContainer);

        if(findViewById(R.id.viewContainer) != null){

            if (savedInstanceState != null){
                return;
            }

            if (prefConfig.readLoginStatus()){
                setViewPager(2);
            }else {
                setUpViewPager(viewPager);
            }
        }
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionStatePagerAdapter adapter = new SectionStatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new RegisterFragment(), "Register");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }


}