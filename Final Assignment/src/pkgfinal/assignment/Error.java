/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pkgfinal.assignment;

/**
 *
 * @author SongJuN
 */
import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

  public class Error {

    public void errorMonth() {
        int[] numError = new int[7];
        for (int i = 0; i < 7; i++) {
            numError[i] = 0;
        }

        try {
            Scanner read = new Scanner(new FileInputStream("extracted_log"));

            //read from the data, find the error
            PrintWriter output = new PrintWriter(new FileOutputStream("File_error"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.matches(".*error.*")) {
                    output.write(line + "\n");
                }
            }
            read.close();
            output.close();
            Scanner read_byUser = new Scanner(new FileInputStream("File_error"));
            while (read_byUser.hasNextLine()) {
                String data = read_byUser.nextLine();
                numError = monthCount(data, numError);
            }
            print("Error in month", numError);

            pieChartForError(numError);
            read_byUser.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    //read the username and the date of errror and the reason of error
    public void printUserError() {
        int num = 0;
        int total = 0;
        try {
            Scanner read = new Scanner(new FileInputStream("extracted_log"));

            PrintWriter output = new PrintWriter(new FileOutputStream("File_error"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.matches(".*error.*")) {
                    output.write(line + "\n");
                }
            }
            read.close();
            output.close();

            Scanner read_byUser = new Scanner(new FileInputStream("File_error"));
            PrintWriter output_byUser = new PrintWriter(new FileOutputStream("errorByUser"));

            while (read_byUser.hasNextLine()) {
                String data = read_byUser.nextLine();

                if (data.matches(".*error.*user='.*")) //can use match if the format is for the whole region on input sequence
                {
                    output_byUser.write(data + "\n");
                }
            }
            output_byUser.close();
            read_byUser.close();

            Scanner readUserError = new Scanner(new FileInputStream("errorByUser"));
            PrintWriter output_user = new PrintWriter(new FileOutputStream("username"));
            String regex = "(\\[*\\d{4}-\\d{2}-\\w+:\\d{2}:\\d{2}.\\d{3}\\]*).*user='(\\w+\\_*\\.*\\w*\\.*\\w*)'.* (does .*)"; //about 08T16 can alos put like //d{2}T\\d{2
            Pattern pattern = Pattern.compile(regex);

            System.out.println("+" + "-".repeat(103) + "+");
            System.out.printf("| %-4s| %-23s | %-28s | %-39s|\n", "No", "UserName", "Time", "Reason");
            System.out.println("+" + "-".repeat(103) + "+");

            while (readUserError.hasNextLine()) {
                String data = readUserError.nextLine();
                Matcher matcher = pattern.matcher(data);

                if (matcher.find()) {
                    total++;
                    output_user.write(matcher.group(2) + "\n");
                    num++;
                    System.out.printf("| %-3d | %-23s | %-28s | %-38s |\n", num, matcher.group(2), matcher.group(1), matcher.group(3));
                    System.out.println("+" + "-".repeat(103) + "+");
                }
            }
            System.out.printf("Number of job that have error which cause by correspponding user: %d\n", total);
            System.out.println("");

            readUserError.close();
            output_user.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public ArrayList<String> sortUser() {
        HashSet<String> username = new HashSet<>();
        ArrayList<String> sortUserName = new ArrayList<>();

        try {
            Scanner read = new Scanner(new FileInputStream("extracted_log"));

            //read from the data, find the error
            PrintWriter output = new PrintWriter(new FileOutputStream("File_error"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.matches(".*error.*")) {
                    output.write(line + "\n");
                }
            }
            read.close();
            output.close();

            Scanner read_byUser = new Scanner(new FileInputStream("File_error"));
            PrintWriter output_byUser = new PrintWriter(new FileOutputStream("errorByUser"));

            while (read_byUser.hasNextLine()) {
                String data = read_byUser.nextLine();

                if (data.matches(".*error.*user='.*")) //can use match if the format is for the whole region on input sequence
                {
                    output_byUser.write(data + "\n");
                }
            }
            output_byUser.close();
            read_byUser.close();

            Scanner readUser = new Scanner(new FileInputStream("errorByUser"));
            PrintWriter output_user = new PrintWriter(new FileOutputStream("username"));
            String regex = "(\\[*\\d{4}-\\d{2}-\\w+:\\d{2}:\\d{2}.\\d{3}\\]*).*user='(\\w+\\_*\\.*\\w*\\.*\\w*)'.* (does .*)"; //about 08T16 can alos put like //d{2}T\\d{2
            Pattern pattern = Pattern.compile(regex);
            while (readUser.hasNextLine()) {
                String data = readUser.nextLine();
                Matcher matcher = pattern.matcher(data);

                if (matcher.find()) {
                    output_user.write(matcher.group(2) + "\n");
                }
            }
            readUser.close();
            output_user.close();

            Scanner readUserName = new Scanner(new FileInputStream("username"));
            while (readUserName.hasNextLine()) {
                String line = readUserName.nextLine();
                username.add(line);
            }
            for (String userName : username) {
                sortUserName.add(userName);
            }
            Collections.sort(sortUserName);
            readUserName.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return sortUserName;

    }

    //read the number of error that cause by each user
    public void nameUser() {
        int i = 0;
        int num;
        ArrayList<String> name = new ArrayList<>();
        name.addAll(sortUser());
        int[] numErrorUser = new int[name.size()];
        try {
            Scanner read = new Scanner(new FileInputStream("extracted_log"));

            //read from the data, find the error
            PrintWriter output = new PrintWriter(new FileOutputStream("File_error"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (line.matches(".*error.*")) {
                    output.write(line + "\n");
                }
            }
            read.close();
            output.close();

            Scanner read_byUser = new Scanner(new FileInputStream("File_error"));
            PrintWriter output_byUser = new PrintWriter(new FileOutputStream("errorByUser"));

            while (read_byUser.hasNextLine()) {
                String data = read_byUser.nextLine();

                if (data.matches(".*error.*user='.*")) //can use match if the format is for the whole region on input sequence
                {
                    output_byUser.write(data + "\n");
                }
            }
            output_byUser.close();
            read_byUser.close();

            Scanner readUser = new Scanner(new FileInputStream("errorByUser"));
            PrintWriter output_user = new PrintWriter(new FileOutputStream("username"));
            String regex = "(\\[*\\d{4}-\\d{2}-\\w+:\\d{2}:\\d{2}.\\d{3}\\]*).*user='(\\w+\\_*\\.*\\w*\\.*\\w*)'.* (does .*)"; //about 08T16 can alos put like //d{2}T\\d{2
            Pattern pattern = Pattern.compile(regex);
            while (readUser.hasNextLine()) {
                String data = readUser.nextLine();
                Matcher matcher = pattern.matcher(data);

                if (matcher.find()) {
                    output_user.write(matcher.group(2) + "\n");
                }
            }
            readUser.close();
            output_user.close();
            System.out.println("+" + "-".repeat(34) + "+");
            System.out.println("| Number error caused by each user |");
            System.out.println("+" + "-".repeat(34) + "+");
            System.out.printf("| %-16s| %-15s|\n", "User", "Number");
            System.out.println("+" + "-".repeat(34) + "+");
            for (String username : name) {

                num = 0;
                Scanner readName = new Scanner(new FileInputStream("username"));
                while (readName.hasNextLine()) {
                    String line = readName.nextLine();
                    if (line.contains(username)) {
                        num++;
                        numErrorUser[i]++;
                    }
                }
                i++;
                System.out.printf("| %-16s| %6d         |\n", username, num);
            }
            pieChartForUser(numErrorUser);
            System.out.println("+" + "-".repeat(34) + "+");
            System.out.println("");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void getErrorTime(String timeStart, String timeEnd) {
        int numErrorAtTime = 0;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date start = dateFormat.parse(timeStart);
            Date end = dateFormat.parse(timeEnd);

            Scanner read_time = new Scanner(new FileInputStream("extracted_log"));
            PrintWriter output_time = new PrintWriter(new FileOutputStream("errorInTimeRange"));

            while (read_time.hasNextLine()) {
                String data = read_time.nextLine();

                if (data.contains("error")) {
                    String timestamp = data.substring(1, 11);
                    Date date = dateFormat.parse(timestamp);
                    if (!(date.before(start) || date.after(end))) {
                        numErrorAtTime++;
                    }
                }
            }
            System.out.printf("Number of job error betweem %s and %s: %d\n", timeStart, timeEnd, numErrorAtTime);
            read_time.close();
            output_time.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void typeError() {
        HashSet<String> errorType = new HashSet<>();
        int numType = 0, numInvalidQos = 0, numSecurity = 0, numlookUpFailure = 0, numInvalidNode = 0, numRequeue = 0, numNotResponding = 0,
                numPrologLaunch = 0, numValidate = 0, numComplete = 0, numRegistered = 0, numHandle = 0, numConfiguredCpu = 0,
                numController = 0, numDifferent = 0, numErrorRunning = 0, numJobResources = 0, numAborting = 0, numDealloc = 0,
                numMembers = 0, numUserNotFound = 0, numPriority = 0, numEpilog = 0;

        try {
            Scanner readUser = new Scanner(new FileInputStream("extracted_log"));
            while (readUser.hasNextLine()) {
                String line = readUser.nextLine();
                if (line.matches(".*Invalid qos.*")) {
                    numInvalidQos++;

                    errorType.add("Invalid qos");
                } else if (line.matches(".*Security.*")) {
                    numSecurity++;
                    errorType.add("Security");

                } else if (line.matches(".*lookup failure.*")) {
                    numlookUpFailure++;
                    errorType.add("lookup failure");
                } else if (line.matches(".*invalid node.*")) {
                    numInvalidNode++;
                    errorType.add("invalid node");
                } else if (line.matches(".*requeue.*")) {
                    numRequeue++;
                    errorType.add("requeue");
                } else if (line.matches(".*not responding.*")) {
                    numNotResponding++;
                    errorType.add("not responding");
                } else if (line.matches(".*error: Prolog launch.*")) //cant match
                {
                    numPrologLaunch++;
                    errorType.add("Prolog launch");
                } else if (line.matches(".*validate_node_specs:.*")) //cant match
                {
                    numValidate++;
                    errorType.add("validate_node_specs");
                } else if (line.matches(".*_complete_job.*")) {
                    numComplete++;
                    errorType.add("complete_job");
                } else if (line.matches(".*Registered PENDING.*")) {
                    numRegistered++;
                    errorType.add("Registered PENDING");
                } else if (line.matches(".*_handle_assoc.*")) {
                    numHandle++;
                    errorType.add("handle_assoc");
                } else if (line.matches(".*Configured cpu.*")) {
                    numConfiguredCpu++;
                    errorType.add("Configured cpu");
                } else if (line.matches(".*configure_controller.*")) {
                    numController++;
                    errorType.add("configure_controller");
                } else if (line.matches(".*Node.*different.*")) {
                    numDifferent++;
                    errorType.add("Node different");
                } else if (line.matches(".*error running.*")) {
                    numErrorRunning++;
                    errorType.add("error running");

                } else if (line.matches(".*valid_job_resources.*")) {
                    numJobResources++;
                    errorType.add("valid_job_resources");
                } else if (line.matches(".*Aborting.*")) {
                    numAborting++;
                    errorType.add("Aborting");
                } else if (line.matches(".*dealloc.*")) {
                    numDealloc++;
                    errorType.add("dealloc");
                } else if (line.matches(".*_get_group_members.*")) {
                    numMembers++;
                    errorType.add("get_group-members");
                } else if (line.matches(".*User.*not found")) {
                    numUserNotFound++;
                    errorType.add("User not found");
                } else if (line.matches(".*sched:.*priority.*")) {
                    numPriority++;
                    errorType.add("priority");
                } else if (line.matches(".*epilog_complete:.*")) {
                    numEpilog++;
                    errorType.add("epilog_complete");
                }

            }
            System.out.println("+" + "-".repeat(31) + "+");
            System.out.printf("|Total type of error: %d\t|\n", errorType.size());
            System.out.println("+" + "-".repeat(31) + "+");
            System.out.printf("| %-15s | %3d         |\n", "Invalid qos", numInvalidQos);
            System.out.printf("| %-15s | %3d         |\n", "Security", numSecurity);
            System.out.printf("| %-15s | %3d         |\n", "Lookup Failure", numlookUpFailure);
            System.out.printf("| %-15s | %3d         |\n", "Invalid node", numInvalidNode);
            System.out.printf("| %-15s | %3d         |\n", "Requeue", numRequeue);
            System.out.printf("| %-15s | %3d         |\n", "Not responding", numNotResponding);
            System.out.printf("| %-15s | %3d         |\n", "Prolog", numPrologLaunch);
            System.out.printf("| %-15s | %3d         |\n", "Validate", numValidate);
            System.out.printf("| %-15s | %3d         |\n", "Complete", numComplete);
            System.out.printf("| %-15s | %3d         |\n", "Registered", numRegistered);
            System.out.printf("| %-15s | %3d         |\n", "Handle", numHandle);
            System.out.printf("| %-15s | %3d         |\n", "configuredCpu", numConfiguredCpu);
            System.out.printf("| %-15s | %3d         |\n", "Controller", numController);
            System.out.printf("| %-15s | %3d         |\n", "Different", numDifferent);
            System.out.printf("| %-15s | %3d         |\n", "Error running", numErrorRunning);
            System.out.printf("| %-15s | %3d         |\n", "Job Resources", numJobResources);
            System.out.printf("| %-15s | %3d         |\n", "Aborting", numAborting);
            System.out.printf("| %-15s | %3d         |\n", "Dealloc", numDealloc);
            System.out.printf("| %-15s | %3d         |\n", "Members", numMembers);
            System.out.printf("| %-15s | %3d         |\n", "User not found", numUserNotFound);
            System.out.printf("| %-15s | %3d         |\n", "Priority", numPriority);
            System.out.printf("| %-15s | %3d         |\n", "Epilog", numEpilog);
            System.out.println("+" + "-".repeat(31) + "+");

            readUser.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void pieChartForError(int[] numError) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("June", numError[0]);
        dataset.setValue("July", numError[1]);
        dataset.setValue("Ogos", numError[2]);
        dataset.setValue("September", numError[3]);
        dataset.setValue("October", numError[4]);
        dataset.setValue("November", numError[5]);
        dataset.setValue("December", numError[6]);

        // Create a chart
        JFreeChart chart = ChartFactory.createPieChart3D("Total Error In 7 Month", dataset, true, true, false);
        // Create a panel to display the chart
        ChartPanel panel = new ChartPanel(chart);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getTitle().setPaint(Color.BLACK);
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 14));
        chart.getLegend().setBackgroundPaint(Color.LIGHT_GRAY);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setSectionPaint(0, Color.GREEN);
        plot.setSectionPaint(1, Color.RED);
        plot.setSectionPaint(2, Color.BLUE);
        plot.setSectionPaint(3, Color.ORANGE);
        plot.setSectionPaint(4, Color.DARK_GRAY);
        plot.setSectionPaint(5, Color.PINK);
        plot.setSectionPaint(7, Color.YELLOW);
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 14));
        // Add the panel to a frame
        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public void pieChartForUser(int[] numErrorUser) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("aah", numErrorUser[0]);
        dataset.setValue("aznul", numErrorUser[1]);
        dataset.setValue("chiiuling", numErrorUser[2]);
        dataset.setValue("f4ww4z", numErrorUser[3]);
        dataset.setValue("fahmi8", numErrorUser[4]);
        dataset.setValue("fairus", numErrorUser[5]);
        dataset.setValue("farhantabjani", numErrorUser[6]);
        dataset.setValue("han", numErrorUser[7]);
        dataset.setValue("hass", numErrorUser[8]);
        dataset.setValue("hongvin", numErrorUser[9]);
        dataset.setValue("htt_felicia", numErrorUser[10]);
        dataset.setValue("hva170037", numErrorUser[11]);
        dataset.setValue("janvik", numErrorUser[12]);
        dataset.setValue("kurk", numErrorUser[13]);
        dataset.setValue("liew.wei.shiung", numErrorUser[14]);
        dataset.setValue("lin0618", numErrorUser[15]);
        dataset.setValue("lobbeytan", numErrorUser[16]);
        dataset.setValue("manoj", numErrorUser[17]);
        dataset.setValue("mk_98", numErrorUser[18]);
        dataset.setValue("noraini", numErrorUser[19]);
        dataset.setValue("ongkuanhung", numErrorUser[20]);
        dataset.setValue("roland", numErrorUser[21]);
        dataset.setValue("shahreeza", numErrorUser[22]);
        dataset.setValue("tingweijing", numErrorUser[23]);
        dataset.setValue("xinpeng", numErrorUser[24]);
        dataset.setValue("yatyuen.lim", numErrorUser[25]);

        // Create a chart
        JFreeChart chart = ChartFactory.createPieChart3D("Number of Error cause by User", dataset, true, true, false);
        // Create a panel to display the chart
        ChartPanel panel = new ChartPanel(chart);
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 24));
        chart.getTitle().setPaint(Color.BLACK);
        chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 14));
        chart.getLegend().setBackgroundPaint(Color.LIGHT_GRAY);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setSectionPaint(0, Color.GREEN);
        plot.setSectionPaint(1, Color.RED);
        plot.setSectionPaint(2, Color.BLUE);
        plot.setSectionPaint(3, Color.ORANGE);
        plot.setSectionPaint(4, Color.DARK_GRAY);
        plot.setSectionPaint(5, Color.PINK);
        plot.setSectionPaint(7, Color.YELLOW);
        plot.setSectionPaint(8, Color.DARK_GRAY);
        plot.setSectionPaint(9, Color.BLUE);
        plot.setSectionPaint(10, Color.MAGENTA);
        plot.setSectionPaint(11, Color.RED);
        plot.setSectionPaint(12, Color.CYAN);
        plot.setSectionPaint(13, Color.PINK);
        plot.setSectionPaint(14, Color.YELLOW);
        plot.setSectionPaint(15, Color.blue);
        plot.setSectionPaint(16, Color.PINK);
        plot.setSectionPaint(17, Color.cyan);
        plot.setSectionPaint(18, Color.ORANGE);
        plot.setSectionPaint(19, Color.blue);
        plot.setSectionPaint(20, Color.GREEN);
        plot.setSectionPaint(21, Color.GRAY);
        plot.setSectionPaint(22, Color.black);
        plot.setSectionPaint(23, Color.blue);
        plot.setSectionPaint(24, Color.green);
        plot.setSectionPaint(25, Color.YELLOW);
        plot.setSectionPaint(26, Color.ORANGE);

        plot.setLabelFont(new Font("Arial", Font.PLAIN, 14));
        // Add the panel to a frame
        JFrame frame = new JFrame("Bar Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public int[] monthCount(String line, int[] count) {
        if (line.contains("2022-06")) {
            count[0] += 1;
        } else if (line.contains("2022-07")) {
            count[1] += 1;
        } else if (line.contains("2022-08")) {
            count[2] += 1;
        } else if (line.contains("2022-09")) {
            count[3] += 1;
        } else if (line.contains("2022-10")) {
            count[4] += 1;
        } else if (line.contains("2022-11")) {
            count[5] += 1;
        } else if (line.contains("2022-12")) {
            count[6] += 1;
        }
        return count;
    }

    public void print(String title, int[] count) {
        int total = 0;
        for (int i = 0; i < 7; i++) {
            total += count[i];
        }
        System.out.println("+" + "-".repeat(37) + "+");
        System.out.printf("| %-36s|\n", title);
        System.out.println("+" + "-".repeat(37) + "+");
        System.out.printf("| %-14s   | %-17s|\n", "Month", "Number");
        System.out.println("+" + "-".repeat(37) + "+");
        System.out.printf("| %-14s   | %-17d| \n", "June", count[0]);
        System.out.printf("| %-14s   | %-17d| \n", "July", count[1]);
        System.out.printf("| %-14s   | %-17d| \n", "August", count[2]);
        System.out.printf("| %-14s   | %-17d| \n", "September", count[3]);
        System.out.printf("| %-14s   | %-17d| \n", "October", count[4]);
        System.out.printf("| %-14s   | %-17d| \n", "November", count[5]);
        System.out.printf("| %-14s   | %-17d| \n", "December", count[6]);
        System.out.println("+" + "-".repeat(37) + "+");
        System.out.printf("|Total= %-30d|\n", total);
        System.out.println("_".repeat(39));
        System.out.println("");
    }
}





