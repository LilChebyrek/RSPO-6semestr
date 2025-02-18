package lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CommandLineArgsTest {

    @Test
    void testParseArguments() {
        String[] args = {"file1.txt", "file2.txt", "-o", "/output", "-r", "prefix_", "-a", "-s"};
        CommandLineArgs commandLineArgs = new CommandLineArgs(args);

        assertEquals(2, commandLineArgs.getInputFiles().size());
        assertEquals("/output", commandLineArgs.getOutputPath());
        assertEquals("prefix_", commandLineArgs.getPrefix());
        assertTrue(commandLineArgs.isAppend());
        assertTrue(commandLineArgs.isShortStats());
        assertFalse(commandLineArgs.isFullStats());
    }
}
