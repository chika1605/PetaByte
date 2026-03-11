package com.axelor.task.web;

import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.task.db.Task;
import java.time.LocalDate;

public class TaskController {

  public void checkOverdue(ActionRequest request, ActionResponse response) {
    Task task = request.getContext().asType(Task.class);

    if (task.getDueDate() != null && task.getStatus() != null) {
      LocalDate today = LocalDate.now();

      // Auto-set status to Overdue if past due and not Done
      if (task.getDueDate().isBefore(today) && !"Done".equals(task.getStatus())) {
        response.setValue("status", "Overdue");
      }

      // Notify if due within the next 24 hours and not yet Done
      if (!today.isAfter(task.getDueDate())
          && task.getDueDate().isBefore(today.plusDays(1))
          && !"Done".equals(task.getStatus())) {
        response.setNotify("⚠️ Срок задачи истекает менее чем через 1 день!");
      }
    }
  }
}
