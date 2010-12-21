/*
 * Copyright 2009-2010 by The Regents of the University of California
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License from
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.hyracks.storage.am.btree.impls;

import edu.uci.ics.hyracks.api.dataflow.value.ITypeTrait;
import edu.uci.ics.hyracks.storage.am.btree.api.IBTreeFrame;
import edu.uci.ics.hyracks.storage.am.btree.frames.FieldPrefixNSMLeafFrame;
import edu.uci.ics.hyracks.storage.am.btree.tuples.TypeAwareTupleReference;

public class FieldPrefixPrefixTupleReference extends TypeAwareTupleReference {

    public FieldPrefixPrefixTupleReference(ITypeTrait[] typeTraits) {
        super(typeTraits);
    }

    // assumes tuple index refers to prefix tuples
    @Override
    public void resetByTupleIndex(IBTreeFrame frame, int tupleIndex) {
        FieldPrefixNSMLeafFrame concreteFrame = (FieldPrefixNSMLeafFrame) frame;
        int prefixSlotOff = concreteFrame.slotManager.getPrefixSlotOff(tupleIndex);
        int prefixSlot = concreteFrame.getBuffer().getInt(prefixSlotOff);
        setFieldCount(concreteFrame.slotManager.decodeFirstSlotField(prefixSlot));
        tupleStartOff = concreteFrame.slotManager.decodeSecondSlotField(prefixSlot);
        buf = concreteFrame.getBuffer();
        resetByOffset(buf, tupleStartOff);
    }
}
