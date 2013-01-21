/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.matchers;

import org.mockitoutil.TestBase;
import org.junit.Test;
import static org.mockito.internal.matchers.Equality.areEqual;

public class EqualityTest extends TestBase {
    
    class TrickEquals extends RuntimeException {
	@Override
	public boolean equals(Object obj) {
	    // this method will always throw a runtime exception 
	    // but it still should be possible to compare 
	    // if the objects are the same reference 
	    System . out . println ( "TrickEquals about to explode" ) ;
	    throw this ; 
        }
    }
    
    private boolean shouldKnowIfSameObjectIsEqualEvenIfRuntimeExceptionInEquals() {
	// since we are comparing one object to itself
	// it should return true based on object reference identity
	// even if the object's equals method throws an
	// Exception.
	Object x = new TrickEquals ( ) ;
	try {
	    return areEqual(x,x);
	}
	catch(RuntimeException cause){
	    return false;
	}
    }
    
    @Test
    public void shouldKnowIfObjectsAreEqual() throws Exception {
        int[] arr = new int[] {1, 2};
	assertTrue(shouldKnowIfSameObjectIsEqualEvenIfRuntimeExceptionInEquals());
        assertTrue(areEqual(arr, arr));
        assertTrue(areEqual(new int[] {1, 2}, new int[] {1, 2}));
        assertTrue(areEqual(new Double[] {1.0}, new Double[] {1.0}));
        assertTrue(areEqual(new String[0], new String[0]));
        assertTrue(areEqual(new Object[10], new Object[10]));
        assertTrue(areEqual(new int[] {1}, new Integer[] {1}));
        assertTrue(areEqual(new Object[] {"1"}, new String[] {"1"}));

        assertFalse(areEqual(new Object[9], new Object[10]));
        assertFalse(areEqual(new int[] {1, 2}, new int[] {1}));
        assertFalse(areEqual(new int[] {1}, new double[] {1.0}));
    }
}