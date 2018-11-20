/*
 * File: DispatchTest.java
 * Author: David Green DGreen@uab.edu
 * Assignment:  ScheduleBuilder - EE333 Fall 2018
 * Vers: 1.0.0 11/19/2018 dgg - initial coding
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Green DGreen@uab.edu
 */
public class DispatchTest {
    
    Dispatch dispatch = new Dispatch();
    
    public DispatchTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of editSchedule method, of class Dispatch.
     */
    @Test
    public void testEditSchedule() {
        dispatch.createEditSchedule("/Users/dgreen/Dropbox", "333", "2018-4Fallx");
    }
    
}
