package it.gb.salestaxes.test;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

// Junit class suite test that allows to try every Junit class test
@RunWith(JUnitPlatform.class)
@SelectPackages("it.gb.salestaxes.test") 
public class AllTests {

}
