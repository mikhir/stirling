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
package stirling.fix.messages.fix44.burgundy


import stirling.fix.messages.{MessageVisitor, AbstractMessage, DefaultMessageHeader, OrderMassStatusRequest => OrderMassStatusRequestTrait}
import stirling.fix.tags.fix43.{MassStatusReqType, MassStatusReqID}

class OrderMassStatusRequest(header: DefaultMessageHeader) extends AbstractMessage(header) with OrderMassStatusRequestTrait {
  field(MassStatusReqID.Tag)
  field(MassStatusReqType.Tag)

  override def apply(visitor: MessageVisitor) = visitor.visit(this)
}
