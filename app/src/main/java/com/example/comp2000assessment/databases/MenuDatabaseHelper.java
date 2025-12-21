package com.example.comp2000assessment.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.comp2000assessment.menu.RestMenuItem;

import java.util.ArrayList;

public class MenuDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MenuDB";
    private static final String ITEM_TABLE_NAME = "MenuItems";
    private static final String CATEGORY_TABLE_NAME = "Category";
    private static final String LOG_TABLE_NAME = "LogMenu";
    private static final int DATABASE_VER = 5;

    public MenuDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create item category table
        String createCategoryTable = "CREATE TABLE IF NOT EXISTS Category(" +
                "categoryID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "categoryName TEXT NOT NULL" +
                ");";
        db.execSQL(createCategoryTable);

        //inserting the menu category into the category table
        String insertCategories = "INSERT INTO Category(categoryName) " +
                "VALUES ('Starters'), ('Mains'), ('Desserts'), ('Drinks'), ('Sides');";
        db.execSQL(insertCategories);

        //create menu item table
        String createMenuItemsTable = "CREATE TABLE IF NOT EXISTS MenuItems(" +
                "itemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL UNIQUE," +
                "description TEXT NOT NULL UNIQUE," +
                "price REAL NOT NULL," +
                "image BLOB NOT NULL," +
                "categoryID INTEGER NOT NULL," +
                "FOREIGN KEY(categoryID) REFERENCES Category(categoryID)" +
                ");";
        db.execSQL(createMenuItemsTable);

        //create view for starter items
        String createStartersViewSQL = "CREATE VIEW IF NOT EXISTS StartersView " +
                "AS " +
                "SELECT " +
                "m.itemID, " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price, " +
                "m.categoryID " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.CategoryID = c.categoryID " +
                "WHERE c.categoryName = 'Starters' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createStartersViewSQL);

        //create mains view
        String createMainsViewSQL = "CREATE VIEW IF NOT EXISTS MainsView " +
                "AS " +
                "SELECT " +
                "m.itemID, " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price, " +
                "m.categoryID " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Mains' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createMainsViewSQL);

        //desserts view
        String createDessertsViewSQL = "CREATE VIEW IF NOT EXISTS DessertsView " +
                "AS " +
                "SELECT " +
                "m.itemID, " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price, " +
                "m.categoryID " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Desserts' " +
                "ORDER BY image, name, description, price; ";
        db.execSQL(createDessertsViewSQL);

        String createDrinksView = "CREATE VIEW IF NOT EXISTS DrinksView " +
                "AS " +
                "SELECT " +
                "m.itemID, " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price, " +
                "m.categoryID " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Drinks' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createDrinksView);

        String createSidesView = "CREATE VIEW IF NOT EXISTS SidesView " +
                "AS " +
                "SELECT " +
                "m.itemID, " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price, " +
                "m.categoryID " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Sides' " +
                "ORDER BY image, name, description, price; ";
        db.execSQL(createSidesView);

        //create auditTable for when new menu item is added
        String createLogTable = "CREATE TABLE IF NOT EXISTS LogMenu( " +
                "itemID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "log_date DATETIME DEFAULT (datetime('now')) NOT NULL, " +
                "name TEXT NOT NULL UNIQUE," +
                "description TEXT NOT NULL UNIQUE," +
                "price REAL NOT NULL," +
                "image BLOB NOT NULL," +
                "categoryID INTEGER NOT NULL" +
                ");";
        db.execSQL(createLogTable);

        String createTriggerSQL = "CREATE TRIGGER IF NOT EXISTS trg_AddNewItem " +
                "AFTER INSERT ON MenuItems " +
                "BEGIN " +
                "INSERT INTO LogMenu(name, image, description, price, categoryID) " +
                "VALUES (NEW.name, NEW.image, NEW.description, NEW.price, NEW.categoryID);" +
                "END;";
        db.execSQL(createTriggerSQL);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //dropping all dependencies
        // so referential integrity is maintained
//        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + LOG_TABLE_NAME);

        //dropping views
        db.execSQL("DROP VIEW IF EXISTS StartersView");
        db.execSQL("DROP VIEW IF EXISTS MainsView");
        db.execSQL("DROP VIEW IF EXISTS DessertsView");
        db.execSQL("DROP VIEW IF EXISTS DrinksView");
        db.execSQL("DROP VIEW IF EXISTS SidesView");

        //dropping trigger
        db.execSQL("DROP TRIGGER IF EXISTS trg_AddNewItem");

        //recreating all the tables and views
        onCreate(db);
    }


    //CREATE
    //boolean to return result of insertion
    //if returns 1, add new item confirmation screen will be triggered.
    public boolean addItem(RestMenuItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        //get content values
        ContentValues itemValues = new ContentValues();
        itemValues.put("price", item.getPriceAsDouble());
        itemValues.put("name", item.getName());
        itemValues.put("description", item.getDescription());
        itemValues.put("categoryID", item.getCategoryID());
        itemValues.put("image", item.getImageBlob());

        //returning rowID of the newly inserted record
        long insert_result = db.insert(ITEM_TABLE_NAME, null, itemValues);


        //if insert was successful return true (bigger than 0 = true, less than is false, unsuccessful)
        return insert_result > 0;
    }

    //DELETE
    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("MenuItems", "itemID=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    //READ
    public ArrayList<RestMenuItem> showMenuItems(String category) {
//        declare and initiate an array list of type RestMenuItems
//        this will hold all the records of items that the query returns (matching the category given)
        ArrayList<RestMenuItem> menuItems = new ArrayList<>();

        //getting a readable database
        SQLiteDatabase db = this.getReadableDatabase();

//        since menu items are shown in categories, (for which there are views created above)
//        I will select from that view to return menu items
        String viewName;

        //given the category passed in to this method, set the content of viewName
        switch (category) {
            case "starters":
                viewName = "StartersView";
                break;
            case "mains":
                viewName = "MainsView";
                break;
            case "desserts":
                viewName = "DessertsView";
                break;
            case "drinks":
                viewName = "DrinksView";
                break;
            case "sides":
                viewName = "SidesView";
                break;
            default:
                return menuItems;
        }
        //creating the query with the necessary view
        //selecting all from the view as my RestMenuItem constructor requires all fields
        String selectQuery = "SELECT * FROM " + viewName + ";";

        //creating an instance of a cursor in order to traverse through the records in MenuItems table
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                //below I am retrieving all the fields of the RestMenuItem constructor from the record the cursor is pointing at
                //getting itemID from the cursor
                int itemID = cursor.getInt(cursor.getColumnIndexOrThrow("itemID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                int categoryID = cursor.getInt(cursor.getColumnIndexOrThrow("categoryID"));
                byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));

                //creating a new RestMenuItem object and adding it to the array list
                RestMenuItem item = new RestMenuItem(itemID, name, price, categoryID, description, image);
                menuItems.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return menuItems;
    }

    //UPDATE
    public boolean updateItem(RestMenuItem item){
        //getting a writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //retrieving the values to be updated
        ContentValues itemValues = new ContentValues();
        double priceVal = item.getPriceAsDouble();
        itemValues.put("price", priceVal);
        itemValues.put("name", item.getName());
        itemValues.put("description", item.getDescription());
        itemValues.put("categoryID", item.getCategoryID());
        itemValues.put("image", item.getImageBlob());

        //setting the where clause and where args
        String whereClause = "itemID = ?";
        String[] whereArgs = {String.valueOf(item.getItemID())};

        //updating the menu item and closing db
        long updateResult = db.update(ITEM_TABLE_NAME, itemValues, whereClause, whereArgs);

        return updateResult > 0;

    }

    }



