package fr.univtln.sgrassell418.d25.projet.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.*;
import java.util.List;
import java.util.logging.Logger;

// The Java class will be hosted at the URI path "/event"
@Path("/event")
public class ResourceEvent {

    private EventManager em = EventManager.getInsance();

    // The Java method will process HTTP GET requests
    @GET
    @Path("/{event}")
    @Produces("text/plain")
    public String getIt(@PathParam("event") String event) {
        try{
            int i = Integer.parseInt(event);
            return(em.get(i).mapToJson());
        }catch (Exception e){
            Logger.getLogger("LOG").info("Le paramètre n'est pas numérique ou l'Id demandé n'existe pas");
        }
        try{
            String s = "";
            for(Event e : em.get(event)){
                s += e.mapToJson();
                s += "\n";
            }
            if (s.isEmpty())
                    s="Aucun résultat disponible";
            return s;
        }catch (Exception e){
            Logger.getLogger("LOG").info("Erreur dans ResourceEvent.java");
        }
        return "Erreur serveur : La requête réçue n'était ni une chaine de caractère, ni un id";
    }

    // The Java method will process HTTP GET requests
    @GET
    @Path("/next/{nb}")
    @Produces("text/plain")
    public String getNext(@PathParam("nb") int nb) {
        try{
            String s = "";
            List<Event> events = em.getNexts();
            for(int i = 0; i<nb; i++){
                s += events.get(i).mapToJson();
                s += "\n";
            }
            if (s.isEmpty())
                s="Aucun résultat disponible";
            return s;
        }catch (Exception e){
            Logger.getLogger("LOG").info("Erreur dans ResourceEvent.java");
        }
        return "Erreur serveur : La requête réçue n'était ni une chaine de caractère, ni un id";
    }

    // The Java method will process HTTP DELETE requests  Java method will process HTTP DELETE requests
    @DELETE
    @Path("/{event}")
    @Produces("text/plain")
    public String deleteIt(@PathParam("event") String event) {
        try{
            int i = Integer.parseInt(event);
            Event e = em.get(i);
            em.delete(e);
            return(e.mapToJson()+" supprimé");
        }catch (Exception e){
            Logger.getLogger("LOG").info("Le paramètre n'est pas numérique ou l'Id demandé n'existe pas");
        }
        try{
            Event ev = Event.jsonToEvent(event);
            Event e = em.get(ev.getId());
            em.delete(e);
            System.out.println("ok");
            return(ev.mapToJson()+" supprimé");
        }catch (Exception e){
            Logger.getLogger("LOG").info("Erreur dans ResourceEvent.java");
        }
        return "Erreur serveur : La requête réçue n'était ni une chaine de caractère, ni un id";
    }

    // The Java method will process HTTP PUT requests  Java method will process HTTP PUT requests
    @PUT
    @Produces("text/plain")
    @Consumes("application/json")
    public String insertIt(Event event) {
        try{
            System.out.println(event);
            event = new Event(event.getActivity(), event.getDate());
            em.insert(event);
            return(event.mapToJson()+" ajouté avec l'identifiant : "+event.getId());
        }catch (Exception er){
            er.printStackTrace();
            Logger.getLogger("LOG").info("Erreur dans ResourceEvent.java");
        }
        return "Erreur serveur : La requête réçue n'était ni une chaine de caractère, ni un id";
    }

    // The Java method will process HTTP POST requests  Java method will process HTTP POST requests
    @POST
    @Produces("text/plain")
    @Consumes("application/json")
    public String updateIt(Event event) {
        try{
            System.out.println(event);
            em.update(event);
            return(event.mapToJson());
        }catch (Exception e){
            e.printStackTrace();
            Logger.getLogger("LOG").info("Erreur dans ResourceEvent.java");
        }
        return "Erreur serveur : La requête réçue n'était ni une chaine de caractère, ni un id";
    }



}
