package com.example.shihao8_foodbook;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.LongAccumulator;


public class AddEditFoodFragment extends DialogFragment {
    /* AddEditFoodFragment class is used to display a fragment for the user to add/view/edit a food entry's info */
    // create a new Fragment object called AddEditFoodFragment by extending DialogFragment class

    // AddEditFoodFragment class attributes
    private EditText foodDescription;
    private EditText foodLocation;
    private EditText foodBestBefore;
    private EditText foodCount;
    private EditText foodUnitCost;
    private OnFragmentInteractionListener listener;
    private DatePickerDialog dialog;
    // on below line dateStr stores the string of user's selected date
    private String dateStr;

    public interface OnFragmentInteractionListener{
        // create the OnFragmentInteractionListener interface that will call in MainActivity onOkPressed(), onEdit() and onDeletePressed() methods
        // and pass a new FoodEntry Object as a parameter inside three methods below
        // on below the three methods will be overridden under MainActivity in order to establish their actual use
        void onOkPressed(FoodEntry newFoodEntry);

        void onEdit(FoodEntry existingFoodEntry, int oldCount, int oldUnitCost, int newCount, int newUnitCost);

        void onDeletePressed(FoodEntry existingFoodEntry);
    }

    public static AddEditFoodFragment newInstance(FoodEntry foodEntry){
        // use this method to create a new Fragment when editing an existing FoodEntry object
        // take in a FoodEntry object and store it in the Fragment's Bundle object
        // Later on in my onCreateDialog() method, I can access the Bundle using getArguments() and retrieve the FoodEntry object there
        Bundle args = new Bundle();
        // store the key-value pair inside the Bundle args
        args.putSerializable("food",foodEntry);
        AddEditFoodFragment newFragment = new AddEditFoodFragment();
        newFragment.setArguments(args);
        return newFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context+ "must implement OnFragmentInteractionListener");
        }
    }

    // override the onCreateDialog() method where we will initialize our AddCityFragment’s editText fields
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_edit_food_fragment_layout, null);
        // override this method to initialize our AddEditFoodFragment's editText fields
        foodDescription = view.findViewById(R.id.food_description_input);
        foodLocation = view.findViewById(R.id.food_location_input);
        foodBestBefore = view.findViewById(R.id.food_best_before_input);
        foodCount = view.findViewById(R.id.food_count_input);
        foodUnitCost = view.findViewById(R.id.food_unit_cost_input);

        // After initializing the View and the EditText fields,
        // create a new AlertDialog object using its builder method
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Bundle bundle = getArguments();

        /* Assistance and conditions for user to input proper data */

        foodDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below check if this editText field is left as empty
                if (foodDescription.getText().toString().isEmpty()){
                    foodDescription.setError("This field cannot be empty!");
                    foodDescription.requestFocus();
                }
            }
        });

        foodLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodLocation.setFocusable(true);
                // on below check if this editText field is left as empty
                if (foodLocation.getText().toString().isEmpty()){
                    foodLocation.setError("This field cannot be empty!");
                    foodLocation.requestFocus();
                }
            }
        });

        foodCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below check if this editText field is left as empty
                if (foodCount.getText().toString().isEmpty()){
                    foodCount.setError("This field cannot be empty!");
                    foodCount.requestFocus();
                } else if (foodCount.getText().toString().equals("0")){
                    // on above check if the input count is a positive integer
                    foodCount.setError("Please enter a positive integer");
                    foodCount.requestFocus();
                }
            }
        });

        foodUnitCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // on below check if this editText field is left as empty
                if (foodUnitCost.getText().toString().isEmpty()){
                    foodUnitCost.setError("This field cannot be empty!");
                    foodUnitCost.requestFocus();
                } else if(Float.parseFloat(foodUnitCost.getText().toString()) <= 0){
                    // on above check if the input unit cost is positive
                    foodUnitCost.setError("Please enter a positive value");
                    foodUnitCost.requestFocus();
                }
            }
        });


        /*DatePicker for food entry's Best Before Date*/
        foodBestBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar myCalendar = Calendar.getInstance();
                int myYear = myCalendar.get(Calendar.YEAR);
                int myMonth = myCalendar.get(Calendar.MONTH);
                int myDay = myCalendar.get(Calendar.DAY_OF_MONTH);
                dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            // ensure the date is shown in the format of yyyy-mm-dd
                        if (monthOfYear < 9 && dayOfMonth < 10){
                            foodBestBefore.setText(year + "-0" + (monthOfYear+1) + "-0" + dayOfMonth);
                            dateStr = year + "-0" + (monthOfYear+1) + "-0" + dayOfMonth;

                        } else if (dayOfMonth < 10){
                            foodBestBefore.setText(year + "-" + (monthOfYear+1) + "-0" + dayOfMonth);
                            dateStr = year + "-" + (monthOfYear+1) + "-0" + dayOfMonth;
                        } else if (monthOfYear < 9){
                            foodBestBefore.setText(year + "-0" + (monthOfYear+1) + "-" + dayOfMonth);
                            dateStr = year + "-0" + (monthOfYear+1) + "-" + dayOfMonth;
                        } else{
                            foodBestBefore.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                            dateStr = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                        }
                    }
                }, myYear, myMonth, myDay);
                dialog.show();
            }
        });

        /*Edit an existing city*/
        //
        if (bundle != null) {
            // this argument Bundle object contains key-value pair(s)
            // from Serializable type then casting to a FoodEntry object for using
            FoodEntry currentFood = (FoodEntry) bundle.getSerializable("food");
            int foodOldCount = Integer.parseInt(currentFood.getFoodCount());
            int foodOldUnitCost = Integer.parseInt(currentFood.getFoodUnitCost());
            // after clicking on a (Food Entry) item on the ListView, initialize the editTxt objects' default str values as their current str values
            foodDescription.setText(currentFood.getFoodName(), TextView.BufferType.EDITABLE);
            foodLocation.setText(currentFood.getFoodLocation().toLowerCase(), TextView.BufferType.EDITABLE);
            foodBestBefore.setText(currentFood.getFoodBestBeforeDate(), TextView.BufferType.EDITABLE);
            dateStr = currentFood.getFoodBestBeforeDate();
            foodCount.setText(currentFood.getFoodCount(), TextView.BufferType.EDITABLE);
            foodUnitCost.setText(currentFood.getFoodUnitCost(), TextView.BufferType.EDITABLE);
            /*click to view or edit an existing food entry*/
            return builder
                    .setView(view)
                    .setTitle("View/Edit Food Entry Info")
                    .setNegativeButton("Cancel", null)

                    // for the Delete Button
                    .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            listener.onDeletePressed(currentFood);
                        }
                    })

                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // on below check if the user enters correct input and fills all editText fields
                            // if not, then the input data will not be proceeded further
                            if (foodDescription.getText().toString().isEmpty()){
                                return;
                            } else if(foodLocation.getText().toString().isEmpty()){
                                return;
                            } else if(!(foodLocation.getText().toString().toLowerCase().equals("pantry") ||
                                    foodLocation.getText().toString().toLowerCase().equals("freezer") ||
                                    foodLocation.getText().toString().toLowerCase().equals("fridge"))){
                                return;
                            } else if(foodCount.getText().toString().isEmpty()){
                                return;
                            } else if (Integer.parseInt(foodCount.getText().toString()) <= 0){
                                return;
                            } else if(foodUnitCost.getText().toString().isEmpty()){
                                return;
                            } else if(Float.parseFloat(foodUnitCost.getText().toString()) <= 0){
                                return;
                            }


                            String name = foodDescription.getText().toString();

                            String location = foodLocation.getText().toString().toLowerCase();

                            String count = foodCount.getText().toString();
                            // on below round up in unit cost after user input
                            String unitCost = foodUnitCost.getText().toString();
                            float unitCostValue = Float.parseFloat(unitCost);
                            unitCost = Integer.toString((int)Math.ceil(unitCostValue));

                            currentFood.setFoodName(name);
                            currentFood.setFoodBestBeforeDate(dateStr);
                            currentFood.setFoodLocation(location);
                            currentFood.setFoodCount(count);
                            currentFood.setFoodUnitCost(unitCost);
                            listener.onEdit(currentFood,foodOldCount,foodOldUnitCost,Integer.parseInt(currentFood.getFoodCount()),Integer.parseInt(currentFood.getFoodUnitCost()));

                        }
                    }).create();
        }

        /*Add a new food entry here*/
        return builder
                .setView(view)
                .setTitle("Add Food Entry")
                .setNegativeButton("Cancel", null)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (foodDescription.getText().toString().isEmpty()){
                            return;
                        }
                        if(foodLocation.getText().toString().isEmpty()){
                            return;
                        } else if(!(foodLocation.getText().toString().toLowerCase().equals("pantry") ||
                                foodLocation.getText().toString().toLowerCase().equals("freezer") ||
                                foodLocation.getText().toString().toLowerCase().equals("fridge"))){
                            return;
                        }
                        if(foodCount.getText().toString().isEmpty()){
                            return;
                        }
                        if (Integer.parseInt(foodCount.getText().toString()) <= 0){
                            return;
                        }
                        if(foodUnitCost.getText().toString().isEmpty()){
                            return;
                        }
                        if(Float.parseFloat(foodUnitCost.getText().toString()) <= 0){
                            return;
                        }

                        String name = foodDescription.getText().toString();

                        String location = foodLocation.getText().toString().toLowerCase();

                        String count = foodCount.getText().toString();

                        // round up in unit cost after user input
                        String unitCost = foodUnitCost.getText().toString();
                        float unitCostValue = Float.parseFloat(unitCost);
                        unitCost = Integer.toString((int)Math.ceil(unitCostValue));

                        listener.onOkPressed(new FoodEntry(name, dateStr, location, count, unitCost));
                    }
                }).create();
    }

}
