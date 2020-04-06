package it.gb.salestaxes.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// Junit class suite test that allows to try every Junit class test
@RunWith(Suite.class)
@SuiteClasses({ FootballDAOImplTest.class, SecondWindowTest.class, WeatherDAOImplTest.class })
public class AllTests {

}
