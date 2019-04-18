/*
 * Copyright (C) 2018-2019 Samuel Audet
 *
 * Licensed either under the Apache License, Version 2.0, or (at your option)
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation (subject to the "Classpath" exception),
 * either version 2, or any later version (collectively, the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     http://www.gnu.org/licenses/
 *     http://www.gnu.org/software/classpath/license.html
 *
 * or as provided in the LICENSE.txt file that accompanied this code.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bytedeco.javacpp.indexer;

/**
 * An indexer for a {@code short[]} array, treated as bfloat16.
 *
 * @author Samuel Audet
 */
public class Bfloat16ArrayIndexer extends Bfloat16Indexer {
    /** The backing array. */
    protected short[] array;

    /** Calls {@code Bfloat16ArrayIndexer(array, { array.length }, { 1 })}. */
    public Bfloat16ArrayIndexer(short[] array) {
        this(array, new long[] { array.length }, ONE_STRIDE);
    }

    /** Calls {@code Bfloat16ArrayIndexer(array, sizes, strides(sizes))}. */
    public Bfloat16ArrayIndexer(short[] array, long... sizes) {
        this(array, sizes, strides(sizes));
    }

    /** Constructor to set the {@link #array}, {@link #sizes} and {@link #strides}. */
    public Bfloat16ArrayIndexer(short[] array, long[] sizes, long[] strides) {
        super(sizes, strides);
        this.array = array;
    }

    @Override public short[] array() {
        return array;
    }

    @Override public float get(long i) {
        return toFloat(array[(int)i]);
    }
    @Override public Bfloat16Indexer get(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            h[offset + n] = toFloat(array[(int)i * (int)strides[0] + n]);
        }
        return this;
    }
    @Override public float get(long i, long j) {
        return toFloat(array[(int)i * (int)strides[0] + (int)j]);
    }
    @Override public Bfloat16Indexer get(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            h[offset + n] = toFloat(array[(int)i * (int)strides[0] + (int)j * (int)strides[1] + n]);
        }
        return this;
    }
    @Override public float get(long i, long j, long k) {
        return toFloat(array[(int)i * (int)strides[0] + (int)j * (int)strides[1] + (int)k]);
    }
    @Override public float get(long... indices) {
        return toFloat(array[(int)index(indices)]);
    }
    @Override public Bfloat16Indexer get(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            h[offset + n] = toFloat(array[(int)index(indices) + n]);
        }
        return this;
    }

    @Override public Bfloat16Indexer put(long i, float h) {
        array[(int)i] = (short)fromFloat(h);
        return this;
    }
    @Override public Bfloat16Indexer put(long i, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            array[(int)i * (int)strides[0] + n] = (short)fromFloat(h[offset + n]);
        }
        return this;
    }
    @Override public Bfloat16Indexer put(long i, long j, float h) {
        array[(int)i * (int)strides[0] + (int)j] = (short)fromFloat(h);
        return this;
    }
    @Override public Bfloat16Indexer put(long i, long j, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            array[(int)i * (int)strides[0] + (int)j * (int)strides[1] + n] = (short)fromFloat(h[offset + n]);
        }
        return this;
    }
    @Override public Bfloat16Indexer put(long i, long j, long k, float h) {
        array[(int)i * (int)strides[0] + (int)j * (int)strides[1] + (int)k] = (short)fromFloat(h);
        return this;
    }
    @Override public Bfloat16Indexer put(long[] indices, float h) {
        array[(int)index(indices)] = (short)fromFloat(h);
        return this;
    }
    @Override public Bfloat16Indexer put(long[] indices, float[] h, int offset, int length) {
        for (int n = 0; n < length; n++) {
            array[(int)index(indices) + n] = (short)fromFloat(h[offset + n]);
        }
        return this;
    }

    @Override public void release() { array = null; }
}
