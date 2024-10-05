# Bonus

## 1. Generarea Arborilor de acoperire
Am folosit clasa **GraphBuilder** din biblioteca Graph4J pentru a genera un graf fara muchii cu un anumit număr de noduri. Pentru a adauga
muchii in graf (ca sa-l facem complet) cu o anumita pondere am folosit **graph.addEdge(new Edge(i, j, weight))**. 

Pentru a genera arborii de acoperire în ordinea crescătoare a greutății am folosit clasa **WeightedSpanningTreeIterator** din Graph4J
Stocarea acestor arbori se face folosind un map de tip **LinkedHashMap** pentru a mentine ordinea inserării lor. Map-ul are cheie 
multimea de muchii iar valoare este greutatea arborelui.

## 2. Crearea Servletului
Servletul primește ca parametrii ordinul grafului si k care indica numarul de arbori de acoperire generati. Servletul apealează funcția
**getAllSpanningTreesOrderedByWeight(int order, int k)** pentru a calcula primii k arbori in functie de ordinul. Răspunsul este dat sub
formă de tabel in care prima coloana reprezinta multimea de muchii, iar a doua greutatea arborului de acoperire.

## 3. Crearea Servletului
Pentru a analiza performanța am creat o noua clasă **BonusGraphRequest** care va trimite cereri către servlet în funcție de atributele 
clasei:

- NUM_THREADS: Numărul de fire de execuție (threads) folosite pentru cereri concurente.
- NUM_REQUESTS: Numărul total de cereri care vor fi trimise.
- ORDER și K: Parametrii pentru graf, trimiși ca parametri de interogare la servlet.
- URL_STRING: URL-ul servletului cu parametrii de interogare încorporați.
