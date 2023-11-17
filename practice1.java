import javax.xml.stream.events.EntityReference;
import java.util.*;
import java.io.*;

public class practice1 {
    private HashMap<String, String> IS = new HashMap<>();
    private HashMap<String, String> DL = new HashMap<>();

    HashMap<String,String> symtab = new HashMap();
    HashMap<String,String> littab = new HashMap();
    HashMap<String,Integer> symtabmap = new HashMap();
    HashMap<String,Integer> littabmap = new HashMap();
    ArrayList<Integer>pooltab = new ArrayList();

    practice1(){
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
        DL.put("DS","01");
        DL.put("DC","02");
    }

    public static void main(String[] args) {
        practice p1 = new practice();
        p1.pass1();
    }
    void pass1() {
        try {
            System.out.println("KGade");
            FileReader fileReader = new FileReader("C:\\Users\\gades\\Downloads\\input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            FileWriter fileWriter = new FileWriter("op.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            int locptr = 0;
            int symtabptr = 0;
            int littabpte = 0;
            int pooltabptr = 0;

            String currentline = bufferedReader.readLine();
            String[] line = currentline.split(" ");

            if(line[1].equals("START")){
                locptr = Integer.parseInt(line[2]);
                bufferedWriter.write("AD\t01\tC\t"+line[2] +"\n");
            }

            while((currentline = bufferedReader.readLine())!=null){
                boolean islocptrset = false;
                line = currentline.split("[ ,]");

                if (!line[0].isEmpty()){
                    symtab.put(line[0], String.valueOf(locptr));

                    if(symtab.get(line[0]) == null){
                        symtabmap.put(line[0], symtabptr);
                        symtabptr++;
                    }
                }


                if (line[1].equals("ORIGIN")){

                    int adr = Integer.parseInt(symtab.get(line[2]));

                    if(line[3].equals("+"))
                        adr += Integer.parseInt(line[4]);
                    else
                        adr -= Integer.parseInt(line[4]);

                    bufferedWriter.write("AD\t01\t");
                    bufferedWriter.write("C\t"+adr);
                    locptr = adr;
                    islocptrset = true;
                }

                if (line[1].equals("EQU")){

                    int adr = Integer.parseInt(symtab.get(line[2]));

                    if(line[3].equals("+"))
                        adr += Integer.parseInt(line[4]);
                    else
                        adr -= Integer.parseInt(line[4]);

                    bufferedWriter.write("AD\t04\t");
                    bufferedWriter.write(String.valueOf(locptr));
                    symtab.put(line[0], String.valueOf(adr));
                    islocptrset = true;
                }

                if(line[1].equals("LTORG")){
                    pooltab.add(pooltabptr);

                    for(Map.Entry<String, String>table:littab.entrySet()){
                        if(table.getValue().isEmpty()){
                            table.setValue(String.valueOf(locptr));
                            bufferedWriter.write(table.getKey()+ "\t"+ String.valueOf(locptr));
                            pooltabptr++;
                            locptr++;
                            islocptrset = true;
                        }
                    }
                }

                for(Map.Entry<String, String>table:DL.entrySet()){
                    if(table.getKey().equals(line[1])){
                        if (table.getKey().equals("DC")){
                            symtab.put(line[0],String.valueOf(locptr));
                            bufferedWriter.write("DL\t02\tC");
                            bufferedWriter.write(String.valueOf(locptr));
                        }
                        else {
                            // the statement is DS
                            int address = locptr + Integer.parseInt(line[2]);
                            symtab.put(line[0], String.valueOf(locptr));

                            bufferedWriter.write("DL\t01\tC\t");
                            bufferedWriter.write(String.valueOf(locptr));

                            locptr = address;
                            islocptrset = true;
                        }
                    }
                }

                if (line[1].equals("END")){
                    pooltab.add(pooltabptr);

                    for (Map.Entry<String, String>table:littab.entrySet()){
                        if (table.getValue().isEmpty()){
                            table.setValue(String.valueOf(locptr));
                            bufferedWriter.write(table.getKey() + "\t" + locptr);
                            locptr++;
                            islocptrset = true;
                        }
                    }
                }

                for(Map.Entry<String, String>table:IS.entrySet()){
                    if(table.getKey().equals(line[1])){
                        bufferedWriter.write("IS\t"+table.getValue()+"\t");

                        if (line.length>2)
                            switch (line[2]){
                                case "AREG" : bufferedWriter.write("1\t");
                                case "BREG" : bufferedWriter.write("2\t");
                                case "CREG" : bufferedWriter.write("3\t");
                                case "DREG" : bufferedWriter.write("4\t");
                            }
                        if (line.length>3){
                            if (line[3].contains("=")){
                                littab.put(line[3],"");
                                bufferedWriter.write("L\t"+littabpte);
                                bufferedWriter.write(String.valueOf(locptr));
                                littabmap.put(line[3],locptr);
                                littabpte++;
                            }
                            else {
                                if (symtab.get(line[3]) == null) {
                                    symtab.put(line[3], "");
                                    symtabmap.put(line[3], symtabptr);
                                    bufferedWriter.write("S\t" + symtabptr + "\t");
                                    bufferedWriter.write(String.valueOf(locptr));
                                    symtabptr++;
                                } else {
                                    bufferedWriter.write("S\t" + symtabmap.get(line[3]) + "\t");
                                    bufferedWriter.write(String.valueOf(locptr));
                                }
                            }
                        }
                        else{
                            if (symtab.get(line[2]) == null) {
                                symtab.put(line[2], "");
                                symtabmap.put(line[2], symtabptr);
                                bufferedWriter.write("S\t" + symtabptr + "\t");
                                bufferedWriter.write(String.valueOf(locptr));
                                symtabptr++;
                            } else {
                                bufferedWriter.write("S\t" + symtabmap.get(line[2]) + "\t");
                                bufferedWriter.write(String.valueOf(locptr));
                            }
                        }

                    }
                }





                bufferedWriter.newLine();
                if(!islocptrset)
                    locptr++;
            }
            bufferedWriter.close();
            bufferedReader.close();

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

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}