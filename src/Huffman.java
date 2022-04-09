

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    public int key;
    public String c;
    Node left;
    Node right;

    Node(){

    }

    public Node(int key,String c){
        this.key = key;
        this.c = c;
        left = right = null;
    }


    @Override
    public int compareTo(Node o) {
        return 0;
    }
}

class File{
    public String getString(String title){
        String str = "";
        try(FileInputStream fis = new FileInputStream(title)){
            int i;
            while((i= fis.read())!=-1){
                str = str + (char)i;
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        str = str.replaceAll(" ","");
        return str;
    }
}

class Check{
    public void CheckCount(String[] arr,int[] count,String str){
        for(int i=0;i<str.length();i++){
            String c = str.substring(i,i+1);
            int isDuplicate = duplicate(arr,c);
            if(isDuplicate==-1){
                arr[i] = c;
                count[i]++;
            } else if(isDuplicate!=-1){
                count[isDuplicate]++;
            }
        }
    }

    public int duplicate(String[] arr,String c){
        for(int i=0;i< arr.length;i++){
            if ( arr[i]!= null) {
                if (arr[i].equals(c))
                    return i;
            }
        }
        return -1;
    }


    public void print(String[] arr,int[] count){
        for(int i=0;i< arr.length;i++){
            if(arr[i]!=null)
                System.out.print(arr[i]+": "+count[i]+ ", ");
        }
    }
}




public class Huffman {





    static void print(Node node){
        if(node != null) {
            if(node.left != null) print(node.left);
            if(node.left == null && node.right == null)
                System.out.println(node.key + " "+ node.c);
            if(node.right != null) print(node.right);
        }
    }

    static void HuffmanNode(PriorityQueue<Node> priorityQueue){
        while (true) {
            if(priorityQueue.size()==1)
                return;
            Node node = new Node();
            node.left = priorityQueue.poll();
            node.right = priorityQueue.poll();
            node.key = node.left.key + node.right.key;

            priorityQueue.add(node);

        }
    }


    public static void main(String[] args) {
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
//        priorityQueue.add(new Node(7,'a'));








        File file = new File();
        String text = file.getString("test.txt");
        System.out.println(text);

        Check check = new Check();
        String[] strArr = new String[100];
        int[] countArr = new int[100];
        check.CheckCount(strArr,countArr,text);
        check.print(strArr,countArr);




        for(int i=0;i<strArr.length;i++){
            if(strArr[i]!=null){
                Node node = new Node();
                node.key = countArr[i];
                node.c = strArr[i];
                node.left = node.right = null;
                priorityQueue.add(node);
            }
        }

        Node node = new Node();
        HuffmanNode(priorityQueue);
        node = priorityQueue.poll();
        print(node);












    }

}
