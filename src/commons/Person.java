/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package commons;

/**
 * Classe usata per rappresentare una persona, attore o regista,
 * nell'applicazione Movida.
 * 
 * Una persona � identificata in modo univoco dal nome 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * Semplificazione: <code>name</code> � usato per memorizzare il nome completo (nome e cognome)
 * 
 * La classe pu� essere modicata o estesa ma deve implementare il metodo getName().
 * 
 */
public class Person implements Comparable<Person>{

	private boolean director;

	private int numFilm;

	private String name;

	public Person(String name, boolean dir) {
		this.name = name;
		director = dir;
		numFilm = 0;
	}
	
	public String getName(){
		return this.name;
	}

	/**
	 *	Funzione per ottenere la chiave di una persona
	 *	La chiave è identificata dal suo nome in minuscolo e senza spazi (case insensitive)
	 *  @return la chiave della persona
	 *
	 */
	public String key() {
		return name.replace(" ", "").toLowerCase();
	}

	/**
	 *	Funzione per verificare il ruolo di una persona (Attore/Direttore)
	 *
	 */
	public boolean isDirector(){return director;}

	/**
	 *	Funzione per aggiornare il conto di film in cui la persona ha preso parte
	 *
	 */
	public void addFilm(){ numFilm++; }

	public void removeFilm(){
		if(numFilm > 0) numFilm--;
	}

	public int getNumFilm(){ return numFilm; }

	/**
	 *	Override del metodo toString
	 * 	@return Concatenzaione del nome di actorA, del suo ruolo e il numero di film
	 */
	@Override
	public String toString() {
		return this.name + ", " + (director ? "director" : "actor") + ", FILMS: " + numFilm;
	}

	/**
	 *	Override del metodo equals
	 *  @param obj oggetto da confrontare con this
	 *  @return booleano rappresentante un uguaglianza (sulla chiave degli oggetti)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		return this.key().equalsIgnoreCase(other.key());
	}

	/**
	 *	Override di hashCode per usarlo nelle HashMap
	 *  @return hashCode della chiave della Persona
	 */
	@Override
	public int hashCode() {
		return this.key().hashCode();
	}

	@Override
	public int compareTo(Person person) {
		return this.key().compareTo(person.key());
	}
}
