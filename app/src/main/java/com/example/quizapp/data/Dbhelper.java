package com.example.quizapp.data;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.quizapp.Questions;
import static com.example.quizapp.data.QuizContract.MovieEntry.KEY_ANSWER;
import static  com.example.quizapp.data.QuizContract.MovieEntry.KEY_ID;
import static  com.example.quizapp.data.QuizContract.MovieEntry.KEY_OPTA;
import static  com.example.quizapp.data.QuizContract.MovieEntry.KEY_OPTB;
import static  com.example.quizapp.data.QuizContract.MovieEntry.KEY_OPTC;
import static  com.example.quizapp.data.QuizContract.MovieEntry.KEY_QUES;
import static  com.example.quizapp.data.QuizContract.MovieEntry.TABLE_QUEST;

public class Dbhelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "samochodQuiz";

    private SQLiteDatabase dbase;
    public Dbhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase=db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES + " TEXT, " + KEY_ANSWER+ " TEXT, "+KEY_OPTA +" TEXT, " +KEY_OPTB +" TEXT, "+KEY_OPTC+" TEXT)";
        db.execSQL(sql);
        addQuestions();
    }

    private void addQuestions()
    {
        Questions q1=new Questions("Marka, której symbolem są cztery pierścienie, symbolizujące cztery firmy założycielskie to:","Volkswagen", "BMW", "Audi", "Audi");
        this.addQuestion(q1);
        Questions q2=new Questions("Symbol marki, którą tworzą trzy elipsy koloru srebrnego", "Toyota", "Subaru", "Ford", "Toyota");
        this.addQuestion(q2);
        Questions q3=new Questions("Lew, jakiej marki jest symbolem?","Viper", "Peugeot","BMW", "Peugeot" );
        this.addQuestion(q3);
        Questions q4=new Questions("Litera 'L' w okręgu jakiej marki jest to symbol?", "Lexus", "Lancia", "Ferrari","Lexus");
        this.addQuestion(q4);
        Questions q5=new Questions("Marka ta najpierw pojawiła się w USA, teraz staje się coraz popularniejsza w Europie","Acura","Infiniti","Lincoln","Infiniti");
        this.addQuestion(q5);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);

        onCreate(db);
    }

    public void addQuestion(Questions quest) {
        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }
    public List<Questions> getAllQuestions() {
        List<Questions> quesList = new ArrayList<Questions>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        dbase=this.getReadableDatabase();
        Cursor cursor = dbase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Questions quest = new Questions();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }
    public int rowcount()
    {
        int row=0;
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row=cursor.getCount();
        return row;
    }
}