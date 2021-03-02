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
Il grafo è implementato attraverso un HashMap per avere costi costanti in accesso agli elementi. HashMap ha come parametri Person e un insieme di Collaboration
É presente un classe privata "weighedQueue" per definire una coda con priorità. La coda ha elementi di tipo "Coppia" (altra classe presente nella classe Grafo) contenente la chiave dell'elemento e la priorità dell'elemento.
Nella classe Coppia è stato effettuato l'Override dei metodi compareTo, equals, usati per ordinare la coda in base alla priorità degli elementi.
La classe "weighedQueue" definisce e implementa le operazioni base su una coda.
Il metodo principale per la popolazione del grafo è "addMovie" che scorre il cast e per ogni coppia di attori, crea (se non esiste) la collaborazione e aggiunge il film.
Il metodo "removeMovie" serve per rimuovere un film ed eventuali collaborazioni non più presenti per via del film rimosso. Rimuove inoltre eventuali persone senza più collaborazioni.
Il metodo "getAllCollaboration", ritorna in output un array di Collaboration contenente tutte le collaborazioni che caratterizzano il grafo
Il metodo "getCollaborationsOf", prende in input un attore e ritorna un array di Collaborazioni che caratterizzano quell'attore
Il metodo "getDirectCollaboratorsOf" prende in input un attore e ritorna un array di Persone rappresentante i collaboratori diretti di quell'attore
Il metodo "getTeamOf" sfrutta la struttura del grafo (essendo formato da più componenti connesse). Prende in input un attore ed effettua una visita in ampiezza partendo da quell'attore.
Il metodo "maximizeCollaborationsInTheTeamOf" sfrutta l'algoritmo di Prim invertendo i pesi (per adattarlo alla funzione di Maximun Spanning Tree) e usa un coda di tipo "weighedQueue", oltre ad una HashMap mst dove tener traccia dei nodi e le rispettive 
collaborazioni facenti parte del mst, e una HashMap per tenere traccia dei nodi già visitati e della loro "distanza" 
