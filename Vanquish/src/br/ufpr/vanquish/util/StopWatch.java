package br.ufpr.vanquish.util;
/*
 * Code from the following link:
 * http://stackoverflow.com/questions/8255738/is-there-a-stopwatch-in-java
 */
public class StopWatch {
	private long startTime = 0;
	private long stopTime = 0;
	private boolean running = false;


	public void start() {
		this.startTime = System.currentTimeMillis();
		this.running = true;
	}


	public void stop() {
		this.stopTime = System.currentTimeMillis();
		this.running = false;
	}

	//elaspsed time in milliseconds
	public long getElapsedTime() {
		long elapsed;
		if (running) {
			elapsed = (System.currentTimeMillis() - startTime);
		}
		else {
			elapsed = (stopTime - startTime);
		}
		return elapsed;
	}

}
