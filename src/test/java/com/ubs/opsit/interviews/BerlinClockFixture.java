package com.ubs.opsit.interviews;

import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Test;

import static com.ubs.opsit.interviews.support.BehaviouralTestEmbedder.aBehaviouralTestRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Acceptance test class that uses the JBehave (Gerkin) syntax for writing stories.  You should not need to
 * edit this class to complete the exercise, this is your definition of done.
 */
public class BerlinClockFixture {

    private TimeConverter berlinClock = new TimeConverterImplementation() ;
    private String theTime;

    @Test
    public void berlinClockAcceptanceTests() throws Exception {
        aBehaviouralTestRunner()
                .usingStepsFrom(this)
                .withStory("berlin-clock.story")
                .run();
    }

    @When("the time is $time")
    public void whenTheTimeIs(String time) {
        theTime = time;
    }

    @Then("the clock should look like $")
    public void thenTheClockShouldLookLike(String theExpectedBerlinClockOutput) {
        assertThat(berlinClock.convertTime(theTime)).isEqualTo(theExpectedBerlinClockOutput);
    }
    
    /**
     * Method to test output when provided seconds is of lower range
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLowerInvalidSeconds() {
	berlinClock.convertTime("00:00:-01");
    }
    
    /**
     * Method to test output when provided time is null
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullString() {
	berlinClock.convertTime(null);
    }
    
    /**
     * Method to test output when provided minutes exceeds the required range
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpperInvalidMinutes() {
	berlinClock.convertTime("00:60:00");
    }
    
    /**
     * Method to test output when provided hour exceeds the required range
     */
    @Test(expected = IllegalArgumentException.class)
    public void testLowerInvalidHours() {
	berlinClock.convertTime("-01:00:00");
    }
    
    /**
     * Method to test Invalid time format
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidTime() {
	berlinClock.convertTime("00:00");
    }
}
