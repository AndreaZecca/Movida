package digregoriozecca.dataStructures;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public abstract class StrutturaDati<T> implements Iterable<T>{

    /**
     * Campo per tenere conto degli elementi presenti nella struttura dati
     */
    protected int size;

    /**
     * Costruttore (size = 0)
     */
    public StrutturaDati(){ this.size = 0; }

    /**
     * Funzione per inserire un elemento generico nella struttura dati
     * @param keyToInsert Chiave dell'elemento da inserire
     * @param elemToInsert Elemento generico da inserire
     */
    public abstract void insert(Comparable keyToInsert, T elemToInsert);

    /**
     * Funzione per la ricerca all'intero della struttura dati di un elemento
     * @param keyToSearch la chiave da cercare
     * @return elemento trovato o null (assenza dell'elemento)
     */
    public abstract T search(Comparable keyToSearch);

    /**
     * Funzione per eliminare un elemento dalla struttura dati
      * @param key la chiave dell'elemento da rimuovere
     * @return (true->elemento elimiato) (false->elemento non trovato)
     */
    public abstract boolean delete(Comparable key);

    /**
     * Funzione per rimuovere tutti gli elementi dalla struttura dati
     */
    public abstract void clear();

    /**
     * Getter per camopo size
     * @return valore di size
     */
    public int getSize(){ return this.size; }
}
