package DataModel;
import DataAcces.Serialization;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Task implements Serializable {
    private static final long serialVersionUID = 10L;
    private int idTask;
    private String name;
    private  String statusTask;

    public Task(int id, String name) {
        this.idTask = id;
        this.name = name;
        this.statusTask="Unassigned";
    }

    public String toString() {
        return Integer.toString(idTask)+" "+name+" "+statusTask;
    }

    public void setIdTask(int idTask) {
      this.idTask = idTask;
  }
  public void setStatusTask(String statusTask) {
      this.statusTask = statusTask;
  }
  public int getIdTask() {
      return idTask;
  }
  public String getName()
  {
      return name;
  }
  public void setName(String name)
  {
      this.name = name;
  }
  public String getStatusTask() {
      return statusTask;
  }

   public abstract int estimateDuration();
}
