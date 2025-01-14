# FillTheVoid

## Cuprins

- [Introducere](#introducere)
- [Tehnologii folosite](#tehnologii-folosite)
    - [Autentificare și autorizare JWT (Spring Security)](#autentificare-și-autorizare-jwt-spring-security)
    - [Java Persistence API (JPA)](#java-persistence-api-jpa)
    - [Arhitectura Model-View-Controller (Spring MVC)](#arhitectura-model-view-controller-spring-mvc)
    - [Servicii REST](#servicii-rest)
    - [Design Patterns](#design-patterns)
- [Instalare](#instalare)
- [Utilizare](#utilizare)

## Introducere
Aplicația "Fill in the Void" oferă două funcționalități principale: restaurarea artefactelor din imagini și vizualizarea lor într-un spațiu 3D interactiv. Utilizatorii pot încărca imagini deteriorate pentru a fi restaurate, iar rezultatele pot fi descărcate, salvate sau incluse în albume personalizate. Aceste albume pot fi explorate într-o galerie virtuală, oferind o experiență imersivă în care picturile restaurate sunt expuse pe pereți, similar unui muzeu. Aplicația permite editarea imaginilor, organizarea lor în albume și partajarea rezultatelor, consolidând astfel rolul utilizatorilor ca păstrători ai artei și patrimoniului cultural.

### Backend-ul aplicației este construit folosind Java și framework-ul Spring.

## Tehnologii folosite
Lista cu ce poate face proiectul:
- Autentificare și autorizare JWT (Spring Security)
- Gestionarea relației dintre obiecte Java și bazele de date relaționale (Java Persistence API (JPA))
- Arhitectura Model-View-Controller (Spring MVC)
- Servicii REST
- Design Patterns
- Folosirea unui script Python pentru integrarea cu partea de AI
- Aspect-Oriented Programming (AOP) folosind AspectJ
- Monitor-Oriented Programming (MOP)
- Testare funcțională (Unit Testing)

## Autentificare și autorizare JWT (Spring Security)

Spring Security este un framework de autentificare și control al accesului puternic și extrem de personalizabil. Reprezintă standardul pentru securizarea aplicațiilor bazate pe Spring.

### Caracteristici

- Suport cuprinzător și extensibil pentru autentificare și autorizare
  
  Spring Security oferă o gamă largă de mecanisme de autentificare și autorizare.

  Autentificarea suportă metode precum autentificarea pe bază de formulare, autentificarea cu token-uri JWT, OAuth2, LDAP, baze de date, și multe altele.

- Integrare API Servlet

  Spring Security se integrează perfect cu API-ul Servlet, ceea ce înseamnă că poate fi utilizat în orice aplicație web Java care folosește servlete.

  Acesta permite filtrarea și protejarea cererilor HTTP înainte ca acestea să ajungă la componentele de bază ale aplicației.

- Integrare cu Spring Web MVC

  Spring Security  se integrează foarte bine cu Spring Web MVC. Acesta include:

  1. Configurare simplificată în cadrul aplicațiilor Spring MVC prin configurări bazate pe adnotări și convenții specifice.
  2. Managementul sesiunilor de utilizator și al autentificării este integrat nativ cu fluxurile și controalele MVC.
  3. Suport pentru filtre și interceptoare în cadrul lanțului de filtre al Spring MVC pentru a aplica politici de securitate înainte de a ajunge la controllere.
 
## Configurarea Securității Web cu JWT [SecurityConfiguration.java](./src/main/java/com/taip/FillTheVoid/config/SecurityConfiguration.java)

### Adnotări de Configurare

1. @Configuration: Indică faptul că această clasă conține configurații Spring și definește unul sau mai multe bean-uri.
2. @EnableWebSecurity: Activează securitatea web în cadrul aplicației, permițând personalizarea setărilor de securitate.
3. @RequiredArgsConstructor: Generază un constructor cu argumente pentru toate câmpurile finale, facilitând injecția de dependențe.
4. @EnableMethodSecurity: Activează securitatea la nivel de metode, permițând utilizarea adnotărilor de securitate precum @PreAuthorize.

### Componente importante pentru configurarea securității

1. Filtrul de Autentificare JWT [JwtAuthenticationFilter.java](./src/main/java/com/taip/FillTheVoid/config/JwtAuthenticationFilter.java)

    Reprezintă un filtru personalizat de autentificare bazat pe JWT (JSON Web Token). Scopul său este de a procesa tokenurile JWT din cererile HTTP, verificând validitatea acestora și autentificând utilizatorul pe baza informațiilor din token.

3. Providerul de Autentificare [ApplicationConfig.java](./src/main/java/com/taip/FillTheVoid/config/ApplicationConfig.java)

    Este responsabil pentru gestionarea procesului de autentificare a utilizatorilor. AuthenticationProvider validează detaliile de autentificare (cum ar fi utilizatorul și parola) și produce un token de autentificare complet pentru utilizatorul autentificat. Acesta este configurat să utilizeze baza de date, pentru verificarea autentificării.

### Generarea și validarea token-urilor JWT [RealJwtService.java](./src/main/java/com/taip/FillTheVoid/config/proxy/RealJwtService.java)

- Configurare și Inițializare

    Se inițializează o cheie secretă utilizată pentru semnarea și verificarea tokenurilor JWT. Cheia secretă este un șir de caractere care va fi utilizat pentru a cripta și decripta tokenurile, astfel încât acestea nu pot fi falsificate sau modificate neautorizat.

- Generarea unui Token JWT

  Această metodă creează un token JWT care conține mai multe atribute. Jwts.builder() este folosit pentru a crea tokenul, unde sunt setate revendicările (claims), care includ numele și prenumele utilizatorului. În plus, data emiterii (setIssuedAt) și expirării (setExpiration) sunt setate pentru token. Ulterior, tokenul este semnat cu cheia utilizând algoritmul HS256. Acest token este compactat pentru a genera un șir de caractere care reprezintă tokenul JWT.

- Validarea Tokenului JWT

  Validarea tokenului JWT este un pas important în asigurarea securității unei aplicații web. Acesta confirmă identitatea și autorizarea unui utilizator pe baza informațiilor stocate în token și compară aceste informații cu cele din sistemul de autentificare.

  Această verificare se face prin:

  1. Compararea numelui de utilizator din token cu numele de utilizatorului furnizat pentru a confirma autenticitatea acestuia.
  2. Verificarea datei de expirare a tokenului pentru a asigura că acesta este încă valabil și nu expirat.
 
- Extragerea Tuturor Atributelor Tokenului

  Atunci când lucrăm cu tokenuri JSON Web Tokens (JWTs) în cadrul unei aplicații Spring, trebuie să putem accesa și să interpretăm revendicările (claims) incluse în acestea.

    Prin intermediul librăriei JWT, se utilizează cheia de semnare specificată pentru a valida și decripta tokenul primit. Ulterior, se extrage și se returnează toate revendicările din corpul tokenului, oferind astfel acces la informații.


## Java Persistence API (JPA)

Include și Hibernate, care este una dintre cele mai populare implementări de JPA, fiind un framework care contribuie la maparea obiectelor Java la tabelele unei baze de date relaționale și gestionează operațiunile de tip CRUD (Create, Read, Update, Delete).

### Object-Relational Mapping (ORM) [Gallery.java](./src/main/java/com/taip/FillTheVoid/gallery/Gallery.java)

JPA oferă adnotări standardizate pentru maparea claselor și atributelor Java la tabele și coloane dintr-o bază de date relațională. De exemplu:

- @Entity marchează o clasă ca entitate JPA.
- @Table controlează denumirea și constrângerile tabelului asociat.
- @Column ajustează proprietățile unei coloane (nume, unicitate, nullable etc.).

Hibernate extinde aceste adnotări cu caracteristici suplimentare.

**Gestionarea relațiilor între entități**

- Relațiile pot fi modelate cu
    - @OneToOne
    - @OneToMany
    - @ManyToOne
    - @ManyToMany
  
Exemple:

În clasa Gallery, relația @ManyToMany între galerii și picturi este mapată folosind o tabelă intermediară (gallery_painting).

Relația @ManyToOne cu Owner reprezintă o relație proprietar-galerie.

### Facilitarea accesului la baza de date [GalleryRepository.java](./src/main/java/com/taip/FillTheVoid/gallery/GalleryRepository.java)

- JpaRepository este o interfață centrală din cadrul Spring Data JPA, care oferă funcționalități pentru gestionarea de metode pentru operații CRUD (Create, Read, Update, Delete) și gestionarea entităților.

**Operații CRUD Standard**

- JpaRepository oferă metode implicite pentru operațiile esențiale de manipulare a entităților:

    - save(S entity) – Salvează sau actualizează o entitate.
    - findById(ID id) – Găsește o entitate după ID.
    - delete(T entity) – Șterge o entitate.
    - existsById(ID id) – Verifică existența unei entități după ID.

**Suport pentru Interogări Personalizate**

- JpaRepository permite utilizarea interogărilor scrise în JPQL sau SQL nativ prin adnotarea @Query.

**Integrare cu Tranzacțiile**

- JpaRepository funcționează perfect cu mecanismul tranzacțional din Spring, permițând utilizarea adnotărilor precum @Transactional și @Modifying.

  
    1. Adnotarea @Transactional este utilizată pentru a defini limitele unei tranzacții în cadrul aplicațiilor bazate pe Spring. O tranzacție reprezintă o unitate atomică de lucru care garantează consistența și integritatea datelor.

    2. Adnotarea @Modifying este utilizată împreună cu @Query pentru a specifica că o interogare personalizată afectează datele (e.g., INSERT, UPDATE, DELETE). Este necesară pentru metodele care modifică datele în baza de date.
 
## Arhitectura Model-View-Controller (Spring MVC)

Spring MVC este un sub-framework care permite dezvoltarea de aplicații web folosind arhitectura Model-View-Controller. Aceasta separă clar logica de prezentare de logica de business și de modelul de date, facilitând astfel dezvoltarea și întreținerea aplicațiilor web.

![MVC](https://github.com/user-attachments/assets/24368a38-e253-4d9b-92b8-b03d41b5441f)

**Separarea responsabilităților**

- Model: Clasele de entitate și repository-urile.
    - [Gallery.java](./src/main/java/com/taip/FillTheVoid/gallery/Gallery.java)
    - [GalleryRepository.java](./src/main/java/com/taip/FillTheVoid/gallery/GalleryRepository.java)
- View: Deoarece este o aplicație RESTful, view-ul este înlocuit cu răspunsurile JSON generate de controlor.
- Controller: Gestionează cererile HTTP și direcționează apelurile către Service.
    - [GalleryController.java](./src/main/java/com/taip/FillTheVoid/gallery/GalleryController.java)

  - definit cu @RestController. Expune endpoint-uri prin care utilizatorii pot interacționa cu aplicația, cum ar fi:
    - @PostMapping pentru adăugarea unei galerii.
    - @GetMapping pentru obținerea galeriilor.
    - @PutMapping pentru editarea unei galerii.
    - @DeleteMapping pentru ștergerea unei galerii.
   
  - [GalleryService.java](./src/main/java/com/taip/FillTheVoid/gallery/GalleryService.java) 

  - Adnotarea @Service în Spring este utilizată pentru a marca o clasă ca fiind un bean de serviciu (service bean). Aceasta indică faptul că respectiva clasă conține logica de business și este gestionată de containerul Spring ca parte a ciclului de viață al aplicației.

## Servicii REST

REST (Representational State Transfer) este un tip de arhitectură care vine în sprijinul serverului și care presupune un set de reguli pe care aplicația trebuie să le îndeplinească pentru a fi o aplicație **RESTful**.

![image](https://github.com/user-attachments/assets/3ba9e85b-771a-4115-a981-68b0e45874d9)

## Design Patterns

[Factory Method](../design-patterns/factory-method/README.md) 

[Decorator](../design-patterns/decorator/README.md) 

[Iterator](../design-patterns/iterator/README.md) 

[Proxy](../design-patterns/proxy/README.md) 


### Detalii suplimentare
Explicații mai detaliate despre funcționalități.

## Instalare
Instrucțiuni de instalare.

## Utilizare
Cum să folosești proiectul.
