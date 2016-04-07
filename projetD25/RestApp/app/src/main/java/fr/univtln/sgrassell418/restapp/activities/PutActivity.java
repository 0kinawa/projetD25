package fr.univtln.sgrassell418.restapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.univtln.sgrassell418.restapp.R;
import fr.univtln.sgrassell418.restapp.utils.ApiEventManager;
import fr.univtln.sgrassell418.restapp.utils.Event;

public class PutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put);
    }

    public void putEvent(View view){
        try {
            EditText activity = (EditText)findViewById(R.id.entry_activity);
            EditText date = (EditText)findViewById(R.id.entry_date);
            EditText time = (EditText)findViewById(R.id.entry_time);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            Event event = new Event(activity.getText().toString(), new Timestamp(dateFormat.parse(date.getText().toString()+" "+time.getText().toString()).getTime()));

            ApiEventManager.getINSTANCE(this).putEvent(event);

            Toast.makeText(this, "Created : \n" + event.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
