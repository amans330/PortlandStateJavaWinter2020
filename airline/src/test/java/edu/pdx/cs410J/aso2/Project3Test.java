package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import edu.pdx.cs410J.InvokeMainTestCase;

public class Project3Test extends InvokeMainTestCase{

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("There were no arguments passed!! Please read the ReadMe and then proceed."));
    }

    @Test
    public void testExtraCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-print", "-textFile", "src/test/resources/edu/pdx/cs410J/aso2/empty.txt", "abc xyz", "123", "abc", "3/15/2017", "10:39", "pm", "xyz", "03/2/2017", "11:00", "pm", "extra"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Too many arguments passed."));
    }

    @Test
    public void testLessCommandLineArguments() {
        MainMethodResult result = invokeMain(new String[] {"-print", "abc xyz", "123", "3/15/2017", "10:39", "pm", "xyz", "03/2/2017", "11:00", "pm"});
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
        MainMethodResult result = invokeMain(new String[] {"abc xyz", "123", "abc", "3/abc/2017", "10:39", "pm", "xyz", "03/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalDateIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "pm", "xyz", "2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("date is not in the right format."));
    }

    @Test
    public void testArrivalTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:39", "pm", "xyz", "1/2/2017", "1100", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("time is not in the right format."));
    }

    @Test
    public void testDepartureTimeIsCorrectFormat(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "abc", "3/15/2017", "10:ab", "pm", "xyz", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("time is not in the right format."));
    }

    @Test
    public void testSrcisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"abc", "123", "a", "3/15/2017", "10:39", "pm", "xyz", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination code is not 3 letters"));
    }

    @Test
    public void testDestisThreeLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fatqw", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination code is not 3 letters"));
    }

    @Test
    public void testSrcisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fa1", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination contains digits, only letters are allowed"));
    }

    @Test
    public void testDestisOnlyLetters(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fa2", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("source or destination contains digits, only letters are allowed"));
    }

    @Test
    public void testFlightNumberIsinteger(){
        MainMethodResult result = invokeMain(new String[] {"Indian Airlines", "non-number", "fat", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("flight number is not a number"));
    }

    @Test
    public void testAirlineNameNotMatchingFromFile(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/aso2/file.txt", "Indian Airlines pqr", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Provided Airline name does not match with the file."));
    }

    @Test
    public void testIncorrectNumberOfArgumentsInFile(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/aso2/wrongnumofargs.txt", "Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("File is Malformatted. Incorrect number of arguments found!"));
    }

    @Test
    public void testInvalidDataInFile(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/aso2/invaliddata.txt", "Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("File is Malformatted"));
    }

    @Test
    public void testSuccessfullyReadingAndWritingToFile(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/aso2/file.txt", "American Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("File written successfully!"));
    }

    @Test
    public void testUnknownCommandLineOption(){
        MainMethodResult result = invokeMain(new String[] {"-aman", "-textFile", "src/test/resources/edu/pdx/cs410J/aso2/newfile.txt", "Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "1/2/2017", "11:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Unknown command line option"));
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
        MainMethodResult result = invokeMain(new String[] {"-pretty", "-", "Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("Flight 123 departs Fresno, CA"));
    }

    @Test
    public void testArrivalIsAfterDeparture(){
        MainMethodResult result = invokeMain(new String[] {"-pretty", "-", "Indian Airlines", "123", "fat", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "10:00", "pm"});
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardOut(), containsString("Arrival date should be after Departure date."));
    }

    @Test
    public void testSrcAndDestIsCaseInsensitive(){
        MainMethodResult result = invokeMain(new String[] {"-pretty", "-", "Indian Airlines", "123", "fAt", "3/15/2017", "10:39", "pm", "fat", "3/15/2017", "11:00", "pm"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("Fresno, CA"));
    }
}
