import java.util.*;
import java.io.*;

public class Assembler2 {

    private HashMap<String, String> _imperativeStatements;
    private HashMap<String, String> _declarativeStatements;

    private HashMap<String, String> getMap(ArrayList<String> statements, int opcode) {
        HashMap<String, String> output = new HashMap<>();

        for (String key : statements) {
            String value = null;

            if (opcode < 10) {
                value = "0" + opcode;
            } else {
                value = String.valueOf(opcode);
            }

            output.put(key, value);
            opcode++;
        }

        return output;
    }

    Assembler2() {

        ArrayList<String> imperativeStatements = new ArrayList<>(
                Arrays.asList("STOP", "ADD", "SUB", "MULT", "MOVEM", "MOVER", "COMP", "DIV", "READ", "PRINT"));
        _imperativeStatements = getMap(imperativeStatements, 0);

        ArrayList<String> declarativeStatements = new ArrayList<>(Arrays.asList("DS", "DC"));
        _declarativeStatements = getMap(declarativeStatements, 1);

    }

    public static void main(String[] args) {
        Assembler2 assem = new Assembler2();
        assem.Passone();
    }

    void Passone() {

        try {

            FileReader filereader = new FileReader("C:\\Users\\gades\\Downloads\\input.txt");
            BufferedReader bufferReader = new BufferedReader(filereader);

            FileWriter fileWriter = new FileWriter("C:\\Users\\gades\\Downloads\\IC.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            HashMap<String, String> symbolTable = new HashMap<>();
            HashMap<String, String> literalTable = new HashMap<>();
            ArrayList<Integer> poolTable = new ArrayList<>();

            int locationPointer = 0;
            int symbolTablePointer = 1;
            int literalTablePointer = 1;
            int poolTablePointer = 1;

            String currentLine = bufferReader.readLine();
            String[] Line = currentLine.split(" ");

            if (Line[1].equals("START")) {
                locationPointer = Integer.parseInt(Line[2]);
                bufferedWriter.write("AD\t01" + "C\t" + locationPointer);

            }

            while ((currentLine = bufferReader.readLine()) != null) {
                Line = currentLine.split("[ ,]");

                boolean isLocationPointerSet = false;

                if (!Line[0].isEmpty()) {
                    symbolTable.put(Line[0], String.valueOf(locationPointer));

                }

                // if Origin

                if (Line[1].equals("ORIGIN")) {
                    int address = Integer.parseInt(symbolTable.get(Line[2]));

                    if (Line[3].equals("+")) {
                        address += Integer.parseInt(Line[4]);
                    }

                    else {
                        address -= Integer.parseInt(Line[4]);
                    }

                    locationPointer = address;

                    bufferedWriter.write("AD\t02");
                    bufferedWriter.write("C\t" + String.valueOf(locationPointer));
                    isLocationPointerSet = true;
                }

                if (Line[1].equals("EQU")) {
                    int address = Integer.parseInt(symbolTable.get(Line[2]));

                    if (Line[3].equals("+")) {
                        address += Integer.parseInt(Line[4]);
                    } else {
                        address -= Integer.parseInt(Line[4]);
                    }
                    bufferedWriter.write(("AD\t04\t") + Line[0] + String.valueOf(locationPointer));
                }

                // if LTORG

                if (Line[1].equals("LTORG")) {
                    poolTable.add(poolTablePointer);

                    for (Map.Entry<String, String> table : literalTable.entrySet()) {
                        if (table.getValue().isEmpty()) {
                            table.setValue(String.valueOf(locationPointer));
                            locationPointer++;
                            poolTablePointer++;
                            isLocationPointerSet = true;
                        }

                    }
                }

                // if declarative statements

                for (Map.Entry<String, String> table : _declarativeStatements.entrySet()) {

                    if (table.getKey().equals(Line[1])) {
                        if (table.getKey().equals("DC")) {

                            bufferedWriter.write("DL\t02" + Line[2]);
                            symbolTable.put(Line[0], String.valueOf(locationPointer));

                        } else {
                            int address = locationPointer + Integer.parseInt(Line[2]);
                            locationPointer = address;

                            bufferedWriter.write("DL\t01" + Line[2]);
                            bufferedWriter.write(Line[0] + String.valueOf(locationPointer));

                            isLocationPointerSet = true;

                        }
                    }
                }

                // imperative statements;

                for (Map.Entry<String, String> table : _imperativeStatements.entrySet()) {

                    if (table.getKey().equals(Line[1])) {

                        bufferedWriter.write("IS\t" + table.getValue());

                        if (Line.length > 2) {
                            switch (Line[2]) {
                                case "AREG":
                                    bufferedWriter.write("1\t");
                                case "BREG":
                                    bufferedWriter.write("2\t");
                                case "CREG":
                                    bufferedWriter.write("3\t");
                                case "DREG":
                                    bufferedWriter.write("4\t");

                            }

                            if (Line.length > 3) {
                                if (Line[3].contains("=")) {
                                    bufferedWriter.write("L\t" + literalTablePointer);
                                    literalTable.put(Line[3], "");
                                    literalTablePointer++;
                                } else {
                                    if (symbolTable.get(Line[3]) == null) {

                                        bufferedWriter.write("S\t" + symbolTablePointer);
                                        symbolTable.put(Line[3], "");
                                        symbolTablePointer++;
                                    }

                                }
                            }

                            if (Line[1].equals("END")) {
                                poolTable.add(poolTablePointer);

                                for (Map.Entry<String, String> tab : literalTable.entrySet()) {
                                    if (tab.getValue().isEmpty()) {
                                        tab.setValue(String.valueOf(locationPointer));
                                        locationPointer++;
                                        poolTablePointer++;
                                        isLocationPointerSet = true;
                                    }

                                }

                                bufferedWriter.write("AD\t05\t");

                            }

                            if (isLocationPointerSet == false) {
                                locationPointer++;
                            }

                            bufferedWriter.newLine();
                        }

                    }
                }
            }

            bufferReader.close();
            bufferedWriter.close();

            System.out.println("--------------------symbol-Tabel-------------------");
            FileWriter symbolTableWriter = new FileWriter("SYMBOLTABLE.txt");
            BufferedWriter BSymbolTableWriter = new BufferedWriter(symbolTableWriter);

            for (Map.Entry<String, String> tt : symbolTable.entrySet()) {

                BSymbolTableWriter.write(tt.getKey() + "\t" + tt.getValue() + "\n");
                System.out.println(tt.getKey() + "\t" + tt.getValue());
            }

            BSymbolTableWriter.close();

            FileWriter literalFileWriter = new FileWriter("LiteralTable.txt");
            BufferedWriter BliteralTableWriter = new BufferedWriter(literalFileWriter);

            System.out.println("-------------------------Literal-Table-----------------------");

            for (Map.Entry<String, String> tt : literalTable.entrySet()) {

                BliteralTableWriter.write(tt.getKey() + "\t" + tt.getValue() + "\n");
                System.out.println(tt.getKey() + "\t" + tt.getValue());

            }
            BliteralTableWriter.close();

            FileWriter poolTableWriter = new FileWriter("PoolTABLE.txt");
            BufferedWriter BpoolTablewriter = new BufferedWriter(poolTableWriter);

            for (Integer k : poolTable) {

                BpoolTablewriter.write(k + "\n");
                System.out.println(k);
            }

            BpoolTablewriter.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
