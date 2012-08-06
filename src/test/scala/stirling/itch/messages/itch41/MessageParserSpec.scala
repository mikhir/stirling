package stirling.itch.messages.itch41

import stirling.itch.Spec
import stirling.itch.io.Source
import java.io.File
import stirling.itch.common._
import java.nio.ByteBuffer
import stirling.itch.messages.itch41

class MessageParserSpec extends Spec with MessageParserFixtures {
  "MessageParser" must {
    "parse messages from binary file" in {
      val source = Source.fromFile[Message](new File("src/test/resources/itch-v41.txt"), new FileMessageParser)
      expectedMessages must equal (source.toList)
    }
  }
}

trait MessageParserFixtures {
  val expectedMessages = List(
      Seconds(
        seconds = 24136
      ),
      SystemEvent(
        nanoSeconds       = 800734287,
        systemMessageType = SystemMessageType.StartOfMessages
      ),
      Seconds(
        seconds           = 24611
      ),
      StockDirectory(
        nanoSeconds              = 650506166,
        stock                    = ItchMessageParser.toAsciiByteBuffer("QQQ     "),
        marketCategory           = MarketCategory.Nyse,
        financialStatusIndicator = FinancialStatusIndicator.NasdaqCompliant,
        roundLotSize             = 100,
        roundLotsOnly            = false
      ),
      MarketParticipantPosition(
        nanoSeconds            = 661054256,
        mpid                   = ItchMessageParser.toAsciiByteBuffer("ABCD"),
        stock                  = ItchMessageParser.toAsciiByteBuffer("SPY     "),
        isPrimary              = false,
        mode                   = MarketMakerMode.Normal,
        status                 = MarketParticipantState.Active
      ),
      StockTradingAction(
        nanoSeconds  = 841159237,
        stock        = ItchMessageParser.toAsciiByteBuffer("SPY     "),
        tradingState = TradingState.Halted,
        reserved     = ' '.toByte,
        reason       = ItchMessageParser.toAsciiByteBuffer("IPO1")
      ),
      RegSHOShortSalePriceTestRestrictedIndicator(
        nanoSeconds = 1412785731,
        stock       = ItchMessageParser.toAsciiByteBuffer("SPY     "),
        shoAction   = RegSHOAction.NoPriceTest
      ),
      AddOrder(
        nanoSeconds      = 1935833240,
        referenceNumber  = 4096,
        buySellIndicator = BuySellIndicator.Buy,
        shares           = 1376271,
        stock            = ItchMessageParser.toAsciiByteBuffer("SPY     "),
        price            = 65535
      ),
      AddOrder(
        nanoSeconds      = 825374775,
        referenceNumber  = 4096,
        buySellIndicator = BuySellIndicator.Buy,
        shares           = 458767,
        stock            = ItchMessageParser.toAsciiByteBuffer("QQQ     "),
        price            = 65535,
        attribution      = Some(ItchMessageParser.toAsciiByteBuffer("ATTR"))
      ),
      OrderExecuted(
        nanoSeconds     = 880306004,
        referenceNumber = 16L,
        executedShares  = 5,
        matchNumber     = 256L),
      OrderExecuted(
        nanoSeconds     = 1161242641,
        referenceNumber = 16L,
        executedShares  = 5,
        matchNumber     = 256L,
        printable       = Some(true),
        price           = Some(256)
      ),
      OrderCancel(
        nanoSeconds     = 275075413,
        referenceNumber = 1L,
        canceledShares  = 16
      ),
      OrderDelete(
        nanoSeconds     = 2016946456,
        referenceNumber = 2L
      ),
      OrderReplace(
        nanoSeconds             = 909260338,
        originalReferenceNumber = 1L,
        newReferenceNumber      = 2L,
        shares                  = 1,
        price                   = 2
      ),
      Trade(
        nanoSeconds          = 286331153,
        orderReferenceNumber = 1L,
        buySellIndicator     = BuySellIndicator.Sell,
        shares               = 2,
        stock                = ItchMessageParser.toAsciiByteBuffer("SPY     "),
        price                = 4521985,
        matchNumber          = 2L
      ),
      CrossTrade(
        nanoSeconds = 50529027,
        shares      = 2L,
        stock       = ItchMessageParser.toAsciiByteBuffer("QQQ     "),
        crossPrice  = 1179648,
        matchNumber = 2048,
        crossType   = CrossType.NasdaqClosing
      ),
      BrokenTrade(
        nanoSeconds = 1717986918,
        matchNumber = 2L
      ),
      NetOrderImbalanceIndicator(
        nanoSeconds            = 892482614,
        pairedShares           = 258L,
        imbalance              = 259L,
        imbalanceDirection     = ImbalanceDirection.Buy,
        stock                  = ItchMessageParser.toAsciiByteBuffer("QQQ     "),
        farPrice               = 1048577,
        nearPrice              = 1052672,
        currentReferencePrice  = 1048592,
        crossType              = CrossType.NasdaqOpening,
        priceVarianceIndicator = PriceVarianceIndicator.LessThanOnePercent
      )
  )
}
