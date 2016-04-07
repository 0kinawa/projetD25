package fr.univtln.sgrassell418.restapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import fr.univtln.sgrassell418.restapp.R;
import fr.univtln.sgrassell418.restapp.utils.ApiEventManager;
import fr.univtln.sgrassell418.restapp.utils.Event;

public class GetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
    }

    public void getById(View view){
        try {
            EditText entry = (EditText)findViewById(R.id.entry_id);
            Event event = ApiEventManager.getINSTANCE(this).getEventById(Integer.parseInt(entry.getText().toString()));
            Intent intent = new Intent(this, EventActivity.class);
            intent.putExtra("event", event.mapToJson());
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getByActivity(View view){
        try {
            EditText entry = (EditText)findViewById(R.id.entry_activity);
            List<Event> eventList = ApiEventManager.getINSTANCE(this).getEventByActivity(entry.getText().toString());
            List<String> jsonList = new ArrayList<>();
            for(Event e : eventList){
                jsonList.add(e.mapToJson());
            }
            Intent intent = new Intent(this, EventListActivity.class);
            intent.putStringArrayListExtra("eventList", (ArrayList) jsonList);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getNext(View view){
        try {
            EditText entry = (EditText)findViewById(R.id.entry_next);
            List<Event> eventList = ApiEventManager.getINSTANCE(this).getNextEvents(Integer.parseInt(entry.getText().toString()));
            List<String> jsonList = new ArrayList<>();
            for(Event e : eventList){
                jsonList.add(e.mapToJson());
            }
            Intent intent = new Intent(this, EventListActivity.class);
            intent.putStringArrayListExtra("eventList", (ArrayList)jsonList);
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
