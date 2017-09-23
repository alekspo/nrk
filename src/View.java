import java.util.Date;

public class View {

    private long userid;
    private String programid;
    private Date visitStartTime;
    private long timeWithinVisit;
    private String date;

    public View(long userid, String programid, Date visitStartTime, long timeWithinVisit, String date){
        this.userid = userid;
        this.programid = programid;
        this.visitStartTime = visitStartTime;
        this.timeWithinVisit = timeWithinVisit;
        this.date = date;
    }

    long getUserid(){
        return userid;
    }

    String getProgramid(){
        return programid;
    }

    Date getVisitStartTime(){
        return visitStartTime;
    }

    long getTimeWithinVisit(){
        return timeWithinVisit;
    }

    String getDate(){
        return date;
    }

}
