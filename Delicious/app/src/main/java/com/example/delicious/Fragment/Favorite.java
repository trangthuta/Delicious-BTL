package com.example.delicious.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.delicious.API.CloudDB.DishFavorite;
import com.example.delicious.API.CloudDB.FoodInfo;
import com.example.delicious.Adapter.ItemDishAdapter;
import com.example.delicious.MainActivity;
import com.example.delicious.R;
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

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class Favorite extends Fragment {

   RecyclerView favoriteDish ;
   View rootView;
   ItemDishAdapter itemDishAdapter = new ItemDishAdapter(getActivity());


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        AnhXa();
        checkLiked(MainActivity.idAcc);
        return  rootView;

    }
    public void AnhXa() {
        favoriteDish = rootView.findViewById(R.id.favoriteDish) ;
        itemDishAdapter = new ItemDishAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favoriteDish.setLayoutManager(linearLayoutManager);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        favoriteDish.setLayoutManager(linearLayoutManager);

    }
    public void checkLiked(String idAcc)
    {
        Task<CloudDBZoneSnapshot<DishFavorite>> queryTask =  mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(DishFavorite.class).equalTo("idAcc", idAcc),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY
        );
        queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<DishFavorite>>() {
            @Override
            public void onSuccess(CloudDBZoneSnapshot<DishFavorite> snapshot) {
                CloudDBZoneObjectList<DishFavorite> dbZoneObjectList = snapshot.getSnapshotObjects();
                ArrayList<DishFavorite> dishFavorites = new ArrayList<>();
                try {
                    while (dbZoneObjectList.hasNext()) {
                        DishFavorite dishFavorite = dbZoneObjectList.next();
                        dishFavorites.add(dishFavorite);
                    }

                } catch (AGConnectCloudDBException e) {
                    Log.w(TAG, "processQueryResult: " + e.getMessage());
                } finally {
                    snapshot.release();
                }
                getFoodInfo(dishFavorites);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("err", e.getMessage());
            }
        });
    }
    public void getFoodInfo(ArrayList<DishFavorite> dishFavorites) {
        for(DishFavorite dishFavorite: dishFavorites) {
            Task<CloudDBZoneSnapshot<FoodInfo>> queryTask =  mCloudDBZone.executeQuery(
                    CloudDBZoneQuery.where(FoodInfo.class).equalTo("idF", dishFavorite.getIdF()),
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

                    itemDishAdapter.setData(foodInfos);
                    favoriteDish.setAdapter(itemDishAdapter);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    Log.e("err", e.getMessage());
                }
            });
        }

    }
}