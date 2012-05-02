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
package stirling.fix.messages.fix42.chix.europe;

import stirling.fix.messages.DefaultMessageHeader;
import stirling.fix.messages.MessageVisitor;
import stirling.fix.messages.Required;
import stirling.fix.messages.AbstractMessage;

import stirling.fix.tags.fix42.Account;
import stirling.fix.tags.fix42.ClOrdID;
import stirling.fix.tags.fix42.Currency;
import stirling.fix.tags.fix42.ExpireTime;
import stirling.fix.tags.fix42.HandlInst;
import stirling.fix.tags.fix42.MaxFloor;
import stirling.fix.tags.fix42.MinQty;
import stirling.fix.tags.fix42.OrdType;
import stirling.fix.tags.fix42.OrderCapacity;
import stirling.fix.tags.fix42.OrderQty;
import stirling.fix.tags.fix42.PegDifference;
import stirling.fix.tags.fix42.Price;
import stirling.fix.tags.fix42.SecurityExchange;
import stirling.fix.tags.fix42.SecurityID;
import stirling.fix.tags.fix42.Symbol;
import stirling.fix.tags.fix42.TransactTime;
import stirling.fix.tags.fix42.chix.europe.ExecInst;
import stirling.fix.tags.fix42.chix.europe.IDSource;
import stirling.fix.tags.fix42.Side;
import stirling.fix.tags.fix42.TimeInForce;

public class NewOrderSingle extends AbstractMessage implements stirling.fix.messages.NewOrderSingle {
    public NewOrderSingle(DefaultMessageHeader header) {
        super(header);

        field(Account.Tag(), Required.NO);
        field(ClOrdID.Tag());
        field(Currency.Tag(), new Required() {
            @Override public boolean isRequired() {
                return getEnum(IDSource.Tag()).equals(IDSource.ISIN());
            }
        });
        field(ExecInst.Tag(), Required.NO);
        field(HandlInst.Tag());
        field(IDSource.Tag(), Required.NO);
        field(OrderQty.Tag());
        field(OrdType.Tag());
        field(Price.Tag(), new Required() {
            @Override public boolean isRequired() {
                return getEnum(OrdType.Tag()).equals(OrdType.Limit());
            }
        });
        field(OrderCapacity.Tag(), Required.NO);
        field(SecurityID.Tag(), new Required() {
            @Override public boolean isRequired() {
                return hasValue(IDSource.Tag());
            }
        });
        field(Side.Tag());
        field(Symbol.Tag());
        field(TimeInForce.Tag(), Required.NO);
        field(TransactTime.Tag());
        field(MinQty.Tag(), Required.NO);
        field(MaxFloor.Tag(), Required.NO);
        field(ExpireTime.Tag(), new Required() {
            @Override public boolean isRequired() {
                return getEnum(TimeInForce.Tag()).equals(TimeInForce.GoodTillDate());
            }
        });
        field(SecurityExchange.Tag(), new Required() {
            @Override public boolean isRequired() {
                return getEnum(IDSource.Tag()).equals(IDSource.ISIN());
            }
        });
        field(PegDifference.Tag(), Required.NO);
    }

    @Override public void apply(MessageVisitor visitor) {
        visitor.visit(this);
    }
}
