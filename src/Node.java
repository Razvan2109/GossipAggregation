import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Node extends Thread{

    private int id;
    private ArrayList<Node> neighbors;
    private float data;
    public static int delta;
    public boolean isActive=false;


    public Node(int id, ArrayList<Node> neighbors, float data) {
        this.id = id;
        this.neighbors = neighbors;
        this.data = data;

    }

    public Node(int id, float data){
        this.id=id;
        this.neighbors=new ArrayList<>();
        this.data = data;

    }

    public int getIdNode() {
        return id;
    }

    public void setIdNode(int id) {
        this.id = id;
    }

    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    private void updateData(float mod) {

       // System.out.println("FACEM UPDATE LA " + id + "\n \n");
       // System.out.println("Pentru" + id + "Data este: " + data);
       // System.out.println("Modificatorul este: " + mod);

        this.data = (this.data + mod) / 2;
       // System.out.println("GATA UPDATE \n \n");

    }

    public void OnPull(Node sender){
        updateData(sender.getData());
    }

    public void OnPush(Node receiver){
        float receiverData=receiver.getData(); //3 si 4
        receiver.OnPull(this);  //3 si 3.5
        updateData(receiverData);  //3.25 si 3.25 !!

    }

    public void addNeighbor(Node n){
        neighbors.add(n);
    }
    public void removeNeighbors(ArrayList<Node> n) {
        neighbors.removeAll(n);
    }

    public List<Integer> getNeighborsId(){
        List<Integer> nIds=new ArrayList<Integer>();
        for(Node node:neighbors){
            nIds.add(node.getIdNode());
        }
        return nIds;

    }
    public Node SelectPeer(){
        Random rand=new Random();
        int random= rand.nextInt(neighbors.size());
        return neighbors.get(random);
    }

    public void run() {

        Random rand=new Random();
        isActive=rand.nextBoolean();
        delta=1000;
        while(true) {

            try {
                sleep(delta);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if(!neighbors.isEmpty()) {
                Node peer = SelectPeer();
                System.out.println("Pentru nodul" + id + "s-a selectat vecinul:" + peer.getIdNode());

                peer.OnPush(this);
                System.out.println("Pentru nodul" + id + "Data este:" + data);
            }



        }
    }
}
