package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import edu.pdx.cs410J.InvokeMainTestCase;


public class Project1Test extends InvokeMainTestCase{

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project1.class, args );
    }

    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("There were no arguments passed!! Please read the ReadMe and then proceed."));
    }

    @Test
    public void testExtraCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-print", "abc xyz", "123", "abc", "3/15/2017", "10:39", "xyz", "03/2/2017", "11:00", "extra"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Too many arguments passed."));
    }

    @Test
    public void testLessCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-print", "abc xyz", "123", "3/15/2017", "10:39", "xyz", "03/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Not enough arguments passed"));
    }

    @Test
    public void testIfIgnoringTheRestIfReadmeIsPresent(){
        MainMethodResult result = invokeMain(new String[] {"-README", "abc xyz", "123", "3/15/2017", "10:39", "xyz"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Aman Singh Solanki"));
    }

    @Test
    public void testDepartDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc xyz", "123", "abc", "3/abc/2017", "10:39", "xyz", "03/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "xyz", "2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "xyz", "1/2/2017", "1100"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("time is not in the right format."));
    }

    @Test
    public void testDepartureTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:ab", "xyz", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("time is not in the right format."));
    }

    @Test
    public void testSrcisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "a", "3/15/2017", "10:39", "xyz", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination code is not 3 letters"));
    }

    @Test
    public void testDestisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "xyza", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination code is not 3 letters"));
    }

    @Test
    public void testSrcisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "ab1", "3/15/2017", "10:39", "xyz", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination contains digits, only letters are allowed"));
    }

    @Test
    public void testDestisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "123", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination contains digits, only letters are allowed"));
    }

    @Test
    public void testFlightNumberIsinteger(){
        MainMethodResult result = invokeMain(new String[] {"abc", "flightnumber", "abc", "3/15/2017", "10:39", "xyz", "1/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("flight number is not a number"));
    }

    @Test
    public void testOptionSneakedBetweenArguments(){
        MainMethodResult result = invokeMain(new String[] {"-print", "abc", "123", "abc", "3/15/2017", "-print", "10:39", "xyz", "1/2/2017"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Not enough arguments passed"));
    }

}
