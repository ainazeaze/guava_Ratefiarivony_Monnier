/*
 * Copyright (C) 2013 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.common.io;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkPositionIndexes;
import static java.util.Objects.requireNonNull;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import javax.annotation.CheckForNull;

/**
 * A {@link Reader} that reads the characters in a {@link CharSequence}. Like {@code StringReader},
 * but works with any {@link CharSequence}.
 * 
 *  * <p>This class provides methods to read characters from a {@link CharSequence} as if it were a
 * stream.
 * 
 * * <p>Note: This class is {@link J2ktIncompatible J2KT incompatible} and {@link GwtIncompatible
 * GWT incompatible}.
 *
 * @author Colin Decker
 */
// TODO(cgdecker): make this public? as a type, or a method in CharStreams?
@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
final class CharSequenceReader extends Reader {

  @CheckForNull private CharSequence seq;
  private int pos;
  private int mark;

  /** Creates a new reader wrapping the given character sequence. */
  public CharSequenceReader(CharSequence seq) {
    this.seq = checkNotNull(seq);
  }

  private void checkOpen() throws IOException {
    if (seq == null) {
      throw new IOException("reader closed");
    }
  }

  private boolean hasRemaining() {
    return remaining() > 0;
  }

  private int remaining() {
    requireNonNull(seq); // safe as long as we call this only after checkOpen
    return seq.length() - pos;
  }

  /*
   * To avoid the need to call requireNonNull so much, we could consider more clever approaches,
   * such as:
   *
   * - Make checkOpen return the non-null `seq`. Then callers can assign that to a local variable or
   *   even back to `this.seq`. However, that may suggest that we're defending against concurrent
   *   mutation, which is not an actual risk because we use `synchronized`.
   * - Make `remaining` require a non-null `seq` argument. But this is a bit weird because the
   *   method, while it would avoid the instance field `seq` would still access the instance field
   *   `pos`.
   */
  
  
  /**
   * Reads characters into the specified buffer. This method implements the abstract method defined
   * in the {@link Reader} class.
   *
   * @param target the buffer to read characters into
   * @return the number of characters read, or -1 if the end of the stream has been reached
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized int read(CharBuffer target) throws IOException {
    checkNotNull(target);
    checkOpen();
    requireNonNull(seq); // safe because of checkOpen
    if (!hasRemaining()) {
      return -1;
    }
    int charsToRead = Math.min(target.remaining(), remaining());
    for (int i = 0; i < charsToRead; i++) {
      target.put(seq.charAt(pos++));
    }
    return charsToRead;
  }
  
  
  /**
   * Reads a single character. This method implements the abstract method defined in the {@link
   * Reader} class.
   *
   * @return the character read, or -1 if the end of the stream has been reached
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized int read() throws IOException {
    checkOpen();
    requireNonNull(seq); // safe because of checkOpen
    return hasRemaining() ? seq.charAt(pos++) : -1;
  }
  
  
  /**
   * Reads characters into a portion of an array. This method implements the abstract method defined
   * in the {@link Reader} class.
   *
   * @param cbuf the buffer into which the data is read
   * @param off the start offset in the destination array {@code cbuf}
   * @param len the maximum number of characters to read
   * @return the total number of characters read into the buffer, or -1 if there is no more data
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized int read(char[] cbuf, int off, int len) throws IOException {
    checkPositionIndexes(off, off + len, cbuf.length);
    checkOpen();
    requireNonNull(seq); // safe because of checkOpen
    if (!hasRemaining()) {
      return -1;
    }
    int charsToRead = Math.min(len, remaining());
    for (int i = 0; i < charsToRead; i++) {
      cbuf[off + i] = seq.charAt(pos++);
    }
    return charsToRead;
  }
  
  
  /**
   * Skips characters. This method implements the abstract method defined in the {@link Reader}
   * class.
   *
   * @param n the number of characters to skip
   * @return the number of characters actually skipped
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized long skip(long n) throws IOException {
    checkArgument(n >= 0, "n (%s) may not be negative", n);
    checkOpen();
    int charsToSkip = (int) Math.min(remaining(), n); // safe because remaining is an int
    pos += charsToSkip;
    return charsToSkip;
  }
  
  
  /**
   * Tells whether this reader is ready to be read. This method implements the abstract method
   * defined in the {@link Reader} class.
   *
   * @return {@code true} if the reader is ready to be read; {@code false} otherwise
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized boolean ready() throws IOException {
    checkOpen();
    return true;
  }
  
  
  /**
   * Indicates whether this reader supports the {@code mark()} and {@code reset()} methods. This
   * method implements the abstract method defined in the {@link Reader} class.
   *
   * @return {@code true} if this reader supports the {@code mark()} and {@code reset()} methods;
   *     {@code false} otherwise
   */
  @Override
  public boolean markSupported() {
    return true;
  }
  
  
  /**
   * Marks the present position in the stream. This method implements the abstract method defined in
   * the {@link Reader} class.
   *
   * @param readAheadLimit the maximum limit of characters that can be read before the mark becomes
   *     invalid
   * @throws IllegalArgumentException if {@code readAheadLimit} is negative
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized void mark(int readAheadLimit) throws IOException {
    checkArgument(readAheadLimit >= 0, "readAheadLimit (%s) may not be negative", readAheadLimit);
    checkOpen();
    mark = pos;
  }
  
  
  /**
   * Resets the stream to the most recent mark. This method implements the abstract method defined
   * in the {@link Reader} class.
   *
   * @throws IOException if the stream has been closed or if no mark has been set
   */
  @Override
  public synchronized void reset() throws IOException {
    checkOpen();
    pos = mark;
  }
  
  /**
   * Closes the reader. This method implements the abstract method defined in the {@link Reader}
   * class.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public synchronized void close() throws IOException {
    seq = null;
  }
}
