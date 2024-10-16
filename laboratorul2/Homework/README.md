# Homework

## 1. Logare cereri pentru input.jsp

Clasa **InputJSPLogFilter** este responsabilă pentru interceptarea cererilor către **input.jsp** și logarea adresei IP a clientului care face cererea, împreună cu data și ora la care cererea a fost făcută.

## 2. Web listener pentru citirea parametrilor de inițializare la start-up

Metoda **contextInitialized(ServletContextEvent ce)** din clasa **ApplicationListener** citește parametrii de context la momentul inițializării aplicației.  \
Valorile parametrilor **prelude** și **coda** sunt prelucrate și salvate ca atribute ale contextului aplicației, disponibile pe întreaga durată de viață a aplicației. \
\
În web.xml sunt definiți cei doi parametrii **prelude** și **coda** în secțiunea **context-param**. \
**prelude**: va stoca versiunea Java curentă. \
**coda**: reține informația despre data curentă. \
Tot în **web.xml** este adăugat listener-ul **ApplicationListener** pentru a asculta evenimentele de start și stop ale aplicației.

## 3. Decorarea răspunsului cu prelude și coda

Clasa **ResponseWrapper** extinde clasa **HttpServletResponseWrapper** pentru a intercepta și a modifica răspunsul HTTP înainte ca acesta să fie trimis clientului. \
Conținutul răspunsului este capturat într-un **StringWriter** pentru a putea fi modificat ulterior.
\
Clasa **ResponseDecorator** definește un filtru care se aplică tuturor resurselor **(/*)**. \
Metoda **doFilter** folosește clasa **ResponseWrapper** pentru a captura răspunsul generat de servlet. \
Valorile **prelude** și **coda** sunt preluate din contextul aplicației, unde au fost stocate de un listener la inițializarea aplicației. \
Tot în cadrul acestei metode se adaugă textul **prelude** la început și textul **coda** la finalul conținutului răspunsului. \
La final se scrie conținutul modificat în fluxul de ieșire al răspunsului.

## 4. Adăugarea CAPTCHA pentru încărcarea fișierului

Kaptcha este o bibliotecă Java utilizată pentru generarea de CAPTCHA. \
\
Metoda **doPost** din clasa **FileUploadServlet** verifică **CAPTCHA**-ul introdus de utilizator înainte de a procesa încărcarea fișierului. \
Clasa **KaptchaServlet** generează o imagine **CAPTCHA** pe baza unei configurări specifice și o trimite ca răspuns. \
Pagina **input.jsp** include imaginea CAPTCHA generată și un câmp de text pentru introducerea CAPTCHA-ului.
