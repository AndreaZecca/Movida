package digregoriozecca.grafo;

import commons.*;

import java.util.*;

public class GrafoPersone {
    /**
     * Viene usata una HashMap per mappare le Persone e il relativo insieme di collaborazione, costi costanti nell'accesso
     */
    private HashMap<Person, Set<Collaboration>> graph;

    public GrafoPersone() {
        this.graph = new HashMap<>();
    }

    /**
     * Aggiunge una persona al grafo solo se non è presente
     * @param actor Persona da aggiungere
     */
    private void addActor(Person actor){
        if(!graph.containsKey(actor)){
            graph.put(actor, new HashSet<>());
        }
    }

    /**
     * Aggiunge una coppia di attori (creando la collaborazione se necessario) e aggiungendo il film alla collaborazione
     * @param actor1 Attore 1
     * @param actor2 Attore 2
     * @param movie Film in cui entrambi hanno recitato
     */
    private void addPair(Person actor1, Person actor2, Movie movie){
        Person actorA, actorB;
        //Ordine lessicologico decrescente
        if (actor1.key().compareTo(actor2.key()) > 0) {
            actorA = actor1;
            actorB = actor2;
        } else {
            actorA = actor2;
            actorB = actor1;
        }

        //ignorati se già presenti
        addActor(actorA);
        addActor(actorB);

        //prendo tutte le collaborazioni dell'attore A
        Collaboration collaboration = null;
        Set<Collaboration> collaborations = graph.get(actorA);

        //Controllo se esiste già una collaborazione con l'attore B
        for(Collaboration c : collaborations){
            if(c.getActorB().equals(actorB)) collaboration = c;
        }

        //se non esiste, la creo e l'aggiungo ad entrambi gli attori
        if(collaboration == null){
            collaboration = new Collaboration(actorA, actorB);
            graph.get(actorA).add(collaboration);
            graph.get(actorB).add(collaboration);
        }
        //se la collaborazione non contiene il film, lo aggiungo
        if(!collaboration.getMovie().contains(movie))
            collaboration.getMovie().add(movie);
    }

    /**
     * Dato un film, scorre il cast e aggiunge dove necessario, per ogni coppia di attori, la collaborazione e il film
     * @param movie film da aggiungere
     */
    public void addMovie(Movie movie){
        for (Person actor1 : movie.getCast()) {
            for (Person actor2 : movie.getCast()) {
                if (!actor1.equals(actor2)) {
                    addPair(actor1, actor2, movie);
                }
            }
        }
    }

    /**
     * Rimuove un film, e se necessario rimuove le collaborazioni non più presenti, rimuovendo eventuali Persone rimaste senza collaborazioni
     * @param movie film da rimuovere
     */
    public void removeMovie(Movie movie) {
        List<Person> actorsToRemove = new ArrayList<>();
        //Per ogni persona nel cast controllo le collaborazioni, e rimuovo il film.
        for (Person actor : movie.getCast()) {
            List<Collaboration> collaborationsToRemove = new ArrayList<>();
            Set<Collaboration> collaborations = graph.get(actor);

            //Se la lista di film della collaborazione è vuota, devo rimuovere la collaborazione
            for (Collaboration collaboration : collaborations) {
                collaboration.getMovie().remove(movie);
                if (collaboration.getMovie().isEmpty()) {
                    collaborationsToRemove.add(collaboration);
                }
            }

            //Rimuovo le collaborazioni vuote
            for (Collaboration collaborationToRemove : collaborationsToRemove) {
                collaborations.remove(collaborationToRemove);
            }

            //Se un attore non ha più collaborazioni, lo rimuovo dal grafo
            if (collaborations.isEmpty()) {
                actorsToRemove.add(actor);
            }
        }
        //Rimuovo gli attori rimasti senza collaborazioni
        for (Person actorToRemove : actorsToRemove) {
            graph.remove(actorToRemove);
        }
    }

    /**
     * Funzione per rimuovere tutti gli elementi dal grafo
     */
    public void clear(){
        this.graph.clear();
    }

    /**
     * Funzione per ottenere tutte le collaborazioni di un attore in forma di array
     * @param actor Attore
     * @return Array di Collaborazioni
     */
    private Collaboration[] getCollaborationsOf(Person actor) {
        Set<Collaboration> collaborations = graph.get(actor);
        if (collaborations == null) {
            return new Collaboration[0];
        } else {
            return collaborations.toArray(new Collaboration[0]);
        }
    }

    /**
     * Funzione per ottenere le collaborazioni dirette di un atore
     * @param actor Attore
     * @return Array di Person contenente le persone con cui ha collaborato direttamente
     */
    public Person[] getDirectCollaboratorsOf(Person actor) {
        Collaboration[] collaborations = getCollaborationsOf(actor); //Costo costante
        Person[] collaborators = new Person[collaborations.length];

        for (int i = 0; i < collaborations.length; i++) {
            if (collaborations[i].getActorA().equals(actor)) {
                collaborators[i] = collaborations[i].getActorB();
            } else {
                collaborators[i] = collaborations[i].getActorA();
            }
        }
        return collaborators;
    }

    /**
     * Funzione per ottenere il Team di una persona
     * Essendo il grafo costituito da varie componenti connesse, basta effettuare una visita in ampiezza partendo dal nodo dell'attore
     * @param actor Attore
     * @return Array di Person che fanno parte del Team dell'attore
     */
    public Person[] getTeamOf(Person actor) {
        //HashSet delle persone già visitate (accesso in tempo costante)
        HashSet<Person> visited = new HashSet<>();

        //Coda per la visita in ampiezza
        Queue<Person> queue = new LinkedList<>();
        queue.add(actor);

        while (!queue.isEmpty()) {
            Person u = queue.poll(); //Costante
            //Prendo tutte le collaborazioni di u
            for (Collaboration collaboration : getCollaborationsOf(u)) { //grado(u) controllo tutti gli archi di u
                Person otherCollaborator; //attore con cui u ha collaborato nell'arco collaboration che sto visitando
                //data una collaborazione di U, prendo il collaborator
                if (collaboration.getActorA().equals(u)) {
                    otherCollaborator = collaboration.getActorB();
                } else {
                    otherCollaborator = collaboration.getActorA();
                }
                //se non è già presente nell'hashSet lo inserisco e lo inserisco in coda
                if (!visited.contains(otherCollaborator) && !otherCollaborator.equals(actor)) { //Costante, controllo anche di non inserire l'attore passato in input
                    visited.add(otherCollaborator);
                    queue.add(otherCollaborator);
                }
            }
        }

        return visited.toArray(new Person[0]); //O(|visited|)
    }

    /**
     * Funzione per maximung spanning tree a partire da un Attore
     * Uso algoritmo di Prim adattato
     * @param actor Attore
     * @return Array di Collaboration che formano il MST
     */
    //Cost O(m log n)
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor){
        //HashMap delle persone e della loro distanza (peso = -score collaborazione)
        HashMap<Person, Double> d = new HashMap<>(); //accesso costante

        //HashMap contenente le persone e le relative collaborazioni che faranno parte dell MST
        HashMap<Person, Collaboration> mst = new HashMap<>(); //accesso costante
        d.put(actor, 0.0);

        //Coda con priorità pesata
        weighedQueue<Person> queue = new weighedQueue<Person>();
        queue.add(actor,0.0);

        while(!queue.isEmpty()){
            Person actual = queue.remove();
            //inserisco la persona attuale nell'hashmap, con priorità -infinito
            d.put(actual, Double.NEGATIVE_INFINITY);

            //controllo le varie collaborazioni della persona actual
            for(Collaboration c : getCollaborationsOf(actual)){
                Person otherCollaborator; //prelevo il collaboratore della persona actual

                if(c.getActorA().key().equals(actual.key()))
                    otherCollaborator = c.getActorB();
                else otherCollaborator = c.getActorA();

                //se non è presente nell'HashMap lo aggiungo e lo aggiungo in coda con priorità = -score della collaborazione
                if(!d.containsKey(otherCollaborator)){ //accesso alla hashMap in tempo costante
                    queue.add(otherCollaborator, -c.getScore());
                    d.put(otherCollaborator, -c.getScore());
                    mst.put(otherCollaborator, c);
                }
                else{
                    //se trovo un arco migliore (peso della collaborazione migliore)
                    if(-c.getScore() < d.get(otherCollaborator)){
                        //aggiorno l'elemento nella coda di priorità e inserisco nella hashMap e nel MST il nuovo arco
                        queue.updatePriority(otherCollaborator, -c.getScore());
                        d.put(otherCollaborator, -c.getScore()); //aggiorna la "distanza" dell'attore otherCollaborator
                        mst.put(otherCollaborator, c); //aggiungo al mst la collaborazione migliore
                    }
                }
            }
        }
        return mst.values().toArray(new Collaboration[0]);
    }

    /**
     * Funzione per ottenere tutte le collaborazioni tra tutti i vari attori nel grafo (usata per dei test sul funzionamento del grafo)
     * @return Array di Collaboration
     */
    public Collaboration[] getAllCollaboration(){
        LinkedList<Collaboration> toReturn = new LinkedList<>();
        Collection<Set<Collaboration>> tmp = graph.values();
        for(Set<Collaboration> coll : tmp){
            for(Collaboration c : coll){
                if(!toReturn.contains(c)) toReturn.add(c);
            }
        }
        return toReturn.toArray(new Collaboration[0]);
    }


    /**
     * Classe privata per la creazione di una coda con priorità simile a quella vista a lezione
     * @param <K> il tipo degli elementi in coda
     */
    private class weighedQueue<K extends Comparable<K>>{
        private PriorityQueue<Coppia<K>> queue;

        public weighedQueue(){
            this.queue = new PriorityQueue<>();
        }

        /**
         * Aggiunta di un elemento in coda rispettando la priorità
         * @param key chiave dell'elemento
         * @param priority priorità elemento
         */
        public void add(K key, double priority){
            this.queue.add(new Coppia<>(key, priority));
        }

        /**
         * Funzione per la rimozione del primo elemento dalla coda (con priorità maggiore)
         * @return
         */
        public K remove(){
            Coppia<K> tmp = queue.remove();
            return tmp.key;
        }

        /**
         * Funzione per verificare se la coda ha ancora elementi o è vuota
         * @return true/false
         */
        public boolean isEmpty() { return this.queue.isEmpty(); }

        /**
         * Funzione per modificare la priorità ad un elemento in coda
         * @param key la chiave dell'elemento a cui modificare la priorità
         * @param newPriority la nuova priorità
         */
        public void updatePriority(K key, double newPriority){
            //Non ci interessa la priorita al momento della rimozione o della ricerca (-1)
            if(!queue.contains(new Coppia<>(key, -1))) return;
            queue.remove(new Coppia<>(key, -1));
            queue.add(new Coppia<>(key, newPriority));
        }
    }

    /**
     * Classe privata per la definizione di elementi della coda con priorità
     * @param <K> Il tipo degli elementi
     */
    private static class Coppia<K extends Comparable<K>> implements Comparable<Coppia<K>>{
        private K key;
        private double priority;

        public Coppia(K key, double priority){
            this.key = key;
            this.priority = priority;
        }

        //getter Key
        public K getKey() {
            return this.key;
        }

        //getter priority
        public double getPriority() {
            return this.priority;
        }

        /**
         * Override metodo compareTo usato dalla coda con priorità
         * @param c Elemento con cui fare il confronto
         * @return intero
         */
        @Override
        public int compareTo(Coppia<K> c) {
            return Double.compare(this.priority, c.priority);
        }

        /**
         * Overrode metodo equals
         * @param obj oggetto con cui verificare l'uguaglianza sulla base delle chiavi
         * @return true/false
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof Coppia)) {
                Coppia other = (Coppia) obj;
                return this.key.equals(other.key);
            } else {
                return false;
            }
        }
    }
}
