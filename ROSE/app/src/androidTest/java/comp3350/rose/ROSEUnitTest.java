package comp3350.rose;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.junit.*;

import comp3350.rose.Business.MyApplication;
import comp3350.rose.Controller.DBInterface;
import comp3350.rose.Presentation.Details;
import comp3350.rose.Presentation.Edit;
import comp3350.rose.Presentation.MenuViewer;
import comp3350.rose.Presentation.SortRecipes;
import comp3350.rose.Presentation.ViewRecipes;
import comp3350.rose.Presentation.init;
import comp3350.rose.model.Recipe;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ROSEUnitTest extends ListActivity {

    private static MyApplication myApp = (MyApplication)new init().getApplication();
    private static DBInterface repository = myApp.getRepository(myApp);
    private static ArrayList<Recipe> temp_recipes = repository.getList();

    private static ListView RecipeListView;
    private static Recipe temp_Recipe;
    private static ListView DetailsListView;

    private static String meals[];

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Set Up Recipe ListView
        ArrayList<String> recDisplay = new ArrayList<>();

        for(int i=0; i<temp_recipes.size(); i++) {
            recDisplay.add(temp_recipes.get(i).getName());
        }

        ArrayAdapter<String> myArrayAdapter;// = new ArrayAdapter<>(new init(), android.R.layout.simple_list_item_1, recDisplay);

        //RecipeListView = new init().getListView();
       // RecipeListView.setAdapter(myArrayAdapter);

        // Set Up Recipe (First Recipe in List)
        temp_Recipe = myApp.getSortedRecipes().get(1);

        // Set Up Details ListView
        ArrayList<String> recDetails = new ArrayList<>();

        recDetails.add(temp_Recipe.getName());
        recDetails.add(temp_Recipe.getDescription());
        recDetails.add("Ingredients:");
        for(int i=0; i<temp_Recipe.getIngredients().size(); i++){
            recDetails.add(temp_Recipe.getIngredients().get(i));
        }
        recDetails.add("Instructions:");
        for(int i=0; i<temp_Recipe.getDirections().size(); i++){
            recDetails.add(temp_Recipe.getDirections().get(i));
        }

        myArrayAdapter = new ArrayAdapter<>(new Details(), android.R.layout.simple_list_item_1, recDetails);
        DetailsListView = new Details().getListView();
        DetailsListView.setAdapter(myArrayAdapter);

        meals = new String[] {"Breakfast", "Lunch", "Supper"};
    }

    @Test
    protected void ViewRecipesTest() {
        ViewRecipes initializer = new ViewRecipes();

        ListView VRListView = initializer.getListView();

        assertEquals("Recipes were not loaded from the stub database to the ListView correctly.", VRListView, RecipeListView);

    }

    @Test
    protected void RecipeTest() {
        String name = temp_Recipe.getName();
        String mealType = temp_Recipe.getMealType();
        String mainIngredient = temp_Recipe.getMainIngredient();
        String description = temp_Recipe.getDescription();
        String notes = temp_Recipe.getNotes();

        int rating = temp_Recipe.getRating();
        int cooktime = temp_Recipe.getCooktime();

        ArrayList<String> ingredients = temp_Recipe.getIngredients();
        ArrayList<String> directions = temp_Recipe.getDirections();

        String temp_string = "";
        int temp_int = 0;
        ArrayList<String> temp_recipeDisplayList = new ArrayList<>();

        assertEquals("Name is not a valid String.", name, temp_string);
        assertEquals("Meal Type is not a valid String.", mealType, temp_string);
        assertEquals("Main Ingredient is not a valid String.", mainIngredient, temp_string);
        assertEquals("Description is not a valid string.", description, temp_string);
        assertEquals("Notes is not a valid String.", notes, temp_string);

        assertEquals("Rating is not a valid integer.", rating, temp_int);
        assertEquals("Cook Time is not a valid integer.", cooktime, temp_int);

        assertEquals("Ingredients is not a valid ArrayList.", ingredients, temp_recipeDisplayList);
        assertEquals("Directions is not a valid ArrayList.", directions, temp_recipeDisplayList);
    }

    @Test
    protected void DetailsTest() {
        Details dets = new Details();

        ListView detListView = dets.getListView();
        int detRecipePos = dets.recipePosition;

        int temp_Pos = 0;

        assertEquals("Clicked Recipe Position is not a valid Recipe Position.", detRecipePos, temp_Pos);
        assertEquals("Recipe Details were not loaded from the Recipe to the ListView correctly.", detListView, DetailsListView);

    }

    @Test
    protected void EditTest() {
        Edit changer = new Edit();

        int recPos = changer.recipePosition;
        int EType = changer.editType;
        int recID = changer.rID;

        Recipe rec2Mod = changer.recipeToModify;

        int changePos = changer.changePosition;
        int ingrSID = changer.ingredSID;
        int instSID = changer.instruSID;

        int temp_int = 0;

        assertEquals("Recipe Position is not a valid Recipe in the Database.", recPos, temp_int);
        assertEquals("Edit operation is not a valid operation.", EType, temp_int);
        assertEquals("Recipe ID is not a valid recipe ID.", recID, temp_int);
        assertEquals("Clicked Recipe is not a valid Recipe.", rec2Mod, temp_Recipe);
        assertEquals("Change Position is not a valid Position.", changePos, temp_int);
        assertEquals("Ingredient ID is not a valid ingredient ID.", ingrSID, temp_int);
        assertEquals("Instruction ID is not a valid instruction ID.", instSID, temp_int);

    }

    @Test
    protected void SLDetailsTest() {

        // No Variables to test

    }

    @Test
    protected void MenuViewerTest() {
        MenuViewer m_view = new MenuViewer();

        ListView MVListView = m_view.getListView();
        String item = m_view.items[0];

        assertEquals("Menu Recipes were not loaded to the ListView Correctly.", MVListView, RecipeListView);
        assertEquals("Not Valid Breakfast String.", item, meals[0]);
        item = m_view.items[1];
        assertEquals("Not Valid Lunch String.", item, meals[1]);
        item = m_view.items[2];
        assertEquals("Not Valid Supper String.", item, meals[2]);

    }

    @Test
    protected void SortRecipesTest() {
        SortRecipes sort = new SortRecipes();

        int mealPos = sort.mealTypePos;
        int sortPos = sort.sortTypePos;
        int ingredPos = sort.mainIngredientPos;

        int temp_int = 0;

        assertEquals("Meal Type Position is not a valid Position.", mealPos, temp_int);
        assertEquals("Sort Type Position is not a valid Position.", sortPos, temp_int);
        assertEquals("Main Ingredient Position is not a valid Position.", ingredPos, temp_int);

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // No Cleanup Necessary
    }

}