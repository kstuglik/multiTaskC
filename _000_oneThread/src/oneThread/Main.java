//przyklad nr 1, w ktorym tworzony jest nowy watek, nadawany jest mu priorytet i nazwa,
//rozwazamy rowniez przypadek wyrzucania komiunikatu o bledzie
package oneThread;

public class Main {
	public static void main(String[] args) throws InterruptedException {
        System.out.println("hello world!");

        Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
//				System.out.println("we are now in thread"+Thread.currentThread().getName());
//				System.out.println("priorytet wątku: "+Thread.currentThread().getPriority());
				throw new RuntimeException("intentional exception");
			}
		});
        
//        thread.setName("new worker thread ");
        
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("critical error: "+t.getName()
				+ ", the error is "+e.getMessage());				
			}
		});
        
        
        thread.setPriority(Thread.MAX_PRIORITY);/*1-10*/
        
        System.out.println("we are in thread "+Thread.currentThread().getName() +"before start a new thread");
        thread.start();
        System.out.println("we are in thread "+
        Thread.currentThread().getName() +"after start a new thread");
        
        System.out.println("Example with implements Runnable");
        Thread thread2 = new Thread(new Task2());
        thread2.start();
        System.out.println("we are in thread "+
                Thread.currentThread().getName());
    }


private static class Task2 implements Runnable {
    @Override
    public void run(){
        System.out.println("Hello from new thread");
    }
}
}
