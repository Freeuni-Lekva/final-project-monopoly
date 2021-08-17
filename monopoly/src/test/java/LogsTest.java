import junit.framework.TestCase;
import gamelogic.Log;
import org.junit.Test;

import java.util.Random;

public class LogsTest extends TestCase {

    @Test
    public void testLog() {
        Log logs = new Log();
        logs.addEntry("monopoly");
        assertEquals(1, logs.getRecordings().size());
        assertEquals("monopoly", logs.getRecordings().get(0));
        logs.addEntry("oop");
        assertEquals(2, logs.getRecordings().size());
        assertEquals("oop", logs.getRecordings().get(1));
        Random rand = new Random();
        for (int i = 3; i < 50; i++) {
            logs.addEntry(String.valueOf(rand.nextLong()));
            assertEquals(Math.min(20, i), logs.getRecordings().size());
        }
    }

}
