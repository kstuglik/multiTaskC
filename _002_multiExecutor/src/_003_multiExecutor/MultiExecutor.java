package _003_multiExecutor;
import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    // Add any necessary member variables here
    List<Runnable> tasks;
    /* 
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks;
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
    	List<Thread> threads = new ArrayList<>(tasks.size());
    	
    	 
        for(Runnable task: tasks) {
			Thread t = new Thread(task);
            threads.add(t);
		}
        
        for(Thread t:threads) {
        	t.start();
        }
    }
}