package digregoriozecca.ordinamento;

import java.util.Comparator;

public class HeapSort implements Sort{
    /**
     * metodo per ordinare un array generico
     * @param source array da ordinare
     * @param c cosa confrontare per ordinare l'array
     * @param <T> il supertipo del comparator
     */
    @Override
    public <T> void sort(T[] source, Comparator<? super T> c) {
        if(c==null) return;
        T[] tmp = java.util.Arrays.copyOf(source, source.length+1);  //crea un array di dim + 1
        for(int i=tmp.length - 1; i>0; i--){                           //sposta tutti gli elementi  di una posizione a dx
            tmp[i] = tmp[i-1];
        }
        tmp[0] = null;

        heapify(tmp, c, tmp.length - 1, 1);

        int i=0;
        for(int x= tmp.length - 1; x>0; x--){
            T tempo = findMax(tmp);
            deleteMax(tmp, c, x);
            source[i] = tempo;
            i++;
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
    public <T> void nSort(T[] source, Comparator <? super T> c, int n) {
        if(c==null || n<=0) return;
        if(n>=source.length) sort(source,c);
        else{
            T[] tmp = java.util.Arrays.copyOf(source, source.length+1);
            for(int i=tmp.length - 1; i>0; i--){
                tmp[i] = tmp[i-1];
            }
            tmp[0] = null;
            heapify(tmp, c, tmp.length - 1, 1);
            int i = 0;
            for(int x=tmp.length - 1; x> tmp.length - n - 1; x--){
                T tempo = findMax(tmp);
                deleteMax(tmp, c, x);
                source[i]=tempo;
                i++;
            }
        }
    }

    /**
     * metodo per costruire un max heap a partire da un array privo di alcun ordine
     * @param S array disordinato da trasformare in max heap
     * @param com cosa confrontare per ordinare l'array
     * @param n indice dell'ultimo elemento dello heap
     * @param <T> il supertipo del comparator
     * @param i indice dell'elemento che diventerà la radice dello heap
     */
    public <T> void heapify(T[] S, Comparator<? super T> com, int n, int i){
        if(i>n) return;
        heapify(S, com, n, 2*i);
        heapify(S, com, n, (2*i) + 1);
        fixHeap(S,com, n, i);
    }

    /**
     * metodo per ripristinare la proprietà di max heap nel sottoalbero radicato nel nodo i
     * @param S array heap
     * @param com cosa confrontare per ordinare l'array
     * @param c indice dell'ultimo elemento dell'array
     * @param <T> il supertipo del comparator
     * @param i indice dell'elemento di S su cui applicare fixheap
     */
    public <T> void fixHeap(T[] S, Comparator<? super T> com, int c, int i){
        int max= 2*i;
        if(2 * i > c) return;
        if((2 * i + 1<= c) && (com.compare((S[2 * i]),(S[2 * i + 1])) < 0))
            max= 2*i+1;
        if(com.compare(S[i],S[max]) < 0){
            T temp= S[max];
            S[max] = S[i];
            S[i] = temp;
            fixHeap(S, com, c, max);
        }
    }

    /**
     * metodo per ottenere l'elemento massimo dello heap
     * @param S array heap
     */
    public <T> T findMax(T[] S){
        return S[1];
    }

    /**
     * metodo per rimuovere l'elemento max dallo heap
     * @param S array heap
     * @param com cosa confrontare per ordinare l'array
     * @param c indice dell'ultimo elemento dello heap
     */
    public <T> void deleteMax(T[] S, Comparator<? super T> com, int c){
        if(c <= 0) return;
        S[1]= S[c];   //al posto della vecchia radice inserisco il valore in ultima posizione dell'array heap
        c--;
        fixHeap(S, com, c, 1);
    }
}