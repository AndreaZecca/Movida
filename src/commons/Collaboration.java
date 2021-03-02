package commons;

import java.util.ArrayList;

public class Collaboration {

	Person actorA;
	Person actorB;
	ArrayList<Movie> movies;

	public Collaboration(Person actorA, Person actorB) {
		this.actorA = actorA;
		this.actorB = actorB;
		this.movies = new ArrayList<Movie>();
	}

	/**
	 *	Restituisce l'ArrayList contenente tutti i film della Collaborazione
	 *
	 */
	public ArrayList<Movie> getMovie() { return this.movies; }

	public Person getActorA() {
		return actorA;
	}

	public Person getActorB() {
		return actorB;
	}

	public Double getScore(){
		Double score = 0.0;
		
		for (Movie m : movies)
			score += m.getVotes();
		
		return score / movies.size();
	}

	/**
	 *	Override del metodo toString
	 * 	@return Concatenzaione del nome di actorA, del nome di actorB, numeri di film e punteggio della collaborazione
	 */
	@Override
	public String toString() {
		return actorA.getName() + " & " + actorB.getName() +  ". Num Film = " + movies.size() + ". Score: " + getScore();
	}

	/**
	 *	Override del metodo equals (usato per le hashMap)
	 *  @param obj oggetto da confrontare con this
	 *  @return booleano rappresentante un uguaglianza (simmetrica o totale nei nome di actorA,actorB di this e obj)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Collaboration)) {
			return false;
		}
		Collaboration other = (Collaboration) obj;
		boolean sameEquals = this.actorA.equals(other.actorA) && this.actorB.equals(other.actorB);
		boolean symmetricEquals = this.actorA.equals(other.actorB) && this.actorB.equals(other.actorA);

		return sameEquals || symmetricEquals;
	}

	/**
	 *	Override di hashCode per usarlo nelle HashMap
	 *  @return hashCode di actorA + hashCode di actorB
	 */
	@Override
	public int hashCode() {
		return actorA.hashCode() + actorB.hashCode();
	}
}
