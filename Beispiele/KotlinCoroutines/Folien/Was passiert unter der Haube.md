# Kotlin JVM

Kotlin-Coroutines sind ein leichtgewichtiges Modell zur Nebenläufigkeit, das auf der JVM läuft. Im Hintergrund generiert Kotlin keinen nativen Java-Code für Coroutines, sondern nutzt die Kotlin-Standardbibliothek und die JVM, um Coroutines effizient zu implementieren.

### Generierter Code bei Coroutines
1. **Suspend Functions**: Das `suspend`-Keyword wird in Java durch eine zusätzliche Callback-Mechanik umgesetzt. Intern wird eine State Machine generiert, die den Zustand der Coroutine (z. B. pausiert oder fortgesetzt) verwaltet[1][4].

2. **Continuation Interface**: Jede `suspend`-Funktion wird in Java als Methode mit einem zusätzlichen Parameter vom Typ `Continuation` übersetzt. Dieses Interface speichert den aktuellen Zustand der Coroutine und ermöglicht es, sie später fortzusetzen[5][9].

3. **Coroutine Builder**: Funktionen wie `launch` oder `async` nutzen Worker-Threads aus einem Thread-Pool, um Coroutines zu starten. Diese Threads wechseln transparent zwischen Aufgaben, was effizienter ist als native Java-Threads[4][6].

4. **Dispatcher**: Der Dispatcher steuert, welcher Thread für die Coroutine verwendet wird (z. B. Main-Thread oder IO-Thread)[9].

Mit diesem Ansatz bleiben Coroutines leichtgewichtig und performant, da sie keinen eigenen Thread pro Ausführung benötigen.

Citations:
[1] https://www.codecentric.de/wissens-hub/blog/einfuehrung-in-kotlin-coroutines
[2] https://30dayscoding.com/blog/java-reactive-programming-kotlin-coroutines-flow
[3] https://kt.academy/de/article/cc-testing
[4] https://www.codecentric.de/wissens-hub/blog/tolles-paar-spring-webflux-kotlin-coroutines
[5] https://dev.to/devsegur/java-virtual-threads-vs-kotlin-coroutines-4ma8
[6] https://www.studysmarter.de/schule/informatik/programmiersprachen/kotlin/
[7] https://kt.academy/de/article/cc-why
[8] https://stackoverflow.com/questions/2846664/implementing-coroutines-in-java
[9] https://developer.android.com/kotlin/coroutines?hl=de
[10] https://developer.android.com/topic/libraries/architecture/coroutines?hl=de


Die vom Kotlin-Compiler generierte State Machine für Coroutines ermöglicht das Pausieren und Fortsetzen von Code, der sequentiell aussieht, aber asynchron ausgeführt wird. Hier ist, wie sie funktioniert:

### **Funktionsweise der State Machine**
1. **Zustände und Labels**:
    - Jede `suspend`-Funktion wird in mehrere Zustände (z. B. `STATE_0`, `STATE_1`, ...) unterteilt, die den Code zwischen den Suspendierungspunkten repräsentieren.
    - Der Compiler fügt ein `label`-Feld hinzu, das den aktuellen Zustand speichert.

2. **Switch-Statement**:
    - Der generierte Code enthält ein großes `switch`-Statement (oder ähnliches), das basierend auf dem `label` entscheidet, welcher Codeblock ausgeführt werden soll.
    - Beim Aufrufen einer suspendierenden Funktion wird der Zustand gespeichert und die Ausführung pausiert.

3. **Wiederaufnahme**:
    - Wenn die Coroutine fortgesetzt wird, prüft der Code den gespeicherten `label`, um genau an der Stelle weiterzumachen, an der sie pausiert wurde.

4. **Speicherung von Variablen**:
    - Lokale Variablen werden in einer synthetischen Klasse (z. B. einer Unterklasse von `ContinuationImpl`) gespeichert, sodass sie zwischen den Zuständen erhalten bleiben.

### **Beispiel**
Ein einfacher Coroutine-Code:
```kotlin
suspend fun example() {
    val a = suspendFunction1()
    val b = suspendFunction2(a)
    println(b)
}
```

Wird in etwa so übersetzt:
```java
class ExampleContinuation extends ContinuationImpl {
    int label;
    Object result;
    Object a;

    public Object invokeSuspend(Object result) {
        this.result = result;
        switch (label) {
            case 0: 
                a = suspendFunction1(this);
                if (a == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED;
                label = 1;
            case 1: 
                Object b = suspendFunction2(a, this);
                if (b == COROUTINE_SUSPENDED) return COROUTINE_SUSPENDED;
                label = 2;
            case 2: 
                System.out.println(b);
                return Unit.INSTANCE;
        }
    }
}
```

### **Sequentielles Verhalten**
Obwohl der Code sequentiell aussieht, wird er durch die State Machine in Abschnitte zerlegt. Jeder Abschnitt wird nur dann ausgeführt, wenn die Coroutine an diesem Punkt wieder aufgenommen wird. Dies ermöglicht asynchrone Verarbeitung ohne Blockierung des Threads[1][5][7].

Citations:
[1] https://proandroiddev.com/coroutine-suspension-mechanics-the-finite-state-machine-within-58edac6dfb2e
[2] https://developer.android.com/topic/libraries/architecture/coroutines?hl=de
[3] https://www.codecentric.de/wissens-hub/blog/einfuehrung-in-kotlin-coroutines
[4] https://kotlinlang.org/docs/composing-suspending-functions.html
[5] https://proandroiddev.com/design-of-kotlin-coroutines-879bd35e0f34
[6] https://github.com/Kotlin/kotlinx.coroutines/issues/74
[7] https://kotlinlang.org/spec/asynchronous-programming-with-coroutines.html
[8] https://kotlinlang.org/docs/coroutines-basics.html


### 1. **Kotlin Coroutines und Java Bytecode**
Kotlin Coroutines werden vom Compiler in Java-Bytecode umgewandelt. Dabei nutzt Kotlin das Konzept der **Continuations**. Jedes `suspend`-Funktion wird in eine Art "zustandsbehaftete Maschine" umgewandelt, die den aktuellen Ausführungsstatus speichert.

### 2. **Continuation-Passing Style (CPS)**
Eine `suspend`-Funktion wird **nicht direkt blockierend** ausgeführt, sondern **speichert ihren Zustand** und gibt die Kontrolle zurück. Das wird mit einer **Continuation** (eine Art Callback-Mechanismus) realisiert:

- Wenn eine `suspend`-Funktion unterbrochen wird (z. B. durch `delay()` oder `await()`), speichert die Continuation den aktuellen Zustand.
- Beim Fortsetzen (`resume()`) springt die Coroutine exakt an die Stelle zurück, an der sie unterbrochen wurde.

### 3. **Wie wird eine Coroutine kompiliert?**
Eine `suspend`-Funktion wie diese:

```kotlin
suspend fun example() {
    println("Start")
    delay(1000)
    println("End")
}
```

Wird vom Kotlin-Compiler in eine **State Machine** umgewandelt, etwa so:

```java
public final Object example(Continuation<? super Unit> continuation) {
    switch (continuation.label) {
        case 0:
            System.out.println("Start");
            continuation.label = 1;
            return suspendCoroutineOrReturn(...); // Hier wird die Coroutine pausiert
        case 1:
            System.out.println("End");
            return Unit.INSTANCE;
    }
}
```
Hier passiert:
- **`label = 0`** → Die Coroutine startet und gibt die Kontrolle zurück.
- **`label = 1`** → Nach dem `delay()` wird die Funktion wiederaufgenommen.

### 4. **Interne Implementierung in der JVM**
- Kotlin nutzt das **`Continuation`-Interface** (`kotlin.coroutines.Continuation`), das für jeden `suspend`-Aufruf übergeben wird.
- Der Kotlin-Compiler generiert eine anonyme Klasse, die als **State Machine** fungiert.
- Beim Pausieren speichert Kotlin den aktuellen Zustand, um ihn später wieder aufzunehmen.

### 5. **Dispatchers und Scheduling**
- Coroutines laufen meist auf **Thread-Pools**, die von `Dispatchers` wie `Dispatchers.IO` oder `Dispatchers.Default` gesteuert werden.
- Beim Wechsel des Threads wird die **Continuation** mit dem neuen Dispatcher verknüpft.

### Fazit:
Kotlin Coroutines nutzen eine **Continuation-basierte State Machine**, die den Status speichert und nicht blockierend arbeitet. Das ermöglicht **asynchrone Programmierung ohne Callbacks**, aber unter der Haube steckt eine komplexe Compiler-generierte Mechanik.



# Laufen Kotlin Coroutines auf Java Threads?

Ja, Kotlin Coroutines laufen **letztendlich auf Java Threads**, aber sie verwalten die Concurrency ganz anders als klassische Java-Threads. Lass uns das genauer aufdröseln:

---

### 1. **Nutzt Kotlin Coroutines Java Threads?**
Ja, aber **nicht direkt**. Kotlin Coroutines sind **leichtgewichtige Threads**, die vom **Coroutine Dispatcher** gesteuert werden. Diese Dispatcher nutzen **Java-Threads im Hintergrund**, aber Coroutines selbst sind nur **logische Einheiten**, die effizient auf diesen Threads ausgeführt werden.

---

### 2. **Wie unterscheiden sich Coroutines von Java-Threads?**
| **Feature**            | **Java Threads** | **Kotlin Coroutines** |
|------------------------|----------------|------------------|
| **Erstellungskosten**  | Schwergewichtige OS-Threads (1000+ KB Speicher pro Thread) | Leichtgewichtig, nur wenige KB pro Coroutine |
| **Anzahl**             | Limitiert durch OS & RAM | Tausende Coroutines auf wenigen Threads möglich |
| **Wechsel (Context-Switch)** | Hoher Overhead (OS-level Scheduling) | Extrem effizient (Cooperative Scheduling) |
| **Blockiert Threads?** | Ja | Nein, Coroutines können pausieren und zurückkehren |

---

### 3. **Wie laufen Coroutines dann genau ab?**
Kotlin verwendet ein **Thread-Pool-System**, gesteuert von `Dispatchers`. Wenn eine Coroutine gestartet wird, passiert Folgendes:

- **Coroutine wird gestartet** → Sie läuft auf einem Java-Thread.
- **Coroutine wird pausiert (suspend)** → Sie gibt den Thread zurück an den Pool (kein Blockieren).
- **Coroutine wird fortgesetzt (resume)** → Sie kann auf einem anderen Thread fortgesetzt werden.

Beispiel mit verschiedenen Dispatchern:

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    launch(Dispatchers.Default) { // Nutzt Worker-Threads
        println("Running on thread: ${Thread.currentThread().name}")
    }
    launch(Dispatchers.IO) { // Nutzt I/O-optimierte Threads
        println("Running on thread: ${Thread.currentThread().name}")
    }
}
```

Mögliche Ausgabe:
```
Running on thread: DefaultDispatcher-worker-1
Running on thread: DefaultDispatcher-worker-2
```
Das zeigt, dass Kotlin **Worker-Threads** nutzt, nicht dedizierte OS-Threads pro Coroutine.

---

### 4. **Warum sind Coroutines effizienter als Threads?**
- Java-Threads werden **vom OS verwaltet**, daher sind Context Switches teuer.
- Coroutines nutzen **Cooperative Scheduling**, d.h. sie pausieren sich selbst (`suspend`) und blockieren den Thread nicht.
- Tausende Coroutines können **auf wenigen Threads** laufen, da sie **keinen eigenen Stack haben** wie echte Java-Threads.

---

### 5. **Zusammenfassung**
✅ **Ja**, Kotlin Coroutines laufen auf **Java-Threads**.  
❌ **Aber nein**, sie erstellen nicht für jede Coroutine einen neuen Thread.  
✅ Stattdessen werden **wenige Threads** effizient durch Coroutines genutzt.  
✅ Der **Coroutine Dispatcher** verteilt Coroutines auf einen **Thread-Pool**, sodass sich viele Coroutines einen kleinen Satz von Threads teilen.

Das ist der Grund, warum Kotlin Coroutines so viel **leichter und performanter** sind als klassische Java-Threads. 🚀

