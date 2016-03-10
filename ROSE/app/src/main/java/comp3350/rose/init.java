package comp3350.rose;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import comp3350.rose.Controller.DBInterface;
import comp3350.rose.model.Recipe;
import comp3350.rose.Stub.StubDB;
import comp3350.rose.Database.RecipeDatabase;

public class init extends ListActivity {

    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        //The code to change to switch from stub to database
        //DBInterface repository = RecipeDatabase.getInstance(this);
        DBInterface repository = StubDB.getInstance(this);

        ArrayList<Recipe> recipes = repository.getList();
        ArrayList<String> recipeDisplay = new ArrayList<>();
        for(int i=0; i<recipes.size(); i++) {
            recipeDisplay.add(recipes.get(i).getName());
        }

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeDisplay);
        lv = this.getListView();
        lv.setAdapter(myArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
        Log.d("INFO", "Init onResume called");
    }

    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id){
        super.onListItemClick(l, v, pos, id);

        Intent myIntent = new Intent(this, Details.class);
        myIntent.putExtra("recipePosition", pos);
        startActivity(myIntent);
    }

    public void shoppingListButton(View view){
        startActivity(new Intent(this, ShoppingList.class));
    }

    public void addRecipeButton(View view){
        Intent myIntent = new Intent(this, Edit.class);
        myIntent.putExtra("editType", 1); // 1 corresponds to add recipe
        startActivity(myIntent);
    }

    private void refreshList(){
        //DBInterface repository = RecipeDatabase.getInstance(this);
        DBInterface repository = StubDB.getInstance(this);
        ArrayList<Recipe> recipes = repository.getList();
        ArrayList<String> recipeDisplay = new ArrayList<>();
        for(int i=0; i<recipes.size(); i++) {
            recipeDisplay.add(recipes.get(i).getName());
        }

        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipeDisplay);
        lv = this.getListView();
        lv.setAdapter(myArrayAdapter);
    }


}
