package org.usfirst.frc.team5895.robot.framework;

import edu.wpi.first.wpilibj.Timer;

public class History {

	private double[] vals;
	private Gettable g;
	private long period;
	private long timeToRemember;
	private int index = 0;
	private double lastRecordTime = 0;
	

	/**
	 * Creates a record of data from past
	 * 
	 * @param gettable The function that returns the data to store
	 * @param period How often to record the data, in ms
	 * @param timeToRemember How long, in ms, before data is forgotten
	 */
	public History(Gettable gettable, long period, long timeToRemember) {
		this.period = period;
		this.timeToRemember = timeToRemember;
		
		int numValsToRemember = (int) (timeToRemember/period)+1;
		vals = new double[numValsToRemember];
		
		g = gettable;
		
		Looper r = new Looper(period);
		r.add(this::remember);
		r.start();
	}
	
	private void remember() {
		lastRecordTime = Timer.getFPGATimestamp();
		vals[index] = g.get();
		index++;
		if (index >= vals.length)
			index = 0;
	}
	
	/**
	 * Gives the value at specified time
	 * 
	 * @param timeAgo How long from present value is from, in ms
	 * @return The value at that time, based on linear interpolation of data points
	 */
	public double getValAt(long timeAgo) {		
		if (timeAgo > timeToRemember || timeAgo < 0)
			return 0;
		else if (timeAgo < period) {
			double curVal = g.get();
			double lastRecordVal = vals[index];
			
			double curTime = Timer.getFPGATimestamp();
			double timeToLastRecord = (curTime-lastRecordTime)*1000.0;
			
			//linear interpolation
			double truePeriodsAgo = ((double) timeAgo)/timeToLastRecord;
			double curWeight = 1-truePeriodsAgo;
			double recordWeight = truePeriodsAgo;
			
			return curVal*curWeight + lastRecordVal*recordWeight;
		}
		else {
			
			double curTime = Timer.getFPGATimestamp();
			double timeToLastRecord = (curTime-lastRecordTime)*1000.0;
			
			//true periodsAgo
			double t = timeAgo - timeToLastRecord;
			double p = (double) period;
			double periodsAgo = t/p;
			
			//recorded periodsAgo
			int lowerPeriodsAgo = (int) (timeAgo/period);
			int upperPeriodsAgo = lowerPeriodsAgo + 1;
			
			//linear interpolation
			double lowerWeight = 1-(periodsAgo - lowerPeriodsAgo);
			double upperWeight = 1-(upperPeriodsAgo - periodsAgo);

			int lowerIndex = (index-lowerPeriodsAgo)%vals.length;
			if (lowerIndex < 0) lowerIndex += vals.length;
			double lowerVal = vals[lowerIndex];
			
			int upperIndex = (index-upperPeriodsAgo)%vals.length;
			if (upperIndex < 0) upperIndex += vals.length;
			double upperVal = vals[upperIndex];

			return lowerWeight*lowerVal + upperWeight*upperVal;
		}
	}
} 
