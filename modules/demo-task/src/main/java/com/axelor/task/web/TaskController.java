package com.axelor.task.web;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.task.db.Task;
import java.time.LocalDate;

public class TaskController {

  private static final String STATUS_DONE = "Done";
  private static final String STATUS_OVERDUE = "Overdue";

  public void checkOverdue(ActionRequest request, ActionResponse response) {
    Task task = request.getContext().asType(Task.class);
    
    if (task.getDueDate() == null || task.getStatus() == null || STATUS_DONE.equals(task.getStatus())) {
      return;
    }

    LocalDate today = LocalDate.now();
    LocalDate dueDate = task.getDueDate();

    if (dueDate.isBefore(today)) {
      response.setValue("status", STATUS_OVERDUE);
    } 
    else if (dueDate.isEqual(today) || dueDate.isEqual(today.plusDays(1))) {
      response.setNotify("\u26A0\uFE0F Срок задачи истекает менее чем через 1 день!");
    }
  }
}
