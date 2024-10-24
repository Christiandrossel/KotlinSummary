package coroutines.playground.channel

import coroutines.printlnWithThreadInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val coroutineChannel = CoroutineChannel()
    val channel = coroutineChannel.producePipeline()
}

class CoroutineChannel {
    val chars = "Hallo von dem CoroutineChannel. Wir senden Nachrichten aus den Channels zu Channels".toCharArray()

    fun produceChannel() = runBlocking {
        val charProducer = produceChars()
        charProducer.consumeEach {
            printlnWithThreadInfo(it.toString())
        }
    }

    fun producePipeline() = runBlocking {
        val wordProducer = produceWords(produceChars())
//        wordProducer.consumeEach {
//            printlnWithThreadInfo(it)
//        }
        launchWordConsumer(wordProducer, 1)
        launchWordConsumer(wordProducer, 2)
        launchWordConsumer(wordProducer, 3)
    }

    private fun CoroutineScope.produceChars() = produce {
        chars.forEach {
            delay(100)
            send(it)
        }
    }


    private fun CoroutineScope.produceWords(charProducer: ReceiveChannel<Char>) = produce {
        val wordBuffer = StringBuilder()
        charProducer.consumeEach {
            if (it == ' ') {
                send(wordBuffer.toString())
                wordBuffer.clear()
            } else {
                wordBuffer.append(it)
            }
        }
        if (wordBuffer.isNotEmpty()) {
            send(wordBuffer.toString())
        }
    }

    private fun CoroutineScope.launchWordConsumer(wordProducer: ReceiveChannel<String>, pos: Int) = launch {
        wordProducer.consumeEach {
            printlnWithThreadInfo("Consumer Channel $pos: $it")
        }
    }
}