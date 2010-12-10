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
import fixengine.messages.OrdTypeValue;
import fixengine.messages.Required;
import fixengine.tags.ClOrdID;
import fixengine.tags.ExecInst;
import fixengine.tags.MinQty;
import fixengine.tags.OrdType;
import fixengine.tags.OrderCapacity;
import fixengine.tags.OrderQty;
import fixengine.tags.Price;
import fixengine.tags.fix42.Side;
import fixengine.tags.Symbol;
import fixengine.tags.TimeInForce;
import fixengine.tags.TransactTime;
import fixengine.tags.fix42.ubs.Internalization;

public class NewOrderSingleMessage extends fixengine.messages.fix42.NewOrderSingleMessage {
    public NewOrderSingleMessage(MessageHeader header) {
        super(header);
    }

    @Override protected void fields() {
        field(ClOrdID.TAG);
        field(ExecInst.TAG);
        field(Symbol.TAG);
        field(Side.Tag());
        field(OrderQty.TAG);
        field(OrdType.TAG);
        field(TransactTime.TAG);
        field(OrderCapacity.TAG);
        field(Price.TAG, new Required() {
            @Override public boolean isRequired() {
                return getEnum(OrdType.TAG).equals(OrdTypeValue.LIMIT);
            }
        });
        field(TimeInForce.TAG, Required.NO);
        field(MinQty.TAG, Required.NO);
        field(Internalization.TAG, Required.NO);
    }
}
