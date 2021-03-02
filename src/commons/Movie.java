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
 * Classe usata per rappresentare un film
 * nell'applicazione Movida.
 * 
 * Un film � identificato in modo univoco dal titolo 
 * case-insensitive, senza spazi iniziali e finali, senza spazi doppi. 
 * 
 * La classe pu� essere modicata o estesa ma deve implementare tutti i metodi getter
 * per recupare le informazioni caratterizzanti di un film.
 * 
 */
public class Movie {
	
	private String title;
	private Integer year;
	private Integer votes;
	private Person[] cast;
	private Person director;
	
	public Movie(String title, Integer year, Integer votes,	Person[] cast, Person director) {
		this.title = title;
		this.year = year;
		this.votes = votes;
		this.cast = cast;
		this.director = director;
	}

	public String getTitle() {
		return this.title;
	}

	public Integer getYear() {
		return this.year;
	}

	public Integer getVotes() {
		return this.votes;
	}

	public Person[] getCast() {
		return this.cast;
	}

	public Person getDirector() {
		return this.director;
	}

	/**
	 *	Funzione per verificare la presenza di una determinata Persona all'interno del cast del film
	 *  @param name il nome della persona
	 *  @return booleano (true->presenza) (false->assenza)
	 *
	 */
	public boolean isInCast(String name){
		for(Person p: cast){
			if(p.key().equals(name)) return true;
		}
		return false;
	}

	/**
	 *	Funzione per ottenere la chiave di un Film
	 *	La chiave è identificata dal suo nome in minuscolo e senza spazi (case insensitive)
	 *	@return la chiave del film
	 *
	 */
	public String key() {
		return title.replace(" ", "").toLowerCase();
	}

	/**
	 *	Override del metodo toString
	 * 	@return Concatenzaione del titolo del film, del nome del direttore, dell'anno di produzione, dei voti ricevuti e dal nome delle persone nel cast
	 */
	@Override
	public String toString() {
		String s = "";
		int i = 0;
		String nameCast = "";
		for(Person p: cast) nameCast+= p.getName() + ", ";

		s += "\"" + title + "\", directed by " + director.getName() + ", " + year + ", VOTES: " + votes + ", Cast: " + nameCast;
		return s;
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
		if (!(obj instanceof Movie)) {
			return false;
		}
		Movie other = (Movie) obj;
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
}
