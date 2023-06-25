package project3;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Queue;

public class proj3 {
	static char[] jobNames;
	static int[] startTimes;
	static int[] durationTimes;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] file = ReadFile("jobs.txt"); // read file
		// char[] jobNames; int[] startTimes; int[] durationTimes;
		int jobs = fillArrays(file);

		String command = "ALL";
		if (args.length > 0) {
			command = args[0];
		}
		if (command.equals("RR")) {
			int[] executing = Round_Robin2(jobs);
			System.out.println("Round Robin: ");
			printSchedule(jobs, executing);
		} else if (command.equals("SRT")) {
			int[] executing = SRT(jobs);
			System.out.println("SRT: ");
			printSchedule(jobs, executing);
		} else if (command.equals("FB")) {
			int[] executing = feedback(jobs);
			System.out.println("Feedback: ");
			printSchedule(jobs, executing);
		} else {
			int[] executing = Round_Robin2(jobs);
			System.out.println("Round Robin: ");
			printSchedule(jobs, executing);

			executing = SRT(jobs);
			System.out.println("SRT: ");
			printSchedule(jobs, executing);

			executing = feedback(jobs);
			System.out.println("Feedback: ");
			printSchedule(jobs, executing);
		}

	}

	public static char[] ReadFile(String fileName) { // read file done
		char[] charArray = new char[100];// limited to arbitrary number, does it matter ?
		try {
			FileReader fileReader = new FileReader(fileName);
			fileReader.read(charArray);
			fileReader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return charArray;
	}

	public static int fillArrays(char[] file) {
		int i = 0; // char
		int n = 0; // lines
		jobNames = new char[24]; // returns 3 arrays as global variables
		startTimes = new int[24]; // because in java you cant return arrays unless you nest them
		durationTimes = new int[24]; // and I didn't want to do that. Probably should've tho

		while (Character.getNumericValue(file[i]) != -1) { // while not reading end of file
			jobNames[n] = file[i];// letter
			i++;// tab
			i++;// num
			startTimes[n] = Character.getNumericValue(file[i]); // startTime
			i++;// tab
			i++;// num
			durationTimes[n] = Character.getNumericValue(file[i]);// durationtime
			i++;// endline
			i++;// start the next line
			n++;
		}
		return n; // returns number of lines as the number of jobs
	}

	public static void printArray(char[] array) {
		for (char c : array) {
			System.out.print(c);
		}
		System.out.println();
	}

	public static void printArray(int[] array) {
		for (int c : array) {
			System.out.print(c);
		}
		System.out.println();
	}

	public static int getTotalTime(int jobs) {
		int totalTime = 0;
		for (int i = 0; i < jobs; i++) {
			totalTime += durationTimes[i];
		}
		return totalTime;
	}

	public static void printSchedule(int jobs, int[] executing) {
		int totalTime = 0;
		for (int i = 0; i < jobs; i++) {
			System.out.print(jobNames[i] + " ");
		}
		System.out.println();

		totalTime = getTotalTime(jobs);
		for (int i = 0; i < totalTime; i++) {
			int a = executing[i]; // left space
			int b = jobs - executing[i]; // right space
			for (int j = 0; j < a; j++) {
				System.out.print("  "); // left space
			}
			System.out.print("X"); // X
			for (int k = 0; k < b; k++) {
				System.out.print("  "); // right space
			}
			System.out.println();
		}
	}

	public static int[] FCFS(int jobs) {
		int[] executing = new int[getTotalTime(jobs)];
		Queue<Integer> jobQ = new LinkedList<>();
		for (int i = 0; i < jobs; i++) {

			jobQ.add(i);
		}
		int executingJobPosition = 0;
		int executingJobDuration = 0;
		for (int i = 0; i < executing.length; i++) {
			if (executingJobDuration == 0) {
				executingJobPosition = jobQ.remove();
				executingJobDuration = durationTimes[executingJobPosition];
			}
			executing[i] = executingJobPosition;
			executingJobDuration--;
		}

		return executing;
	}

	public static int[] Round_Robin2(int jobs) {
		int[] executing = new int[getTotalTime(jobs)];
		int[] durationCopy = durationTimes.clone();
		Queue<Integer> jobQ = new LinkedList<>(); // jobQ

		int executingJobPosition = 0;
		jobQ.add(executingJobPosition); // add 1st job to queue
		durationCopy[0]--;
		for (int i = 0; i < executing.length; i++) { // i is where it is in the track
			for (int j = 0; j < jobs; j++) { // j is what job it is
				if ((i + 1) == startTimes[j]) { // give priority to new jobs
					jobQ.add(j);
					durationCopy[j]--;
				}
			}
			executingJobPosition = jobQ.remove(); // pop job position from queue
			executing[i] = executingJobPosition; // record job position
			if (durationCopy[executingJobPosition] > 0) { // if not done readd to queue
				jobQ.add(executingJobPosition); //
				durationCopy[executingJobPosition]--;
			}
		}

		return executing;
	}

	public static int[] feedback(int jobs) {
		int[] executing = new int[getTotalTime(jobs)];
		int[] durationCopy = durationTimes.clone();
		Queue<Integer> jobQ = new LinkedList<>(); // jobQ
		Queue<Integer> jobQ2 = new LinkedList<>(); // jobQ2
		Queue<Integer> jobQ3 = new LinkedList<>(); // job Q3

		int executingJobPosition = 0;
		jobQ.add(executingJobPosition); // add 1st job to queue
		durationCopy[0]--;
		for (int i = 0; i < executing.length; i++) { // i is where it is in the track
			for (int j = 0; j < jobs; j++) { // j is what job it is
				if ((i) == startTimes[j]) { // give priority to new jobs
					jobQ.add(j);
					durationCopy[j]--;
				}
			}
			if (jobQ.peek() != null) { // check Q1
				executingJobPosition = jobQ.remove(); // pop job position from queue
				executing[i] = executingJobPosition; // record job position
				if (durationCopy[executingJobPosition] > 0) { // if not done readd to queue
					jobQ2.add(executingJobPosition); // q1->q3
					durationCopy[executingJobPosition]--;
				}
			} else if (jobQ2.peek() != null) { // check Q2
				executingJobPosition = jobQ2.remove(); // pop job position from queue
				executing[i] = executingJobPosition; // record job position
				if (durationCopy[executingJobPosition] > 0) { // if not done readd to queue
					jobQ3.add(executingJobPosition); // q2->q3
					durationCopy[executingJobPosition]--;
				}
			} else if (jobQ3.peek() != null) { // check Q3
				executingJobPosition = jobQ3.remove(); // pop job position from queue
				executing[i] = executingJobPosition; // record job position
				if (durationCopy[executingJobPosition] > 0) { // if not done readd to queue
					jobQ3.add(executingJobPosition); // add to q3
					durationCopy[executingJobPosition]--;
				}
			}
		}

		return executing;
	}

	public static int[] SRT(int jobs) {
		int[] executing = new int[getTotalTime(jobs)];// create a return array
		int[] durationCopy = durationTimes.clone(); // create a copy of the duration times for manipulation
		// this works by finding the next min that both has started and isn't completed
		int executingJobPosition = 0;// the executing process
		for (int i = 0; i < executing.length; i++) { // i is where it is in the track
			int min = 9999999;
			for (int j = 0; j < jobs; j++) { // find the min
				if (durationCopy[j] < min && durationCopy[j] > 0 && i >= startTimes[j]) {
					min = durationCopy[j]; // set new min
					executingJobPosition = j; // set new executing job position
				}
			}
			executing[i] = executingJobPosition;// save to output array
			durationCopy[executingJobPosition]--;// decrement duration in copy
		}
		return executing;
	}
}
