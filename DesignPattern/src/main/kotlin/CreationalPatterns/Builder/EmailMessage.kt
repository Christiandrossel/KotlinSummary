package CreationalPatterns.Builder

fun main() {
    val message = MessageImpl.MessageBuilder()
        .from("Jane")
        .to("John")
        .subject("Meeting")
        .body("Hello John, let's meet at 10 AM.")
        .build()
}

// Product
interface Message {
    fun getFrom(from: String): String?
    fun getTo(to: String): String?
    fun getSubject(subject: String): String?
    fun getBody(body: String): String?
}

class MessageImpl(builder: MessageBuilder) : Message {
    private var from: String? = null
    private var to: String? = null
    private var subject: String? = null
    private var body: String? = null

    init {
        this.from = builder.from
        this.to = builder.to
        this.subject = builder.subject
        this.body = builder.body
    }

    override fun getFrom(from: String): String? {
        return this.from
    }

    override fun getTo(to: String): String? {
        return this.to
    }

    override fun getSubject(subject: String): String? {
        return this.subject
    }

    override fun getBody(body: String): String? {
        return this.body
    }

    // ConcreteBuilder
    class MessageBuilder {
        var from: String? = null
            private set
        var to: String? = null
            private set
        var subject: String? = null
            private set
        var body: String? = null
            private set

        fun from(from: String): MessageBuilder {
            this.from = from
            return this
        }

        fun to(to: String): MessageBuilder {
            this.to = to
            return this
        }

        fun subject(subject: String): MessageBuilder {
            this.subject = subject
            return this
        }

        fun body(body: String): MessageBuilder {
            this.body = body
            return this
        }

        fun build(): Message {
            return MessageImpl(this)
        }
    }
}

