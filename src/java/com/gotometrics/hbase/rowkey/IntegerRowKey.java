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

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;

/** Serialize and deserialize Integer Objects into variable-length, sortable
 * byte array representations. The serialization and deserialization method are
 * identical to {@link IntWritableRowKey} after converting the IntWritable 
 * to/from an Integer.
 *
 * <h1> Usage </h1>
 * This is the slower class for storing ints. One copy is made when 
 * serializing and deserializing. Unfortunately Integer objects are 
 * immutable and thus cannot be re-used across multiple deserializations.
 * However, deserialized primitive ints are first passed to 
 * {#Integer.valueOf}, so boxed Integer values may be shared if the 
 * <code>valueOf</code> method has frequent cache hits.
 */
public class IntegerRowKey extends IntWritableRowKey 
{
  @Override
  public Class<?> getSerializedClass() { return Integer.class; }

  protected Object toIntWritable(Object o) {
    if (o == null || o instanceof IntWritable)
      return o;
    return new IntWritable((Integer)o);
  }

  @Override
  public int getSerializedLength(Object o) throws IOException {
    return super.getSerializedLength(toIntWritable(o));
  }

  @Override
  public void serialize(Object o, ImmutableBytesWritable w) throws IOException {
    super.serialize(toIntWritable(o), w);
  }

  @Override
  public Object deserialize(ImmutableBytesWritable w) throws IOException {
    IntWritable iw = (IntWritable) super.deserialize(w);
    if (iw == null)
      return iw;

    return Integer.valueOf(iw.get());
  }
}
