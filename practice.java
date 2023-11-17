import java.util.*;
import java.io.*;
public class practice {
    private HashMap<String,String> IS = new HashMap<>();
    private HashMap<String,String> DL = new HashMap<>();

    HashMap<String, Integer> symbolTablePointerMapper = new HashMap<String, Integer>();
    HashMap<String, Integer> literalTablePointerMapper = new HashMap<String, Integer>();
    HashMap<String,String> symtab = new HashMap<>();
    HashMap<String,String> littab = new HashMap<>();
    ArrayList<Integer>pooltab = new ArrayList<>();

    practice(){
        IS.put("STOP","00");
        IS.put("ADD","01");
        IS.put("SUB","02");
        IS.put("MULT","03");
        IS.put("MOVER","04");
        IS.put("MOVEM","05");
        IS.put("COMP","06");
        IS.put("BC","07");
        IS.put("DIV","08");
        IS.put("READ","09");
        IS.put("PRINT","10");
        DL.put("DC","02");
        DL.put("DS","01");

    }


    public static void main(String[] args) {
        practice pss = new practice();
        pss.pass1();
    }

    void pass1(){
        try{

            FileReader fileReader = new FileReader("C:\\Users\\gades\\Downloads\\input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter = new FileWriter("op.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int locptr = 0,symtabptr = 1, littabptr = 1, pooltabptr = 1;



            String currentLine = bufferedReader.readLine();
            String[] line = currentLine.split(" ");
            if (line[1].equals("START")){
                locptr=Integer.parseInt(line[2]);
                bufferedWriter.write("AD\t01\tC\t"+Integer.parseInt(line[2]));
                bufferedWriter.newLine();
            }

            while((currentLine=bufferedReader.readLine())!=null){
                boolean islocptrset = false;
                line = currentLine.split("[ ,]");

                if (!line[0].isEmpty()) {
                    symtab.put(line[0], String.valueOf(locptr));
                    if (symtab.get(line[0]) == null) {
//                        symbolTablePointerMapper.put(line[0], symtabptr);
                        symtabptr++;
                    }
                }

                if(line[1].equals("ORIGIN")){
                    int adr = Integer.parseInt(symtab.get(line[2]));

                    if (line[3]=="+")
                        adr += Integer.parseInt(line[4]);
                    else {
                        adr -= Integer.parseInt(line[4]);
                    }
                    bufferedWriter.write("AD\t03\t");
                    bufferedWriter.write(String.valueOf(adr));
                    locptr = adr;
                    islocptrset = true;
                }

                if(line[1].equals("EQU")){
                    int adr = Integer.parseInt(symtab.get(line[2]));

                    if (line[3].equals("+"))
                        adr += Integer.parseInt(line[4]);
                    else
                        adr -= Integer.parseInt(line[4]);

                    symtab.put(line[0],String.valueOf(adr));
                    bufferedWriter.write("AD\t04\t");
                    bufferedWriter.write(String.valueOf(adr));
                    islocptrset = true;
                }

                if (line[1].equals("LTORG")){
                    pooltab.add(pooltabptr);
                    for (Map.Entry<String,String>table:littab.entrySet()){
                        if (table.getValue().isEmpty()){
                            table.setValue(String.valueOf(locptr));
                            bufferedWriter.write(table.getKey()+"\t"+String.valueOf(locptr));
                            pooltabptr++;
                            locptr++;
                            islocptrset = true;
                        }

                    }
                }

                if (line[1].equals("END")){
                    pooltab.add(pooltabptr);
                    for (Map.Entry<String,String>table:littab.entrySet()){
                        if (table.getValue().isEmpty()){
                            table.setValue(String.valueOf(locptr));

                            bufferedWriter.write(table.getKey()+"\t"+locptr+"\n");
                            pooltabptr++;
                            locptr++;
                            islocptrset = true;
                        }

                    }

                    bufferedWriter.write("AD\t05\t");
                    bufferedWriter.write(String.valueOf(locptr));
                }

                for (Map.Entry<String, String>table:DL.entrySet()){
                    if (table.getKey().equals(line[1])){
                        if (table.getKey().equals("DC")){
                            bufferedWriter.write("DL\t02\tC\t" +line[2]);
                            bufferedWriter.write(String.valueOf(locptr));
                            symtab.put(line[0],String.valueOf(locptr));
                        }
                        else{
                            int adr = locptr + Integer.parseInt(line[2]);
                            symtab.put(line[0],String.valueOf(locptr));
                            bufferedWriter.write("DL\t01\tC\t"+line[2]);
                            locptr = adr;
                            islocptrset = true;
                        }

                    }
                }

                for (Map.Entry<String, String>table:IS.entrySet()){
                    if (table.getKey().equals(line[1])){
                        bufferedWriter.write("IS\t"+table.getValue()+"\t");
                        if(line.length > 2){
                            switch (line[2]){
                                case "AREG":
                                    bufferedWriter.write("1\t");
                                    break;
                                case "BREG":
                                    bufferedWriter.write("2\t");
                                    break;
                                case "CREG":
                                    bufferedWriter.write("3\t");
                                    break;
                                case "DREG":
                                    bufferedWriter.write("4\t");
                                    break;
                            }
                        }
                        if (line.length>3){
                            if (line[3].contains("=")){
                                littab.put(line[3],"");
                                bufferedWriter.write("L\t"+littabptr+"\t");
                                bufferedWriter.write(String.valueOf(locptr));
//                                literalTablePointerMapper.put(line[3], littabptr);
                                littabptr++;
                            }
                            else{
                                if (symtab.get(line[3]) == null) {
                                    symtab.put(line[3], "");
//                                    symbolTablePointerMapper.put(line[3], symtabptr);
                                    bufferedWriter.write("S\t" + symtabptr + "\t");
                                    bufferedWriter.write(String.valueOf(locptr));
                                    symtabptr++;
                                }
                                else {
//                                    bufferedWriter.write("S\t" + symbolTablePointerMapper.get(line[3]) + "\t");
                                    bufferedWriter.write(String.valueOf(locptr));
                                }
                            }
                        }

                        else if(line.length>2){
                            if (symtab.get(line[2]) == null) {
                                symtab.put(line[2], "");
//                                symbolTablePointerMapper.put(line[2], symtabptr);
                                bufferedWriter.write("S\t" + symtabptr + "\t");
                                bufferedWriter.write(String.valueOf(locptr));
                                symtabptr++;
                            }
                            else {
//                                bufferedWriter.write("S\t" + symbolTablePointerMapper.get(line[2]) + "\t");
                                bufferedWriter.write(String.valueOf(locptr));
                            }
                        }
                    }
                }
                bufferedWriter.newLine();
                if(!islocptrset)
                    locptr++;
            }
            bufferedReader.close();
            bufferedWriter.close();

            FileWriter symbolTableFile = new FileWriter("symbolTable.txt");
            BufferedWriter bufferSymbolTableWriter = new BufferedWriter(symbolTableFile);

            System.out.println("------------- Symbol Table --------------");
            for (Map.Entry<String, String> table : symtab.entrySet()) {
                bufferSymbolTableWriter.write(table.getKey() + "\t" + table.getValue() + "\n");
                System.out.println(table.getKey() + "\t" + table.getValue());
            }

            bufferSymbolTableWriter.close();

            /*
             * Write the literal table into the file
             */
            FileWriter literalTableFile = new FileWriter("literalTable.txt");
            BufferedWriter bufferLiteralTableWriter = new BufferedWriter(literalTableFile);

            System.out.println("------------- Literal Table --------------");
            for (Map.Entry<String, String> table : littab.entrySet()) {
                bufferLiteralTableWriter.write(table.getKey() + "\t" + table.getValue() + "\n");
                System.out.println(table.getKey() + "\t" + table.getValue());
            }

            bufferLiteralTableWriter.close();

            /*
             * Write the pool table into the file
             */
            FileWriter poolTableFile = new FileWriter("poolTable.txt");
            BufferedWriter bufferPoolTableWriter = new BufferedWriter(poolTableFile);

            System.out.println("------------- Pool Table --------------");
            for (Integer integer : pooltab) {
                bufferPoolTableWriter.write(integer + "\n");
                System.out.println(integer);
            }

            bufferPoolTableWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}