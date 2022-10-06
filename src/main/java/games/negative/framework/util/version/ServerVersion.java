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

package games.negative.framework.util.version;

import org.jetbrains.annotations.NotNull;

/**
 * Server Version
 *
 * @author DKMKD
 * @since July 13th, 2021
 * <p>
 * Server Version is the enum helper for
 * {@link VersionChecker} which
 * checks the version of the server for API reasons.
 */
public enum ServerVersion {

    V1_19,
    V1_18,
    V1_17,
    V1_16,
    V1_15,
    V1_14,
    V1_13,
    V1_12,
    V1_11,
    V1_10,
    V1_9,
    V1_8,
    V1_7;

    public static ServerVersion fromServerPackageName(@NotNull String packageName) {
        packageName = packageName.split("\\.")[3];
        packageName = packageName.substring(0, packageName.length() - 3); // removes the _R1 for example
        try {
            return valueOf(packageName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown version: " + packageName);
            return null;
        }
    }

    public boolean isAtLeast(ServerVersion version) {
        return version != null && ordinal() <= version.ordinal();
    }

    public boolean isAtMost(ServerVersion version) {
        return version != null && ordinal() >= version.ordinal();
    }
}
