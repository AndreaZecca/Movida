package digregoriozecca.dataStructures;


import java.util.*;
import java.lang.Comparable;
import java.util.Iterator;

/**
 * Classe per gestire un AVL
 * @param <T> Il tipo di elementi dei nodi dell'albero
 */
public class AVL<T> extends StrutturaDati<T> {

    /**
     * Classe per definire un nodo di un Albero
     * @param <T> il tipo generico dell'elemento salvato all'interno del nodo
     */
    private class Node<T>{
        /**
         * Campi per informazioni del nodo
         * Chiave key, elemento T, puntatori a padre e figli e altezza
         */
        protected Comparable key;
        protected T elem;
        protected Node<T> left, right;
        protected int height ;

        /**
         * Costruttore classe Nodo
         * @param keyToInsert chiave del nodo this
         * @param elemToInsert elemento del nodo this
         */
        public Node(Comparable keyToInsert, T elemToInsert){
            this.key = keyToInsert;
            this.elem = elemToInsert;
            left = right = null;
        }
    }

    private Node<T> root;
    //Campo utile per la rimozione di un elemento
    int oldSize;

    public AVL(){
        super();
        root = null;
    }

    /**
     * Aggiorna l'altezza di un nodo
     * @param n Nodo in cui aggiornare l'altezza
     */
    public void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    /**
     * Altezza di un nodo
     * @param n Nodo
     * @return altezza del nodo
     */
    public int height(Node n) {
        return n == null ? -1 : n.height;
    }

    /**
     * Bilanciamento di un nodo
     * @param n Nodo
     * @return bilanciamento (altezza sottoablero dx - altezza sottoalbero sx)
     */
    public int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    /**
     * Rotazione a destra di un Nodo
     * @param y Nodo radice del sottoablero da ruotare
     * @return Nuova radice
     */
    public Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /**
     * Rotazione a sinistra di un Nodo
     * @param y Nodo radice del sottoablero da ruotare
     * @return Nuova radice
     */
    public Node rotateLeft(Node y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /**
     * In base allo sbilanciamento del nodo vengono effettuate le rotazioni necessarie
     * @param z radice del sottoablero da bilanciare
     * @return radice aggiornata
     */
    public Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    /**
     * Inserimento di un nodo nell'albero
     * @param keyToInsert Chiave dell'elemento da inserire
     * @param elemToInsert Elemento generico da inserire
     */
    @Override
    public void insert(Comparable keyToInsert, T elemToInsert){
        Node<T> node = new Node(keyToInsert, elemToInsert);
        root = insertRec(root, node);
        this.size++;
    }

    /**
     * Inserimento ricorsivo di un nodo nell'albero
     * @param root radice
     * @param toInsert Nodo da inserire
     * @return radice dell'albero
     */
    private Node insertRec(Node root, Node toInsert){
        if(root == null) return toInsert;
        if(root.key.compareTo(toInsert.key) > 0) root.left = insertRec(root.left, toInsert);
        else root.right = insertRec(root.right, toInsert);
        return rebalance(root);
    }

    /**
     * Ricerca logaritmica di un elemento nell'albero data la chiave
     * @param keyToSearch la chiave da cercare
     * @return null se chiave non trovata, elemento altrimenti
     */
    @Override
    public T search(Comparable keyToSearch){
        Node<T> current = root;
        while(current != null){
            if(current.key.compareTo(keyToSearch) == 0) break;
            current = current.key.compareTo(keyToSearch)>0 ? current.left : current.right;
        }
        if(current == null) return null;
        else return current.elem;
    }

    /**
     * Rimozione di un Nodo data la chiave dell'elemento
     * @param key la chiave dell'elemento da rimuovere
     * @return booleano per confermare l'avvenuta rimozione, false altrimenti
     */
    @Override
    public boolean delete(Comparable key) {
        this.oldSize = this.size;
        this.root = deleteRecursive(root, key, false);
        if(this.oldSize > this.size) return true;
        else return false;
    }

    /**
     * Rimozione ricorsiva di un elemento nell'albero
     * @param root la radice dell'albero
     * @param keyToDelete chiave elemento da eliminare
     * @param decremented parametro aggiuntivo per gestire la size della struttura dati
     * @return
     */
    public Node<T> deleteRecursive(Node<T> root, Comparable keyToDelete, boolean decremented){
        //Albero vuoto
        if(root == null) return null;
        //Cerco nel sottoalbero sx
        else if(root.key.compareTo(keyToDelete) > 0) root.left = deleteRecursive(root.left, keyToDelete, decremented);
            //Cerco nel sottoalbero dx
        else if(root.key.compareTo(keyToDelete) < 0) root.right = deleteRecursive(root.right, keyToDelete, decremented);
        //elemento trovato
        else{
            //se ha solo un figlio, unisco il figlio del nodo da eliminare al padre del nodo da eliminare
            if(root.left == null || root.right == null){
                if(!decremented){
                    //questo controllo mi permette di decrementare size solo una volta, se avviene la rimozione del nodo
                    decremented = true;
                    this.size--;
                }
                root = (root.left == null) ? root.right : root.left;
            }
            else{
                //se ha entrambi i figli, scambio il nodo con il successore e ricorsivamente elimino il successore
                if(!decremented){
                    //questo controllo mi permette di decrementare size solo una volta, se avviene la rimozione del nodo
                    decremented = true;
                    this.size--;
                }
                Node<T> successor = getSuccessor(root.right);
                root.key = successor.key;
                root.elem = successor.elem;
                root.right = deleteRecursive(root.right, root.key, decremented);
            }
        }
        //ribilanciamento della radice del sottoalbero
        if(root != null) root = rebalance(root);
        return root;
    }

    /**
     * Funzione per trovare il successore di un nodo all'interno di un albero
     * @param root la radice dell'albero
     * @return il successore
     */
    public Node<T> getSuccessor(Node<T> root){
        while(root.left != null) root = root.left;
        return root;
    }

    @Override
    public void clear() {
        root = null;
        this.size = 0;
    }

    /**
     * Funzione per avere un Iterator che funzioni su un AVL
     * @return
     */
    @Override
    public Iterator<T> iterator() {
        return new AVLIterator<T>(root);
    }

    /**
     * Classe Iterator
     * @param <E>
     */
    private class AVLIterator<E extends T> implements Iterator<T>{
        Queue<Node> coda = new LinkedList<>();
        //listaDei nodi e lista temporanea per la visita a livelli
        NodeList<T> listOfNodes = null;
        NodeList<T> tempoList = listOfNodes;

        private class NodeList<T>{
            T elem;
            NodeList<T> next;
        }

        /**
         * Costruttore AVLIterator
         * @param root
         */
        public AVLIterator(Node<T> root){
            Node<T> tmpNode = root;
            //effettua una visita a livello dell'albero e inserisce l'elemento del nodo visitato nella lista
            coda.offer(root);
            while (!coda.isEmpty()){
                Node<T> tmp = coda.poll();
                if(tmp != null) {
                    if (listOfNodes == null) {
                        listOfNodes = new NodeList();
                        listOfNodes.elem = tmp.elem;
                        listOfNodes.next = null;
                        tempoList = listOfNodes;
                    } else {
                        tempoList.next = new NodeList();
                        tempoList = tempoList.next;
                        tempoList.elem = tmp.elem;
                        tempoList.next = null;
                    }
                    coda.offer(tmp.left);
                    coda.offer(tmp.right);
                }
            }
        }

        /**
         * Funzione per verificare se l'iterator ha un elemento successivo
         * @return true/false
         */
        @Override
        public boolean hasNext() {
            //se la lista è != null, ha ancora elementi
            return !(listOfNodes==null);
        }

        /**
         * Funzione per ritornare l'elemento in cima alla lista e far avanzare il puntatore
         * @return elemento di tipo T
         */
        @Override
        public T next() {
            if(hasNext()){
                T toReturn = listOfNodes.elem;
                listOfNodes = listOfNodes.next;
                return toReturn;
            }
            else return null;
        }
    }
}