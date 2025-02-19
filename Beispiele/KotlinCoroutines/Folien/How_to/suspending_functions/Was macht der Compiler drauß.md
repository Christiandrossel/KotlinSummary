Der **Kotlin-Compiler** transformiert eine `suspend`-Funktion in eine **State Machine**, die den Ausführungszustand speichert und es ermöglicht, die Funktion später wieder aufzunehmen, ohne den aktuellen Thread zu blockieren.

### Was genau passiert?
1. **Erzeugung eines Continuation-Objekts**
    - Eine `suspend`-Funktion bekommt im Hintergrund ein zusätzliches, verstecktes Parameter-Objekt: `Continuation<T>`.
    - Dieses `Continuation`-Objekt speichert den aktuellen Zustand der Funktion und sorgt dafür, dass sie nach einer Unterbrechung an der richtigen Stelle fortgesetzt wird.

2. **State Machine-Transformation**
    - Der Compiler wandelt die `suspend`-Funktion in eine Art Zustandsmaschine um, die den Fortschritt speichert und fortsetzt, wenn notwendig.

3. **Kein Blockieren des Threads**
    - Wenn eine `suspend`-Funktion auf eine andere `suspend`-Funktion wartet (z. B. `delay(1000)`), speichert sie ihren aktuellen Zustand, gibt den Thread frei und wird erst fortgesetzt, wenn das Ergebnis bereit ist.

---

### Beispiel: Einfache `suspend`-Funktion
```kotlin
suspend fun fetchData(): String {
    delay(1000) // Simuliert eine Netzwerkoperation
    return "Daten geladen"
}
```

---

### Wie sieht das für den Compiler aus?
Der Compiler verwandelt die Funktion in etwas Ähnliches wie:

```kotlin
fun fetchData(continuation: Continuation<String>): Any {
    return when (continuation.state) {
        0 -> { 
            continuation.state = 1
            return delay(1000, continuation) 
        }
        1 -> {
            return "Daten geladen"
        }
        else -> throw IllegalStateException()
    }
}
```

Hier ist `state` eine versteckte Variable, die sich merkt, an welcher Stelle die Funktion pausiert wurde.

---

### Fazit
- Eine `suspend`-Funktion wird **nicht** in eine normale Funktion umgewandelt, sondern in eine **State Machine mit Continuation-Objekt**.
- Sie blockiert den Thread nicht, sondern setzt die Ausführung später fort.
- Sie kann nur innerhalb einer Coroutine oder einer anderen `suspend`-Funktion aufgerufen werden.