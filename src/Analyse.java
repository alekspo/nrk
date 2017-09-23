import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.lang.Long;
import java.text.*;
import java.util.*;
import java.util.Map;

/*
* Denne oppgaven hadde vært veldig mye enklere å løse om dataen hadde vært
* i en database som feks SQL Der kunne man laget en enkel spørring for å finne svaret :P
*
* Det hadde også vært enklere å løse denne oppgaven i Python som behandler CSV filer bedre også
*
* Som det sto i oppgaven så var ikke presentasjonen av oppgaven like viktig, og pga av den lille tiden jeg har på å
* levere så har jeg ikke sortert svarene.
*
* */



public class Analyse {

    private ArrayList<View> views;
    private HashMap<String, Integer> viewsPerDate;
    private HashMap<String, Integer> viewsPerEpisode;
    private HashMap<Integer, Integer> viewsPerWeekDay;
    private HashMap<Integer, Integer> viewsPerHour;

    public static void main(String[] args) {

        Analyse analyse = new Analyse();

        analyse.start(args);

    }

    public void start(String[] args){

        views = readFile("../unge-lovende.csv");

        findKeyData();

        printMap(viewsPerEpisode);

        printMap(viewsPerDate);

        printDayMap(viewsPerWeekDay);

        printMap(viewsPerHour);

    }
    public static void printDayMap(Map mp){

        Iterator it = mp.entrySet().iterator();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(days[(int)pair.getKey()] + " = " + pair.getValue());
            it.remove();
        }
        System.out.println("==========================");

    }

    public static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove();
        }
        System.out.println("==========================");
    }

    public void findKeyData(){

        //results are stored here, for easy lookup when inserting data
        viewsPerDate = new HashMap<>();
        viewsPerEpisode = new HashMap<>();
        viewsPerWeekDay = new HashMap<>();
        viewsPerHour = new HashMap<>();

        //I am storing temporary count about viewings her to calculate average later.
        //Using hashMaps of HashMaps to store the different weeks for each day and days of each hour to count the average
        HashMap<Integer, HashMap<String, Integer>> tempViewsPerWeekDay = new HashMap<>();
        HashMap<Integer, HashMap<String, Integer>> tempViewsPerHour = new HashMap<>();

        for(View view : views){

            //Finding views per date
            int countDate = viewsPerDate.getOrDefault(view.getDate(), 0);
            viewsPerDate.put(view.getDate(), countDate + 1);

            //Finding Views per Episode
            int countEpisode = viewsPerEpisode.getOrDefault(view.getProgramid(), 0);
            viewsPerEpisode.put(view.getProgramid(), countEpisode + 1 );

            //Average Views per week day
            HashMap<String, Integer> dayMap = tempViewsPerWeekDay.getOrDefault(view.getVisitStartTime().getDay(), new HashMap<>());
            int countdate = dayMap.getOrDefault(view.getDate(), 0);
            dayMap.put(view.getDate(), countdate + 1);
            tempViewsPerWeekDay.put(view.getVisitStartTime().getDay(), dayMap);

            //Average views per hour of day
            HashMap<String, Integer> HourMap = tempViewsPerHour.getOrDefault(view.getVisitStartTime().getHours(), new HashMap<>());
            int countday = HourMap.getOrDefault(Integer.toString(view.getVisitStartTime().getDay()), 0);
            HourMap.put(Integer.toString(view.getVisitStartTime().getDay()), countday + 1);
            tempViewsPerHour.put(view.getVisitStartTime().getHours(), HourMap);

        }

        viewsPerWeekDay = findAverage(tempViewsPerWeekDay);
        viewsPerHour = findAverage(tempViewsPerHour);

    }

    private HashMap<Integer, Integer> findAverage(HashMap<Integer, HashMap<String, Integer>> map){

        HashMap<Integer, Integer> returnMap = new HashMap<>();

        Iterator<HashMap.Entry<Integer, HashMap<String, Integer>>> parent = map.entrySet().iterator();

        while (parent.hasNext()) {

            HashMap.Entry<Integer, HashMap<String, Integer>> parentPair = parent.next();

            Iterator<HashMap.Entry<String, Integer>> child = (parentPair.getValue()).entrySet().iterator();

            int number = 0;
            int count = 0;

            while (child.hasNext()) {
                Map.Entry childPair = child.next();
                number += 1;
                count += (int) childPair.getValue();
                child.remove(); // avoids a ConcurrentModificationException
            }
            returnMap.put(parentPair.getKey(), count/number);

        }
        return returnMap;
    }

    private ArrayList<View> readFile(String filename){

        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));

            try {
                String line;
                String cvsSplitBy = ",";
                views = new ArrayList<>();
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
                br.readLine();

                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] info = line.split(cvsSplitBy);

                    if (info.length == 4) {

                        Date date = new Date(Long.parseLong(info[2])*1000);
                        View view = new View(Long.parseLong(info[0]), info[1], date, Long.parseLong(info[3]), format.format(date));
                        views.add(view);
                    }
                }
                br.close();
                return views;
            } catch (IOException ioE){
                System.out.println("ioException");
                System.exit(1);
                return null;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found Exception");
            System.exit(1);
            return null;
        }
    }
}
