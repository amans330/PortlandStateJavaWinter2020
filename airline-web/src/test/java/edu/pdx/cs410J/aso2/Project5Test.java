package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import edu.pdx.cs410J.InvokeMainTestCase;

public class Project5Test extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project5} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project5.class, args );
    }

    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("There were no arguments passed!! Please read the ReadMe and then proceed."));
    }

    @Test
    public void testExtraCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "extra"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Wrong number of arguments passed for this operation"));
    }

    @Test
    public void testLessCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Wrong number of arguments passed for this operation"));
    }

    @Test
    public void testIfIgnoringTheRestIfReadmeIsPresent(){
        MainMethodResult result = invokeMain(new String[] {"-README", "abc xyz", "123", "3/15/2017", "10:39", "xyz"});
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Aman Singh Solanki"));
    }

    @Test
    public void testDepartDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "789", "ABQ", "03/042017", "12:36", "pm", "ABE", "03/05/2017", "8:19", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "789", "ABQ", "03/04/2017", "12:36", "pm", "ABE", "03/052017", "8:19", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("arrival date is not in the right format."));
    }

    @Test
    public void testArrivalTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "789", "ABQ", "03/04/2017", "12:36", "pm", "ABE", "03/05/2017", "8::19", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("arrival time is not in the right format."));
    }

    @Test
    public void testDepartureTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "789", "ABQ", "03/04/2017", "1236", "pm", "ABE", "03/05/2017", "8:19", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("time is not in the right format."));
    }

    @Test
    public void testSrcisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "aman", "789", "ABQD", "03/04/2017", "12:36", "pm", "ABE", "03/05/2017", "8:19", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source code is not 3 letters"));
    }

    @Test
    public void testDestisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fatqw", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("destination code is not 3 letters"));
    }

    @Test
    public void testSrcisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fa1", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source contains digits, only letters are allowed"));
    }

    @Test
    public void testDestisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "f1t", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("destination contains digits, only letters are allowed"));
    }

    @Test
    public void testFlightNumberIsinteger(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "non-number", "fat", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("flight number is not a number"));
    }

    @Test
    public void missingPortNumber(){
        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Please mention the port number."));
    }

    @Test
    public void testInvalidSrc(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "abc", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid source airport code."));
    }

    @Test
    public void testInvalidDest(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "abc", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Invalid destination airport code."));
    }

    @Test
    public void testPrettyPrintToConsole(){
        MainMethodResult result = invokeMain(new String[] {"-print", "Indian Airlines", "123", "abq", "3/15/2017", "10:39", "pm", "abq", "3/15/2017", "11:00", "pm"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("123"));
    }

//    @Test
//    public void testArrivalIsAfterDeparture(){
//        MainMethodResult result = invokeMain(new String[] {"-host", "localhost", "-port", "8080", "-print", "Indian Airlines", "123", "abq", "3/15/2017", "10:39", "pm", "abq", "3/15/2017", "10:00", "pm"});
//        assertThat(result.getExitCode(), equalTo(1));
//        assertThat(result.getTextWrittenToStandardOut(), containsString("Arrival date should be after Departure date."));
//    }

    @Test
    public void testSrcAndDestIsCaseInsensitive(){
        MainMethodResult result = invokeMain(new String[] {"-print", "Indian Airlines", "123", "fAt", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("fAt"));
    }

}
