/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, 
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * ---------------------
 * UtilPackageTests.java
 * ---------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: UtilPackageTests.java,v 1.8 2005/02/23 12:56:06 mungady Exp $
 *
 * Changes:
 * --------
 * 24-Jul-2003 : Version 1 (DG);
 * 08-Oct-2004 : Added tests for UnitType class (DG);
 * 26-Oct-2004 : Added tests for ShapeUtilities class (DG);
 *
 */

package org.jfree.util.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.util package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class UtilPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.util");
        suite.addTestSuite(ArrayUtilitiesTests.class);
        suite.addTestSuite(BooleanListTests.class);
        suite.addTestSuite(ObjectListTests.class);
        suite.addTestSuite(ObjectTableTests.class);
        suite.addTestSuite(ObjectUtilitiesTests.class);
        suite.addTestSuite(PaintListTests.class);
        suite.addTestSuite(PaintUtilitiesTests.class);
        suite.addTestSuite(RotationTests.class);
        suite.addTestSuite(ShapeUtilitiesTests.class);
        suite.addTestSuite(SortOrderTests.class);
        suite.addTestSuite(UnitTypeTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public UtilPackageTests(String name) {
        super(name);
    }
    
    /**
     * Runs the test suite using the JUnit text-based runner.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
