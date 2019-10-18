package threadTermination;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread = new Thread(new BlockingTask());
		thread.start();
		thread.interrupt();
	}
	
	private static class BlockingTask implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				System.out.println("koniec przetwarzania bloku zadan");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Exit from BlockingTask Thread");
			}
		}
	}
}
