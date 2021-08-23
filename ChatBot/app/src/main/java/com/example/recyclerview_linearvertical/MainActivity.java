package com.example.recyclerview_linearvertical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcvUser;
    UserAdapter userAdapter;
    ArrayList listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listUser = new ArrayList();
        listUser.add(new User(R.drawable.anh1, "Nguyen Ha Xuan Truong"));
        listUser.add(new User(R.drawable.anh1, "Nguyen Ha Xuan Truong"));
        listUser.add(new User(R.drawable.anh1, "Nguyen Ha Xuan Truong"));
        listUser.add(new User(R.drawable.anh1, "Nguyen Ha Xuan Truong"));
        listUser.add(new User(R.drawable.anh1, "Nguyen Ha Xuan Truong"));

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(MainActivity.this,
                        RecyclerView.VERTICAL,
                        false
                );
        rcvUser = findViewById(R.id.rcvUser);
        rcvUser.setLayoutManager(linearLayoutManager);

        userAdapter = new UserAdapter(MainActivity.this);
        userAdapter.setData(listUser);
        rcvUser.setAdapter(userAdapter);
    }

}