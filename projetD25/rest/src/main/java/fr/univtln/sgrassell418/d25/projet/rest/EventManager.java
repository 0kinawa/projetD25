package fr.univtln.sgrassell418.d25.projet.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stephane on 31/03/16.
 */
public class EventManager{
    
    private static EventManager INSTANCE = null;

    private EntityManagerFactory emf = Persistence
            .createEntityManagerFactory("h2-eclipselink");
    private EntityManager em = emf.createEntityManager();

    private EntityTransaction transac = em.getTransaction();

    private List<String> activities = new ArrayList<String>();
    private Random rand = new Random();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private ObjectMapper jsonMapper = new ObjectMapper();

    public static EventManager getInsance(){
        if (INSTANCE == null)
            INSTANCE = new EventManager();

        return INSTANCE;
    }
    
    public void insert(Event e){
        transac.begin();
        em.persist(e);
        transac.commit();
    }

    public void delete(Event e){
        transac.begin();
        em.remove(e);
        transac.commit();
    }

    public void update(Event e){
        Event old = get(e.getId());

        transac.begin();
        old.setActivity(e.getActivity());
        old.setDate(e.getDate());
        transac.commit();
    }

    public Event get(int id){
        return em.find(fr.univtln.sgrassell418.d25.projet.rest.Event.class, id);
    }

    public List<Event> get(String activity){
        Query q = em.createQuery("Select e from Event e where e.activity = '"+activity+"'");
        return q.getResultList();
    }

    public List<Event> getNexts(){
        Query q = em.createQuery("Select e from Event e group by e.date, e.id");
        return q.getResultList();
    }

    public String mapToJson(Event e) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(e);
    }


    public void initActivities(){
        activities.add("Randonnée pédestre");
        activities.add("Cyclisme");
        activities.add("Football");
        activities.add("Prendre un café");
        activities.add("Shopping");
        activities.add("Cinéma");
        activities.add("Plage");
        activities.add("Piscine");
        activities.add("Moto");
    }

    public String getRandomActivity(){
        if (activities.size()<1)
            initActivities();
        return activities.get(rand.nextInt(activities.size()));
    }

    public String getRandomDate(){
        int mois = rand.nextInt((12 - 4) + 1) + 4;
        int jour = rand.nextInt(30);
        int heure = rand.nextInt(24);
        int minute = rand.nextInt(60);
        return "2016-"+mois+"-"+jour+" "+heure+":"+minute;
    }

    public void insertRandom() throws ParseException {

        for(int i = 0;i<100;i++){
            insert(new Event(getRandomActivity(), new Timestamp(dateFormat.parse(getRandomDate()).getTime())));
        }
    }
}
