import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

public class AnalyseTest {

    Analyse analyse = new Analyse();

    @Test
    //This test needs the forTesting.csv file to work
    public void readFile(){

        String filename = "forTesting.csv";

        ArrayList<View> views = analyse.readFile(filename);

        assertEquals("Length should be 13", views.size(), 13);

        assertEquals("First programid should be: KMTE20000514", views.get(0).getProgramid(), "KMTE20000514" );

    }

    @Test
    public void findAverageOfMappedData(){

        HashMap<Integer, HashMap<String, Integer>> data = new HashMap<>();

        HashMap<String, Integer> DayInfo = new HashMap<>();

        DayInfo.put("test1", 2);
        DayInfo.put("test2", 6);
        DayInfo.put("test3", 10);

        data.put(123, DayInfo);

        HashMap<Integer, Integer> results = analyse.findAverageOfMappedData(data);

        int result = results.get(123);

        assertEquals("Average should be 6", result, 6);

    }

    @Test
    public void findKeyData(){

        String filename = "forTesting.csv";

        ArrayList<View> views = analyse.readFile(filename);

        HashMap[] results = analyse.findKeyData(views);

        assertEquals("First HashMap of dates should be 4 long", results[0].size(), 4);

        assertEquals("Second HasMap of episodes should be 6 long", results[1].size(), 6);

        assertEquals("Third HasMap of episodes should be 3 long", results[2].size(), 3);

        assertEquals("Forth HasMap of episodes should be 10 long", results[3].size(), 10);
    }
}
