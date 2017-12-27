package org.lasarobotics.hazylib.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Parameters {

    public static LinkedList<Parameter> parametersList = new LinkedList<>();
    private static File parametersFile;

    public static void init() {
        parametersFile = new File("/home/admin/params.txt");

        try {
            parametersFile.createNewFile();
        } catch (Exception e) {

        }
    }

    public static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(parametersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                int pos = line.indexOf(" ");
                if (pos != -1) {
                    for (Parameter c : parametersList) {
                        if (c.getName().equals(line.substring(0, pos))) {
                            c.value = Double.parseDouble(line.substring(pos));

                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid line!");
                }
            }
        } catch (Exception e) {
            System.out.println("Messed up readin paramters");
        }
    }

    public static class Parameter {

        private double value;
        private String name;

        public Parameter(String nm, double val) {
            this.name = nm;
            this.value = val;
            parametersList.add(this);
        }

        public double getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
