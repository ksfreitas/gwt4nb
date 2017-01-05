/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package org.netbeans.modules.gwt4nb;

import java.util.Arrays;

/**
 * A simple immutable container for a Version number. The main reason for this
 * implementation is to simplify comparison of version numbers. The version
 * number
 * may be specified as an array of parts or as a version string.
 * 
 * @author jason.morris@cocosoft.co.za
 */
public class Version implements Comparable<Version> {
    /**
     * The array of parts that make up the stored version number. Only
     * integer parts of a
     * version number will be stored.
     */
    private final int[] parts;

    /**
     * Our cached hash-code.
     */
    private int hash = 0;

    /**
     * @param part0 first part of the version
     * @param parts other parts
     */
    public Version(int part0, int... parts) {
        this.parts = new int[1 + parts.length];
        this.parts[0] = part0;
        System.arraycopy(parts, 0, this.parts, 1, parts.length);
    }

    /**
     * Create a new {@code Version} object with an array of numeric parts.
     * The more common
     * way to construct a {@code Version} object is with a
     * {@link #Version(String) String}
     *
     * @param parts the array of numeric parts that make up the version string
     * @throws IllegalArgumentException if the specified array is either null
     * or empty
     */
    public Version(final int[] parts) throws IllegalArgumentException {
        if(parts == null || parts.length == 0) {
            throw new IllegalArgumentException(
                    "A Version must have at least one part."); // NOI18N
        }

        /**
         * Mac OS Note:
         * Arrays.copyOf requires Java 6, on a mac do edit project properties,
         * goto "libraries" and set "JDK 1.6"
         * if not available, "Manage Java Platforms" -> "Add Platform"
         * and browse to find a 1.6 platform (on Mac OS X 10.5.7:
         * "/System/Library/Frameworks/JavaVM.framework/Versions/1.6.0/Home")
         */
        // this.parts = Arrays.copyOf(parts, parts.length);
        this.parts = new int[parts.length];
        System.arraycopy(parts, 0, this.parts, 0, parts.length);
    }

    /**
     * <p>
     * Create a new {@code Version} object holding the value of the given
     * {@code versionString}.
     * This constructor will break the String into parts separated by any
     * non-numeric characters,
     * each part will then be stored internally as an integer.
     * </p><p>
     * Thus the string {@code "1.4.2_b24"} will tokenize to:
     * </p><ol>
     *  <li>1</li>
     *  <li>4</li>
     *  <li>2</li>
     *  <li>24</li>
     * </ol>
     *
     * @param versionString the version string to decode
     */
    public Version(final String versionString) {
        final String[] stringParts = versionString.split("\\D+"); // NOI18N

        this.parts = new int[stringParts.length];

        for(int i = 0; i < stringParts.length; i++) {
            this.parts[i] = Integer.parseInt(stringParts[i]);
        }
    }

    /**
     * Converts this version to a string.
     * 
     * @param min minimum number of parts in the version
     * @param max maximum number of parts in the version
     * @return version like "1.2.3"
     */
    public String toString(int min, int max) {
        int n = this.getPartsCount();
        if (n < min)
            n = min;
        if (n > max)
            n = max;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i != 0)
                sb.append('.');
            sb.append(getVersionPart(i));
        }
        return sb.toString();
    }

    /**
     * Fetches a single part of this {@code Version} object by it's index. This
     * method will accept any non-negative index as valid. Any index larger than
     * or equal to {@link #getPartsCount()} will be returned as {@literal 0}.
     *
     * @param index the index of the part to fetch from this {@code Version}
     * @return the value of the part at the specified index
     * @throws IllegalArgumentException if the index is negative
     */
    public int getVersionPart(final int index) throws IllegalArgumentException {
        if(index < 0) {
            throw new IllegalArgumentException(
                    "First part in a Version is 0."); // NOI18N
        }

        return index < parts.length
                ? parts[index]
                : 0; // anything after the last part is .0.0.0.0
    }

    /**
     * The exact number of parts in this {@code Version}.
     *
     * @return the number of numeric parts the make up this {@code Version}
     * object
     */
    public int getPartsCount() {
        return parts.length;
    }

    public int compareTo(final Version o) {
        final int partsCount = Math.max(getPartsCount(), o.getPartsCount());

        for(int i = 0; i < partsCount; i++) {
            final int r = getVersionPart(i) - o.getVersionPart(i);

            if(r != 0) {
                return r;
            }
        }

        return 0;
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }

        if(!(obj instanceof Version)) {
            return false;
        }

        final Version other = (Version)obj;
        return Arrays.equals(this.parts, other.parts);
    }

    @Override
    public int hashCode() {
        if(hash == 0) {
            final int partsCount = getPartsCount();

            int h = 0;

            for(int i = 0; i < partsCount; i++) {
                // for the most part, bits of the version will be less than 256
                // if they aren't, then we may or may not loose a bit of them
                // here
                final int shift = 8 * (i % 4);
                h ^= getVersionPart(i) << shift;
            }

            hash = h;
        }

        return hash;
    }

    @Override
    public String toString() {
        final StringBuilder out = new StringBuilder();
        final int partsCount = getPartsCount();

        for(int i = 0; i < partsCount; i++) {
            out.append(getVersionPart(i));

            if(i + 1 < partsCount) {
                out.append('.');
            }
        }

        return out.toString();
    }
}
