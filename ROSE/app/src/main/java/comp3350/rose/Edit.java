package comp3350.rose;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {
    int recipePosition = 0;
    int editType = 0;

    Recipe recipeToModify;
    String name = "";
    String description = "";
    ArrayList<String> ingredients = new ArrayList<String>();
    ArrayList<String> instructions = new ArrayList<String>();

    int changePosition;
    int ingredSID = 0, instruSID = 0;
    String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        int editType = getIntent().getIntExtra("editType", 0);
        //editType == 1  will be add recipe
        //editType == 2  will be modify recipe
        //editType == 3  will be add to shopping list

        if(editType == 1){ // Add Recipe
//            Toast.makeText(getApplicationContext(), "Add recipe not yet implemented!", Toast.LENGTH_LONG).show();

        }
        else if(editType == 2){ //Modify Recipe
            recipePosition = getIntent().getIntExtra("recipePosition", 0);
            Recipe recipeToModify = StubDB.getRecipes().get(recipePosition);

            ArrayList<String> recipeDetails = new ArrayList<>();
            recipeDetails.add(recipeToModify.getName());
            recipeDetails.add(recipeToModify.getDescription());
            recipeDetails.add("Ingredients:");
            for(int i=0; i<recipeToModify.getIngredients().size(); i++){
                recipeDetails.add(recipeToModify.getIngredients().get(i));
            }
            recipeDetails.add("Instructions:");
            for(int i=0; i<recipeToModify.getDirections().size(); i++){
                recipeDetails.add(recipeToModify.getDirections().get(i));
            }

            ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeDetails);
            ListView lv = (ListView) findViewById(R.id.editListField);
            lv.setAdapter(myArrayAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ListView lv = (ListView) findViewById(R.id.editListField);
                    EditText editField = (EditText) findViewById(R.id.editField);
                    changePosition = position;

                    for (int i = 0; i < lv.getAdapter().getCount(); i++) {
                        if (lv.getItemAtPosition(i).toString().contentEquals("Ingredients:")) {
                            ingredSID = i;
                        }
                        if (lv.getItemAtPosition(i).toString().contentEquals("Instructions:")) {
                            instruSID = i;
                        }
                    }

                    if (lv.getItemIdAtPosition(position) != ingredSID &&
                            lv.getItemIdAtPosition(position) != instruSID) {
                        editField.setText(lv.getItemAtPosition(position).toString());
                        input = editField.getText().toString();
                    }
                }
            });
//            Toast.makeText(getApplicationContext(), "Modify recipe not yet implemented!", Toast.LENGTH_LONG).show();

        }else if(editType == 3){ // Add to Shopping List
//            Toast.makeText(getApplicationContext(), "Add to shopping list not yet implemented!", Toast.LENGTH_LONG).show();

        }
    }

    public void saveClick(View view) {
        ListView lv = (ListView) findViewById(R.id.editListField);
        if (changePosition == 0) {
            name = input;
            description = lv.getAdapter().getItem(1).toString();
            for (int i = ingredSID + 1; i < instruSID; i++) {
                ingredients.add(lv.getAdapter().getItem(i).toString());
            }
            for (int i = instruSID + 1; i < lv.getAdapter().getCount(); i++) {
                instructions.add(lv.getAdapter().getItem(i).toString());
            }
        } else if (changePosition == 1) {
            name = lv.getAdapter().getItem(0).toString();
            description = input;
            for (int i = ingredSID + 1; i < instruSID; i++) {
                ingredients.add(lv.getAdapter().getItem(i).toString());
            }
            for (int i = instruSID + 1; i < lv.getAdapter().getCount(); i++) {
                instructions.add(lv.getAdapter().getItem(i).toString());
            }
        } else if (changePosition > ingredSID && changePosition < instruSID) {
            name = lv.getAdapter().getItem(0).toString();
            description = lv.getAdapter().getItem(1).toString();
            for (int i = ingredSID + 1; i < instruSID; i++) {
                if (i == changePosition) {
                    ingredients.add(input);
                } else {
                    ingredients.add(lv.getAdapter().getItem(i).toString());
                }
            }
            for (int i = instruSID + 1; i < lv.getAdapter().getCount(); i++) {
                instructions.add(lv.getAdapter().getItem(i).toString());
            }
        } else if (changePosition > instruSID) {
            name = lv.getAdapter().getItem(0).toString();
            description = lv.getAdapter().getItem(1).toString();
            for (int i = ingredSID + 1; i < instruSID; i++) {
                ingredients.add(lv.getAdapter().getItem(i).toString());
            }
            for (int i = instruSID + 1; i < lv.getAdapter().getCount(); i++) {
                if (i == changePosition) {
                    instructions.add(input);
                } else {
                    instructions.add(lv.getAdapter().getItem(i).toString());
                }
            }
        }
        recipeToModify = new Recipe(name, description, ingredients, instructions);
        // TODO: update to database
        Toast.makeText(getApplicationContext(), "Update to database", Toast.LENGTH_LONG).show();
    }
}