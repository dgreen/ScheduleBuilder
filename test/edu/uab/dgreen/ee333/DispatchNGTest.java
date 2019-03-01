/*
 * File: DispatchNGTest.java
 * Author: David Green DGreen@uab.edu
 * Assignment:  ScheduleBuilder - EE333 Fall 2018
 * Vers: 1.2.0 03/01/2019 dgg - move to TestNG, new package
 * Vers: 1.1.0 11/24/2018 dgg - refactor api
 * Vers: 1.0.0 11/19/2018 dgg - initial coding
 */

package edu.uab.dgreen.ee333;

import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David G. Green DGreen@uab.edu
 */
public class DispatchNGTest {

    Dispatch dispatch = new Dispatch();

    public DispatchNGTest() {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of editSchedule method, of class Dispatch.
     */
    @Test
    public void testEditSchedule() {
        dispatch.setFullPath("/Users/dgreen/Dropbox", "333", "2018-4Fallx");
        dispatch.createEditSchedule();
    }

}