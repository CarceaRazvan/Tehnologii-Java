# Factory method design pattern 
(Clasele JwtServiceFactory.java, AuthJwtServiceFactory.java, JwtService.java, AuthJwtService.java)

## JwtServiceFactory (Clasă Abstractă):
  Aceasta este o clasă abstractă care definește o metodă de fabricație numită instantiateJwtService().  \
  Această metodă este responsabilă pentru a crea și returna o instanță de JwtService.  \
  Metoda abstractă createJwtService(): Subclasele concrete vor trebui să implementeze această metodă pentru a crea instanțe concrete de JwtService.  \
  În această implementare, instantiateJwtService() delegă crearea instanței metodei abstracte createJwtService().  \
  Acest lucru asigură că subclasele pot schimba comportamentul fără a modifica logica din JwtServiceFactory.  

## AuthJwtServiceFactory (Subclasa concretă a JwtServiceFactory):
  Această clasă extinde JwtServiceFactory și implementează metoda abstractă createJwtService() pentru a crea o instanță de AuthJwtService.

## JwtService (Interfață):
  Aceasta definește o interfata pentru orice serviciu JWT (JSON Web Token) care implementează această interfață. Orice clasă care implementează JwtService trebuie să implementeze metodele:
  1. extractSubject(String jwt) - pentru a extrage subiectul din token.
  2. isTokenValid(String jwt, UserDetails userDetails) - pentru a verifica dacă token-ul este valid pentru un anumit utilizator.
  3. generateToken(UserDetails userDetails) - pentru a genera un token JWT pe baza detaliilor unui utilizator.

## AuthJwtService (implementare concretă a interfeței JwtService)
  Are rolul concret de "Product" în cadrul Factory Method Design Pattern. În acest context, "Product" se referă la obiectul care este creat de metoda de fabricație.  \
  În Factory Method Pattern, produsele concrete sunt cele care implementează interfața.

## Utilizarea în JwtAuthenticationFilter
  În constructorul lui JwtAuthenticationFilter, se creează o instanță de AuthJwtServiceFactory, care este folosită pentru a apela metoda instantiateJwtService(). \
  Aceasta returnează o instanță de AuthJwtService.  \
  Acum, JwtAuthenticationFilter poate folosi acest authJwtService pentru toate operațiunile legate de JWT (cum ar fi validarea token-ului și generarea acestuia) fără 
    să știe detalii despre modul în care a fost creată instanța.

## Beneficiile utilizării Factory Method în acest exemplu:
  1. **Separarea logicii de creare a obiectelor de restul aplicației**:  \
     JwtAuthenticationFilter nu este responsabilă de detaliile creării unei instanțe de JwtService.  \
     Această responsabilitate este delegată către JwtServiceFactory și, în mod specific, către subclasa AuthJwtServiceFactory.
  2. **Flexibilitate și extensibilitate**: \
     Dacă pe viitor se va schimba implementarea lui **JwtService**, nu trebuie să se modifice **JwtAuthenticationFilter**.  \
     Tot ce trebuie de facut este creare unei noi subclase de **JwtServiceFactory** care returnează o altă implementare concretă a lui **JwtService**.  \
     Această schimbare se poate face fără a afecta restul aplicației.
  3. **Encapsulare și abstractizare**:  \
     Pattern-ul Factory Method permite abstractizarea procesului de creare a obiectelor, astfel încât JwtAuthenticationFilter să lucreze doar cu interfața JwtService,  \
     fără să știe ce implementare concretă este folosită (în acest caz, AuthJwtService).
     
     
![Screenshot from 2024-10-20 21-09-58](https://github.com/user-attachments/assets/4e5bf65b-33c0-4ed9-86a7-cbbbe96f9d5f)
![Screenshot from 2024-10-20 21-10-46](https://github.com/user-attachments/assets/bf1b899f-f0b4-40f9-957a-2758fccfda8e)
![Screenshot from 2024-10-20 21-12-29](https://github.com/user-attachments/assets/75efc2e1-b8c7-42c4-927a-c1f7b2d6a350)
![image](https://github.com/user-attachments/assets/ca1b06d9-9f3e-4a18-b64c-a9cdf47836bf)
![Screenshot from 2024-10-20 21-26-17](https://github.com/user-attachments/assets/68c6d4c0-7e1e-4247-b67e-41e404b9ba79)
![diagram](https://github.com/user-attachments/assets/1532207e-1ec2-447d-b5aa-d8ddcd8b8d69)


  
  
