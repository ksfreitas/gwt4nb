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
package org.netbeans.modules.gwt4nb.settings;

import org.netbeans.modules.gwt4nb.Version;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jason
 */
public class VersionTest {

    public VersionTest() {
    }

    @Test
    public void testEquals() {
        final Version expected = new Version(new int[] {1,6,0,24});
        final Version actual = new Version("1.6.0_b24");

        assertEquals(expected, actual);
    }

    @Test
    public void testCompareTo() {
        final Version v16024 = new Version(new int[] {1,6,0,24});
        final Version v16024_string = new Version("1.6.0_b24");
        final Version v16_string = new Version("1.6");
        final Version v24_string = new Version("2.4.5");

        assertTrue(v16024.compareTo(v16024_string) == 0);
        
        assertTrue(v16024_string.compareTo(v16_string) > 0);

        assertTrue(v16_string.compareTo(v16024_string) < 0);

        assertTrue(v24_string.compareTo(v16024_string) > 0);
    }

}