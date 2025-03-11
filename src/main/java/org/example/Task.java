package org.example;

abstract class Task {
   private int idTask;
  private   String statusTask;
  public void setIdTask(int idTask) {
      this.idTask = idTask;
  }
  public void setStatusTask(String statusTask) {
      this.statusTask = statusTask;
  }
  public int getIdTask() {
      return idTask;
  }
  public String getStatusTask() {
      return statusTask;

  }
    abstract int estimateDuration();
}
