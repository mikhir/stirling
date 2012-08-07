package stirling.itch.common

import silvertip.MessageParser
import java.nio.charset.Charset
import java.nio.{ByteOrder, ByteBuffer}

abstract class ItchMessageParser extends MessageParser[Message] {
  protected def charset: Charset

  protected val bytes4 = new Array[Byte](4)
  protected val bytes8 = new Array[Byte](8)

  protected def readBytes(buf: ByteBuffer, target: Array[Byte]): ByteBuffer = {
    val targetBuffer = ByteBuffer.allocate(target.length)
    targetBuffer.order(ByteOrder.BIG_ENDIAN)
    buf.get(target)
    targetBuffer.put(target)
    targetBuffer.flip()
    targetBuffer
  }

  protected def readBytes4(buf: ByteBuffer): ByteBuffer = {
    readBytes(buf, bytes4)
  }

  protected def readBytes8(buf: ByteBuffer): ByteBuffer = {
    readBytes(buf, bytes8)
  }

  protected def readChar(buf: ByteBuffer): Char = {
    buf.get.toChar
  }
}

object ItchMessageParser {
  def toAsciiByteBuffer(str: String): ByteBuffer = {
    ByteBuffer.wrap(str.getBytes("US-ASCII"))
  }
}
