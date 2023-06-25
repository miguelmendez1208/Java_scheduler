This Java project is a job scheduler simulation that can emulate different scheduling algorithms. It reads job data from a text file, 'jobs.txt', and generates a schedule based on the selected scheduling algorithm.

Features
Implements Round Robin (RR), Shortest Remaining Time (SRT), and Feedback (FB) scheduling algorithms.
Can execute all scheduling algorithms and compare their results.
Takes job inputs from 'jobs.txt', each line should represent a job in the format [Job Name] [Arrival Time] [Burst Time].
Generates a schedule of jobs and prints it to the console.
Running the Program
To run this program, use the following command:

css
Copy code
java proj3.java [scheduling_algorithm]
Replace [scheduling_algorithm] with either RR for Round Robin, SRT for Shortest Remaining Time, FB for Feedback, or all to execute all scheduling algorithms and compare their results.

Example Usage
To execute the Round Robin scheduling algorithm:

Copy code
java proj3.java RR
To execute all scheduling algorithms and compare their results:

css
Copy code
java proj3.java all
Note
Ensure that the 'jobs.txt' file is in the same directory as the project and is formatted correctly. Each job should be on its own line in the format [Job Name] [Arrival Time] [Burst Time]. Example:

Copy code
Job1 0 5
Job2 1 3
Job3 2 7
Disclaimer
This is a simulation tool for educational purposes. It does not interact with any real-world systems or perform actual job scheduling.
