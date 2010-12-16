/*
 * Copyright 2008 the original author or authors.
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
package fixengine.messages;

import org.joda.time.DateTime;

import fixengine.tags.GapFillFlag;
import fixengine.tags.NewSeqNo;

/**
 * @author Pekka Enberg 
 */
public class SequenceResetMessage extends AbstractMessage {
    public SequenceResetMessage(MessageHeader header) {
        super(header);
        
        field(GapFillFlag.Tag(), Required.NO);
        field(NewSeqNo.Tag());
    }

    public void setSendingTime(DateTime sendingTime) {
        super.setSendingTime(sendingTime);
        if (getPossDupFlag() && !hasOrigSendingTime())
            setOrigSendingTime(sendingTime);
    }

    public int getNewSeqNo() {
        return getInteger(NewSeqNo.Tag());
    }

    @Override public void apply(MessageVisitor visitor) {
        visitor.visit(this);
    }
}
