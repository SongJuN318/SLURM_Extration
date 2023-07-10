package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;


import java.awt.*;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.renderer.category.BarRenderer3D;


public class AverageTime {

    private String filename="extracted_log";
    private String[]jobEnd = {"WEXITSTATUS 0","WEXITSTATUS 1","WEXITSTATUS 2","OOM failure","WEXITSTATUS"};
    private int condition;
    private static int numCompleteID;
    private String[]successId;
    private String[]endTIme;
    private String[]startTIme;
    private String[]excecuteTime;
    private String averageExcecuteTime;
    private int numID_withStartEndTime;
    private Duration totalExcecuteTime = Duration.ZERO;
    private Duration[]totalTimeperMonth = new Duration[12];
    private long[]totalTimeperMonth_sec = new long[12];
    private int[]numCompleteID_withinmonth = new int[12];
    private String[]AverageExcecutetimeperMonth = new String[12];
    private long[]AverageExcecutetimeperMonth_sec = new long[12];
    private String[]month = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    private String[]totalTimeperMonth_MilitaryTime;



    public void setjobEndCondition(int newCondition){

        condition = newCondition;
    }

    public void getNumCompleteID(){

        try {

            Scanner input = new Scanner(new File(filename));

            numCompleteID = 0;

            if (condition == 4){

                while (input.hasNextLine()){
                    String line = input.nextLine();

                    if (line.contains(jobEnd[condition])){
                        numCompleteID++;
                    }
                }
            }

            else {

                while (input.hasNextLine()){
                    String line = input.nextLine();

                    if (line.endsWith(jobEnd[condition])){
                        numCompleteID++;
                    }
                }
            }

            input.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error in reading the file to get number of complete ID : " + filename);
        }
    }

    public void saveCompleteID_endtime(){

        successId = new String[numCompleteID];
        endTIme = new String[numCompleteID];

        try {

            Scanner input = new Scanner(new File(filename));
            int counter = 0;

            while (input.hasNextLine()){
                String line = input.nextLine();

                if (condition == 4){

                    if (line.contains(jobEnd[condition])){
                        successId[counter] = line.substring(47, 52);
                        endTIme[counter] = line.substring(1, 24) + "Z";
                        counter++;
                    }
                }


                else {

                }
                if (line.endsWith(jobEnd[condition])){
                    successId[counter] = line.substring(47, 52);
                    endTIme[counter] = line.substring(1, 24) + "Z";
                    counter++;
                }
            }

            input.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error in reading the file to save complete ID and their end time: " + filename);
        }
    }

    public void saveStarttime(){

        try {

            Scanner input = new Scanner(new File(filename));

            int counter = 0;
            startTIme = new String[numCompleteID];

            input = new Scanner(new File("extracted_log"));

            while (input.hasNextLine()){
                String line = input.nextLine();

                if(line.contains("submit") || line.contains("Allocate") || line.contains("allocate")|| line.contains("backfill")){
                    for (int i = 0; i < numCompleteID;i++){

                        if (line.contains(successId[i])){
                            startTIme[i] = line.substring(1, 24) + "Z";
                            counter++;
                            break;
                        }
                    }
                }
            }

            input.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error in reading the file to save their start time: " + filename);
        }
    }

    public void initialize_totalTimeperMonth(){

        for (int i = 0; i < 12; i++){

            totalTimeperMonth[i] = Duration.ZERO;
        }
    }

    public void calTotalExcecutetime(){

        excecuteTime = new String[numCompleteID];
        initialize_totalTimeperMonth();

        numID_withStartEndTime = 0;

        for (int i = 0; i < numCompleteID; i++){

            Duration temp = Duration.ZERO;

            if (startTIme[i] != null && endTIme[i] != null){

                temp = calDurationOfTwo_time(startTIme[i],endTIme[i]);
                excecuteTime[i] = reformat_MilitaryTime(reformat_Seconds(temp));
                totalExcecuteTime = totalExcecuteTime.plus(temp);
                numID_withStartEndTime++;

                // Get month for each time
                int monthStart = Integer.parseInt(startTIme[i].substring(5,7));
                int monthEnd = Integer.parseInt(endTIme[i].substring(5,7));

                // If the job starts and end within a month
                if (monthStart == monthEnd){

                    totalTimeperMonth[monthStart - 1] = totalTimeperMonth[monthStart - 1].plus(calDurationOfTwo_time(startTIme[i],endTIme[i]));
                    numCompleteID_withinmonth[monthStart - 1]++;
                }
            }
        }
    }

    public String calAverageExcecutetime(Duration total, int numID_withStartEndTime){

        long durationInSeconds = reformat_Seconds(total);
        long averageInSeconds = durationInSeconds / numID_withStartEndTime;
        return (reformat_MilitaryTime(averageInSeconds));
    }

    public void calAverageExcecutetime_perMonth(){

        for (int i = 0; i < 12; i++){

            if (numCompleteID_withinmonth[i] != 0)
                AverageExcecutetimeperMonth[i] = calAverageExcecutetime(totalTimeperMonth[i],numCompleteID_withinmonth[i]);
        }
    }


    public void outputAll(String[]excecuteTime){

        System.out.println(jobEnd[condition]);
        System.out.println("Number of job done      : " + numCompleteID);
        System.out.println("Total execution time    : " + reformat_MilitaryTime(reformat_Seconds(totalExcecuteTime)));
        System.out.println("Average excecution time : " + averageExcecuteTime);
        System.out.println("");

        System.out.println("+----------------------------------------------------------------------------------------------+");
        System.out.printf("%-13s %-32s %-32s %-20s\n", "Month", "Num_job","Total hour", "Average");

        for (int i = 0; i < 12; i++){

            if (AverageExcecutetimeperMonth[i] != null)
                System.out.printf("%-13s %-32s %-32s %-20s\n",month[i],numCompleteID_withinmonth[i],reformat_MilitaryTime(reformat_Seconds(totalTimeperMonth[i])),AverageExcecutetimeperMonth[i]);
        }

        System.out.println("+----------------------------------------------------------------------------------------------+");


        // Write into txt file
        try {

            PrintWriter output = new PrintWriter (new File(jobEnd[condition]));
            output.println(jobEnd[condition]);
            output.println("Number of job done      : " + numCompleteID);
            output.println("Total execution time    : " + reformat_MilitaryTime(reformat_Seconds(totalExcecuteTime)));
            output.println("Average excecution time : " + averageExcecuteTime);
            output.println();

            // Write into txt file
            output.println("+----------------------------------------------------------------------------------------------+");
            output.printf("%-13s %-32s %-32s %-20s\n", "Month", "Num_job","Total hour", "Average");

            for (int i = 0; i < 12; i++){

                if (AverageExcecutetimeperMonth[i] != null)
                    output.printf("%-13s %-32s %-32s %-20s\n",month[i],numCompleteID_withinmonth[i],reformat_MilitaryTime(reformat_Seconds(totalTimeperMonth[i])),AverageExcecutetimeperMonth[i]);
            }

            output.println("+----------------------------------------------------------------------------------------------+");

            output.println("");

            output.println("+----------------------------------------------------------------------------------------------+");
            output.printf("%-13s %-32s %-32s %-20s\n","JobID","Start time", "Complete time", "Excecution time");

            for (int i = 0; i < successId.length; i++){

                if (startTIme[i] != null && endTIme[i] != null){
                    output.printf("%-13s %-32s %-32s %-20s\n",successId[i],startTIme[i],endTIme[i],excecuteTime[i]);
                }
            }
            output.println("+----------------------------------------------------------------------------------------------+");

            output.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error while writing into new file");
        }
    }

    public void setAverageExcecuteTime(String newAverageExcecuteTime){

        averageExcecuteTime = newAverageExcecuteTime;
    }

    public Duration getTotalExcecuteTime(){

        return totalExcecuteTime;
    }

    public int getNumID_withStartEndTime(){

        return numID_withStartEndTime;
    }

    public String[] getExcecuteTime(){

        return excecuteTime;
    }

    public long[] getTotalTimeperMonth_sec(){

        return totalTimeperMonth_sec;
    }

    public long[] getAverageExcecutetimeperMonth_sec(){

        for (int i = 0; i < 12; i++){
            if (numCompleteID_withinmonth[i] != 0){
                AverageExcecutetimeperMonth_sec[i] = totalTimeperMonth_sec[i] / numCompleteID_withinmonth[i];
            }
        }
        return AverageExcecutetimeperMonth_sec;
    }

    public Duration calDurationOfTwo_time(String newStartTIme,String newEndTIme){

        Instant start = Instant.parse(newStartTIme);
        Instant end = Instant.parse(newEndTIme);
        Duration duration = Duration.between(start, end);
        return duration;
    }

    public long reformat_Seconds(Duration time){

        return time.getSeconds();
    }

    public String reformat_MilitaryTime(long timeinSeconds){

        Duration time = Duration.ofSeconds(timeinSeconds);
        long hours = time.toHours();
        long minutes = time.toMinutes() % 60;
        long seconds = time.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void barchart_totalTimeperMonthinSec(){

        for (int i = 0; i < 12; i++){

            totalTimeperMonth_sec[i] = reformat_Seconds(totalTimeperMonth[i]);
        }
    }


    public void barchart(long[]arr, String chartTitle){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < arr.length; i++){

            if(arr[i]!= 0)
                dataset.addValue(arr[i],month[i], "Month");
        }

        // Create a chart
        JFreeChart chart = ChartFactory.createBarChart3D(
                chartTitle + jobEnd[condition],              // Chart title
                "",                // Domain axis label
                (chartTitle +"each month"),             // Range axis label
                dataset,                                        // Data
                PlotOrientation.VERTICAL,           // Plot Orientation
                true,                                 // Show Legend
                true,                              // Use tooltips
                false                                // Configure chart to generate URLs?
        );

        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 14));

        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();

        renderer.setWallPaint(Color.LIGHT_GRAY);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.ORANGE);
        renderer.setSeriesPaint(2, Color.YELLOW);
        renderer.setSeriesPaint(3, Color.GREEN);
        renderer.setSeriesPaint(4, Color.BLUE);
        renderer.setSeriesPaint(5, Color.PINK);
        renderer.setSeriesPaint(6, Color.WHITE);


        // Create a panel to display the chart
        ChartPanel panel = new ChartPanel(chart);

        // Add the panel to a frame
        JFrame frame = new JFrame(chartTitle + jobEnd[condition]);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void settotalExcecuteTime_zero(){
        totalExcecuteTime = Duration.ZERO;
    }
}
