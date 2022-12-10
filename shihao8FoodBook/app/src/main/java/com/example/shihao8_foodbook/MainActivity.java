package com.example.shihao8_foodbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddEditFoodFragment.OnFragmentInteractionListener{

    // MainActivity attributes
    // on below line foodEntryList displays the food entries on the main page
    private ListView foodEntryList;
    // on below line foodEntryAdapter updates data changes
    private ArrayAdapter<FoodEntry> foodEntryAdapter;
    // on below line foodEntryDataList holds food entries
    private ArrayList<FoodEntry> foodEntryDataList = new ArrayList<>();
    // on below line totalCost holds the total cost of all food entries after calculation
    private int totalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodEntryList = findViewById(R.id.food_list);
        foodEntryAdapter = new CustomList(this, foodEntryDataList);
        foodEntryList.setAdapter(foodEntryAdapter);

        final FloatingActionButton addCityButton = findViewById(R.id.add_food_button);
        addCityButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AddEditFoodFragment().show(getSupportFragmentManager(), "ADD_FOOD_ENTRY");
                    }
                });
        foodEntryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // grab the clicked item out of the ListView
                Object clickedItem = foodEntryList.getItemAtPosition(i);
                // casting this clicked item to FoodEntry type from Object type
                FoodEntry clickedFood = (FoodEntry) clickedItem;
                // use it as newInstance argument to create its associated AddEditFoodFragment object
                AddEditFoodFragment foodFragment = AddEditFoodFragment.newInstance(clickedFood);
                // use Fragment Transaction
                getSupportFragmentManager().beginTransaction()
                        .add(foodFragment,null)
                        .commit();
            }
        });
    }

    @Override
    public void onOkPressed(FoodEntry newFoodEntry) {
        /* onOkPressed() will be called and executed after the user add food entry */
        // on below foodEntryAdapter will add the new food entry in foodEntryDataList
        foodEntryAdapter.add(newFoodEntry);
        totalCost += Integer.parseInt(newFoodEntry.getFoodCount()) * Integer.parseInt(newFoodEntry.getFoodUnitCost());
        // on below the textView will update and display the new total cost after taking in the food entry
        TextView totalCostView = (TextView) findViewById(R.id.total_cost_displayer);
        totalCostView.setText("Total Cost: $" + totalCost);
    }

    @Override
    public void onEdit(FoodEntry existingFoodEntry, int oldCount, int oldUnitCost, int newCount, int newUnitCost) {
        /* onEdit() will be called and executed after the user edit food entry */
        // on below foodEntryAdapter will add the updated food entry with data changes in foodEntryDataList
        // on below line totalCost will abandon the cost of the food entry before changing its data
        totalCost -= oldCount * oldUnitCost;
        // on below line totalCost will add in the cost of the food entry before updating its data
        totalCost += newCount * newUnitCost;
        // on below line the textView will update and display the new total cost after updating the food entry's data
        TextView totalCostView = (TextView) findViewById(R.id.total_cost_displayer);
        totalCostView.setText("Total Cost: $" + totalCost);
        foodEntryAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDeletePressed(FoodEntry existingFoodEntry) {
        /* onDeletePressed() will be called and executed after the user edit food entry */
        // on below foodEntryAdapter will delete the selected food entry with its data
        foodEntryAdapter.remove(existingFoodEntry);
        // on below line totalCost will abandon the cost of the food entry before changing its data, then display the new total cost
        totalCost -= Integer.parseInt(existingFoodEntry.getFoodCount()) * Integer.parseInt(existingFoodEntry.getFoodUnitCost());
        TextView totalCostView = (TextView) findViewById(R.id.total_cost_displayer);
        totalCostView.setText("Total Cost: $" + totalCost);
    }
}