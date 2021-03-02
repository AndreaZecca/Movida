package digregoriozecca.ordinamento;

import java.util.Comparator;

public class SelectionSort implements Sort{
    /**
     * metodo per ordinare in loco un array generico
     * @param source array da ordinare
     * @param c cosa confrontare per ordinare l'array
     * @param <T> il supertipo del comparator
     */
    @Override
    public <T> void sort(T[] source, Comparator<? super T> c){
        if(c == null) return;
        for(int i = 0; i < source.length - 1; i++) {
            int max = i;
            for(int j = i+1; j < source.length; j++) {
                if(c.compare(source[j], source[max]) > 0) max = j; //memo: compare ritorna 0 se x=y, -1 se x<y, 1 se x>y;
            }
            if(max != i) {
                T tmp = source[i];
                source[i] = source[max];
                source[max] = tmp;
            }
        }
    }

    /**
     * metodo per ordinare in loco i primi N elementi di un array generico
     * @param source array da ordinare
     * @param c cosa confrontare per ordinare l'array
     * @param n il numero di elementi da ordinare
     * @param <T> il supertipo del comparator
     */
    @Override
    public <T> void nSort(T[] source, Comparator <? super T> c, int n){
        if(c == null || n < 0) return;
        if(n >= source.length) sort(source, c);
        else{
            for(int i = 0; i < n; i++) {
                int max = i;
                for(int j = i +1; j < source.length; j++) {
                    if(c.compare(source[j], source[max]) > 0) max = j; //memo: compare ritorna 0 se x=y, -1 se x<y, 1 se x>y;
                }
                if(max != i) {
                    T tmp = source[i];
                    source[i] = source[max];
                    source[max] = tmp;
                }
            }
        }
    }
}
