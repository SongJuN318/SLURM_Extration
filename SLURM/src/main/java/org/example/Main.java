package org.example;


import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        Job j=new Job();
        Error e=new Error();
        Partition p= new Partition();
        AverageTime time1=new AverageTime();
        Status s=new Status();
        int i = 0 ;
        while (i >=0 && i <=13){
            System.out.println("1 --- Number of Job Allocate by Month ");
            System.out.println("2 --- Number of Job Backfill by Month");
            System.out.println("3 --- Number of Job Done by Month");
            System.out.println("4 --- Number of Job Kill Sucess and Fail by Month");
            System.out.println("5 --- Number of Each Partition by Month");
            System.out.println("6 --- Number of Error In Each Month");
            System.out.println("7 --- Number of Error Caused By User And Table");
            System.out.println("8 --- Number of Error Caused By Each User");
            System.out.println("9 --- Error Caused in the Time Range");
            System.out.println("10 --- Total Type of Error and Number of Each Error");
            System.out.println("11 --- Total and Average Execution Time by Status");
            System.out.println("12 --- Total Successful and Unsuccessful Exit Status");
            System.out.println("13 --- Total and Average Cleanup Completion Time");
            System.out.println("Enter '-1' to exit");
            System.out.println("");
            System.out.print("Choose number to show the output: ");
            i=sc.nextInt();

            switch (i) {
                case 1:
                    j.job();
                    break;
                case 2:
                    j.backfill();
                    break;
                case 3:
                    j.done();
                    break;
                case 4:
                    j.kill();
                    break;
                case 5:
                    p.print();
                    break;
                case 6:
                    e.errorMonth();
                    break;
                case 7:
                    e.printUserError();
                    break;
                case 8:
                    e.nameUser();
                    break;
                case 9:
                    System.out.print("Enter Start Time: ");
                    String timeStart=sc.next();
                    System.out.println("");
                    System.out.print("Enter End Time: ");
                    String timeEnd=sc.next();
                    e.getErrorTime(timeStart,timeEnd);
                    break;
                case 10:
                    e.typeError();
                    break;
                case 11:
                    System.out.print(" 0 -> WEXITSTATUS 0\n 1 -> WEXITSTATUS 1\n 2 -> WEXITSTATUS 2\n 3 -> OOM failure\n 4 -> WEXITSTATUS\nEnter exit status : ");
                    time1.setjobEndCondition(sc.nextInt());
                    time1.getNumCompleteID();
                    time1.saveCompleteID_endtime();
                    time1.saveStarttime();
                    time1.calTotalExcecutetime();
                    time1.setAverageExcecuteTime(time1.calAverageExcecutetime(time1.getTotalExcecuteTime(),time1.getNumID_withStartEndTime()));
                    time1.calAverageExcecutetime_perMonth();

                    System.out.println("");
                    time1.outputAll(time1.getExcecuteTime());
                    time1.barchart_totalTimeperMonthinSec();
                    time1.barchart(time1.getTotalTimeperMonth_sec(),"Total excecution time for ");
                    time1.barchart(time1.getAverageExcecutetimeperMonth_sec(),"Average excecution time for ");
                    time1.settotalExcecuteTime_zero();
                    break;
                case 12:
                    s.WEXITSTATUS ();
                    break;
                case 13:
                    s.CLEANUP();
                    break;
                case -1 :
                    System.out.println("You have successfully exit.");
                    break;
                default :
                    System.out.println("Invalid Number.");
            }


        }

    }
}
