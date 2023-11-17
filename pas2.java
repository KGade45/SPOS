import java.util.*;
import java.io.*;
public class pas2 {
    HashMap<String,String>littab = new HashMap<>();
    HashMap<String,Integer>littabm = new HashMap<>();
    HashMap<String,String>symtab = new HashMap<>();
    HashMap<String,Integer>symtabm = new HashMap<>();

    pas2(){
        symtab.put("A","208");
        symtab.put("B","204");
        symtab.put("L1","200");
        symtab.put("L2","202");
        littab.put("='3'","214");
        littab.put("=‘5’","215");

        symtabm.put("A",208);
        symtabm.put("B",204);
        symtabm.put("L1",200);
        symtabm.put("L1",202);
        littabm.put("'=3'",214);
        littabm.put("'=5'",215);
    }
    void pass2(){
        try{
            FileReader fl = new FileReader("pp.txt");
            BufferedReader br = new BufferedReader(fl);

            FileWriter fw = new FileWriter("Code.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            String cl = br.readLine();
            while((cl = br.readLine())!=null){
                if(cl.isEmpty())
                    continue;

                String[] line = cl.split("\t");

                if(Objects.equals(line[0],"IS")){
                    bw.write(line[1]+"\t");
                    bw.write(line[2]+"\t");

                    if (line[3].equals("S")){
                        for (Map.Entry<String,Integer>table:symtabm.entrySet()){
                            if (table.getValue().equals(Integer.parseInt(line[4]))){
                                bw.write(symtab.get(table.getKey()));
                            }
                        }
                    }
                    else if (line[3].equals("L")) {
                        for (Map.Entry<String, Integer> table : littabm.entrySet()) {
                            if (table.getValue().equals(Integer.parseInt(line[4]))) {
                                bw.write(littab.get(table.getKey()));
                            }
                        }
                    }
                    bw.newLine();
                }

                if (line[0].contains("=")) {
                    bw.write(littab.get(line[0]));
                    bw.newLine();
                }
            }
            br.close();
            bw.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        pas2 k = new pas2() ;
        k.pass2();
    }

}