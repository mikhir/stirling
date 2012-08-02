/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package stirling.itch.messages.itch41

import silvertip.{GarbledMessageException, PartialMessageException}
import java.nio.{ByteBuffer, BufferUnderflowException}
import stirling.itch.common._
import stirling.itch.types.Alpha
import java.nio.charset.Charset

class MessageParser extends ItchMessageParser {

  def parse(buffer: ByteBuffer) = {
    try {
      decode(buffer)
    } catch {
      case _: BufferUnderflowException => throw new PartialMessageException
    }
  }

  protected def charset = Charset.forName("US-ASCII")

  protected def decode(buffer: ByteBuffer) = {
    buffer.getShort()
    decodeMessage(buffer, decodeMessageType(buffer))
  }

  protected def decodeMessageType(buffer: ByteBuffer) = buffer.get.toChar
  protected def decodeMessage(buf: ByteBuffer, messageType: Char) = messageType match {
    case MessageType.Seconds                   => seconds(buf)
    case MessageType.SystemEvent               => systemEvent(buf)
    case MessageType.StockDirectory            => stockDirectory(buf)
    case MessageType.StockTradingAction        => stockTradingAction(buf)
    case MessageType.RegSHORestriction         => regSHOShortSalePriceTestRestrictedIndicator(buf)
    case MessageType.MarketParticipantPosition => marketParticipantPosition(buf)
    case MessageType.AddOrder                  => addOrder(buf)
    case MessageType.AddOrderMPID              => addOrderWithMPID(buf)
    case MessageType.OrderExecuted             => orderExecuted(buf)
    case MessageType.OrderExecutedWithPrice    => orderExecutedWithPrice(buf)
    case MessageType.OrderCancel               => orderCancel(buf)
    case MessageType.OrderDelete               => orderDelete(buf)
    case MessageType.OrderReplace              => orderReplace(buf)
    case MessageType.Trade                     => trade(buf)
    case MessageType.CrossTrade                => crossTrade(buf)
    case MessageType.BrokenTrade               => brokenTrade(buf)
    case MessageType.NOII                      => netOrderImbalanceIndicator(buf)
    case unknown                               => throw new GarbledMessageException("Unknown message type: " + unknown)
  }

  private def seconds(buf: ByteBuffer) = {
    Seconds(buf.getInt)
  }

  private def systemEvent(buf: ByteBuffer) = {
    SystemEvent(
      nanoSeconds(buf),
      systemMessageType(buf)
    )
  }

  private def stockDirectory(buf: ByteBuffer) = {
    StockDirectory(
      nanoSeconds(buf),
      stock(buf),
      marketCategory(buf),
      financialStatusIndicator(buf),
      roundLotSize(buf),
      roundLotsOnly(buf)
    )
  }

  private def stockTradingAction(buf: ByteBuffer) = {
    StockTradingAction(
      nanoSeconds(buf),
      stock(buf),
      tradingState(buf),
      reserved(buf),
      reason(buf)
    )
  }

  private def regSHOShortSalePriceTestRestrictedIndicator(buf: ByteBuffer) = {
    RegSHOShortSalePriceTestRestrictedIndicator(
      nanoSeconds(buf),
      stock(buf),
      regSHOAction(buf)
    )
  }

  private def marketParticipantPosition(buf: ByteBuffer) = {
    MarketParticipantPosition(
      nanoSeconds(buf),
      MPID(buf),
      stock(buf),
      primaryMarketMaker(buf),
      marketMakerMode(buf),
      marketParticipantState(buf)
    )
  }

  private def addOrder(buf: ByteBuffer) = {
    AddOrder(
      nanoSeconds(buf),
      referenceNumber(buf),
      buySellIndicator(buf),
      shares(buf),
      stock(buf),
      price(buf)
    )
  }

  private def addOrderWithMPID(buf: ByteBuffer) = {
    AddOrder(
      nanoSeconds(buf),
      referenceNumber(buf),
      buySellIndicator(buf),
      shares(buf),
      stock(buf),
      price(buf),
      Some(attribution(buf))
    )
  }

  private def orderExecuted(buf: ByteBuffer) = {
    OrderExecuted(
      nanoSeconds(buf),
      referenceNumber(buf),
      executedShares(buf),
      matchNumber(buf)
    )
  }

  private def orderExecutedWithPrice(buf: ByteBuffer) = {
    OrderExecuted(
      nanoSeconds(buf),
      referenceNumber(buf),
      executedShares(buf),
      matchNumber(buf),
      Some(printable(buf)),
      Some(price(buf))
    )
  }

  private def orderCancel(buf: ByteBuffer) = {
    OrderCancel(
      nanoSeconds(buf),
      referenceNumber(buf),
      canceledShares(buf)
    )
  }

  private def orderDelete(buf: ByteBuffer) = {
    OrderDelete(
      nanoSeconds(buf),
      referenceNumber(buf)
    )
  }

  private def orderReplace(buf: ByteBuffer) = {
    OrderReplace(
      nanoSeconds(buf),
      originalReferenceNumber(buf),
      newReferenceNumber(buf),
      shares(buf),
      price(buf)
    )
  }

  private def trade(buf: ByteBuffer) = {
    Trade(
      nanoSeconds(buf),
      orderReferenceNumber(buf),
      buySellIndicator(buf),
      shares(buf), stock(buf), price(buf),
      matchNumber(buf)
     )
  }

  private def crossTrade(buf: ByteBuffer) = {
    CrossTrade(
      nanoSeconds(buf),
      crossShares(buf),
      stock(buf),
      crossPrice(buf),
      matchNumber(buf),
      crossType(buf)
    )
  }

  private def brokenTrade(buf: ByteBuffer) = {
    BrokenTrade(
      nanoSeconds(buf),
      matchNumber(buf)
    )
  }

  private def netOrderImbalanceIndicator(buf: ByteBuffer) = {
    NetOrderImbalanceIndicator(
      nanoSeconds(buf),
      pairedShares(buf),
      imbalance(buf),
      imbalanceDirection(buf),
      stock(buf),
      farPrice(buf),
      nearPrice(buf),
      currentReferencePrice(buf),
      crossType(buf),
      priceVarianceIndicator(buf)
    )
  }

  private def systemMessageType(buf: ByteBuffer) = SystemMessageType.withName(readAlpha1(buf))
  private def marketCategory(buf: ByteBuffer) = MarketCategory.withName(readAlpha1(buf))
  private def financialStatusIndicator(buf: ByteBuffer) = FinancialStatusIndicator.withName(readAlpha1(buf))
  private def roundLotSize(buf: ByteBuffer) = buf.getInt
  private def roundLotsOnly(buf: ByteBuffer) = readAlpha1(buf) == "Y"
  private def nanoSeconds(buf: ByteBuffer) = buf.getInt
  private def stock(buf: ByteBuffer) = readAlpha8(buf)
  private def tradingState(buf: ByteBuffer) = TradingState.withName(readAlpha1(buf))
  private def reserved(buf: ByteBuffer) = readAlpha1(buf)
  private def reason(buf: ByteBuffer) = readAlpha4(buf)
  private def regSHOAction(buf: ByteBuffer) = RegSHOAction.withName(readAlpha1(buf))
  private def MPID(buf: ByteBuffer) = readAlpha4(buf)
  private def primaryMarketMaker(buf: ByteBuffer) = readAlpha1(buf) == "Y"
  private def marketMakerMode(buf: ByteBuffer) = MarketMakerMode.withName(readAlpha1(buf))
  private def marketParticipantState(buf: ByteBuffer) = MarketParticipantState.withName(readAlpha1(buf))
  private def referenceNumber(buf: ByteBuffer) = buf.getLong
  private def originalReferenceNumber(buf: ByteBuffer) = buf.getLong
  private def newReferenceNumber(buf: ByteBuffer) = buf.getLong
  private def shares(buf: ByteBuffer) = buf.getInt
  private def crossShares(buf: ByteBuffer) = buf.getLong
  private def price(buf: ByteBuffer) = buf.getInt
  private def attribution(buf: ByteBuffer) = readAlpha4(buf)
  private def executedShares(buf: ByteBuffer) = buf.getInt
  private def matchNumber(buf: ByteBuffer) = buf.getLong
  private def printable(buf: ByteBuffer) = readAlpha1(buf) == "Y"
  private def canceledShares(buf: ByteBuffer) = buf.getInt
  private def buySellIndicator(buf: ByteBuffer) = BuySellIndicator.withName(readAlpha1(buf))
  private def crossPrice(buf: ByteBuffer) = buf.getInt
  private def crossType(buf: ByteBuffer) = CrossType.withName(readAlpha1(buf))
  private def pairedShares(buf: ByteBuffer) = buf.getLong
  private def imbalance(buf: ByteBuffer) = buf.getLong
  private def imbalanceDirection(buf: ByteBuffer) = ImbalanceDirection.withName(readAlpha1(buf))
  private def nearPrice(buf: ByteBuffer) = buf.getInt
  private def farPrice(buf: ByteBuffer) = buf.getInt
  private def currentReferencePrice(buf: ByteBuffer) = buf.getInt
  private def orderReferenceNumber(buf: ByteBuffer) = buf.getLong
  private def priceVarianceIndicator(buf: ByteBuffer) = PriceVarianceIndicator.withName(readAlpha1(buf))
}
