# Proxy design pattern 
[Proxy](../../FillTheVoid/src/main/java/com/taip/FillTheVoid/config/proxy/)
(Clasele JwtService.java, RealJwtService.java, JwtServiceProxy.java)

## JwtService (Interfață):
  **JwtService** permite decuplarea între implementarea concretă a serviciului și clasa care îl utilizează. \
  Astfel, atât **JwtServiceProxy**, cât și **RealJwtService** vor implementa această interfață.

## RealJwtService (implementare concretă a interfeței JwtService):
  Aceasta este clasa principală care conține logica de lucru cu tokenurile JWT. Proxy-ul (**JwtServiceProxy**) se \
  va folosi de această clasă pentru a accesa funcționalitățile de bază.

## JwtServiceProxy (implementare concretă a interfeței JwtService)
  **JwtServiceProxy** implementează și ea interfața **JwtService**, dar funcționează ca o interfață intermediară pentru **RealJwtService**. \
  Rolul său este de a controla accesul la **RealJwtService**, adăugând un nivel suplimentar de logică, cum ar fi: 
  1. Validarea parametrilor în isTokenValid prin metoda **verifyParameters**, care verifică dacă parametrii nu sunt **null**. 
  2. Împachetarea apelurilor către RealJwtService pentru a asigura verificarea suplimentară înainte de a permite metodei reale să fie executată.

Această clasă acționează ca un proxy pentru **RealJwtService**, aplicând Proxy Pattern pentru a adăuga funcționalitate suplimentară fără a modifica logica din **RealJwtService**. \
Este marcată cu **@Primary**, indicând că atunci când **JwtService** este injectat, instanța de **JwtServiceProxy** va fi preferată în locul altor implementări, cum ar fi **RealJwtService**.

## Beneficiile utilizării Proxy în acest exemplu:
1. **Controlul Accesului**:  
   I. Intermediere: Proxy-ul acționează ca un intermediar între client și serviciul real, permițându-le dezvoltatorilor să \
   controleze cine și cum accesează anumite funcționalități. \
   II. Securitate: Poate implementa mecanisme de autentificare și autorizare înainte de a permite accesul la serviciul real.

2. **Separation of Concerns**: \
   I. Decuplare: Proxy-ul poate gestiona logica care nu este direct legată de funcționalitatea principală a serviciului real \
   (de exemplu, validarea parametrilor, logarea), lăsând serviciul real să se concentreze pe logica de business. \
   II. Modularitate: Această separare ajută la menținerea codului curat și modular, facilitând întreținerea și extinderea aplicației.

3. **Logare și Monitorizare**: \
   Poate intercepta apelurile către serviciul real pentru a loga informații despre execuția acestora, oferind date valoroase pentru debugging și monitorizare.


   ![image](https://github.com/user-attachments/assets/611774c2-47c4-4f3b-b952-afe978ebe5fc)

   ![image](https://github.com/user-attachments/assets/b45b1566-3c6b-434f-bd26-4c0adb0a5501)

   ![image](https://github.com/user-attachments/assets/34e0956d-2490-45a1-9f31-b122bab3e53e)

   ![Screenshot from 2024-11-02 19-51-56](https://github.com/user-attachments/assets/cad18071-6507-4889-967c-4cca8e5d5f01)

