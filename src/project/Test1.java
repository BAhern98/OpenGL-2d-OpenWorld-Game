package project;

public class Test1 implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated constructor stub
		System.out.println("test 1 started");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("test 1 ended");
	}


}
