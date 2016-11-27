import java.io.*;
import java.util.*;

public class HashtagCounter {

    public static void main(String[] args) {

        FileInputStream in =null;
        BufferedWriter bw =null;
        BufferedReader br = null;
        FileWriter fw = null;

        String line = new String();
        Hashtable<String, FibonacciHeap.Node> tags = new Hashtable();
        FibonacciHeap maxHeap = new FibonacciHeap();
        try{
            fw=new FileWriter(args[1]);
            in = new FileInputStream(args[0]);
            bw= new BufferedWriter(fw);
            br = new BufferedReader(new InputStreamReader(in));
            while ((line = br.readLine()) != null) {
                if(isNumeric(line)) //If input file stream hits a number
                {
                    int count = Integer.parseInt(line);
                    ArrayList<FibonacciHeap.Node> nodeList = new ArrayList<>();
                    System.out.println("extracting max : " + count);
                    //maxHeap.display();
                    for(int i=0; i< count; i++ )
                    {
                        //System.out.println(maxHeap.max.frequency + "  : "+maxHeap.max.degree );
                        FibonacciHeap.Node n = maxHeap.extractMax();
                        //System.out.println(maxHeap.getNumberOfNodes());
                        String s  = n.getTag();
                        nodeList.add(n);
                        if(i==count-1)
                            bw.write(s+""+n.getFrequency());
                        else
                            bw.write(s+""+n.getFrequency()+",");
                        //bw.write(',');

                    // System.out.println("VAlue : "+ nodeList.get(i).getFrequency() + "  Key:  "+ s + " Degree :  "+ nodeList.get(i).getDegree());
                    }
                    bw.write('\n');
                    for(int i=0; i<count; i++ )
                    {
                        maxHeap.insert(nodeList.get(i));
                        //System.out.println("VAlue : "+ nodeList.get(i).getFrequency() + "  Key:  "+  " Degree :  "+ nodeList.get(i).getDegree());
                        //System.out.println("no. of nodes : " +maxHeap.getNumberOfNodes());
                    }
                    continue;
                }
                if(line.equals("STOP"))//if input file reaches EOF
                {
                    System.out.println("***************** End Of File *****************");
                    return;
                }
                String [] input = line.split(" ");
                String tag = input[0].substring(1);
                int frequency = Integer.parseInt(input[1]);
                //System.out.println(tag +" : " +input[1]);
                if(tags.containsKey(tag))
                {
                    FibonacciHeap.Node n = tags.get(tag);
                    maxHeap.increaseKey(n,frequency+n.getFrequency());
                    //System.out.println("increaseKey VAlue : "+ n.getFrequency() + "  Key:  "+ tag + " Degree :  "+ n.getDegree());
                }
                else{
                    FibonacciHeap.Node n1 =  maxHeap.insert(new FibonacciHeap.Node(frequency,tag));
                    tags.put(tag,n1 );
                    //System.out.println("insert VAlue : "+ n1.getFrequency() + "  Key:  "+ tag+ " Degree :  "+ n1.getDegree());
                }

            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            try {
                in.close();
                br.close();
                bw.close();

            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }


    }
    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }
}
