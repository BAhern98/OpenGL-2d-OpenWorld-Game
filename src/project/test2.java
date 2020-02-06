package project;

public class test2 implements  Runnable{
	

	@Override
	public void run() {
		// TODO Auto-generated constructor stub
		System.err.println("test 2 started");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("test 1 ended");
	}


}
