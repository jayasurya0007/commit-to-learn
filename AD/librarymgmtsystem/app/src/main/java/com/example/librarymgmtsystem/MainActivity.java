package com.example.librarymgmtsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI elements
    EditText editTextBookName, editTextAuthor, editTextViewBookName, editTextUpdateBookName, editTextUpdateAuthor, editTextDeleteBookName, editTextBorrowBookName;
    Button buttonSave, buttonView, buttonUpdate, buttonDelete, buttonBorrow, buttonReturn;
    TextView textView;

    // Available and Issued Books Count
    int available = 1000;
    int issued = 300;

    // Database instance
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        editTextBookName = findViewById(R.id.editTextBookName);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextViewBookName = findViewById(R.id.editTextViewBookName);
        editTextUpdateBookName = findViewById(R.id.editTextUpdateBookName);
        editTextUpdateAuthor = findViewById(R.id.editTextUpdateAuthor);
        editTextDeleteBookName = findViewById(R.id.editTextDeleteBookName);
        editTextBorrowBookName = findViewById(R.id.editTextBorrowBookName);

        buttonSave = findViewById(R.id.buttonSave);
        buttonView = findViewById(R.id.buttonView);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonBorrow = findViewById(R.id.buttonBorrow);
        buttonReturn = findViewById(R.id.buttonReturn);

        textView = findViewById(R.id.textView);

        // Initialize database
        db = new DB(MainActivity.this);
    }

    // Save method
    public void sav(View view) {
        String bookName = editTextBookName.getText().toString();
        String author = editTextAuthor.getText().toString();
        db.save(bookName, author);
        Toast.makeText(this, "Successfully Added Book!", Toast.LENGTH_LONG).show();
    }

    // Retrieve method
    public void retrieve(View view) {
        String bookName = editTextViewBookName.getText().toString();
        String author = db.retrieve(bookName);
        textView.setText(author);
    }

    // Update method
    public void update(View view) {
        String bookName = editTextUpdateBookName.getText().toString();
        String author = editTextUpdateAuthor.getText().toString();
        db.update(bookName, author);
        Toast.makeText(this, "Successfully Updated Book!", Toast.LENGTH_LONG).show();
    }

    // Delete method
    public void delete(View view) {
        String bookName = editTextDeleteBookName.getText().toString();
        db.delete(bookName);
        Toast.makeText(this, "Successfully Deleted Book!", Toast.LENGTH_LONG).show();
    }

    // Borrow method
    public void borrow(View view) {
        available--;
        issued++;
        String str = "Available Books: " + available + "\nIssued Books: " + issued;
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    // Return method
    public void returnbook(View view) {
        available++;
        issued--;
        String str = "Available Books: " + available + "\nIssued Books: " + issued;
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
