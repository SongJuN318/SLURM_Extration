package org.example;


import java.util.*;
import java.io.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
public class Partition {
    public void print() throws IOException{
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[]total=new int[6];
        total[0]=NumJob("cpu-epyc");
        total[1]=NumJob("cpu-opteron");
        total[2]=NumJob("gpu-k10");
        total[3]=NumJob("gpu-k40c");
        total[4]=NumJob("gpu-titan");
        total[5]=NumJob("gpu-v100s");
        System.out.println("");

        int[][]data= new int[7][7];
        String []partition={"cpu-epyc","cpu-opteron","gpu-k10","gpu-k40c","gpu-titan","gpu-v100s"};
        for(int i=0;i<6;i++){
            data[i][0]=NumMonth(partition[i],"-06-");
            data[i][1]=NumMonth(partition[i],"-07-");
            data[i][2]=NumMonth(partition[i],"-08-");
            data[i][3]=NumMonth(partition[i],"-09-");
            data[i][4]=NumMonth(partition[i],"-10-");
            data[i][5]=NumMonth(partition[i],"-11-");
            data[i][6]=NumMonth(partition[i],"-12-");
        }

        System.out.printf("%-20s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s","partition","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Total");
        System.out.println("\n==================================================================================================");

        for (int k=0;k<6;k++){
            System.out.printf("%-20s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s",partition[k],data[k][0],data[k][1],data[k][2],data[k][3],data[k][4],data[k][5],data[k][6],total[k]);
            System.out.println();
        }

        dataset.addValue(data[0][0], "cpu-epyc", "Jun");
        dataset.addValue(data[0][1], "cpu-epyc", "Jul");
        dataset.addValue(data[0][2], "cpu-epyc", "Aug");
        dataset.addValue(data[0][3],"cpu-epyc", "Sep");
        dataset.addValue(data[0][4],"cpu-epyc", "Oct");
        dataset.addValue(data[0][5],"cpu-epyc", "Nov");
        dataset.addValue(data[0][6],"cpu-epyc", "Dec");
        dataset.addValue(data[1][0],"cpu-opteron", "Jun");
        dataset.addValue(data[1][1],"cpu-opteron", "Jul");
        dataset.addValue(data[1][2],"cpu-opteron", "Aug");
        dataset.addValue(data[1][3], "cpu-opteron", "Sep");
        dataset.addValue(data[1][4], "cpu-opteron", "Oct");
        dataset.addValue(data[1][5], "cpu-opteron", "Nov");
        dataset.addValue(data[1][6], "cpu-opteron", "Dec");
        dataset.addValue(data[2][0], "gpu-k10", "Jun");
        dataset.addValue(data[2][1], "gpu-k10", "Jul");
        dataset.addValue(data[2][2], "gpu-k10", "Aug");
        dataset.addValue(data[2][3], "gpu-k10", "Sep");
        dataset.addValue(data[2][4], "gpu-k10", "Oct");
        dataset.addValue(data[2][5], "gpu-k10", "Nov");
        dataset.addValue(data[2][6], "gpu-k10", "Dec");
        dataset.addValue(data[3][0], "gpu-k40c", "Jun");
        dataset.addValue(data[3][1], "gpu-k40c", "Jul");
        dataset.addValue(data[3][2], "gpu-k40c", "Aug");
        dataset.addValue(data[3][3], "gpu-k40c", "Sep");
        dataset.addValue(data[3][4], "gpu-k40c", "Oct");
        dataset.addValue(data[3][5], "gpu-k40c", "Nov");
        dataset.addValue(data[3][6], "gpu-k40c", "Dec");
        dataset.addValue(data[4][0], "gpu-titan", "Jun");
        dataset.addValue(data[4][1], "gpu-titan", "Jul");
        dataset.addValue(data[4][2], "gpu-titan", "Aug");
        dataset.addValue(data[4][3], "gpu-titan", "Sep");
        dataset.addValue(data[4][4], "gpu-titan", "Oct");
        dataset.addValue(data[4][5], "gpu-titan", "Nov");
        dataset.addValue(data[4][6], "gpu-titan", "Dec");
        dataset.addValue(data[5][0], "gpu-v100s", "Jun");
        dataset.addValue(data[5][1], "gpu-v100s", "Jul");
        dataset.addValue(data[5][2], "gpu-v100s", "Aug");
        dataset.addValue(data[5][3], "gpu-v100s", "Sep");
        dataset.addValue(data[5][4], "gpu-v100s", "Oct");
        dataset.addValue(data[5][5], "gpu-v100s", "Nov");
        dataset.addValue(data[5][6], "gpu-v100s", "Dec");
        final JFreeChart chart = ChartFactory.createStackedBarChart("Number of Job by Partition", "Months", "Number of Job", dataset);

        final ChartFrame frame = new ChartFrame("Stacked Bar Chart", chart);
        frame.pack();
        frame.setVisible(true);

    }

    public static int NumMonth(String partition, String m)throws FileNotFoundException,IOException{

        Scanner input= new Scanner(new FileInputStream("extracted_log"));
        String lineRead;
        int z=0;
        while(input.hasNextLine()){
            lineRead=input.nextLine();
            if(lineRead.contains(partition)&&lineRead.contains("Allocate JobId")&&lineRead.contains(m))
                z++;
        }
        return z;
    }

    public static int NumJob(String partition) throws FileNotFoundException,IOException{
        Scanner input = new Scanner (new FileInputStream("extracted_log"));
        String lineRead;

        int i=0;
        while(input.hasNextLine()){
            lineRead=input.nextLine();
            if(lineRead.contains(partition)&&lineRead.contains("Allocate JobId"))
                i++;
        }
        System.out.print("Total number of "+partition+" by partition: " + i+"\n");
        return i;
    }

}

