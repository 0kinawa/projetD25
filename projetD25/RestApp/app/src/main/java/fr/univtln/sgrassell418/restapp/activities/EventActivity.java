package fr.univtln.sgrassell418.restapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.univtln.sgrassell418.restapp.R;
import fr.univtln.sgrassell418.restapp.utils.ApiEventManager;
import fr.univtln.sgrassell418.restapp.utils.Event;

public class EventActivity extends AppCompatActivity {

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        TextView txt = (TextView)findViewById(R.id.txt_event);
        Intent intent = getIntent();
        String json = intent.getStringExtra("event");

        try {
            event = Event.jsonToEvent(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String displayEvent =   "Event number "+event.getId()+"\n"+
                                "Activity : "+event.getActivity()+"\n"+
                                "Date : "+event.getDate();

        txt.setText(displayEvent);
    }

    public void deleteEvent(View view){
        try {
            ApiEventManager.getINSTANCE(this).deleteEventbyId(event.getId());
            Toast.makeText(this, "Deleted : \n"+event.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postEvent(View view){
        try {
            EditText activity = (EditText)findViewById(R.id.entry_activity);
            EditText date = (EditText)findViewById(R.id.entry_date);
            EditText time = (EditText)findViewById(R.id.entry_time);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            String sActivity = activity.getText().toString();
            String sDate = date.getText().toString();
            String sTime = time.getText().toString();

            Event evt = new Event();

            evt.setId(event.getId());

            if(sActivity.length()<1)
                evt.setActivity(event.getActivity());
            else
                evt.setActivity(sActivity);
            if(sTime.length()<5 || sDate.length()<8 )
                evt.setDate(event.getDate());
            else
                evt.setDate(new Timestamp(dateFormat.parse(sDate+" "+sTime).getTime()));



            ApiEventManager.getINSTANCE(this).postEvent(evt);

            Toast.makeText(this, "Updated : \n" + evt.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
