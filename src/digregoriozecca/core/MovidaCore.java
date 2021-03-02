package digregoriozecca.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Comparator;

import commons.*;
import digregoriozecca.dataStructures.*;
import digregoriozecca.grafo.GrafoPersone;
import digregoriozecca.ordinamento.*;

public class MovidaCore implements IMovidaConfig, IMovidaDB, IMovidaSearch, IMovidaCollaborations{

    private Sort alg;

    private final String TITLE = "Title:";
    private final String YEAR= "Year:";
    private final String DIR= "Director:";
    private final String CAST= "Cast:";
    private final String VOTES= "Votes:";
    private final String[] fields = {TITLE, YEAR, DIR, CAST, VOTES};

    private SortingAlgorithm sort;
    private MapImplementation map;

    private StrutturaDati<Movie> movies;
    private StrutturaDati<Person> people;

    private GrafoPersone grafo;

    /**
     * Costruttore classe MovidaCore
     */
    public MovidaCore(){
        alg = null;
        sort = null;
        map = null;
        grafo = new GrafoPersone();
    }

    //Cost O(1)
    @Override
    public boolean setSort(SortingAlgorithm a){
        if(sort == a) return false;
        switch (a){
            case SelectionSort:
                sort = a;
                alg = new SelectionSort();
                return true;
            case HeapSort:
                sort = a;
                alg = new HeapSort();
                return true;
            default:
                alg = null;
                return false;
        }
    }

    //Cost (ArrayOrdinato) -> O(|movies|*n) + O(|people|*n)
    //Cost (AVL) -> O(|movies| * logn) + O(|people|* logn)
    @Override
    public boolean setMap(MapImplementation m){
        if(map == m) return false;
        switch (m){
            case ArrayOrdinato: {
                map = m;
                ArrayOrdinato<Movie> mov = new ArrayOrdinato<>();
                if(movies!= null) for(Movie mm : movies) mov.insert(mm.key(), mm); //ArrayOrdinato -> for(|movies| volte) * n  , AVL -> for(|movies| volte) * logn
                movies = mov;
                ArrayOrdinato<Person> per = new ArrayOrdinato<>();
                if(people!= null) for(Person pp : people) per.insert(pp.key(), pp);
                people = per;
                return true;
            }
            case AVL: {
                map = m;
                AVL<Movie> mov = new AVL<>();
                if(movies!= null) for(Movie mm : movies) mov.insert(mm.key(), mm);
                movies = mov;
                AVL<Person> per = new AVL<>();
                if(people!= null) for(Person pp : people) per.insert(pp.key(), pp);
                people = per;
                return true;
            }
            default:
                return false;
        }
    }

    @Override
    public void loadFromFile(File f){
        if(!f.exists() || !f.canRead()) throw new MovidaFileException();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String dati[] = new String[fields.length];
            String testoLetto;
            while(br.ready()){
                for(int i = 0; i< fields.length; i++){
                    testoLetto = br.readLine();
                    if(testoLetto.startsWith(fields[i])) dati[i] = testoLetto.substring(fields[i].length()).trim();
                    else throw new MovidaFileException();
                }

                //creo il direttore
                Person directorProva = new Person(dati[2], true);

                //creo il cast
                String names[] = dati[3].split(", ");
                Person[] castProva = new Person[names.length];
                int i = 0;
                for(String s: names){
                    castProva[i] = new Person(s, false);
                    i++;
                }

                Movie tmp = new Movie(dati[0], Integer.parseInt(dati[1]), Integer.parseInt(dati[4]), castProva, directorProva);
                Movie searchedMovie = movies.search(tmp.key());
                if(searchedMovie == null) {
                    movies.insert(tmp.key(), tmp);
                    for (Person p : castProva) {
                        Person searchedPerson = people.search(p.key());
                        if (searchedPerson == null) {
                            p.addFilm();
                            people.insert(p.key(), p);
                        } else {
                            searchedPerson.addFilm();
                        }
                    }
                    Person directorSearched= people.search(directorProva.key());
                    if(directorSearched==null){
                        directorProva.addFilm();
                        people.insert(directorProva.key(),directorProva);
                    }
                    else{
                        directorSearched.addFilm();
                    }
                    this.grafo.addMovie(tmp);
                }
                br.readLine();
            }
            br.close();
        }catch(IOException e){throw new MovidaFileException();}
    }

    /**
     * Funzione per stampare a video tutti gli elementi presenti nelle strutture dati
     */
    //Cost O(|movies|) + O(|people|)
    public void printElements(){
        if(movies.getSize()>0){
            System.out.println("Totale Film= " + movies.getSize());
            for(Movie m : movies) System.out.println(m.toString());
            System.out.println();
        }
        else{
            System.out.println("No movies Found");
        }
        if(people.getSize()>0){
            System.out.println("Totale Persone= "+people.getSize());
            for(Person p : people) System.out.println(p.toString());
            System.out.println();
        }
        else{
            System.out.println("No people Found");
        }
    }

    //Cost O(|movies| * |cast|)
    @Override
    public void saveToFile(File f) {
        if(f.exists()) f.delete();
        try{
            f.createNewFile();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            for(Movie m:movies){
                bw.write(TITLE + m.getTitle());
                bw.newLine();
                bw.write(YEAR + m.getYear());
                bw.newLine();
                bw.write(DIR + m.getDirector().getName());
                bw.newLine();
                String cast = "";
                int j = 0;
                for(Person p : m.getCast()) {
                    cast += p.getName();
                    if(j != m.getCast().length-1) cast += ", ";
                    j++;
                }
                bw.write(CAST + cast);
                bw.newLine();
                bw.write(VOTES + m.getVotes());
                bw.newLine();
                bw.newLine();
            }
            bw.close();
        }catch (IOException e) { throw new MovidaFileException(); }
    }

    @Override
    public void clear() {
        if(movies.getSize()>0) movies.clear();
        if(people.getSize()>0) people.clear();
        if(grafo != null) grafo.clear();
    }

    //Cost O(1)
    @Override
    public int countMovies() {
        if(movies == null) return 0;
        return movies.getSize();
    }

    //Cost O(1)
    @Override
    public int countPeople() {
        if(people == null) return 0;
        return people.getSize();
    }

    //Cost (ArrayOrdinato) -> O(N)
    //Cost (AVL) -> O(logN)
    @Override
    public boolean deleteMovieByTitle(String title) {
        if(movies == null || movies.getSize() == 0) return false;
        title = title.replace(" ","").toLowerCase();
        Movie toDelete = movies.search(title);  //O(logN)
        if(toDelete != null){
            if(movies.delete(title)){  //ArrayOrdinato -> O(N)  , AVL -> O(logN)
                Person d = people.search(toDelete.getDirector().key());
                d.removeFilm();
                if(d.getNumFilm() == 0) people.delete(d.key());  //ArrayOrdinato -> O(N)  , AVL -> O(logN)

                for(Person x : toDelete.getCast()){
                    Person p = people.search(x.key()); //O(logN)
                    p.removeFilm();
                    if(p.getNumFilm() == 0){
                        people.delete(p.key()); //ArrayOrdinato -> O(N)  , AVL -> O(logN)
                    }
                }
                this.grafo.removeMovie(toDelete);
                return true;
            }
            else return false;
        }
        else return false;
    }

    //Cost O(logN)  -> N=|movies|
    @Override
    public Movie getMovieByTitle(String title){
        if(movies == null || movies.getSize() == 0) return null;
        return movies.search(title.replace(" ","").toLowerCase());
    }

    //Cost O(logN)  -> N=|people|
    @Override
    public Person getPersonByName(String name) {
        if(people == null || people.getSize() == 0) return null;
        return people.search(name.replace(" ","").toLowerCase());
    }

    //Cost O(|movies|)
    @Override
    public Movie[] getAllMovies() {
        if(movies==null || movies.getSize()==0) return null;
        Movie[] tmp = new Movie[movies.getSize()];
        int i=0;
        for(Movie m:movies){tmp[i++]=m;}
        return tmp;
    }

    //Cost O(|people|)
    @Override
    public Person[] getAllPeople() {
        if(people == null || people.getSize()==0) return null;
        Person[] all = new Person[people.getSize()];
        int i =0;
        for(Person p: people) all[i++]= p;
        return all;
    }

    //Cost Cost O(|movies| * logN)
    @Override
    public Movie[] searchMoviesByTitle(String title) {
        if(movies == null || movies.getSize()==0) return null;
        title = title.replace(" ","").toLowerCase();
        AVL<Movie> tmp = new AVL<>();
        for(Movie m: movies) if(m.key().contains(title)) tmp.insert(m.key(),m);
        if(tmp.getSize()==0) return new Movie[0];
        int i = 0;
        Movie[] data = new Movie[tmp.getSize()];
        for(Movie m: tmp) data[i++] = m;
        return data;
    }

    //Cost Cost O(|movies| * logN)
    @Override
    public Movie[] searchMoviesInYear(Integer year) {
        if(movies == null || movies.getSize()==0) return null;
        AVL<Movie> tmp = new AVL<>();
        for(Movie m: movies) if(m.getYear().equals(year)) tmp.insert(m.key(),m);
        if(tmp.getSize()==0) return new Movie[0];
        int i = 0;
        Movie[] data = new Movie[tmp.getSize()];
        for(Movie m: tmp) data[i++] = m;
        return data;
    }

    //Cost O(|movies| * logN)
    @Override
    public Movie[] searchMoviesDirectedBy(String name) {
        if(movies == null || movies.getSize()==0) return null;
        name = name.replace(" ","").toLowerCase();
        AVL<Movie> tmp = new AVL<>();
        for(Movie m: movies) if(m.getDirector().key().equals(name)) tmp.insert(m.key(),m);
        if(tmp.getSize()==0) return new Movie[0];
        int i = 0;
        Movie[] data = new Movie[tmp.getSize()];
        for(Movie m: tmp) data[i++] = m;
        return data;
    }

    //Cost O(|movies| * (|cast|))
    @Override
    public Movie[] searchMoviesStarredBy(String name) {
        if(movies == null || movies.getSize()==0) return null;
        name = name.replace(" ","").toLowerCase();
        AVL<Movie> tmp = new AVL<>();
        for(Movie m: movies){
            if(m.isInCast(name))
                tmp.insert(m.key(),m);
        }
        if(tmp.getSize()==0) return new Movie[0];
        Movie[] data = new Movie[tmp.getSize()];
        int i=0;
        for(Movie m: tmp) data[i++] = m;
        return data;
    }

    //Cost O(|movies|) + sort (O(N log n) con HeapSort, O(n*N) con selection sort)
    @Override
    public Movie[] searchMostVotedMovies(Integer N) {
        if(movies == null || movies.getSize() == 0 || alg == null || N <= 0) return new Movie[0];
        Movie data[] = getAllMovies();
        Comparator c = Comparator.comparing(Movie::getVotes);
        alg.nSort(data, c, N);
        Movie[] film = new Movie[N>movies.getSize() ? movies.getSize() : N];
        for(int i=0; i< film.length; i++) film[i] = data[i];
        return film;
    }

    //Cost O(|movies|) + sort (O(N log n) con HeapSort, O(n*N) con selection sort)
    @Override
    public Movie[] searchMostRecentMovies(Integer N) {
        if(movies == null || movies.getSize() == 0 || alg == null || N <= 0) return new Movie[0];
        Movie data[] = getAllMovies();
        Comparator c = Comparator.comparing(Movie::getYear);
        alg.nSort(data, c,N);
        Movie[] film = new Movie[N>movies.getSize() ? movies.getSize() : N];
        for(int i=0; i<film.length; i++) film[i] = data[i];
        return film;
    }

    //Cost O(|people|)
    public Person[] getAllActors(){
        if(people == null || people.getSize()==0) return new Person[0];
        Person[] all = new Person[people.getSize()];
        int i =0;
        for(Person p: people){
            if(!p.isDirector())
                all[i++]= p;
        }
        Person[] toReturn = new Person[i];
        for(int j=0; j<i; j++) toReturn[j]=all[j];
        return toReturn;
    }

    //Cost costo O(|people|) + sort (O(N log n) con HeapSort, O(n*N) con selection sort)
    @Override
    public Person[] searchMostActiveActors(Integer N) {
        if(people == null || people.getSize() == 0 || alg == null || N<=0) return new Person[0];
        Person data[] = getAllActors();
        Comparator c = Comparator.comparing(Person::getNumFilm);
        alg.nSort(data, c, N);
        Person[] film = new Person[N> data.length ? data.length : N];
        for(int i = 0; i<film.length; i++) film[i] = data[i];
        return film;
    }

    //Cost O(grado(actor))
    @Override
    public Person[] getDirectCollaboratorsOf(Person actor) {
        return this.grafo.getDirectCollaboratorsOf(actor);
    }


    //Cost O
    @Override
    public Person[] getTeamOf(Person actor) {
        return this.grafo.getTeamOf(actor);
    }


    @Override
    public Collaboration[] maximizeCollaborationsInTheTeamOf(Person actor) {
        return this.grafo.maximizeCollaborationsInTheTeamOf(actor);
    }


    public Collaboration[] getAllCollaboration(){
        return this.grafo.getAllCollaboration();
    }
}
