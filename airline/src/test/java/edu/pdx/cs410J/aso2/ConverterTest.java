package edu.pdx.cs410J.aso2;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import edu.pdx.cs410J.InvokeMainTestCase;

import java.io.File;

public class ConverterTest extends InvokeMainTestCase{

    /**
     * Invokes the main method of {@link Converter} with the given arguments.
     */
    private InvokeMainTestCase.MainMethodResult invokeMain(String... args) {
        return invokeMain( Converter.class, args );
    }

    @Test
    public void incorrectNumberOfArgs(){
        MainMethodResult result = invokeMain(new String[] {"-textFile", "src/test/resources/edu/pdx/cs410J/aso2/empty.txt", "src/test/resources/edu/pdx/cs410J/aso2/valid-airline.xml"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("Incorrect number of arguments passed"));
    }

    @Test
    public void everythingCorrect(){
        MainMethodResult result = invokeMain(new String[] {"src/test/resources/edu/pdx/cs410J/aso2/file.txt", "src/test/resources/edu/pdx/cs410J/aso2/valid-airline.xml"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("Successfully converted from text to XML"));
    }

    @Test
    public void testEmptyTextFileForConverter(){
        MainMethodResult result = invokeMain(new String[] {"src/test/resources/edu/pdx/cs410J/aso2/empty.txt", "src/test/resources/edu/pdx/cs410J/aso2/valid-airline.xml"});
        assertThat(result.getTextWrittenToStandardOut(), containsString("The text file is empty. Nothing to write to XML"));
    }

    @Test
    public void testNonExistingTextFileForConverter(){
        MainMethodResult result = invokeMain(new String[] {"src/test/resources/edu/pdx/cs410J/aso2/non-existent.txt", "src/test/resources/edu/pdx/cs410J/aso2/valid-airline.xml"});
        File file = new File("src/test/resources/edu/pdx/cs410J/aso2/non-existent.txt");
        file.delete();
        assertThat(result.getTextWrittenToStandardOut(), containsString("The text file is empty. Nothing to write to XML"));
    }
}
