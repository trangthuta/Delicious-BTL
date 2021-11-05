/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2019-2020. All rights reserved.
 * Generated by the CloudDB ObjectType compiler.  DO NOT EDIT!
 */
package com.example.delicious.API.CloudDB;

import com.huawei.agconnect.cloud.database.CloudDBZoneObject;
import com.huawei.agconnect.cloud.database.Text;
import com.huawei.agconnect.cloud.database.annotations.DefaultValue;
import com.huawei.agconnect.cloud.database.annotations.EntireEncrypted;
import com.huawei.agconnect.cloud.database.annotations.NotNull;
import com.huawei.agconnect.cloud.database.annotations.Indexes;
import com.huawei.agconnect.cloud.database.annotations.PrimaryKeys;

import java.util.Date;

/**
 * Definition of ObjectType FoodComponent.
 *
 * @since 2021-07-24
 */
@PrimaryKeys({"idCom"})
public final class FoodComponent extends CloudDBZoneObject {
    public FoodComponent(Class<? extends CloudDBZoneObject> aClass, String idCom, String nameCom, String numberCom, String determineCom, String idF) {
        super(aClass);
        this.idCom = idCom;
        this.nameCom = nameCom;
        this.numberCom = numberCom;
        this.determineCom = determineCom;
        this.idF = idF;
    }

    private String idCom;

    private String nameCom;

    private String numberCom;

    private String determineCom;

    private String idF;

    public FoodComponent() {
        super(FoodComponent.class);
    }

    public void setIdCom(String idCom) {
        this.idCom = idCom;
    }

    public String getIdCom() {
        return idCom;
    }

    public void setNameCom(String nameCom) {
        this.nameCom = nameCom;
    }

    public String getNameCom() {
        return nameCom;
    }

    public void setNumberCom(String numberCom) {
        this.numberCom = numberCom;
    }

    public String getNumberCom() {
        return numberCom;
    }

    public void setDetermineCom(String determineCom) {
        this.determineCom = determineCom;
    }

    public String getDetermineCom() {
        return determineCom;
    }

    public void setIdF(String idF) {
        this.idF = idF;
    }

    public String getIdF() {
        return idF;
    }

}