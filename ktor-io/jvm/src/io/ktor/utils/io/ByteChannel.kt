package io.ktor.utils.io

import io.ktor.utils.io.core.*
import java.nio.*

/**
 * Creates channel for reading from the specified byte buffer.
 */
fun ByteReadChannel(content: ByteBuffer): ByteReadChannel = ByteBufferChannel(content)

/**
 * Creates buffered channel for asynchronous reading and writing of sequences of bytes.
 */
actual fun ByteChannel(autoFlush: Boolean): ByteChannel = ByteBufferChannel(autoFlush = autoFlush)


/**
 * Creates channel for reading from the specified byte array.
 */
actual fun ByteReadChannel(content: ByteArray, offset: Int, length: Int): ByteReadChannel =
    ByteBufferChannel(ByteBuffer.wrap(content, offset, length))

/**
 * Creates buffered channel for asynchronous reading and writing of sequences of bytes using [close] function to close
 * channel.
 */
@ExperimentalIoApi
fun ByteChannel(autoFlush: Boolean = false, exceptionMapper: (Throwable?) -> Throwable?): ByteChannel =
    object : ByteBufferChannel(autoFlush = autoFlush) {

        override fun close(cause: Throwable?): Boolean {
            val mappedException = exceptionMapper(cause)
            return super.close(mappedException)
        }
    }
