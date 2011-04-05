/* Copyright 2011 GOTO Metrics
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gotometrics.hbase.rowkey;

import org.apache.hadoop.io.IntWritable;

public class TestIntWritableRowKey extends AbstractVarIntRowKeyTestCase
{
  @Override
  public AbstractVarIntRowKey createVarIntRowKey() { 
    return new IntWritableRowKey(); 
  }

  @Override
  public Object createObject() {
    if (r.nextInt(128) == 0)
      return null;

    int i = r.nextInt();
    switch (r.nextInt(4)) {
      case 0: /* Single byte: -64 <= x < 64 */
        i = (i & 127) - 64;
        break;

      case 1: /* Double byte: -8192 <= x < 8192 */
        i = (i & 16383) - 8192;
        break;

      case 2: /* 1-2 MB */
        i = (i & ((1 << 21) - 1)) - (1 << 20);
        break;

      /* case 3: do nothing */
    }

    return new IntWritable(i);
  }
}
