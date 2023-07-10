/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.assignment;

/**
 *
 * @author SongJuN
 */
import java.io.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PiePlot;

public class Job {
    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
        public void job() throws FileNotFoundException, IOException{
         FileInputStream fis = new FileInputStream("extracted_log");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        PrintWriter listpw= new PrintWriter("Number of Job List");
        String line;
        String word="Allocate";
        while ((line = br.readLine()) != null) {
            if(line.contains(word)){
                listpw.println(line);
        }}
        listpw.close();
       int [] jobCount=new int[7];
        for (int i=0; i<7;i++){
            jobCount[i]=0;
        }
            FileInputStream jobfis = new FileInputStream("Number of Job List");
            BufferedReader jobbr = new BufferedReader(new InputStreamReader(jobfis));
            String jobline;
            while ((jobline = jobbr.readLine()) != null) {
                jobCount=monthCount(jobline,jobCount);
                     
            }
            print("Number of Job Allocate",jobCount);
            chart(jobCount,"Number of Job Allocate");
        
        }
        
        //backfill
        public void backfill() throws FileNotFoundException, IOException{
         FileInputStream fis = new FileInputStream("extracted_log");
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        PrintWriter backfillpw= new PrintWriter("Number of Backfill");
        String line;
        String word="backfill";
        while ((line = br.readLine()) != null) {
            if(line.contains(word)){
                backfillpw.println(line);
        }}
        backfillpw.close();
       int [] backfillCount=new int[7];
        for (int i=0; i<7;i++){
            backfillCount[i]=0;
        }
            FileInputStream backfillfis = new FileInputStream("Number of Backfill");
            BufferedReader backfillbr = new BufferedReader(new InputStreamReader(backfillfis));
            String backfillline;
            while ((backfillline = backfillbr.readLine()) != null) {
                backfillCount=monthCount(backfillline,backfillCount);
                     
            }
            print("Number of Backfill Job",backfillCount);
            chart(backfillCount,"Number of Backfill Job");
        
        }
        
            // Total number of Job Done
             public void done() throws FileNotFoundException, IOException{
                 FileInputStream fis = new FileInputStream("extracted_log");
                 BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                 PrintWriter donepw= new PrintWriter("Job Done");
                 String word="done";
                 String line;
                  while ((line = br.readLine()) != null) {
                 if(line.contains(word)){
                donepw.println(line); 
            }}
                  donepw.close();
             int [] doneCount=new int[7];
            for (int i=0; i<7;i++){
            doneCount[i]=0;
        }
            Pattern donepattern= Pattern.compile("JobId=(\\d+)");
            FileInputStream donefis = new FileInputStream("Job Done");
            BufferedReader donebr = new BufferedReader(new InputStreamReader(donefis));
            String doneline;
            while ((doneline = donebr.readLine()) != null) {
                doneCount=monthCount(doneline,doneCount);             
            }
            print(" Number of Job Done",doneCount);
            chart(doneCount,"Number of Job Done");
             }
             
            //Total number of Job kill
            public void kill() throws FileNotFoundException, IOException{
                FileInputStream fis = new FileInputStream("extracted_log");
                 BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                 PrintWriter killpw= new PrintWriter("Job Kill");
                 String word= "_slurm_rpc_kill_job";
                 String line;
                  while ((line = br.readLine()) != null) {
                 if(line.contains(word)){
                killpw.println(line); 
            }}
                  killpw.close();
            int [] killCount=new int[7];
        for (int i=0; i<7;i++){
            killCount[i]=0;
        }
            int [] killFailCount=new int[7];
        for (int i=0; i<7;i++){
            killFailCount[i]=0;
        }
        int [] killSuccessCount=new int[7];
            FileInputStream killfis = new FileInputStream("Job Kill");
            BufferedReader killbr = new BufferedReader(new InputStreamReader(killfis));
            String killline;
            while ((killline = killbr.readLine()) != null) {
                if (!killline.contains(" job_str_signal()")){
                killCount=monthCount(killline,killCount);
                }
                else{
                killFailCount=monthCount(killline,killFailCount); 
                }
                 for (int i=0; i<7;i++){
                     killSuccessCount[i]=killCount[i]-killFailCount[i];
                 }
            }
            print("Number of Job Kill Successfully",killSuccessCount);
            chart(killCount,"Number of Job Kill Successfully");
            print("Number of Job Kill Failure",killFailCount);
            chart(killFailCount,"Number of Job Kill Failure");
            }

            public int [] monthCount(String line, int[] count){
                
                 if (line.contains("2022-06")){
                 count[0]+=1;    
                 }
                 else if (line.contains("2022-07")){
                     count[1]+=1;
                 }
                 else if (line.contains("2022-08")){
                     count[2]+=1;
                 }
                 else if (line.contains("2022-09")){
                     count[3]+=1;
                 }
                 else if (line.contains("2022-10")){
                     count[4]+=1;
                 }
                 else if (line.contains("2022-11")){
                     count[5]+=1;
                 }
                 else if (line.contains("2022-12")){
                     count[6]+=1;
                 }
                 return count;
            }
            public void print(String title, int [] count){
                int total=0; 
                for(int i=0;i<7;i++)
                     total+= count[i];
                System.out.println("+"+"-".repeat(37)+"+");
                System.out.printf("| %-36s|\n",title );
                System.out.println("+"+"-".repeat(37)+"+");
                System.out.printf("| %-14s   | %-6s     \t|\n", "Month", "Number");
                System.out.println("+"+"-".repeat(37)+"+");
                System.out.printf("| %-14s   | %-6d       \t| \n","June" ,count[0]);
                System.out.printf("| %-14s   | %-6d       \t| \n","July" ,count[1]);
                System.out.printf("| %-14s   | %-6d       \t| \n","August" ,count[2]);
                System.out.printf("| %-14s   | %-6d       \t| \n","September" ,count[3]);
                System.out.printf("| %-14s   | %-6d       \t| \n","October" ,count[4]);
                System.out.printf("| %-14s   | %-6d       \t| \n","November" ,count[5]);
                System.out.printf("| %-14s   | %-6d       \t| \n","December" ,count[6]);
                System.out.println("+"+"-".repeat(37)+"+");
                System.out.println("|Total= "+ total+"\t\t\t\t|");
                System.out.println("+"+"-".repeat(37)+"+");
                System.out.println("_____________________________________________________________________");
                System.out.println("");
            }
            
            public void chart(int [] count, String title){
                DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("June", count[0]);
        dataset.setValue("July", count[1]);
        dataset.setValue("August", count[2]);
        dataset.setValue("September", count[3]);
        dataset.setValue("October", count[4]);
        dataset.setValue("November", count[5]);
        dataset.setValue("December", count[6]);
            // Create a chart
        // Create a chart
        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
        chart.setAntiAlias(true);
        // Create a panel to display the chart
        ChartPanel panel = new ChartPanel(chart);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 30));
        chart.getTitle().setPaint(Color.BLACK);
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 20));
        chart.getLegend().setBackgroundPaint(Color.LIGHT_GRAY);
        
        
       
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint(0, Color.GREEN);
        plot.setSectionPaint(1, Color.RED);
        plot.setSectionPaint(2, Color.BLUE);
        plot.setSectionPaint(3, Color.PINK);
        plot.setSectionPaint(4, Color.CYAN);
        plot.setSectionPaint(5, Color.YELLOW);
        plot.setSectionPaint(6, Color.MAGENTA);
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 20));
        plot.setLabelLinkMargin(0.1);
        plot.setMaximumLabelWidth(0.2);
        plot.setMinimumArcAngleToDraw(0.2);
        StandardPieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1} ({2})",
                                                                                      new DecimalFormat("0"),
                                                                                      new DecimalFormat("0%"));
        plot.setLabelGenerator(labelGenerator);
        // Add the panel to a frame
        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
            }
}

