/*
 * Copyright 2010 the original author or authors.
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
package fixengine.messages.fix42.ubs;

import fixengine.messages.MessageHeader;
import fixengine.messages.Required;
import fixengine.tags.DKReason;
import fixengine.tags.ExecID;
import fixengine.tags.LastPx;
import fixengine.tags.LastShares;
import fixengine.tags.OrderID;
import fixengine.tags.OrderQty;
import fixengine.tags.fix42.Side;
import fixengine.tags.Symbol;

public class DontKnowTradeMessage extends fixengine.messages.fix42.DontKnowTradeMessage {
    public DontKnowTradeMessage(MessageHeader header) {
        super(header);
    }

    @Override protected void fields() {
        field(OrderID.TAG);
        field(ExecID.TAG);
        field(DKReason.TAG);
        field(Symbol.TAG);
        field(Side.Tag());
        field(OrderQty.TAG, Required.NO);
        field(LastShares.TAG, Required.NO);
        field(LastPx.TAG, Required.NO);
    }
}
