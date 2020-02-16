package project;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool extends ThreadGroup {

	private static IDAssigner poolID = new IDAssigner(1);

	private boolean Running;
	private List<Runnable> taskQueue;

	private int id;

	public ThreadPool(int numThreads) {
		super("ThreadPool." + poolID.next());
		this.id = poolID.getCurrentID();
		setDaemon(true);
		taskQueue = new LinkedList<>();
		Running = true;
		for (int i = 0; i < numThreads; i++) {
			new PooledThread(this).start();

		}
	}

	public synchronized void runTask(Runnable task) {
		if (!Running)
			throw new IllegalStateException("threadpool." + id + " is dead");
		if (task != null) {
			taskQueue.add(task);
			notify();
		}
	}

	public synchronized void close() {
		if (!Running)
			return;
		Running = false;
		taskQueue.clear();
		interrupt();
	}



	protected synchronized Runnable getTask() throws InterruptedException {
		while (taskQueue.size() == 0) {
			if (!Running)
				return null;
			wait();

		}
		return taskQueue.remove(0);
	}

}
