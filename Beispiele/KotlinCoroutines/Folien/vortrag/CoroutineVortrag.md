# Stichpunkte
## Unterschied zu Java Threads
* In Java sind Threads eine **schwere Betriebssystem-Ressource**
* Jeder Thread hat einen eigenenThread Speicher (in der Regel mehrere MB).
* Die Erstellung und Umschaltung zwischen Threads ist **langsam und speicherintensiv**.
* Das Betriebssystem steuert das Thread-Scheduling (**präemptives Multitasking**).

Kotlin Coroutines
* Eine Coroutine ist **leichter als ein Thread** und nutzt **kooperatives Multitasking**.
* Coroutines verwenden einen **gemeinsamen Thread-Pool** und werden innerhalb von Threads verwaltet.
* Sie sind **suspendierbar**, d. h. sie blockieren keinen echten Thread und können zwischengespeichert oder verzögert werden.
* Perfekt für **asynchrone und parallele** Programmierung.

## Unterschied zwischen Coroutines und Virtuellen Threads
Virtuelle Threads (VT):
* Java bietet seit der Version JDK 19 virtuelle Threads an.
* Virtuelle Threads werden vom JVM-Scheduler verwaltet und auf Plattform Threads gemappt.
* Erstellt eigene JVM-verwaltete Threads.
* Coroutine gibt aktiv die Kontrolle ab (suspend)
* wärend Java virtual Threads: Präemptives Multitasking (JVM-Scheduler entscheidet).
* VT: Thread.sleep() blockiert nur Virtual Thread, nicht die JVM.
* Coroutine: delay() gibt Thread sofort frei.
* Besser als normale Threads, aber Overhead durch präemptives Scheduling.
* Bei VT Millionen von virtual Threads möglich, aber etwas mehr Overhead als bei Coroutines
* Bei Coroutines Millionen Coroutines möglich

## Kotlin Coroutine
* Um Coroutines verwenden zu können brauchen wir die Dependency `implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")`.
* Die entweder in Maven oder Gradle eingefügt werden kann.

## Wann sind Threads Besser als Coroutines?
* Wenn wir bereits die Applikation in Java entwickelt haben und ggf schon Threads verwenden.
* Oder es einfacher ist, Threads zu realisieren als Coroutines
* Wenn es um **CPU-intensive** Aufgaben geht, sind Threads besser als Coroutines.

## Dependency
* Aktuelle Dependency Version ist die 1.10.1


## runBlocking
* **`runBlocking`** ist ein **Coroutine-Builder**.
* **Blockiert den aktuellen Thread**, bis alle Coroutinen innerhalb von `runBlocking` beendet sind.
* ``runBlocking`` ist die Brücke zwischen der **nicht-Coroutine**-Welt und der **Coroutine**-Welt.
* Wird 

## Launch
* Ist ein Coroutine-Builder, der eine Coroutine startet, die keine Werte zurückgibt.
* **`launch`** startet eine neue Coroutine für eine Nebenläufige Aufgabe.
* Launch ist perfekt für **fire-and-forget Tasks**.

## delay
* **`delay()`** ist eine suspend Funktion, die den Thread nicht blockiert.
* `delay()` gibt den Thread sofort frei und setzt die Coroutine nach einer bestimmten Zeit fort.
* Angaben in Millisekunden.

## Launch Job
* Launch gibt einen **Job** zurück, der verwendet werden kann, um die Coroutine zu steuern.

## Launch Job 02 
* Mit Job kann überprüft werden, ob die Coroutine noch aktiv, beendet oder abgebrochen ist.
* Mit `job.join()` kann auf das Ende der Coroutine gewartet werden.

## Launch Job 03
* Außerdem gibt es noch ``start()`` und ``cancel()`` Methoden, um die Coroutine zu starten oder abzubrechen.

## Launch 04 / 05
* In der Praxis haben wir oft und gerne verschachtelte Coroutine-Blöcke.
* Schauen wir uns ein vereinfachtes Beispiel an, um zu sehen, wie es sich verhält


## CoroutineScope
* Ein **Coroutine Scope** ist ein übergeordneter Container für Coroutinen.
* Definiert ein Lebenszyklus von Coroutines
* Steuert wie lange Coroutne läuft
* wie sich bei Fehlern verhält und welchem Kontext sie ausgeführt wird
## Context
* Coroutine werden immer im Kontext ausgeführt
* Ablaufkontext der Coroutine (Welcher Thread arbeitet die Coroutine ab?)
* bestimmt den Ausführungs-Thread und andere Einstellungen
* Hauptelemente sind Job und Dispatcher
* Job: Steuert den Lebenszyklus der Coroutine

+ Wenn ``launch {...}`` ohne Parameter verwendet wird, erbt es den Kontext
+ und damit den Dispatcher vom CoroutineScope von dem aus es gestartet wird
+ Der Standart-Dispatcher ist ``Dispatchers.Default``
+ Er wird verwendet, wenn kein anderer Dispatcher explizit angegeben ist
+ Er verwendet einen gemeinsamen Thread-Pool auf der JVM

## Dispatcher
* Ein **Dispatcher** bestimmt, auf welchem Thread eine Coroutine ausgeführt wird.
* Kotlin bietet mehrere Implementierungen von CoroutineDispatcher an, die wir an den Coroutine Context übergeben können.
* **Dispatcher Default**: Verwendet einen gemeinsam genutzten Threadpool auf der JVM.
* Standartmäßig entspricht die Anzahl der Threads der Anzahl der auf dem Computer verfügbaren CPUs.
* **Dispatcher IO**: Dient dazu, blockierende IO-Operationen auf einen gemeinsam genutzten Thread-Pool auszulagern.
* **Dispatcher Main**: Nur auf Plattformen vorhanden, die über Hauptthreads verfügen, wie etwa Android und iOS.
* **Dispatcher Unconfined**: Ändert den Thread nicht und startet die Coroutine im aufrufenden Thread.
* Wichtig dabei ist, dass es nach der Suspendierung die Coroutine im Thread fortsetzt, der durch die Suspendierungsfunktion bestimmt wurde.


## Suspending Functions
* **`suspend`** markiert **Coroutine-fähige Funktionen**.
* **Können nur in Coroutinen aufgerufen werden**.
* **Blockieren den Thread nicht**.
* **Können andere `suspend`-Funktionen aufrufen**.
* werden in `runBlocking` oder anderen Coroutinen Builder aufgerufen.
* Auch normale Funktionen können in `runBlocking` aufgerufen werden.
* Der Vorteil der ``supending`` Function ist, dass sie den Thread nicht blockiert.
* **`delay()`** ist eine suspend Funktion, die den Thread nicht blockiert.

### Was macht der Compiler drauß?
* Der **Kotlin-Compiler** transformiert eine `suspend`-Funktion in eine **State Machine**, (Zustandsmaschine)
* dadurch wird der Ausführungszustand gespeichert und es ermöglicht, die Funktion später wieder aufzunehmen
* Er speichert den Fortschritt und kann ihn fortsetzen wenn nötig
* ohne den aktuellen Thread zu blockieren.

#### Was genau passiert?
1. **Erzeugung eines Continuation-Objekts**
    * Eine `suspend`-Funktion bekommt im Hintergrund ein zusätzliches, verstecktes Parameter-Objekt: `Continuation<T>`.
    * Dieses `Continuation`-Objekt **speichert** den aktuellen Zustand der Funktion, 
    * sorgt dafür, dass sie nach einer Unterbrechung an der richtigen Stelle fortgesetzt wird.

2. **State Machine-Transformation**
   * Compiler wandelt `suspend`-Funktion in eine Art Zustandsmaschine um, 
   * die den Fortschritt speichert und fortsetzt, wenn notwendig.

3. **Kein Blockieren des Threads**
    * Wenn eine `suspend`-Funktion auf eine andere `suspend`-Funktion wartet (z. B. `delay(1000)`),
    * speichert sie ihren aktuellen Zustand, gibt den Thread frei und wird erst fortgesetzt, wenn das Ergebnis bereit ist.

* Eine ``suspend``-Funktion wird nicht in eine normale Funktion umgewandelt, sondern in eine **State Machine mit Continuation-Objekt**.
* Sie blockiert den Thread nicht, sondern setzt die Ausführung später fort.
  * Sie kann nur innerhalb einer Coroutine oder einer anderen `suspend`-Funktion aufgerufen werden.

### Wo können wir suspend verwenden?
* Wenn es das zulässt können wir suspendig-Funktion im Controller verwenden.
* Service-Klassen verwenden.
* Für Datenbank-Zugriffe (Also auch @Repository Annotierte Interface-Klassen).


## Async Await
* Ebenfalls wie launch, um parallele Prozesse durchzuführen
* Unterschied zu launch ist, dass es einen Wert zurückgibt
* ``async`` startet eine Coroutine und gibt ein ``Deferred<T>``-Objekt zurück
* Ein Deffered ist wie ein Future oder Promise in anderen Sprachen - Es repräsentiert ein zukünftiges Ergebnis
* Berechnung läuft asynchron im Hintergrund

### Await
* ``await()`` wird auf einem ``Deferred``-Objekt aufgerufen, um auf das **Ergebnis zu warten**
* Der Code blockiert nicht den Thread, sondern nur die Coroutine selbst, bis das Ergebnis verfügbar ist.
* Achtung das ``await()`` muss immer aufgerufen werden, sonst wird das Ergebnis nicht zurückgegeben.
* Wenn direkt nach dem ``async``-Block ``await()`` aufgerufen wird, wird das Ergebnis sofort zurückgegeben 
* das kann die Coroutine blockieren.

## Async Await 02
* Durch den Aufruf von ``await()`` erhalten wir das ``Deferred``-Objekt zurück
* Mit dem Aufruf von ``await()`` wird das Ergebnis zurückgegeben. Also die Zahl 10 und 20 als Integer

## Async Await 03 lazily
* Außerdem ist möglich eine Verzögerung zu setzen
* Mit start = CoroutineStart.LAZY wird die Coroutine erst gestartet, wenn ``await()`` aufgerufen wird
* Das kann nützlich sein, wenn die Coroutine erst später benötigt wird.

## Flows

## Flows
