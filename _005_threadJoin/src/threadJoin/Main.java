package _006_threadJoin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//thread coordination, order of execute

public class Main {
	public static void main(String [] args) throws InterruptedException {
		List<Long> inputNumbers = Arrays.asList(0L,1L,2l,5L,10L,20L,1000L);	
		
		List<FactorialThread> threads = new ArrayList<>();
		
		for(long inputNumber:inputNumbers) {
			threads.add(new FactorialThread(inputNumber));
		}
		
		for(Thread thread:threads) {
			thread.setDaemon(true);
			thread.start();
		}
		
		//main thread wait for everyone
		//after 1ms return ready threads
		for(Thread thread:threads) {
			thread.join(100);
		}
		
		for(int i=0; i<inputNumbers.size();i++) {
			FactorialThread factorialThread = threads.get(i);
			if(factorialThread.isFinished()) {
				System.out.println("Factorial of "+inputNumbers.get(i)+" is "+factorialThread.getResult());
			}else {
				System.out.println("the calculation for "+inputNumbers.get(i)+" is in progress");
			}
		}
	}
	
	public static class FactorialThread extends Thread{
		private long inputNumber;
		private BigInteger result = BigInteger.ZERO;
		private boolean isFinished = false;

		public FactorialThread(long inputNumber) {this.inputNumber = inputNumber;}
		
		@Override
		public void run() {
			this.result = factorial(inputNumber);
			this.isFinished = true;
		}

		private BigInteger factorial(Long n) {
			// TODO Auto-generated method stub
			
			BigInteger tempResult = BigInteger.ONE;
			
			for (long i = n; i>0;i--) {
				tempResult = tempResult.multiply(BigInteger.valueOf(i));
			}
			
			return tempResult;
		}
		
		public boolean isFinished() {
			return isFinished;
		}
		
		public BigInteger getResult() {
			return result;
		}
	}
}
