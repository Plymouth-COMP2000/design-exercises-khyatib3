package com.example.comp2000assessment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.MenuItem;

import androidx.annotation.Nullable;

public class MenuDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MenuDB";
    private static final String ITEM_TABLE_NAME = "MenuItems";
    private static final String CATEGORY_TABLE_NAME = "Category";
    private static final String LOG_TABLE_NAME = "LogMenu";
    private static final int DATABASE_VER = 1;

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
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.CategoryID = c.categoryID " +
                "WHERE c.categoryName = 'Starters' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createStartersViewSQL);

        //create mains view
        String createMainsViewSQL = "CREATE VIEW IF NOT EXISTS MainsView " +
                "AS " +
                "SELECT " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Mains' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createMainsViewSQL);

        //desserts view
        String createDessertsViewSQL = "CREATE VIEW IF NOT EXISTS DessertsView " +
                "AS " +
                "SELECT " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Desserts' " +
                "ORDER BY image, name, description, price; ";
        db.execSQL(createDessertsViewSQL);

        String createDrinksView = "CREATE VIEW IF NOT EXISTS DrinksView " +
                "AS " +
                "SELECT " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price " +
                "FROM MenuItems as m " +
                "INNER JOIN Category c ON m.categoryID = c.categoryID " +
                "WHERE c.categoryName = 'Drinks' " +
                "ORDER BY image, name, description, price; ";

        db.execSQL(createDrinksView);

        String createSidesView = "CREATE VIEW IF NOT EXISTS SidesView " +
                "AS " +
                "SELECT " +
                "m.image, " +
                "m.name, " +
                "m.description, " +
                "m.price " +
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

        String createTriggerSQL = "CREATE TRIGGER IF NOT EXISTS trg_AddNewItem" +
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
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOG_TABLE_NAME);

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
    //boolean to return result of insertion
    //if returns 1, add new item confirmation screen will be triggered.
    public boolean addItem(RestMenuItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        //get content values
        ContentValues itemValues = new ContentValues();
        itemValues.put("price", item.getPrice());
        itemValues.put("name", item.getName());
        itemValues.put("description", item.getDescription());
        itemValues.put("categoryID", item.getCategoryID());
        itemValues.put("image", item.getImageBlob());

        //returning rowID of the newly inserted record
        long insert_result = db.insert("MenuItems", null, itemValues);
        db.close();

        //if insert was successful return true (bigger than 0 = true, less than is false, unsuccessful)
        return insert_result > 0;
    }

    public boolean deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("MenuItems", "itemId=?", new String[]{String.valueOf(id)});
        return rows > 0;
    }



}
