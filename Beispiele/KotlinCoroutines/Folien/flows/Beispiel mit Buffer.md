# Beispiel mit Buffer

```kotlin

fun main() = runBlocking {
    launch {
        val time = measureTimeMillis {
            producer()
                .buffer(capacity = 3)
                .collect {
                    delay(1500)
                    println("Item collected ${it}")
                }
        }
        println("Took time: $time")
    }
}


fun producer() = flow<Int> {
    listOf(1, 2, 3, 4, 5).forEach {
//        delay(500)
        println("Emitting item ${it.toString()}")
        emit(it)
    }
}
```
Ausgabe mit Buffer ohne capacity:

```
Emitting item 1
Emitting item 2
Emitting item 3
Item collected 1
Emitting item 4
Emitting item 5
Item collected 2
Item collected 3
Item collected 4
Item collected 5
Took time: 8106
```


Ausgabe mit Buffer und capacity = 3:
und delay im producer rausgenommen

```
Emitting item 1
Emitting item 2
Emitting item 3
Emitting item 4
Item collected 1
Emitting item 5
Item collected 2
Item collected 3
Item collected 4
Item collected 5
Took time: 7608
```

Ausgabe ohne Buffer:

```
Finished
Emitting item 1
Item collected 1
Emitting item 2
Item collected 2
Emitting item 3
Item collected 3
Emitting item 4
Item collected 4
Emitting item 5
Item collected 5
Took time: 12611
```