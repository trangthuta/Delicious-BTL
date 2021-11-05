package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delicious.API.CloudDB.DishFavorite;
import com.example.delicious.API.CloudDB.FoodInfo;
import com.example.delicious.Adapter.ItemDishAdapter;
import com.huawei.agconnect.cloud.database.CloudDBZoneObjectList;
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery;
import com.huawei.agconnect.cloud.database.CloudDBZoneSnapshot;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.delicious.API.CloudDB.CloudDBZoneWrapper.mCloudDBZone;

public class ListDish extends AppCompatActivity {
    String nameDish = "";
    ItemDishAdapter itemListDishAdapter;
    RecyclerView listDish ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dish);
        Intent intent = getIntent();
        nameDish = intent.getStringExtra("dish");
        Toast.makeText(this, ""+intent.getStringExtra("dish"), Toast.LENGTH_SHORT).show();
        AnhXa();
        getFoodOnType(nameDish);


    }
    public  void AnhXa() {
        listDish = findViewById(R.id.listDishOnType);
        itemListDishAdapter = new ItemDishAdapter(ListDish.this);
        listDish.setLayoutManager(new LinearLayoutManager(ListDish.this, LinearLayoutManager.VERTICAL, false));

    }
    public void getFoodOnType(String typeF)
    {
        Task<CloudDBZoneSnapshot<FoodInfo>> queryTask =  mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(FoodInfo.class).equalTo("typeF", typeF),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY
        );
        queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<FoodInfo>>() {

            @Override
            public void onSuccess(CloudDBZoneSnapshot<FoodInfo> snapshot) {
                CloudDBZoneObjectList<FoodInfo> dbZoneObjectList = snapshot.getSnapshotObjects();
                ArrayList<FoodInfo> foodInfos = new ArrayList<>();
                try {
                    while (dbZoneObjectList.hasNext()) {
                        FoodInfo foodInfo = dbZoneObjectList.next();
                        foodInfos.add(foodInfo);
                    }
                    //Toast.makeText(ListDish.this ,foodInfos.size(), Toast.LENGTH_SHORT).show();
//                    public void onSuccess (Object e){
//                        Toast.makeText(ListDish.this, , Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.LENGTH_LONG

                } catch (AGConnectCloudDBException e) {
                    Log.w(TAG, "processQueryResult: " + e.getMessage());
                } finally {
                    snapshot.release();
                }
//                getFoodInfo(foodInfos);
                itemListDishAdapter.setData(foodInfos);
                listDish.setAdapter(itemListDishAdapter);

            }
        }); queryTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(Exception e) {
            Log.e("err", e.getMessage());
        }
    });
    }





    //AnhXa

    //getList

    public void getList(ArrayList<FoodInfo> list) {
        for(FoodInfo lists: list ) {
            Task<CloudDBZoneSnapshot<FoodInfo>> queryTask =  mCloudDBZone.executeQuery(
                    CloudDBZoneQuery.where(FoodInfo.class).equalTo("typeF", lists.getTypeF()),
                    CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY
            );
            queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<FoodInfo>>() {
                @Override
                public void onSuccess(CloudDBZoneSnapshot<FoodInfo> snapshot) {
                    CloudDBZoneObjectList<FoodInfo> dbZoneObjectList = snapshot.getSnapshotObjects();

                    ArrayList<FoodInfo> foodInfos = new ArrayList<>();

                    try {
                        while (dbZoneObjectList.hasNext()) {
                            FoodInfo foodInfo = dbZoneObjectList.next();
                            foodInfos.add(foodInfo);
                        }

                    } catch (AGConnectCloudDBException e) {
                        Log.w(TAG, "processQueryResult: " + e.getMessage());
                    } finally {
                        snapshot.release();
                    }

                    itemListDishAdapter.setData(foodInfos);
                    listDish.setAdapter(itemListDishAdapter);
                }
            });
            queryTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e("err", e.getMessage());
                }
            });
        }

    }
}