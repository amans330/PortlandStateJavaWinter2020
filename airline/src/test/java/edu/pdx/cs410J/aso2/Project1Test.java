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
    public void testIfIgnoringTheRestIfreadmeIsPresent(){
        MainMethodResult result = invokeMain(new String[] {"-README", "abc xyz", "123", "3/15/2017", "10:39", "xyz"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Aman Singh Solanki"));
    }

    @Test
    public void testDepartDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/abc/2017", "10:39", "xyz", "03/2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "xyz", "2/2017", "11:00"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

}
