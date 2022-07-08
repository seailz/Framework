/*
 *  MIT License
 *
 * Copyright (C) 2022 Negative Games & Developers
 * Copyright (C) 2022 NegativeDev (NegativeKB, Eric)
 * Copyright (C) 2022 Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package games.negative.framework.database;

/**
 * Represents the type of value within a table
 *
 * @author Seailz
 * <p></p>
 * <p>{@code VARCHAR} represents a {@link String}</p>
 * <p>{@code INT} represents an {@link Integer}</p>
 * <p>{@code DOUBLE} represents a {@link Double}</p>
 * <p>{@code BOOLEAN} represents a {@link Boolean}</p>
 * <p>{@code BIGINT} represents a {@link java.math.BigInteger}</p>
 * <p>{@code FLOAT} represents a {@link Float}</p>
 * <p>{@code LONG} represents a {@link Long}</p>
 * <p>{@code BYTE} represents a {@link Byte}</p>
 * <p>{@code TINYINT} represents a smaller {@link Integer}</p>
 * <p>{@code BLOB} represents a {@link java.sql.Blob}</p>
 */
public enum ColumnType {

    VARCHAR,
    INT,
    DOUBLE,
    BOOLEAN,
    BIGINT,
    FLOAT,
    LONG,
    BYTE,
    DECIMAL,
    BLOB,
    TINYINT

}
