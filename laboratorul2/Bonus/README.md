# Bonus

## 1. Colorarea muchiilor grafului utilizând algoritmul greedy
Pentru crearea algoritmului am folosit biblioteca Graph4J. \
Metoda **loadGraphFromDIMACS** din clasa **EdgeColoring** parcurge liniile fișierului DIMACS și construiește graful. \
Metoda **greedyColorEdges**  colorează muchiile grafului folosind algoritmul greedy. Algoritmul funcționează astfel: 
1. Iterarează prin toate muchiile 
2. Determină culorilor deja utilizate de către muchiile vecine
3. Atribuie prima culoare disponibilă care nu este utilizată de muchiile vecine. 

Metoda **getNeighborsEdge** returnează muchiile adiacente pentru o muchie dată, excluzând muchia curentă.
Acest lucru este necesar pentru a determina ce culori sunt utilizate deja de muchiile care împărtășesc un nod.

## 2. Vizualizarea soluției în **result.jsp**
Servletul **FileUploadServlet** utilizează clasa **EdgeColoring** dacă fișierul primit are format DIMACS. \

**vis.js** este o bibliotecă JavaScript pentru manipularea și vizualizarea datelor. Oferă suport pentru diferite tipuri de obiecte, inclusiv grafuri.
Aceasta este folosită în pagina **result.jsp** pentru a vizualiza graful cu muchiile colorate.
La final, o listă cu format DIMACS este generată pentru a afișa rezultatele procesului de colorare.
