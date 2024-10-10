# Homework

## 1. Generarea graficului aleatoriu

Am folosit clasa **RandomGnmGraphGenerator** din biblioteca Graph4J pentru a genera graful.
Graficul este creat folosind **generator.createGraph()**, iar matricea de adiacentă este obținută prin **graph.adjacencyMatrix()**.

## 2. Logarea detaliilor cererii

Metoda **logRequestDetails(HttpServletRequest request)** extrage aceste informații utilizând API-ul **HttpServletRequest**.
Detaliile sunt scrise în logurile serverului folosind **java.util.logging.Logger**.

## 3. Invocarea servletului intr-o aplicație desktop

Metoda **isDesktopClient(String userAgent)** verifică dacă cererea provine de la un client desktop pe baza header-ului User-Agent.
Dacă cererea provine de la un client desktop, răspunsul este setat ca **text/plain**. În caz contrar, este setat ca **text/html**.

Am creat o clasă **GraphRequest** care utilizează **HttpURLConnection** pentru a trimite o cerere **GET** la servlet.
