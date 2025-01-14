# Decorator design pattern
(Fisierele INotifier.java, Notifier.java, BaseNotifierDecorator.java, BadgeDecorator.java, BadgeService.java)

## INotifier (Interfata)

 Aceasta este interfața de bază care definește interfata pentru notificari.  \
 Orice clasă care implementează această interfață trebuie să ofere implementări pentru metodele send și getEmail. \
 Prin utilizarea unei interfețe, se pot crea diverse implementări de notificare, menținând o interacțiune uniformă.

## Notifier (implementare concretă a interfeței INotifier):

Clasa Notifier primește un email și folosește UserService pentru a obține informații despre utilizator.
Aceasta este responsabilă pentru trimiterea mesajelor prin email.

## BaseNotifierDecorator (Clasă Abstractă)

BaseNotifierDecorator este o clasă abstractă care implementează interfața INotifier și primește un INotifier existent (Notifier). \
Aceasta permite decorarea oricărui INotifier existent, adăugând noi funcționalități prin metodele suprascrise. \
Aceasta facilitează reutilizarea codului și simplifică implementarea decoratoarelor, deoarece implementează metodele de bază care pot fi utilizate de clasele care moștenesc din ea. \ 
Clasa de bază asigură că decoratorii pot apela funcționalitățile originale fără a scrie cod duplicat.

## BadgeDecorator (Subclasa concretă a BaseNotifierDecorator)
BadgeDecorator extinde funcționalitatea clasei Notifier prin adăugarea informațiilor despre badge-uri. \
Aceasta primește un obiect INotifier (Notifier) și, în metoda send, apelează metoda send a decoratorului înfășurat (wrapped), \
adăugând apoi informații suplimentare despre badge-uri înainte de a finaliza procesul de trimitere a mesajului. \
Rolul său este de a adăuga funcționalități noi (în acest caz, badge-uri) fără a modifica clasa originală Notifier.

## Utilizarea în BadgeService
Aceasta creează o instanță a clasei Notifier, care este o implementare concretă a interfeței INotifier. \
Constructorul Notifier primește un email, în acest caz, "user@gmail.com".  \
Acest email este stocat în interiorul obiectului Notifier și poate fi utilizat pentru a trimite notificări.

## Beneficiile utilizării Decorator în acest exemplu:
  1. **Extensibilitate**:  \
     Se pot adăuga noi funcționalități fără a modifica clasele existente. Se pot crea noi decoratori în funcție de cerințe.
  2. **Compoziție**: \
     Permite combinarea diferitelor comportamente. Se pot combina mai multi decoratori pentru a obține o funcționalitate mai complexa fără a crea clase mari și complicate.
  3. **Separarea Responsabilităților**:  \
     Responsabilitățile sunt distribuite între clase, ceea ce face codul mai ușor de întreținut și de înțeles.

   ![Screenshot from 2024-10-21 00-56-26](https://github.com/user-attachments/assets/8e2da947-e739-42ad-a6ec-234d531f0305)
   
   ![Screenshot from 2024-10-21 00-56-49](https://github.com/user-attachments/assets/b7d77901-0e7b-4b6f-aad2-ba5ba543e4b7)
   
   ![Screenshot from 2024-10-21 00-57-05](https://github.com/user-attachments/assets/6e5f2d73-9b0c-41ba-9400-dacdf677be83)
   
   ![Screenshot from 2024-10-21 00-57-56](https://github.com/user-attachments/assets/e9aa15d9-c2bb-4a95-be15-c822b264e29f)
   
   ![Screenshot from 2024-10-21 08-32-15](https://github.com/user-attachments/assets/e291cb96-4cfd-4b04-bd66-05238f8233b8)

   ![Screenshot from 2024-10-21 01-00-16](https://github.com/user-attachments/assets/119f6276-2191-4674-ada9-108a9d8e1e42)


   


