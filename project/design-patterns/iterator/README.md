# Iterator design pattern 
(Clasele JwtServiceFactory.java, AuthJwtServiceFactory.java, JwtService.java, AuthJwtService.java)

## Iterator <T> (Interfață):
  Definește comportamentul unui iterator, specificând metodele necesare pentru a itera printr-o colecție de obiecte de tip generic T.

## ByDateIterator<T> (implementare concretă a interfeței Iterator):
  ByDateIterator este o implementare concretă a interfeței Iterator care iterează printr-o listă de obiecte **Painting** ordonate după data de creare (**createdAt**).

## IterableCollection<T> (Interfața)
  Definește interfata pentru o colecție iterabilă.  \
  Orice colecție care implementează această interfață trebuie să poată crea un iterator pentru a traversa elementele sale.

## ConcreteCollection (implementare concretă a interfeței IterableCollection)
  ConcreteCollection este o colecție concretă de obiecte Painting, care implementează interfața IterableCollection. \
  Aceasta permite adăugarea și eliminarea picturilor și, cel mai important, creează un iterator pentru colecția de picturi.

## Beneficiile Pattern-ului Iterator:
1. Separarea responsabilităților: \
   Logica de traversare este separată de structura de colecție.
2. Flexibilitate: \
   Se pot schimba ușor modul în care parcurgi colecția fără a modifica colecția însăși.
3. Extensibilitate: \
   Se pot crea alți iteratori (de exemplu, după nume sau categorie) fără a modifica colecția sau iteratoarele existente.
4. Simplificarea clientului: \
   Codul care folosește iteratorul nu trebuie să știe nimic despre structura internă a colecției, simplificând logica de acces la elemente.
   
![Screenshot from 2024-10-21 01-32-10](https://github.com/user-attachments/assets/aa59766c-b001-4002-b6dc-5d81952225b2)

![Screenshot from 2024-10-21 01-33-02](https://github.com/user-attachments/assets/c25bbc9f-839f-4452-bf7c-218ac232a374)

![Screenshot from 2024-10-21 01-33-19](https://github.com/user-attachments/assets/a293382a-0d7a-4eb1-9498-fc1e6fe578db)

![Screenshot from 2024-10-21 01-33-38](https://github.com/user-attachments/assets/149378af-a1ce-40c0-9de8-7e581c21ae7c)

![Screenshot from 2024-10-21 01-36-00](https://github.com/user-attachments/assets/a3f1be27-5f2e-458c-9583-2b7269e5378e)




