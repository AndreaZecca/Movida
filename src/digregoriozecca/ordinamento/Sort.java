package digregoriozecca.ordinamento;

import java.util.Comparator;

/**
 * Interfaccia per gestire le operazioni base di algoritmo di ordinamento
 *
 */

public interface Sort{

    /**
     * metodo per ordinare un array generico
     * @param source array da ordinare
     * @param c cosa confrontare per ordinare l'array
     * @param <T> il supertipo del comparator
     */
    public abstract <T> void sort(T[] source, Comparator <? super T> c);

    /**
     * metodo per ordinare i primi N elementi di un array generico
     * @param source array da ordinare
     * @param c cosa confrontare per ordinare l'array
     * @param n il numero di elementi da ordinare
     * @param <T> il supertipo del comparator
     */
    public abstract <T> void nSort(T[] source, Comparator <? super T> c, int n);
}
