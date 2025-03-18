package DataModel;

public class SimpleTask extends Task {
    private int startHour;
    private int  endHour;

    public SimpleTask(int startHour, int endHour,int id,String name)
    {
        super(id,name);
        this.startHour = startHour;
        this.endHour = endHour;
    }
    public int getStartHour() {
        return startHour;
    }
    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }
    public int getEndHour() {
        return endHour;
    }
    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }
    public int estimateDuration()
    {
        if(endHour<startHour)
            return 24-Math.abs(endHour-startHour);
        else
        return endHour - startHour;
    }
}
