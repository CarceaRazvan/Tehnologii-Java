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

## 3. Analiza Performanței
Pentru a analiza performanța am creat o noua clasă **BonusGraphRequest** care va trimite mai multe cereri către servlet în funcție de atributele 
clasei:

- NUM_THREADS: Numărul de fire de execuție (threads) folosite pentru cereri concurente.
- NUM_REQUESTS: Numărul total de cereri care vor fi trimise.
- ORDER și K: Parametrii pentru graf, trimiși ca parametri la servlet.
- URL_STRING: URL-ul servletului cu parametrii de încorporați.

ExecutorService (Executors.newFixedThreadPool) creează un pool de fire de execuție cu un număr fix de thread-uri (NUM_THREADS). Aceasta permite rularea paralelă a cererilor HTTP.

Pentru a analiza performanța am rulat algoritmul de mai multe ori în funcție de atributele clasei:

NUM_THREADS: 5, NUM_REQUESTS: 1000, ORDER:  5, K: 20, Time: 426 ms \
NUM_THREADS: 5, NUM_REQUESTS: 1000, ORDER: 15, K: 20, Time: 476 ms \
NUM_THREADS: 5, NUM_REQUESTS: 1000, ORDER: 25, K: 20, Time: 984 ms \
NUM_THREADS: 5, NUM_REQUESTS: 1000, ORDER: 35, K: 20, Time: 1503 ms \
NUM_THREADS: 5, NUM_REQUESTS: 1000, ORDER: 45, K: 20, Time: 2404 ms 

NUM_THREADS: 20, NUM_REQUESTS: 1000, ORDER:  5, K: 20, Time: 351 ms \
NUM_THREADS: 20, NUM_REQUESTS: 1000, ORDER: 15, K: 20, Time: 477 ms \
NUM_THREADS: 20, NUM_REQUESTS: 1000, ORDER: 25, K: 20, Time: 661 ms \
NUM_THREADS: 20, NUM_REQUESTS: 1000, ORDER: 35, K: 20, Time: 1156 ms \
NUM_THREADS: 20, NUM_REQUESTS: 1000, ORDER: 45, K: 20, Time: 2238 ms 

NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 5, K: 20, Time: 334 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 15, K: 20, Time: 431 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 25, K: 20, Time: 628 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 35, K: 20, Time: 1164 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 45, K: 20, Time: 2062 ms 

NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 5, K: 100, Time: 436 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 15, K: 100, Time: 760 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 25, K: 100, Time: 1499 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 35, K: 100, Time: 3613 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 45, K: 100, Time: 7719 ms \
NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 55, K: 100, Time: 17133 ms 

NUM_THREADS: 50, NUM_REQUESTS: 1000, ORDER: 5, K: 20, Time: 418 ms \
NUM_THREADS: 50, NUM_REQUESTS: 2000, ORDER: 5, K: 20, Time: 618 ms \
NUM_THREADS: 50, NUM_REQUESTS: 5000, ORDER: 5, K: 20, Time: 1238 ms \
NUM_THREADS: 50, NUM_REQUESTS: 10000, ORDER: 5, K: 20, Time: 2036 ms \
NUM_THREADS: 50, NUM_REQUESTS: 100000, ORDER: 5, K: 20, Time: 10457 ms 

### Concluzii din teste

Creșterea numărului de thread-uri reduce timpul de execuție, dar beneficiile scad după un anumit prag din cauza resurselor limitate ale sistemului. \
Dimensiunea grafului (ORDER) și numărul de arbori de întindere (K) au un impact major asupra timpului de răspuns, crescând proporțional cu complexitatea calculelor necesare. \
Sistemul scala bine cu numărul de cereri, dar timpii cresc exponențial atunci când grafurile sunt mai mari și se solicită mai mulți arbori de întindere.
