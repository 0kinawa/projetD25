package fr.univtln.sgrassell418.restapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.univtln.sgrassell418.restapp.R;
import fr.univtln.sgrassell418.restapp.utils.Event;

public class EventListActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ListView list = (ListView)findViewById(R.id.lst_event);
        Intent intent = getIntent();
        for(String ev : intent.getStringArrayListExtra("eventList")){
            try {
                System.out.println(ev);
                eventList.add(Event.jsonToEvent(ev));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        list.setAdapter(new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, eventList));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getAdapter().getItem(position);
                Log.i("Selected Event", event.toString());
                Intent intent = new Intent(context, EventActivity.class);
                try {
                    intent.putExtra("event", event.mapToJson());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                context.startActivity(intent);
            }
        });

    }
}
