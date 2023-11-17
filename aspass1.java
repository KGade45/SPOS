import java.util.*;
import java.io.*;

public class aspass1 {

    HashMap<String,String>IS = new HashMap<>();
    HashMap<String,String>DL = new HashMap<>();
    HashMap<String, Integer> symmap = new HashMap<>();

    public HashMap<String, Integer> litmap =new HashMap<>();
    HashMap<String,String>littab = new HashMap<>();
    HashMap<String,String>symtab = new HashMap<>();
    ArrayList<Integer>pooltab = new ArrayList<>();

    aspass1(){
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
        aspass1 as = new aspass1();
        as.pass1();
        as.passTwo();
    }
     void pass1() {
         try {
             FileReader fileReader = new FileReader("C:\\Users\\gades\\Desktop\\input.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader);

             FileWriter fileWriter = new FileWriter("pp.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             boolean islcset = false;

             int lc=0,symtabptr=1,littavptr=1,pooltabptr=1;

             String currentline = bufferedReader.readLine();
             String[] line = currentline.split(" ");

             if(line[1].equals("START")){
                 lc = Integer.parseInt(line[2]);
                 bufferedWriter.write("AD\t01\tC\t"+line[2]);
                 bufferedWriter.newLine();
                 if (symtab.get(line[0]) == null) {
                     symmap.put(line[0], symtabptr);
                     symtabptr++;
                 }
             }

             while((currentline=bufferedReader.readLine())!=null){
                 line = currentline.split("[ ,]");

                 if(!line[0].isEmpty()){
                     symtab.put(line[0],String.valueOf(lc));
                     symtabptr++;
                 }

                 if (line[1].equals("ORIGIN")){
                     int adr = Integer.parseInt(symtab.get(line[2]));
                     if(line[3]=="+")
                         adr += Integer.parseInt(line[4]);
                     else
                         adr -= Integer.parseInt(line[4]);
                     lc = adr;
                     bufferedWriter.write("AD\t03\tC"+String.valueOf(lc));
                     islcset = true;
                 }

                 if (line[1].equals("EQU")){
                     int adr = Integer.parseInt(symtab.get(line[2]));
                     if(line[3]=="+")
                         adr += Integer.parseInt(line[4]);
                     else
                         adr -= Integer.parseInt(line[4]);
                     bufferedWriter.write("AD\t04\t");
                     symtab.put(line[0],String.valueOf(adr));
                 }

                 if (line[1].equals("LTORG")){
                     pooltab.add(pooltabptr);
                     for (Map.Entry<String,String>table:littab.entrySet()){
                         if(table.getValue().isEmpty()){
                             table.setValue(String.valueOf(lc));
                             bufferedWriter.write(table.getKey()+"\t"+table.getValue()+"\t");
                             lc++;
                             pooltabptr++;
                             islcset=true;
                             bufferedWriter.newLine();
                         }
                     }
                 }

                 if (line[1].equals("END")){
                     pooltab.add(pooltabptr);
                     for (Map.Entry<String,String>table:littab.entrySet()){
                         if(table.getValue().isEmpty()){
                             table.setValue(String.valueOf(lc));
                             bufferedWriter.write(table.getKey()+"\t"+table.getValue()+"\t");
                             lc++;
                             pooltabptr++;
                             bufferedWriter.newLine();
                         }
                     }
                 }

                 for (Map.Entry<String,String>table:DL.entrySet()){
                     if (table.getKey().equals(line[1])){
                         if (table.getKey().equals("DC")){
                             symtab.put(line[0],String.valueOf(lc));
                             bufferedWriter.write("DL\t02\tC\t"+line[2]+"\t");
                             bufferedWriter.write(String.valueOf(lc));
                         }
                         else{
                             symtab.put(line[0],String.valueOf(lc));
                             int adr = lc + Integer.parseInt(line[2]);
                             lc = adr;
                             islcset = true;
                             bufferedWriter.write("DL\t01\tC\t"+line[2]+"\t");
                             bufferedWriter.write(String.valueOf(lc));
                         }
                     }
                 }

                 for (Map.Entry<String,String>table:IS.entrySet()){
                     if (table.getKey().equals(line[1])){
                         bufferedWriter.write("IS\t"+table.getValue()+"\t");
                         if(line.length>2){
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
                             if (line[3].contains("="))
                             {
                                 littab.put(line[3],"");
                                 bufferedWriter.write("L\t"+littavptr+"\t");
                                 bufferedWriter.write(String.valueOf(lc));
                                 litmap.put(line[3],littavptr);
                                 littavptr++;

                             }
                             else
                             {
                                 if (symtab.get(line[3])==null) {
                                     symtab.put(line[3], "");
                                     bufferedWriter.write("S\t" + symtabptr+"\t");
                                     bufferedWriter.write(String.valueOf(lc));
                                     symmap.put(line[3],symtabptr);
                                     symtabptr++;
                                 }
                                 else{
                                     bufferedWriter.write("S\t" + symmap.get(line[3]) + "\t");
                                     bufferedWriter.write(String.valueOf(lc));
                                 }
                             }
                         }
                         else if (line.length>2){
                             if (symtab.get(line[2])==null) {
                                 symtab.put(line[2], "");
                                 bufferedWriter.write("S\t" + symtabptr);
                                 bufferedWriter.write(String.valueOf(lc));
                                 symtabptr++;
                             }
                             else{
                                 bufferedWriter.write("S\t" + symmap.get(line[2]) + "\t");
                                 bufferedWriter.write(String.valueOf(lc));
                             }
                         }

                     }
                 }
                 bufferedWriter.newLine();
                 if(!islcset)
                     lc++;

             }



             bufferedReader.close();
             bufferedWriter.close();


             FileWriter f = new FileWriter("sm.txt");
             BufferedWriter b = new BufferedWriter(f);

             for (Map.Entry<String,String>table:symtab.entrySet()){
                 b.write(table.getKey()+"\t"+table.getValue());
                 b.newLine();
             }

             b.close();

         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    void passTwo() {
        try {

            FileWriter machineCodeFile = new FileWriter("Code.txt");
            BufferedWriter bufferMachineCodeWriter = new BufferedWriter(machineCodeFile);

            FileReader intermediateCodeFile = new FileReader("pp.txt");
            BufferedReader bufferIntermediateFileReader = new BufferedReader(intermediateCodeFile);

            String currentLine = bufferIntermediateFileReader.readLine();
            while ((currentLine = bufferIntermediateFileReader.readLine()) != null) {
                if (currentLine.isEmpty())
                    continue;

                String[] line = currentLine.split("\t");

                if (Objects.equals(line[0], "IS")) {

                    bufferMachineCodeWriter.write(line[1] + "\t");
                    bufferMachineCodeWriter.write(line[2] + "\t");

                    if (line[3].equals("S")) {
                        for (Map.Entry<String, String> table : symtab.entrySet()) {
                            if (table.getValue().equals(line[4])) {

                                bufferMachineCodeWriter.write(symtab.get(table.getKey())+"\t");
                                bufferMachineCodeWriter.write(line[5]);
                            }
                        }
                    } else if (line[3].equals("L")) {
                        for (Map.Entry<String, Integer> table : litmap.entrySet()) {
                            if (table.getValue().equals(Integer.parseInt(line[4]))) {
                                bufferMachineCodeWriter.write(littab.get(table.getKey()));
                            }
                        }
                    }

                    bufferMachineCodeWriter.newLine();
                }

                if (line[0].contains("=")) {
                    bufferMachineCodeWriter.write(littab.get(line[0]));
                    bufferMachineCodeWriter.newLine();
                }
            }

            bufferIntermediateFileReader.close();
            bufferMachineCodeWriter.close();

            FileReader machineCodeFileReader = new FileReader("Code.txt");
            BufferedReader bufferMachineCodeReader = new BufferedReader(machineCodeFileReader);

            System.out.println("--------- Machine Code ----------");
            while ((currentLine = bufferMachineCodeReader.readLine()) != null) {
                System.out.println(currentLine);
            }
            bufferMachineCodeReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
