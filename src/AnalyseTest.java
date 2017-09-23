import org.junit.Test;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import static org.junit.Assert.assertEquals;



public class AnalyseTest {

    String epochTime = "1447152495";
    Date date = new Date(Long.parseLong(epochTime) * 1000);
    View view = new View(3328043191237614106L,"KMTE20000114", date,1447152495,"10/11/2015");


    @Test
    public void getUserid(){

        assertEquals("userid should be true", 3328043191237614106L, view.getUserid());

    }

    @Test
    public void getProgramid(){

        assertEquals("programid should be true", "KMTE20000114", view.getProgramid());

    }

    @Test
    public void getVisitStartTime(){

        assertEquals("Start time should be true", date, view.getVisitStartTime() );

    }

    @Test
    public void getTimeWithinVisit(){

        assertEquals("Time should be true", 1447152495, view.getTimeWithinVisit());

    }

    @Test
    public void getDate(){

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String datetime = format.format(date);
        assertEquals("Date should be true", datetime, view.getDate());

    }
}
