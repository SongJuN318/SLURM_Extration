package org.example;

import java.util.*;
import java.io.*;
import java.util.regex.*;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;

public class Status {
    public static void WEXITSTATUS () throws FileNotFoundException {
        PrintWriter output = new PrintWriter (new FileOutputStream("WEXITSTATUS.txt"));
        Scanner input = new Scanner (new FileInputStream("extracted_log"));
        while (input.hasNextLine()){
            String readAll = input.nextLine();
            if (readAll.contains("WEXITSTATUS") )
                output.println(readAll);
        }
        input.close();
        output.close();
        Scanner input2 = new Scanner (new FileInputStream("WEXITSTATUS.txt"));
        int count0 = 0 , countOthers = 0 , countTotal = 0  ;
        while (input2.hasNextLine()){
            String readNum = input2.nextLine();
            if (readNum.contains("WEXITSTATUS 0")){
                count0 ++ ; }
            else countOthers ++ ;
        }
        input2.close();
        countTotal = count0 + countOthers ;
        System.out.println("Total Number of WEXITSTATUS: " +countTotal);
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Number of WEXITSTATUS 0 (successful): " + count0);
        System.out.println("Number of WEXITSTATUS ≠0 (unsuccessful): " + countOthers);
        WEXITSTATUSCHART(count0,countOthers);
    }

    static void CLEANUP () throws FileNotFoundException {
        double average = 0 ;
        int sum = 0 ;
        int linecounter = 0 ;
        PrintWriter output = new PrintWriter (new FileOutputStream("CLEANUP.txt"));
        Scanner loginput = new Scanner (new FileInputStream("extracted_log"));
        while (loginput.hasNextLine()){
            String readAll = loginput.nextLine();
            if (readAll.contains("cleanup_completing") )
                output.println(readAll);
        }
        loginput.close();
        output.close();
        Scanner input = new Scanner (new FileInputStream("CLEANUP.Txt"));
        while(input.hasNextLine()){
            String line = input.nextLine();
            Pattern pattern = Pattern.compile("process took (\\d+) seconds");
            Matcher matcher = pattern.matcher(line);
            while(matcher.find())
                sum = sum + Integer.parseInt(matcher.group(1)) ;
            linecounter ++ ;
        }
        average =  (double)sum/  linecounter ;

        int sumMinutes = sum / 60 ;
        double averageMinutes = average / 60 ;


        System.out.println("The sum of cleanup completion time is: " +sumMinutes +" minutes");
        System.out.printf("The average cleanup completion time is: %.2f minutes\n" , averageMinutes);



        input.close();

    }

    static void WEXITSTATUSCHART (int a, int b){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(a, "WEXITSTATUS (Successful)", "WEXITSTATUS");
        dataset.addValue(b, "WEXITSTATUS (Unsuccesful)", "WEXITSTATUS");

        // Create a chart
        JFreeChart chart = ChartFactory.createBarChart3D("WEXITSTATUS ", "Execution Outcome" ,"Number of Wexitstatus", dataset);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.BLACK);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 14));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, Color.RED);



        // Create a panel to display the chart
        ChartPanel panel = new ChartPanel(chart);

        // Add the panel to a frame
        JFrame frame = new JFrame("WEXITSTATUS BAR CHART");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

