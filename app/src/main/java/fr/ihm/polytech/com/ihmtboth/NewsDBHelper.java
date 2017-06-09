package fr.ihm.polytech.com.ihmtboth;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import fr.ihm.polytech.com.ihmtboth.model.Article;
import fr.ihm.polytech.com.ihmtboth.model.Category;
import fr.ihm.polytech.com.ihmtboth.model.saves.Request;
import fr.ihm.polytech.com.ihmtboth.model.TypeE;

public class NewsDBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "db4.db";
    public static final String TABLE_NAME = "Article";

    public static final String SAVE = "INSERT INTO "+ TABLE_NAME +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private SQLiteDatabase myDataBase;
    private final Context myContext;
    private DataBaseChangeListener listener;

    public void setListener(DataBaseChangeListener listener) {
        this.listener = listener;
    }


    public NewsDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void openDataBase() throws SQLException, IOException {
        //Open the database
        String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                // Copy the database in assets to the application database.
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database", e);
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch(SQLiteException e){
            //database doesn't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = myContext.getDatabasePath(DB_NAME).getAbsolutePath();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Article> getAllArticles() {
        List<Article> list = new ArrayList<>();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM article ORDER BY date DESC",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Article article = getArticle(cursor);
            if(article!=null){
                list.add(article);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private Article getArticle(Cursor cursor) {
        Article article = null;
        int int_category = cursor.getInt(5);
        Category category = Category.values()[int_category-1];
        int int_type = cursor.getInt(4);
        TypeE type = TypeE.values()[int_type];
//        MediaType mediaType = MediaType.values()[cursor.getInt(5)];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
//            Date date = formatter.parse(cursor.getString(6));
            article = new Article(cursor.getInt(0),cursor.getString(1),cursor.getString(3),type,category,new URL(cursor.getString(2)),new Date(cursor.getString(6)),cursor.getFloat(7));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return article;
    }

    public List<Article> getArticlesWithRequest(Request request) {
        List<Article> list = getAllArticles();
        Iterator<Article> iter = list.iterator();

        while (iter.hasNext()) {
            Article article = iter.next();

            if (!request.isWanted(article)) iter.remove();
        }
        return list;
    }

    public void addArticle(Article article) {
        SQLiteStatement statement = myDataBase.compileStatement(SAVE);

        statement.bindDouble(1, article.getId());
        statement.bindString(2, article.getTitle());
        statement.bindString(3, article.getUrl().toString());
        statement.bindString(4, article.getContent());
        statement.bindDouble(5, article.getType().ordinal());
        statement.bindDouble(6, article.getCategory().ordinal()+1);
        statement.bindString(7, article.getDate().toString());
        statement.bindDouble(8, article.getPrice());

        statement.execute();

        statement.close();
        listener.onDatabaseChanged(article);
    }

    public void updateDate(){
        String sql="update "+TABLE_NAME+" set id='200' where title like 'toto'";
        myDataBase.execSQL(sql);
    }
    public void deleteArticleContainingArticleExample() {
        List<Article> articles = getAllArticles();
        for(Article a : articles){
            if(a.getTitle().contains("ArticleExample")){
                if(remove(a.getId())){
                    Log.d("DATABASE","Deleted "+a.getTitle()+" id = "+a.getId());
                }else{
                    Log.d("DATABASE","Failed to Delete "+a.getTitle()+" id = "+a.getId());
                }
            }
        }
    }

    public boolean remove(int id){
        String idString =String.valueOf(id);
        return myDataBase.delete(TABLE_NAME,"id=" + idString,null) >0;
    }
}