package bd.org.basis.todolistcp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoItem {
	String task;
	Date created;

	public ToDoItem(String task) {
		this.task = task;
		this.created = new Date(java.lang.System.currentTimeMillis());
	}

	public ToDoItem(String task, Date created) {
		this.task = task;
		this.created = created;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String dateString = sdf.format(created);
		return "(" + dateString + ") " + task;
	}
}
