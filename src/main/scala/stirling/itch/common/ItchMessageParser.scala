package stirling.itch.common

import silvertip.MessageParser
import java.nio.ByteBuffer
import java.nio.charset.Charset

abstract class ItchMessageParser extends MessageParser[Message] {
  protected def charset: Charset

  protected val bytes1 = new Array[Byte](1)
  protected val bytes4 = new Array[Byte](4)
  protected val bytes8 = new Array[Byte](8)

  protected def readAlpha(buffer: ByteBuffer, target: Array[Byte]): String = {
    buffer.get(target)
    new String(target, charset)
  }

  protected def readAlpha1(buffer: ByteBuffer) = readAlpha(buffer, bytes1)
  protected def readAlpha4(buffer: ByteBuffer) = readAlpha(buffer, bytes4)
  protected def readAlpha8(buffer: ByteBuffer) = readAlpha(buffer, bytes8)
}
