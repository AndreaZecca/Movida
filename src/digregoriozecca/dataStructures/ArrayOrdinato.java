package digregoriozecca.dataStructures;

import java.util.Iterator;

@SuppressWarnings("rawtypes")
public class ArrayOrdinato<T> extends StrutturaDati<T> {

    //array di elementi Coppia<T>
    private coppia<T>[] arr;

    /**
     * Sottoclasse per la gestiore di elementi generici nell'array (abbinamento elemento/chiave)
     * @param <T>
     */
    protected class coppia<T>{
        private T elem;
        private Comparable key;

        public coppia(T ele, Comparable k){
            elem = ele;
            key = k;
        }

        public T getElem() { return elem; }
    }

    @SuppressWarnings("unchecked")
    public ArrayOrdinato(){
        super();
        arr = new coppia[size];
    }

    /**
     * Funzione per l'inserimento di un elemento nell'arrayOrdinato, viene aumentata la dimensione dell'array e viene inserito il nuovo elemento nella posizione corretta rispettando l'ordine
     * @param keyToInsert Chiave dell'elemento da inserire
     * @param elemToInsert Elemento generico da inserire
     */
    @Override
    @SuppressWarnings("unchecked")
    public void insert(Comparable keyToInsert, T elemToInsert){
        int i,j;
        coppia[] tmp = new coppia[arr.length+1];
        for(i=0; i< arr.length; i++) tmp[i]=arr[i];
        arr = tmp;
        for(i=0; i<arr.length - 1; i++)
            if(keyToInsert.compareTo(arr[i].key)<=0) break;
        for(j=arr.length - 1; j>i; j--)
            arr[j]=arr[j-1];
        arr[i] = new coppia(elemToInsert, keyToInsert);
        size++;
    }

    /**
     * Funzione per la ricerca di una chiave nell'array
     * @param keyToSearch la chiave da cercare
     * @return
     */
    @Override
    public T search(Comparable keyToSearch){
        int i=0, j=arr.length, m;
        while(j-i>0){
            m=(i+j)/2;
            if(keyToSearch.equals(arr[m].key)) return (T)arr[m].elem;
            if(keyToSearch.compareTo(arr[m].key)<0) j=m;
            else i=m+1;
        }
        return null;
    }

    /**
     * Funzione per l'eliminazione di un elemento dall'array data la chiave. Viene diminuita la dimensione dell'array di 1, rispettando l'ordine degli elementi
     * @param key la chiave dell'elemento da rimuovere
     * @return
     */
    @Override
    public boolean delete(Comparable key){
        int i;
        for(i=0; i<arr.length; i++){
            if(key.equals(arr[i].key)){
                for(int j=i; j< arr.length-1; j++){
                    arr[j]=arr[j+1];
                }
                coppia[] tmp = new coppia[arr.length-1];
                for(i=0; i< tmp.length; i++) tmp[i] = arr[i];
                arr = tmp;
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
        arr = new coppia[size];
    }

    /**
     * Funzione per ottenere un Iterator per gestire un array
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new IteratorArray<T>(arr);
    }

    /**
     * Classe per la crazione dell'Iterator
     * @param <E>
     */
    private class IteratorArray<E extends T> implements Iterator<T>{
        int i;
        coppia<T> arr[];
        public IteratorArray(coppia<T> l[]){ arr = l; i=0; }

        /**
         * Funzione per verificare la presenza di ulteriori elementi nell'Iterator
         * @return true/false
         */
        public boolean hasNext(){
            return i<arr.length;
        }

        /**
         * Funzione per ritornare l'elemento successivo nell'array
         * @return elemento di tipo T
         */
        public T next(){
            if(hasNext()){
                return arr[i++].elem;
            }
            else return null;
        }
    }
}
