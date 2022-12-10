package com.example.shihao8_foodbook;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FoodEntry implements Serializable {
    /* FoodEntry class is used to generate instances of food entries
    * each instance of FoodEntry Class represents an object of food entry
    * the implementation of Serializable interface will be used in newInstance() method of AddEditFoodFragment cass */

    // FoodEntry class attributes
    // these attributes can only be accessed by instance of FoodEntry class
    private String foodName;
    private String foodBestBeforeDate;
    private String foodLocation;
    private String foodCount;
    private String foodUnitCost;

    // FoodEntry class constructor on below
    public FoodEntry(String namePara, String bestBeforeDatePara, String locationPara, String countPara, String unitCostPara){
        /* Para: Parameter */
        this.foodName = namePara;
        this.foodBestBeforeDate = bestBeforeDatePara;
        this.foodLocation = locationPara;
        this.foodCount = countPara;
        this.foodUnitCost = unitCostPara;
    }

    // FoodEntry class attributes' setters and getters on below
    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodBestBeforeDate() {
        return foodBestBeforeDate;
    }

    public void setFoodBestBeforeDate(String foodBestBeforeDate) {
        this.foodBestBeforeDate = foodBestBeforeDate;
    }

    public String getFoodLocation() {
        return foodLocation;
    }

    public void setFoodLocation(String foodLocation) {
        this.foodLocation = foodLocation;
    }

    public String getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(String foodCount) {
        this.foodCount = foodCount;
    }

    public String getFoodUnitCost() {
        return foodUnitCost;
    }

    public void setFoodUnitCost(String foodUnitCost) {
        this.foodUnitCost = foodUnitCost;
    }


}
