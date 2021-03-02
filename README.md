# Movida
Java Movies Database

Data Structures: AVL, Sorted Array</br>
Sorting Algorithms: Heap Sort, Selection Sort

-------------------------------------------------------------------------
				                         Movida Tester
Movida Tester contiene il main utile a testare le strutture dati e gli algoritmi
di ordinamento implementati. La classe memorizza una StrutturaDati di persone, una StrutturaDati di film, un grafo.
La scelta di memorizzare separatamente una lista di persone è stata fatta per migliorare il costo computazionale delle operazioni esclusivamente su di esse.
Ogni StrutturaDati viene specificata nel metodo setMap().
L'algoritmo di ordinamento segue lo stesso criterio: è presente un'istanza astratta di Sort che viene specificata nel metodo setSort().	
Sono poi presenti le Enumeration "SortingAlgorithm" e "MapImplementation" per memorizzare la configurazione corrente.
Grazie all'uso di classi astratte sia per le strutture dati, sia per l'ordinamento, l'implementazione delle funzioni è unica a prescindere dalla configurazione.

-------------------------------------------------------------------------
				                         Grafo
Il grafo è implementato attraverso un HashMap per avere costi costanti in accesso agli elementi. HashMap ha come parametri Person e un insieme di Collaboration.
É presente un classe privata "weighedQueue" per definire una coda con priorità. La coda ha elementi di tipo "Coppia" (altra classe presente nella classe Grafo) contenente la chiave dell'elemento e la priorità dell'elemento.
Nella classe Coppia è stato effettuato l'Override dei metodi compareTo, equals, usati per ordinare la coda in base alla priorità degli elementi.
La classe "weighedQueue" definisce e implementa le operazioni base su una coda.
Il metodo principale per la popolazione del grafo è "addMovie" che scorre il cast e per ogni coppia di attori, crea (se non esiste) la collaborazione e aggiunge il film.
Il metodo "removeMovie" serve per rimuovere un film ed eventuali collaborazioni non più presenti per via del film rimosso. Rimuove inoltre eventuali persone senza più collaborazioni.
Il metodo "getAllCollaboration", ritorna in output un array di Collaboration contenente tutte le collaborazioni che caratterizzano il grafo.
Il metodo "getCollaborationsOf", prende in input un attore e ritorna un array di Collaborazioni che caratterizzano quell'attore.
Il metodo "getDirectCollaboratorsOf" prende in input un attore e ritorna un array di Persone rappresentante i collaboratori diretti di quell'attore.
Il metodo "getTeamOf" sfrutta la struttura del grafo (essendo formato da più componenti connesse). Prende in input un attore ed effettua una visita in ampiezza partendo da quell'attore.
Il metodo "maximizeCollaborationsInTheTeamOf" sfrutta l'algoritmo di Prim invertendo i pesi (per adattarlo alla funzione di Maximun Spanning Tree) e usa un coda di tipo "weighedQueue", oltre ad una HashMap mst dove tener traccia dei nodi e le rispettive
collaborazioni facenti parte del mst, e una HashMap per tenere traccia dei nodi già visitati e della loro distanza.

-------------------------------------------------------------------------
				                         AVL
É presente una classe privata per definire un Nodo dell'albero. 
La classe Nodo ha i campi per memorizzare l'elemento del nodo, la chiave, l'altezza dell'albero radicato in quel nodo e i puntatori ai figlio (sx,dx).
Definisce i metodi per gestire un AVL : metodi per effettuare una rotazione a destra o a sinistra, metodi per gestire i vari casi di sbilanciamento.
Insert chiama a sua volta una funzione ricorsiva per l'inserimento e alla fine bilancia l'albero (se necessario).
Delete chiama a sua volta una funzione ricorsiva per l'eliminazione del Nodo. Questa funzione, se trova il nodo da eliminare ed il nodo ha entrambi i figli, scambia le informazioni del nodo con il successore ed elimina ricorsivamente il successore. La funzione ritorna un booleano.
Search effettua una ricerca binaria all'interno dell'AVL.

-------------------------------------------------------------------------
				                         ArrayOrdinato
É presente una classe privata (Coppia) per definire il tipo di elementi contenuti nell'Array.
La classe Coppia memorizza un elemento generico e una chiave Comparable.
Nella classe è dichiarato un Array di elementi di tipo Coppia.
L'array mantiene durante insert e delete l'ordinamento in base alle chiavi.
Nell'operazione di insert (Controllo in MovidaCore l'assenza dell'elemento da inserire) l'array viene ridimensionato in un array di un elemento più grande.
Il metodo delete ha come valore di ritorno un booleano per indicare l'avvenuta eliminazione dell'elemento (se l'eliminazione ha successo, l'array viene ridimensionato in un array di un elemento più piccolo).

-------------------------------------------------------------------------
				                         Sort
Interfaccia per fornire i metodi principali da implementare negli algoritmi di ordinamento.
Dichiarazione dei metodi : Sort, nSort (Per ordinare solo i primi N elementi).
É implementata dalle classi SelectionSort, HeapSort.

-------------------------------------------------------------------------
				                         StrutturaDati
Classe Astratta per fornire i metodi principali da implementare in entrambe le strutture dati.
É definita Astratta perchè dichiara e implementa il getter del campo size (comune ad entrambe le strutture dati da implementare).
Ogni struttura dati memorizza elementi di tipo generico.
Dichiarazione dei metodi : Insert, Delete, Search.
Dichiarazione di un campo "size" per tenere conto del numero di elementi presenti nella struttura.
É estesa dalle classi ArrayOrdinato, AVL.

-------------------------------------------------------------------------
				                         Commons
Pacchetto contenente le interfacce fornite come base per la realizzazione del progetto. Sono state effettuate alcune modifiche minori nelle classi Movie, Person e Collaboration. Le modifiche includono anche alcuni metodi toString, utili alla stampa delle informazioni.

-------------------------------------------------------------------------
				                         MovidaCore
É la classe principale, implementa le interfacce e implementa tutti i metodi. 
La classe memorizza una StrutturaDati di persone, una StrutturaDati di film, un grafo.
La scelta di memorizzare separatamente una lista di persone è stata fatta per migliorare il costo computazionale delle operazioni esclusivamente su di esse.
Ogni StrutturaDati viene specificata nel metodo setMap().
L'algoritmo di ordinamento segue lo stesso criterio: è presente un'istanza astratta di Sort che viene specificata nel metodo setSort().	
Sono poi presenti le Enumeration "SortingAlgorithm" e "MapImplementation" per memorizzare la configurazione corrente.
Grazie all'uso di classi astratte sia per le strutture dati, sia per l'ordinamento, l'implementazione delle funzioni è unica a prescindere dalla configurazione.

-----------------------------------------------------------
		Istruzioni per la compilazione
Nel package "digregoriozecca" è presente una classe MovidaTester, con dei test già inclusi.
Altrimenti è possibile istanziare un oggetto di classe MovidaCore attravero il costruttore e usare i metodi forniti dalla classe.</br>
Progetto realizzato da Andrea Zecca e Giorgio Di Gregorio.
