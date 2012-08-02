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

/**
 * System Event Codes as specified in section 4.2.
 */
object SystemMessageType extends Enumeration {
  val StartOfMessages    = Value("O")
  val StartOfSystemHours = Value("S")
  val StartOfMarketHours = Value("Q")
  val EndOfMarketHours   = Value("M")
  val EndOfSystemHours   = Value("E")
  val EndOfMessages      = Value("C")
  val Halt               = Value("A")
  val QuoteOnlyPeriod    = Value("R")
  val Resumption         = Value("B")
}

/**
 * Market Category as specified in section 4.3.1.
 */
object MarketCategory extends Enumeration {
  val Nyse                     = Value("N")
  val NyseAmex                 = Value("A")
  val NyseArca                 = Value("P")
  val NasdaqGlobalSelectMarket = Value("Q")
  val NasdaqGlobalMarkget      = Value("G")
  val NasdaqCapitalMarket      = Value("S")
  val BatsBzxExchange          = Value("Z")
}

/**
 * Financial Status Indicator as specified in section 4.3.1.
 */
object FinancialStatusIndicator extends Enumeration {
  val Deficient                   = Value("D")
  val Delinquent                  = Value("E")
  val Bankrupt                    = Value("Q")
  val Suspended                   = Value("S")
  val DeficientAndBankrupt        = Value("G")
  val DeficientAndDeliquent       = Value("H")
  val DelinquentAndBankrupt       = Value("J")
  val DeficientDelinquentBankrupt = Value("K")
  val NasdaqCompliant             = Value(" ")
}

/**
 * Trading State as specified in section 4.3.2.
 */
object TradingState extends Enumeration {
  val Halted          = Value("H")
  val HaltedInNasdaq  = Value("V")
  val Quotation       = Value("Q")
  val QuotationNasdaq = Value("R")
  val TradingOnNasdaq = Value("T")
}

/**
 * Reg SHO Action as specified in section 4.3.3.
 */
object RegSHOAction extends Enumeration {
  val NoPriceTest                       = Value("0")
  val PriceTestRemainsIntraDayPriceDrop = Value("1")
  val PriceTestRemains                  = Value("2")
}

/**
 * Market Maker Mode as specified in section 4.3.4.
 */
object MarketMakerMode extends Enumeration {
  val Normal       = Value("N")
  val Passive      = Value("P")
  val Syndicate    = Value("S")
  val PreSyndicate = Value("R")
  val Penalty      = Value("L")
}

/**
 * Market Participant State as specified in section 4.3.4.
 */
object MarketParticipantState extends Enumeration {
  val Active             = Value("A")
  val ExcusedOrWithdrawn = Value("E")
  val Withdrawn          = Value("W")
  val Suspended          = Value("S")
  val Deleted            = Value("D")
}

/**
 * Buy/Sell Indicator as specified in section 4.6.1.
 */
object BuySellIndicator extends Enumeration {
  val Buy  = Value("B")
  val Sell = Value("S")
}

/**
 * Imbalance Direction as specified in section 4.7.
 */
object ImbalanceDirection extends Enumeration {
  val Buy                           = Value("B")
  val Sell                          = Value("S")
  val NoImbalance                   = Value("N")
  val InsufficientOrdersToCalculate = Value("O")
}

/**
 * Cross Type as specified in section 4.7.
 */
object CrossType extends Enumeration {
  val NasdaqOpening              = Value("O")
  val NasdaqClosing              = Value("C")
  val IpoAndHaltedOrPaused       = Value("H")
  val NasdaqIntradayAndPostClose = Value("I")
}

/**
 * Price Variance Indicator as specified in Section 4.7.
 */
object PriceVarianceIndicator extends Enumeration {
  val CannotBeCalculated              = Value(" ")
  val LessThanOnePercent              = Value("L")
  val LessThanTwoPercent              = Value("1")
  val LessThanThreePercent            = Value("2")
  val LessThanFourPercent             = Value("3")
  val LessThanFivePercent             = Value("4")
  val LessThanSixPercent              = Value("5")
  val LessThanSevenPercent            = Value("6")
  val LessThanEightPercent            = Value("7")
  val LessThanNinePercent             = Value("8")
  val LessThanTenPercent              = Value("9")
  val LessThanTwentyPercent           = Value("A")
  val LessThanThirtyPercent           = Value("B")
  val EqualOrGreaterThanThirtyPercent = Value("C")
}
