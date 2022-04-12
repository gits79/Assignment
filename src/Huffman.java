import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    public int key;
    public String c;
    public String pre = "";
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

        return key-o.key;
    }
}

class Prefix{
    String c;
    String prefix;
    int key;
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
        System.out.println("end");
    }
}




public class Huffman {

    static void getPrefix(Node node,String prefix,ArrayList<Prefix> arrayList){
        if(node != null) {
            prefix = prefix + node.pre;
            if(node.left != null) getPrefix(node.left,prefix,arrayList);
            if(node.right != null) getPrefix(node.right,prefix,arrayList);
            if(node.left == null && node.right == null){
                Prefix preClass = new Prefix();
                preClass.prefix = prefix;
                preClass.c = node.c;
                preClass.key = node.key;
                arrayList.add(preClass);
                System.out.println("문자: "+node.c +",  빈도수: "+node.key+", 프리픽스 : "+prefix);
            }

        }

    }

    static void setPrefix(Node node){
        if(node== null) {
            return;
        }

        if(node.left != null)
            node.left.pre = node.left.pre + "0";
        if(node.right!=null)
            node.right.pre = node.right.pre + "1";

        if(node.left != null) {
            setPrefix(node.left);
        }
        if(node.right != null) {
            setPrefix(node.right);
        }

    }

    static void huffmanNode(PriorityQueue<Node> priorityQueue){
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
        ArrayList<Prefix> arrayList = new ArrayList<>();


        // 파일 읽어오기
        File file = new File();
        String text = file.getString("test.txt");
        String originalData = text;
        System.out.println(text);


        // 빈도수 계산
        Check check = new Check();
        String[] strArr = new String[10000];
        int[] countArr = new int[10000];
        check.CheckCount(strArr,countArr,text);
//        System.out.printf("각 문자별 빈도수: ");
//        check.print(strArr,countArr);
//        System.out.println();




        // 우선순위 큐에 넣기
        for(int i=0;i<strArr.length;i++){
            if(strArr[i]!=null){
                Node node = new Node();
                node.key = countArr[i];
                node.c = strArr[i];
                node.left = node.right = null;
                priorityQueue.add(node);
            }
        }

        //허프만 트리 만들기 및 프리픽스 부여
        Node node = new Node();
        huffmanNode(priorityQueue);
        node = priorityQueue.poll();
        setPrefix(node);
        System.out.println();
        String prefix = "";
        getPrefix(node,prefix,arrayList);



        // 프리픽스 가져오기 및 인코딩
        String encoding = text;
        for(int j=0;j<encoding.length();j++) {
            String temp = encoding.substring(j,j+1);

            if(encoding.lastIndexOf("0") == encoding.length() && encoding.lastIndexOf("1") == encoding.length())
                break;
            for (int i = 0; i < arrayList.size(); i++) {
                Prefix pre = new Prefix();
                pre = arrayList.get(i);
                if(temp.equals(pre.c))
                    encoding = encoding.replaceFirst(temp,pre.prefix);


            }
        }



        // 디코딩
        String decoding = encoding;
        for(int j=0;j<decoding.length();j++) {
            String temp;
            for (int i = 0; i < arrayList.size(); i++) {

                Prefix pre = new Prefix();
                pre = arrayList.get(i);
                String decode = pre.prefix;
                int len = decode.length();


                // 맨 마지막에 프리픽스가 남은 인코딩 숫자보다 큰경우 제거
                if(j+len>decoding.length())
                    temp = decoding.substring(j,decoding.length());
                else
                    temp = decoding.substring(j,j+len );


                if(decode.equals(temp))
                    decoding = decoding.replaceFirst(temp,pre.c);
                if(!decoding.contains("0") && !decoding.contains("1"))
                    break;

            }
        }




        System.out.println();
        System.out.println("원래 데이터: "+text);
        System.out.println("인코딩 후 데이터: "+encoding);
        System.out.println("디코딩 후 데이터: "+decoding);



        // 압축전 파일의 크기
        int textSize = text.length()*8;
        System.out.println("압축전 파일의 크기: "+textSize);


        // 압축후 사이즈 체크
        int sum = 0;
        for(int i =0;i<arrayList.size();i++){
            Prefix tempPre = arrayList.get(i);
            int size = tempPre.key*tempPre.prefix.length();
            sum += size;
        }
        System.out.println("압축후 파일의 크기: "+sum);



    }

}