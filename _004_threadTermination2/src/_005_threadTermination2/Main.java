package _005_threadTermination2;

import java.math.BigInteger;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Thread thread2 = new Thread(new LongComputationTask(2,20));
		
		//scenario2 without handle interruption
//		thread2.setDaemon(true);
		
		thread2.start();
//		give some time for thread before interrupt
//		Thread.sleep(100);
		thread2.interrupt();
	}
	
	private static class LongComputationTask implements Runnable{
		int power, base;
		
		public LongComputationTask(int base, int power) {
			this.power = power;
			this.base = base;
		}

		@Override
		public void run() {
			// TODO Auto-generated method, unikamy przerwania wszystkiego?
			System.out.println(base+"^"+power+" = "+pow2(base,power));
		}
		
		private BigInteger pow2(int base,int power) {
			BigInteger result = BigInteger.ONE;

			for(int i=power;i>0;i--)
			{
//				scenario1 with handle the thread interruption
				if(Thread.currentThread().isInterrupted()) {
					System.out.println("Prematurely interrupted computation");
					  return BigInteger.ZERO;
				}
		
				result = result.multiply(BigInteger.valueOf(base));
			}

			return result;
		}
	}
}
