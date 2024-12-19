# Homework 10

## 1. Implementați cazuri de testare simple pentru a evidenția suportul oferit de MicroProfile pentru scrierea de microservicii resiliente.

### a) Fallback + Timeout si Retry

Clasa [FaultToleranceController.java](FaultToleranceController.java)

### Endpoint-ul: /resilience/timeout

Metoda doWork are o întârziere de 700ms, ceea ce depășește limita de timp configurată de 500ms (specificată prin adnotarea @Timeout(500)).

### Fallback:

Dacă metoda depășește timeout-ul, se apelează metoda fallbackForTimeout, care returnează răspunsul: "Fallback answer due to timeout".

### Scop:
Demonstrează cum MicroProfile poate detecta timeout-ul și furniza un răspuns de rezervă pentru a menține serviciul operațional.

### Endpoint-ul: /resilience/retry

Metoda doWorkWithRetry simulează o eroare aruncând constant o excepție (RuntimeException).
Adnotarea @Retry(maxRetries = 3, delay = 200, jitter = 50) configurează următoarele:

MaxRetries: Metoda este reluată de maximum 3 ori.
Delay: Între fiecare încercare există o întârziere de 200ms.
Jitter: Adaugă o variație de până la 50ms pentru a evita suprasolicitarea sistemului.

### Fallback:
După ce toate încercările eșuează, este apelată metoda fallbackForRetry, care returnează răspunsul: "Fallback answer after retries failed".

### Scop:
Acest exemplu evidențiază modul în care MicroProfile gestionează operațiunile eșuate și reduce riscul de indisponibilitate a serviciului.

### b) CircuitBreaker

### Clasa [CircuitBreakerController.java](CircuitBreakerController.java)


### Endpoint-ul definit: /circuit

Metoda principală simulateCircuitBreakers este protejată de un circuit breaker configurat prin adnotarea @CircuitBreaker.
Simulează o eroare utilizând metoda auxiliară shouldFail.

### c) Bulkhead thread-pool

### Clasa [BulkheadController.java](BulkheadController.java)


### Endpoint-ul definit: /bulkhead

Metoda principală: simulateBulkhead este protejată de un bulkhead configurat să limiteze cererile concurente și să gestioneze o coadă de așteptare.
Utilizează un fallback pentru a returna un răspuns alternativ atunci când cererile depășesc limitele configurate.

### Parametrii @Bulkhead

value = 5:

Permite procesarea a maximum 5 cereri concurente. Acestea sunt executate simultan pe un thread pool alocat pentru acest bulkhead.
waitingTaskQueue = 8:

Definirea unei cozi de așteptare de 8 cereri. Cererile care depășesc acest număr vor fi respinse imediat cu o excepție BulkheadException.

### Testarea metodei

Folosind Jmeter am simulat mai multe cereri pentru a testa.

![image](https://github.com/user-attachments/assets/a691ccfb-c1f6-4197-8fbb-ce78b02c5660)

Se poate observa ca doar 5 din cele 7 cereri au fost realizate cu succes.

### d)  Bulkhead semaphore

### Clasa [SemaphoreBulkheadController.java](SemaphoreBulkheadController.java)

### Endpoint-ul definit: /semaphore-bulkhead

Controlează simultan maximum 5 cereri concurente utilizând un semafor (Semaphore).
Oferă un răspuns alternativ (fallback) atunci când limitele sunt depășite.

## 2. Implementați și testați o procedură de verificare a stării de sănătate, pentru a determina gradul de pregătire și vitalitatea serviciului dumneavoastră.

Clasa [ServiceReadyHealthCheck.java](ServiceReadyHealthCheck.java)

### Adnotarea @Readiness:

Indică faptul că acest health check este utilizat pentru determinarea stării de pregătire a serviciului.
Este utilizat de orchestratori (ex.: Kubernetes) pentru a decide dacă serviciul poate primi trafic.
Implementarea interfeței HealthCheck:

Metoda call(), care definește logica verificării de sănătate.

## 3 Utilizați API-ul MicroProfile Metrics pentru a monitoriza comportamentul serviciului dvs. Analizați numărul de invocări și timpul de răspuns pentru cel puțin o metodă.

### Clasa [MetricController.java](MetricController.java)

### Endpoint-ul definit: /timed.
Expune o metodă care este monitorizată pentru numărul de invocări și timpul de răspuns.
Adnotări cheie:

@Metric:
Marchează un contor personalizat (Counter) injectat în cod pentru a urmări numărul total de invocări.

@Timed:
Măsoară automat timpul de execuție al metodei timedRequest().

@Gauge:
Definește un punct de monitorizare pentru un indicator calculat, în acest caz, numărul actual de invocări din Counter.

![image](https://github.com/user-attachments/assets/e12bfcc4-d2a1-494f-81c1-caf801807742)
