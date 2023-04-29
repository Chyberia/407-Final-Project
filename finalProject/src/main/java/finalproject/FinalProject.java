/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package finalproject;

/**
 *
 * @author Cole Yonkers
 */
import java.util.Arrays;
import java.util.Random;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.*;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartUtils;
import java.io.File;
import java.io.IOException;

public class FinalProject {

    public static void main(String[] args) {
        fourthExperiment();
    }

    //step 1-4;
    public static void firstExperiment() {
        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Iterations";
        String yAxis = "Total Infections";
        //declare necessary variables.
        int N = 1000;
        double alpha = .005;
        double beta = .1;
        Random random = new Random();
        random.setSeed(1234567890);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected.
        int[] indexesOfInfected = new int[1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;
        //create array of 1000 people
        for (int i = 0; i < N; i++) {
            population[i] = new Person();

        }

        indexesOfInfected[0] = 1;
        numInfected++;

        //Who're the lucky winners of Covid 2.0?
        for (int i = 0; i < 2000; i++) {

            //go through every infected person
            for (int l = 0; l < numInfected; l++) {
                //go through there social connections
                for (int j = 0; j < (int) (alpha * N); j++) {
                    //Sick people can have contact with sick people, this is fine. 
                    int subject = random.nextInt(1000);
                    if (random.nextInt(100) == 1) {
                        if (population[subject].infected == 0 && population[subject].fullImmunity != 1) {

                            population[subject].setInfected();
                            //TODO: Theres a bug that causes there to be one more infected person then intended. This is fine, but it'd be nicer to find the bug.
                            if (numInfected != 1000) {
                                indexesOfInfected[numInfected] = 1;
                                numInfected++;
                                numInfectedThisRound++;
                            }

                        }

                    }
                }
            }
            series1.add(i, numInfectedThisRound);
            System.out.println("Experiment 1 Has " + numInfected + " Infected Individuals on round " + i);

        }
        
        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot1.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

    //step 6, TODO: Report amount of people infected that round. 
    public static void secondExperiment() {

        
        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Iterations";
        String yAxis = "Infections Per Round";
        
        //declare necessary variables.
        int N = 1000;
        double alpha = .005;
        double beta = .05;
        Random random = new Random();
        random.setSeed(1234567890);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;
        //create array of 1000 people
        for (int i = 0; i < N; i++) {
            population[i] = new Person();

        }

        for (int i = 0; i < 2000; i++) {
            numInfectedThisRound = 0;
            //Handle the timer for infected individuals, more complicated then I thought. 

            //remove holes from arrays
            if (i > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int u = 0; u < population.length; u++) {

                    if (indexesOfInfected[u] != 0) {
                        buffer[temp] = indexesOfInfected[u];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (i > 0) {
                int temp = 0;
                while (indexesOfInfected[temp] != 0) {
                    population[temp].infectionCountdown();
                    if (population[temp].infected == 0) {
                        population[temp].fullImmunity = 1;

                    }
                    temp++;
                }

            } else if (i == 0) {
                for (int j = 0; j < 20; j++) {
                    int index = random.nextInt(1000);
                    indexesOfInfected[j] = index;
                    population[index].setInfected();
                    numInfected++;
                }
            }

            for (int l = 0; l < numInfected; l++) {
                //go through there social connections
                for (int j = 0; j < (int) (alpha * N); j++) {
                    int subject = random.nextInt(1000);
                    //TODO: Implement beta into if statement here
                    if (random.nextInt(100) < (int) (100 * beta)) {
                        if (population[subject].infected == 0 && population[subject].fullImmunity != 1) {

                            population[subject].setInfected();
                            //TODO: Theres a bug that causes there to be one more infected person then intended. This is fine, but it'd be nicer to find the bug.
                            if (numInfected != 1000) {
                                indexesOfInfected[numInfected] = subject;
                                numInfected++;
                                numInfectedThisRound++;
                            }

                        }

                    }

                }

            }
            series1.add(i, numInfectedThisRound);
            System.out.println("On round " + i + " there are " + numInfected + " individuals who have been infected so far, " + numInfectedThisRound + " have been infected this round.");
            
        }
        
        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot2.png"), chart, 450, 400);
        } catch (IOException ex) {

        }
        
        
    }

    //step 7
    public static void thirdExperiment() {
        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Iterations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double alpha = .005;
        double beta = .01;
        Random random = new Random();
        random.setSeed(1234567890);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numImmune = 0;
        int numInfectedThisRound = 0;
        //create array of 1000 people
        for (int i = 0; i < N; i++) {
            population[i] = new Person();

        }

        for (int i = 0; i < 2000; i++) {
            numInfectedThisRound = 0;

            if (i > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int u = 0; u < population.length; u++) {

                    if (indexesOfInfected[u] != 0) {
                        buffer[temp] = indexesOfInfected[u];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            //remove holes in immunityIndexes
            if (i > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int u = 0; u < population.length; u++) {

                    if (indexesOfImmune[u] != 0) {

                        buffer[temp] = indexesOfImmune[u];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //Handle immunity timer countdown
            int temp = 0;
            if (i > 0) {
                while (indexesOfImmune[temp] != 0) {
                    population[temp].immunityCountdown();
                    if (population[temp].immunityTimer == 0) {

                        indexesOfImmune[temp] = 0;
                        numImmune--;

                    }
                    temp++;

                }

            }

            //Handle the timer for infected individuals, create index of immune people who have recovered
            if (i > 0) {
                temp = 0;
                int count = 0;
                while (indexesOfInfected[temp] != 0) {
                    population[temp].infectionCountdown();
                    if (population[temp].infected == 0) {
                        population[temp].immunityCountdown(20);
                        indexesOfImmune[count] = temp;
                        numImmune++;
                        count++;
                    }
                    temp++;

                }

            } else if (i == 0) {
                for (int j = 0; j < 20; j++) {
                    int index = random.nextInt(1000);
                    indexesOfInfected[j] = index;
                    population[index].setInfected();
                    numInfected++;
                }
            }

            for (int l = 0; l < numInfected; l++) {
                //go through there social connections
                for (int j = 0; j < (int) (alpha * N); j++) {
                    int subject = random.nextInt(1000);
                    if (random.nextInt(100) < (int) (100 * beta)) {
                        if (population[subject].infected == 0 && population[subject].immunityTimer == 0) {

                            population[subject].setInfected();
                            //TODO: Theres a bug that causes there to be one more infected person then intended. This is fine, but it'd be nicer to find the bug.
                            if (numInfected != 1000) {
                                indexesOfInfected[numInfected] = 1;
                                numInfected++;
                                numInfectedThisRound++;
                            }

                        }

                    }

                }

            }

            series1.add(i, numInfectedThisRound);
            System.out.println("On round " + i + " there are " + numInfected + " individuals who have been infected so far, " + numInfectedThisRound + " have been infected this round.");

        }
        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot3.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

//part a, something is making it run super slow. 
    public static void fourthExperiment() {

        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Vaccinations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double beta = .01;
        double gamma = .05;
        //p in G(n,p)
        double alpha = .1;
        double theta = .05;
        double v1 = .6;
        double v2 = .6;
        int v = 0;
        Random random = new Random();
        random.setSeed(343);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        int[][] adjacencyMatrix = new int[1000][1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;

        //create the adjacency matrix
        for (int row = 0; row < population.length; row++) {
            for (int column = 0; column < population.length; column++) {
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100)) {

                        adjacencyMatrix[row][column] = 1;
                        adjacencyMatrix[column][row] = 1;
                    }
                }
            }
        }
        //figure out how many connections a person has, and who they are.
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    count++;
                }
            }
            population[row] = new Person(count);
        }

        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    population[row].socialGroupConnections[count] = col;
                    count++;
                }
            }

        }

        int breakFlag;
        //choose who is initially infected
        for (int j = 0; j < 20; j++) {
            int index = random.nextInt(1000);
            breakFlag = 0;
            for (int i = 0; i < numInfected; i++) {

                if (indexesOfInfected[i] == index) {
                    breakFlag = 1;
                    j--;
                }
            }
            if (breakFlag != 1) {
                indexesOfInfected[j] = index;
                population[index].setInfected();
                population[index].infectionCountdown(10);
                numInfected++;
            }

        }

        //go through the simulation
        for (int iteration = 0; iteration < 2000; iteration++) {

            numInfectedThisRound = 0;
            //remove holes from arrays
            if (iteration > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfInfected[i] != 0) {
                        buffer[temp] = indexesOfInfected[i];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (indexesOfInfected[0] == 0) {

                System.out.println("The infection has died out at round: " + iteration);
                break;
            }

            //remove holes in immunityIndexes
            if (iteration > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfImmune[i] != 0) {

                        buffer[temp] = indexesOfImmune[i];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //handle immunity countdown
            int temp = 0;
            while (indexesOfImmune[temp] != 0) {

                int subject = indexesOfImmune[temp];
                population[subject].immunityCountdown();
                if (population[subject].immunityTimer == 0) {
                    indexesOfImmune[temp] = 0;
                }
            }

            //handle infection countdown
            temp = 0;
            while (indexesOfInfected[temp] != 0) {

                int subject = indexesOfInfected[temp];
                population[subject].decrementInfection();
                if (population[subject].infected == 0) {
                    indexesOfInfected[temp] = 0;
                    numInfected--;
                    for (int i = 0; i < population.length; i++) {
                        if (indexesOfImmune[i] == 0) {
                            indexesOfImmune[i] = subject;
                            population[subject].immunityCountdown(5);
                            break;
                        }
                    }
                }
                temp++;
            }

            //go through all of the infected individuals
            temp = 0;
            int count = (int) (gamma * population.length);
            while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupConnections.length);
                    if (random.nextInt(100) > (int) (population[connection].vaccinationEffectiveness * 100)) {
                        if (random.nextInt(100) < (int) (beta * 100)) {
                            if (population[connection].infected == 0 && population[connection].immunityTimer == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                        int countdown = random.nextInt(20);
                                        population[connection].infectionCountdown(countdown);
                                        indexesOfInfected[j] = connection;
                                        break;

                                    }


                                }

                            }

                        }
                    }

                }

                temp++;
            }
            series1.add(v, numInfected);
            System.out.print("Infected Amount: " + numInfected);
            System.out.println(", Num Infected This Round: " + numInfectedThisRound);
            System.out.println("Number of Vaccinated People: " + v);

        }

        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot5.png"), chart, 450, 400);
        } catch (IOException ex) {

        }
    }
//part b and c

    public static void fifthExperiment() {

        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Vaccinations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double beta = .01;
        double gamma = .05;
        //p in G(n,p)
        double alpha = .1;
        double theta = .05;
        double v1 = .6;
        double v2 = .6;
        int v = 0;
        Random random = new Random();
        random.setSeed(343);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        int[][] adjacencyMatrix = new int[1000][1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;

        //create the adjacency matrix
        for (int row = 0; row < population.length; row++) {
            for (int column = 0; column < population.length; column++) {
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100)) {

                        adjacencyMatrix[row][column] = 1;
                        adjacencyMatrix[column][row] = 1;
                    }
                }
            }
        }
        //figure out how many connections a person has, and who they are.
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    count++;
                }
            }
            population[row] = new Person(count);
        }

        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    population[row].socialGroupConnections[count] = col;
                    count++;
                }
            }

        }

        int breakFlag;
        //choose who is initially infected
        for (int j = 0; j < 20; j++) {
            int index = random.nextInt(1000);
            breakFlag = 0;
            for (int i = 0; i < numInfected; i++) {

                if (indexesOfInfected[i] == index) {
                    breakFlag = 1;
                    j--;
                }
            }
            if (breakFlag != 1) {
                indexesOfInfected[j] = index;
                population[index].setInfected();
                population[index].infectionCountdown(10);
                numInfected++;
            }

        }

        //go through the simulation
        for (int iteration = 0; iteration < 2000; iteration++) {
            //Who gets the booster? 
            if (iteration > 3) {
                for (int subject = 0; subject < population.length; subject++) {
                    if (random.nextInt(100) < (int) (theta * 100)) {
                        int temp = random.nextInt(N);
                        if (population[temp].vaccinationEffectiveness == 0) {
                            population[temp].vaccinationEffectiveness = v1;
                            v++;
                        }
                    }

                }
            }

            numInfectedThisRound = 0;
            //remove holes from arrays
            if (iteration > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfInfected[i] != 0) {
                        buffer[temp] = indexesOfInfected[i];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (indexesOfInfected[0] == 0) {

                System.out.println("The infection has died out at round: " + iteration);
                break;
            }

            //remove holes in immunityIndexes
            if (iteration > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfImmune[i] != 0) {

                        buffer[temp] = indexesOfImmune[i];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //handle immunity countdown
            int temp = 0;
            while (indexesOfImmune[temp] != 0) {

                int subject = indexesOfImmune[temp];
                population[subject].immunityCountdown();
                if (population[subject].immunityTimer == 0) {
                    indexesOfImmune[temp] = 0;
                }
            }

            //handle infection countdown
            temp = 0;
            while (indexesOfInfected[temp] != 0) {

                int subject = indexesOfInfected[temp];
                population[subject].decrementInfection();
                if (population[subject].infected == 0) {
                    indexesOfInfected[temp] = 0;
                    numInfected--;
                    for (int i = 0; i < population.length; i++) {
                        if (indexesOfImmune[i] == 0) {
                            indexesOfImmune[i] = subject;
                            population[subject].immunityCountdown(5);
                            break;
                        }
                    }
                }
                temp++;
            }

            //go through all of the infected individuals
            temp = 0;
            int count = (int) (gamma * population.length);
            while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupConnections.length);
                    if (random.nextInt(100) > (int) (population[connection].vaccinationEffectiveness * 100)) {
                        if (random.nextInt(100) < (int) (beta * 100)) {
                            if (population[connection].infected == 0 && population[connection].immunityTimer == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                        int countdown = random.nextInt(20);
                                        population[connection].infectionCountdown(countdown);
                                        indexesOfInfected[j] = connection;
                                        break;

                                    }


                                }

                            }

                        }
                    }

                }

                temp++;
            }
            series1.add(v, numInfected);
            System.out.print("Infected Amount: " + numInfected);
            System.out.println(", Num Infected This Round: " + numInfectedThisRound);
            System.out.println("Number of Vaccinated People: " + v);

        }

        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot5.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

//part d    
    public static void sixthExperiment() {

        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Vaccinations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double beta = .01;
        double gamma = .05;
        //p in G(n,p)
        double alpha = .1;
        double theta = .01;
        double v1 = .6;
        double v2 = .6;
        int v = 0;
        double wearoff = .07;
        Random random = new Random();
        random.setSeed(64237);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        int[][] adjacencyMatrix = new int[1000][1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;

        //create the adjacency matrix
        for (int row = 0; row < population.length; row++) {
            for (int column = 0; column < population.length; column++) {
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100)) {

                        adjacencyMatrix[row][column] = 1;
                        adjacencyMatrix[column][row] = 1;
                    }
                }
            }
        }
        //figure out how many connections a person has, and who they are.
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    count++;
                }
            }
            population[row] = new Person(count);
        }

        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    population[row].socialGroupConnections[count] = col;
                    count++;
                }
            }

        }

        int breakFlag;
        //choose who is initially infected
        for (int j = 0; j < 20; j++) {
            int index = random.nextInt(1000);
            breakFlag = 0;
            for (int i = 0; i < numInfected; i++) {

                if (indexesOfInfected[i] == index) {
                    breakFlag = 1;
                    j--;
                }
            }
            if (breakFlag != 1) {
                indexesOfInfected[j] = index;
                population[index].setInfected();
                population[index].infectionCountdown(10);
                numInfected++;
            }

        }

        
        
        
        //go through the simulation
        for (int iteration = 0; iteration < 2000; iteration++) {
            
                        //vaccination wearoff effect
            if (iteration > 4) {

                for (int subject = 0; subject < population.length; subject++) {

                    if (population[subject].vaccinationEffectiveness > 0) {

                        population[subject].vaccinationEffectiveness -= wearoff;

                    }

                }

            }
            
            //Who gets the booster? 
            if (iteration > 3) {
                for (int subject = 0; subject < population.length; subject++) {
                    if (random.nextInt(100) < (int) (theta * 100)) {
                        int temp = random.nextInt(N);
                        if (population[temp].vaccinationEffectiveness == 0) {
                            population[temp].vaccinationEffectiveness = v1;
                            v++;
                        }
                    }

                }
            }


            numInfectedThisRound = 0;
            //remove holes from arrays
            if (iteration > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfInfected[i] != 0) {
                        buffer[temp] = indexesOfInfected[i];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (indexesOfInfected[0] == 0) {

                System.out.println("The infection has died out at round: " + iteration);
                break;
            }

            //remove holes in immunityIndexes
            if (iteration > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfImmune[i] != 0) {

                        buffer[temp] = indexesOfImmune[i];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //handle immunity countdown
            int temp = 0;
            while (indexesOfImmune[temp] != 0) {

                int subject = indexesOfImmune[temp];
                population[subject].immunityCountdown();
                if (population[subject].immunityTimer == 0) {
                    indexesOfImmune[temp] = 0;
                }
            }

            //handle infection countdown
            temp = 0;
            while (indexesOfInfected[temp] != 0) {

                int subject = indexesOfInfected[temp];
                population[subject].decrementInfection();
                if (population[subject].infected == 0) {
                    indexesOfInfected[temp] = 0;
                    numInfected--;
                    for (int i = 0; i < population.length; i++) {
                        if (indexesOfImmune[i] == 0) {
                            indexesOfImmune[i] = subject;
                            population[subject].immunityCountdown(5);
                            break;
                        }
                    }
                }
                temp++;
            }

            //go through all of the infected individuals
            temp = 0;
            int count = (int) (gamma * population.length);
            while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupConnections.length);
                    int target = population[subject].socialGroupConnections[connection];
                    if (random.nextInt(100) > ((int)(population[connection].vaccinationEffectiveness * 100))) {
                        if (random.nextInt(100) < (int) (beta * 100)) {
                            if (population[target].infected == 0 && population[target].immunityTimer == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                       int countdown = random.nextInt(20);
                                    population[target].infectionCountdown(countdown);
                                        indexesOfInfected[j] = target;
                                        break;

                                    }


                                }

                                
                                
                            }

                        }
                    }

                }

                temp++;
            }
            series1.add(v, numInfected);
            System.out.print("Infected Amount: " + numInfected);
            System.out.println(", Num Infected This Round: " + numInfectedThisRound);
            System.out.println("Number of Vaccinated People: " + v);

        }

        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot6.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

// part e, TODO
    public static void seventhExperiment() {

        //part d   
        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Vaccinations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double beta = .15;
        double gamma = .1;
        //p in G(n,p)
        double alpha = .2;
        double alphaScalar = 1.3;
        double theta = .01;
        double v1 = .6;
        double v2 = .6;
        int v = 0;
        double wearoff = .01;
        Random random = new Random();
        random.setSeed(7345);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        int[][] adjacencyMatrix = new int[1000][1000];
        int[][] adjacencyMatrix2 = new int[1000][1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;

        //create the adjacency matrix
        for (int row = 0; row < population.length; row++) {
            for (int column = 0; column < population.length; column++) {
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100)) {

                        adjacencyMatrix[row][column] = 1;
                        adjacencyMatrix[column][row] = 1;
                    }
                }
            }
        }        
        
        //figure out how many connections a person has, and who they are.
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    count++;
                }
            }
            population[row] = new Person(count);
        }
        
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    population[row].socialGroupConnections[count] = col;
                    count++;
                }
            }

        }
        
        //create matrix of increased connections
        //copy adjacency matrix
        for(int row = 0; row < population.length; row++){
            for(int column = 0; column < population.length; column++){
                adjacencyMatrix2[row][column] = adjacencyMatrix[row][column];       
            }     
        }
        
        //create extra connections
        for(int row = 0; row < population.length; row++){
            for(int column = 0; column < population.length; column++){
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100 * alphaScalar)) {

                        adjacencyMatrix2[row][column] = 1;
                        adjacencyMatrix2[column][row] = 1;
                    }
                }                               
            }
            
        }
        
        
        
        
            for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix2[row][col] == 1) {
                    population[row].socialGroupConnections2[count] = col;
                    count++;
                }
                
            }
            
            population[row].socialGroupSize2 = count;
        }
        
        
        int breakFlag;
        //choose who is initially infected
        for (int j = 0; j < 20; j++) {
            int index = random.nextInt(1000);
            breakFlag = 0;
            for (int i = 0; i < numInfected; i++) {

                if (indexesOfInfected[i] == index) {
                    breakFlag = 1;
                    j--;
                }
            }
            if (breakFlag != 1) {
                indexesOfInfected[j] = index;
                population[index].setInfected();
                population[index].infectionCountdown(10);
                numInfected++;
            }

        }

        //go through the simulation
        for (int iteration = 0; iteration < 2000; iteration++) {
            //Who gets the booster? 
            if (iteration > 3) {
                for (int subject = 0; subject < population.length; subject++) {
                    if (random.nextInt(100) < (int) (theta * 100)) {
                        int temp = random.nextInt(N);
                        if (population[temp].vaccinationEffectiveness == 0) {
                            population[temp].vaccinationEffectiveness = v1;
                            v++;
                        }
                    }

                }
            }
            //vaccination wearoff effect
            if (iteration > 3) {

                for (int subject = 0; subject < population.length; subject++) {

                    if (population[subject].vaccinationEffectiveness > 0) {

                        population[subject].vaccinationEffectiveness -= wearoff;

                    }

                }

            }

            numInfectedThisRound = 0;
            //remove holes from arrays
            if (iteration > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfInfected[i] != 0) {
                        buffer[temp] = indexesOfInfected[i];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (indexesOfInfected[0] == 0) {

                System.out.println("The infection has died out at round: " + iteration);
                break;
            }

            //remove holes in immunityIndexes
            if (iteration > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfImmune[i] != 0) {

                        buffer[temp] = indexesOfImmune[i];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //handle immunity countdown
            int temp = 0;
            while (indexesOfImmune[temp] != 0) {

                int subject = indexesOfImmune[temp];
                population[subject].immunityCountdown();
                if (population[subject].immunityTimer == 0) {
                    indexesOfImmune[temp] = 0;
                }
            }

            //handle infection countdown
            temp = 0;
            while (indexesOfInfected[temp] != 0) {

                int subject = indexesOfInfected[temp];
                population[subject].decrementInfection();
                if (population[subject].infected == 0) {
                    indexesOfInfected[temp] = 0;
                    numInfected--;
                    for (int i = 0; i < population.length; i++) {
                        if (indexesOfImmune[i] == 0) {
                            indexesOfImmune[i] = subject;
                            population[subject].immunityCountdown(5);
                            break;
                        }
                    }
                }
                temp++;
            }
            temp = 0;
            int count = (int) (gamma * population.length);
            //period of increased social activity;
            if(iteration > 50 && iteration < 150){
                while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupSize2);
                    if (random.nextInt(100) > (int) (population[connection].vaccinationEffectiveness * 100)) {
                        if (random.nextInt(100) > (int) (beta * 100)) {
                            if (population[connection].infected == 0 && population[connection].immunityTimer == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                        int countdown = random.nextInt(20);
                                        population[connection].infectionCountdown(countdown);
                                        indexesOfInfected[j] = connection;
                                        break;

                                    }


                                }

                            }

                        }
                    }

                }

                temp++;
            }
                
                
            }
            
            else{
//go through all of the infected individuals
            temp = 0;
            count = (int) (gamma * population.length);
            while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupConnections.length);
                    int target = population[subject].socialGroupConnections[connection];
                    if (random.nextInt(100) > ((int)(population[connection].vaccinationEffectiveness * 100))) {
                        if (random.nextInt(100) < (int) (beta * 100)) {
                            if (population[target].infected == 0 && population[target].immunityTimer == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                       int countdown = random.nextInt(20);
                                    population[target].infectionCountdown(countdown);
                                        indexesOfInfected[j] = target;
                                        break;

                                    }


                                }

                                
                                
                            }

                        }
                    }

                }

                temp++;
            }
            }
            series1.add(v, numInfected);
            System.out.print("Infected Amount: " + numInfected);
            System.out.println(", Num Infected This Round: " + numInfectedThisRound);
            System.out.println("Number of Vaccinated People: " + v);
            
        }

        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot7.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

    
     public static void eighthExperiment() {

        //part d   
        //Graph stuff
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Object 1");
        String title = "Infection Rates";
        String xAxis = "Vaccinations";
        String yAxis = "Infections Per Round";

        //declare necessary variables.
        int N = 1000;
        double beta = .15;
        double gamma = .1;
        //p in G(n,p)
        double alpha = .2;
        double alphaScalar = 1.3;
        double theta = .01;
        double v1 = .6;
        double v2 = .6;
        int v = 0;
        double wearoff = .01;
        Random random = new Random();
        random.setSeed(7345);

        Person[] population = new Person[1000];
        //We could waste time checking every single persons infection status, or we could keep an array of indexes of which people are infected. After clearing someone from the index, likely should 
        //should shift array appropriately for ease of use and avoid holes.
        int[] indexesOfInfected = new int[1000];
        int[] indexesOfImmune = new int[1000];
        int[][] adjacencyMatrix = new int[1000][1000];
        int[][] adjacencyMatrix2 = new int[1000][1000];
        //While there are dynamic ways to check for how many people are infected, this is far simpler (and faster) as long as you increment and/or decrement properly. 
        int numInfected = 0;
        int numInfectedThisRound = 0;
        int numCarriers = 0;

        //create the adjacency matrix
        for (int row = 0; row < population.length; row++) {
            for (int column = 0; column < population.length; column++) {
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100)) {

                        adjacencyMatrix[row][column] = 1;
                        adjacencyMatrix[column][row] = 1;
                    }
                }
            }
        }        
        
        //figure out how many connections a person has, and who they are.
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    count++;
                }
            }
            population[row] = new Person(count);
        }
        
        for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix[row][col] == 1) {
                    population[row].socialGroupConnections[count] = col;
                    count++;
                }
            }

        }
        
        //create matrix of increased connections
        //copy adjacency matrix
        for(int row = 0; row < population.length; row++){
            for(int column = 0; column < population.length; column++){
                adjacencyMatrix2[row][column] = adjacencyMatrix[row][column];       
            }     
        }
        
        //create extra connections
        for(int row = 0; row < population.length; row++){
            for(int column = 0; column < population.length; column++){
                if (row != column) {
                    if (random.nextInt(100) < (int) (alpha * 100 * alphaScalar)) {

                        adjacencyMatrix2[row][column] = 1;
                        adjacencyMatrix2[column][row] = 1;
                    }
                }                               
            }
            
        }
        
            for (int row = 0; row < population.length; row++) {
            int count = 0;
            for (int col = 0; col < population.length; col++) {
                if (adjacencyMatrix2[row][col] == 1) {
                    population[row].socialGroupConnections2[count] = col;
                    count++;
                }
                population[row].socialGroupSize2 = count;
            }

        }
        
        
        int breakFlag;
        //choose who is initially infected
        for (int j = 0; j < 20; j++) {
            int index = random.nextInt(1000);
            breakFlag = 0;
            for (int i = 0; i < numInfected; i++) {

                if (indexesOfInfected[i] == index) {
                    breakFlag = 1;
                    j--;
                }
            }
            if (breakFlag != 1) {
                indexesOfInfected[j] = index;
                population[index].setInfected();
                population[index].infectionCountdown(10);
                numInfected++;
            }

        }

        //go through the simulation
        for (int iteration = 0; iteration < 2000; iteration++) {
            //Who gets the booster? 
            if (iteration > 3) {
                for (int subject = 0; subject < population.length; subject++) {
                    if (random.nextInt(100) < (int) (theta * 100)) {
                        int temp = random.nextInt(N);
                        if (population[temp].vaccinationEffectiveness == 0) {
                            population[temp].vaccinationEffectiveness = v1;
                            v++;
                        }
                    }

                }
            }
            //vaccination wearoff effect
            if (iteration > 3) {

                for (int subject = 0; subject < population.length; subject++) {

                    if (population[subject].vaccinationEffectiveness > 0) {

                        population[subject].vaccinationEffectiveness -= wearoff;

                    }

                }

            }

            numInfectedThisRound = 0;
            //remove holes from arrays
            if (iteration > 0) {
                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfInfected[i] != 0) {
                        buffer[temp] = indexesOfInfected[i];
                        temp++;
                    }

                }

                indexesOfInfected = buffer.clone();
            }

            if (indexesOfInfected[0] == 0) {

                System.out.println("The infection has died out at round: " + iteration);
                break;
            }

            //remove holes in immunityIndexes
            if (iteration > 0) {

                int[] buffer = new int[1000];
                int temp = 0;
                for (int i = 0; i < population.length; i++) {

                    if (indexesOfImmune[i] != 0) {

                        buffer[temp] = indexesOfImmune[i];
                        temp++;
                    }

                }
                indexesOfImmune = buffer.clone();
            }

            //handle immunity countdown
            int temp = 0;
            while (indexesOfImmune[temp] != 0) {

                int subject = indexesOfImmune[temp];
                population[subject].immunityCountdown();
                if (population[subject].immunityTimer == 0) {
                    indexesOfImmune[temp] = 0;
                }
            }

            //handle infection countdown
            temp = 0;
            while (indexesOfInfected[temp] != 0) {

                int subject = indexesOfInfected[temp];
                if(population[subject].carrier == 0){
                population[subject].decrementInfection();
                }
                if(population[subject].infected == 0 && random.nextInt(100) == 1){
                    
                    numCarriers++;
                    numInfected--;
                    population[subject].carrier = 1;
                    
                }
                else if (population[subject].infected == 0 && population[subject].carrier == 0) {
                    indexesOfInfected[temp] = 0;
                    numInfected--;
                    for (int i = 0; i < population.length; i++) {
                        if (indexesOfImmune[i] == 0) {
                            indexesOfImmune[i] = subject;
                            population[subject].immunityCountdown(5);
                            break;
                        }
                    }
                }
                temp++;
            }
            temp = 0;
            int count = (int) (gamma * population.length);
            //period of increased social activity;
            if(iteration > 50 && iteration < 150){
                while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupSize2);
                    if (random.nextInt(100) > (int) (population[connection].vaccinationEffectiveness * 100)) {
                        if (random.nextInt(100) > (int) (beta * 100)) {
                            if (population[connection].infected == 0 && population[connection].immunityTimer == 0 && population[subject].carrier == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                        indexesOfInfected[j] = connection;
                                        break;

                                    }
                                    int countdown = random.nextInt(20);
                                    population[connection].infectionCountdown(countdown);


                                }

                            }

                        }
                    }

                }

                temp++;
            }
                
                
            }
            
            else{
            //go through all of the infected individuals
            temp = 0;
            while (indexesOfInfected[temp] != 0) {
                int subject = indexesOfInfected[temp];
                //go through there social connections
                for (int i = 0; i < count; i++) {
                    int connection = random.nextInt(population[subject].socialGroupConnections.length);
                    if (random.nextInt(100) < (int) (population[connection].vaccinationEffectiveness * 100)) {
                        if (random.nextInt(100) < (int) (beta * 100)) {
                            if (population[connection].infected == 0 && population[connection].immunityTimer == 0 && population[subject].carrier == 0) {

                                numInfected++;
                                numInfectedThisRound++;
                                for (int j = 0; j < population.length; j++) {
                                    if (indexesOfInfected[j] == 0) {
                                        indexesOfInfected[j] = connection;
                                        break;

                                    }
                                    population[connection].infectionCountdown(10);

                                }

                            }

                        }
                    }

                }

                temp++;
            }
            }
            series1.add(v, numInfected);
            System.out.print("Infected Amount: " + numInfected);
            System.out.println(", Num Infected This Round: " + numInfectedThisRound);
            System.out.println("Number of Vaccinated People: " + v);
            System.out.println("Number of Carriers: " + numCarriers);
            
        }

        dataset.addSeries(series1);
        boolean showLegend = false;
        boolean createURL = false;
        boolean createTooltip = false;

        JFreeChart chart = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, showLegend, createTooltip, createURL);
        try {
            ChartUtils.saveChartAsPNG(new File("XYPlot8.png"), chart, 450, 400);
        } catch (IOException ex) {

        }

    }

    
}
