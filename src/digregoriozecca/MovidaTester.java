package digregoriozecca;
import commons.*;
import digregoriozecca.core.*;

import java.io.File;

public class MovidaTester {

    public static void main(String args[]){
        /*
        MovidaCore core = new MovidaCore();

        System.out.println("***** MovidaConfig TEST *****");
        System.out.println();

        //MapImplementation m = MapImplementation.AVL;
        MapImplementation m = MapImplementation.ArrayOrdinato;
        if(core.setMap(m)) System.out.println("Changed map implementation to " + m);
        else System.out.println("Unsupported map");


        //SortingAlgorithm a = SortingAlgorithm.HeapSort;
        SortingAlgorithm a = SortingAlgorithm.SelectionSort;
        if(core.setSort(a)) System.out.println("Changed sorting algorithm to " + a);
        else System.out.println("Unsupported algorithm");

        System.out.println();
        //MovidaDb Test
        System.out.println("***** MovidaDB TEST *****");
        System.out.println();

        File loadFile = new File("..\\Progetto Algoritmi e Strutture Dati\\src\\commons\\dati-exam-6.txt");
        File saveFile = new File("..\\Progetto Algoritmi e Strutture Dati\\src\\commons\\saved-data.txt");

        core.clear();
        System.out.println("Cleared all data");

        core.loadFromFile(loadFile);
        System.out.println("Loaded");

        core.saveToFile(saveFile);
        System.out.println("Saved");

        System.out.println("Movie number: " + core.countMovies());
        System.out.println();
        Movie[] movies = core.getAllMovies();
        for(Movie x : movies) System.out.println(x);
        System.out.println();

        System.out.println("People number: " + core.countPeople());
        System.out.println();
        Person[] people = core.getAllPeople();
        for(Person x : people) System.out.println(x);
        System.out.println();

        String movie1 = "The Fugitive";
        System.out.println("Searching movie: " + movie1);
        Movie m1 = core.getMovieByTitle(movie1);
        if(m1 != null) System.out.println("Found: " + m1);
        else System.out.println("Movie not found");

        System.out.println("Deleting movie: " + movie1);
        if(core.deleteMovieByTitle(movie1)) System.out.println("Deleted");
        else System.out.println("Movie not found");

        System.out.println("Searching movie: " + movie1);
        m1 = core.getMovieByTitle(movie1);
        if(m1 != null) System.out.println("Found: " + m1);
        else System.out.println("Movie not found");
        System.out.println();

        System.out.println("Reloading data from save...");
        core.loadFromFile(saveFile);
        System.out.println("Loaded");

        String person1 = "Gary Oldman";
        System.out.println("Searching person: " + person1);
        Person p = core.getPersonByName(person1);
        if(p != null) System.out.println("Found: " + p);
        else System.out.println("Person not found");
        System.out.println();

        //MovidaSearchTest
        System.out.println("***** MovidaSearch TEST *****");
        System.out.println();

        String s1 = "the";
        System.out.println("Searching movies containing: " + s1);
        Movie[] m2 = core.searchMoviesByTitle(s1);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        Integer i1 = 1997;
        System.out.println("Searching movies in year: " + i1);
        m2 = core.searchMoviesInYear(i1);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        String person2 = "Martin Scorsese";
        System.out.println("Searching movies directed by: " + person2);
        m2 = core.searchMoviesDirectedBy(person2);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        String person3 = "Harrison Ford";
        System.out.println("Searching movies starred by: " + person3);
        m2 = core.searchMoviesStarredBy(person3);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        Integer N = 1;
        System.out.println("Searching first " + N + " most voted movies");
        m2 = core.searchMostVotedMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = -1;
        System.out.println("Searching first " + N + " most voted movies");
        m2 = core.searchMostVotedMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 50;
        System.out.println("Searching first " + N + " most voted movies");
        m2 = core.searchMostVotedMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 1;
        System.out.println("Searching first " + N + " most recent movies");
        m2 = core.searchMostRecentMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 0;
        System.out.println("Searching first " + N + " most recent movies");
        m2 = core.searchMostRecentMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 50;
        System.out.println("Searching first " + N + " most recent movies");
        m2 = core.searchMostRecentMovies(N);
        System.out.println("Results:");
        if(m2.length != 0) for(Movie x : m2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 1;
        System.out.println("Searching first " + N + " most active actors");
        Person[] p2 = core.searchMostActiveActors(N);
        System.out.println("Results:");
        if(p2.length != 0) for(Person x : p2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 0;
        System.out.println("Searching first " + N + " most active actors");
        p2 = core.searchMostActiveActors(N);
        System.out.println("Results:");
        if(p2.length != 0) for(Person x : p2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        N = 50;
        System.out.println("Searching first " + N + " most active actors");
        p2 = core.searchMostActiveActors(N);
        System.out.println("Results:");
        if(p2.length != 0) for(Person x : p2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        System.out.println("***** MovidaCollaboration Test *****");
        System.out.println();

        String person4 = "William Baldwin";
        Person p4 = core.getPersonByName(person4);
        System.out.println("Searching direct collaborators of " + person4);
        p2 = core.getDirectCollaboratorsOf(p4);
        System.out.println("Results:");
        if(p2.length != 0) for(Person x : p2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        System.out.println("Searching team of " + p4);
        p2 = core.getTeamOf(p4);
        System.out.println("Results:");
        if(p2.length != 0) for(Person x : p2) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        System.out.println("Maximizing collaborations in the team of " + p4);
        Collaboration[] c1 = core.maximizeCollaborationsInTheTeamOf(p4);
        System.out.println("Results:");
        if(c1.length != 0) for(Collaboration x : c1) System.out.println(x);
        else System.out.println("Nothing found");
        System.out.println();

        System.out.println("***** END OF TEST *****");
    */
    }
}
