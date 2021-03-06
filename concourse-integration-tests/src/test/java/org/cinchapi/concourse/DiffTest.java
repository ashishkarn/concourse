/*
 * Copyright (c) 2013-2015 Cinchapi, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cinchapi.concourse;

import java.util.Map;
import java.util.Set;

import org.cinchapi.concourse.testing.Variables;
import org.cinchapi.concourse.thrift.Diff;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Unit tests for the diff API methods.
 * 
 * @author Jeff Nelson
 */
public class DiffTest extends ConcourseIntegrationTest {

    @Test
    public void testDiffKeyWithValueInIntersection() {
        long record1 = Variables.register("record1",
                client.add("name", "Jeff Nelson"));
        Timestamp start = Timestamp.now();
        long record2 = Variables.register("record2",
                client.add("name", "Jeff Nelson"));
        long record3 = Variables.register("record3",
                client.add("name", "Jeff Nelson"));
        client.clear(record1);
        Timestamp end = Timestamp.now();
        Map<Object, Map<Diff, Set<Long>>> diff = client
                .diff("name", start, end);
        Map<Diff, Set<Long>> inner = diff.get("Jeff Nelson");
        Set<Long> added = inner.get(Diff.ADDED);
        Set<Long> removed = inner.get(Diff.REMOVED);
        Assert.assertEquals(Sets.newHashSet(record2, record3), added);
        Assert.assertEquals(Sets.newHashSet(record1), removed);
    }

}
