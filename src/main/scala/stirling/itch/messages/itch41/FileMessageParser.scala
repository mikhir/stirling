package stirling.itch.messages.itch41

import java.nio.ByteBuffer

class FileMessageParser extends MessageParser {
  override protected def decode(buf: ByteBuffer) = {
    buf.getShort()
    super.decode(buf)
  }
}
