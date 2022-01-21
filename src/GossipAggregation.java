import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GossipAggregation extends JFrame{

    int id=1;
    String tableHeader[] = new String[]{"Node id","Node value","Node Neighbours"};
    ArrayList<Node> nodelist; //Lista de noduri a intregului sistem
    int r,c;
    DefaultTableModel defaultTableModel; //Folosit pentru a gestiona tabelul
    JTextField inputValue;
    JTextField inputNeighbours;
    JLabel nodeValue;
    JTable nodeListTable;
    JLabel nodeNeighbours;
    JButton addButton;
    private JButton deleteButton;
    private JPanel mainFrame;

    public GossipAggregation(){


        setContentPane(mainFrame);
        nodelist=new ArrayList<>();
        defaultTableModel = new DefaultTableModel(tableHeader,0);
        nodeListTable.setModel(defaultTableModel);
        setLocationRelativeTo(null);
        setTitle("Gossip aggregation");
        setSize(500,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                float value=Float.parseFloat(inputValue.getText());
                String[] neighbsTxt=inputNeighbours.getText().split("\\s+");
                int size=neighbsTxt.length;
                int[] neighbs=new int [size];
                for(int i=0;i<size;i++){
                    neighbs[i]=Integer.parseInt(neighbsTxt[i]);
                }
                ArrayList<Node> neighbourNodes=new ArrayList<>();
                for(int i=0;i<size;i++){
                    if(neighbs[i]<id){
                      for(Node n:nodelist){
                          if(n.getIdNode()==neighbs[i]){
                              neighbourNodes.add(n);
                          }
                      }
                    }
                }
                Node tempNode=new Node(id,neighbourNodes,value);
                nodelist.add(tempNode);
                for(Node n:nodelist){
                    if(!n.equals(tempNode)){
                        if(tempNode.getNeighbors().contains(n)){
                            n.addNeighbor(tempNode);
                        }
                    }
                }
                id++;
                defaultTableModel.setRowCount(0);
                refreshTable();
                inputValue.setText("");
                inputNeighbours.setText("");
                tempNode.start();
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r=nodeListTable.getSelectedRow();
                defaultTableModel.removeRow(r);
                Node deletedNode=nodelist.get(r);
                ArrayList<Node> toRemove=new ArrayList<>();
                for(Node node:nodelist){
                    for(Node n:node.getNeighbors()){
                        if(n.equals(deletedNode)){
                            toRemove.add(n);
                        }
                    }
                    node.removeNeighbors(toRemove);

                }

                nodelist.remove(r);

                defaultTableModel.setRowCount(0);
                refreshTable();
                inputValue.setText("");
                inputNeighbours.setText("");
            }
        });
    }

    public void refreshTable(){
        for(int i=0;i<nodelist.size();i++){
            Object[] objects={nodelist.get(i).getIdNode(),nodelist.get(i).getData(),
                    nodelist.get(i).getNeighborsId()};
            defaultTableModel.addRow(objects);
        }
    }
    public static void main(String[] args){


        GossipAggregation demo=new GossipAggregation();
        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.defaultTableModel.setRowCount(0);
            demo.refreshTable();
            demo.setVisible(true);
        }
        /*Node node1=new Node(1,5);
        Node node2=new Node(2,3);
        Node node3=new Node(3,4);

        node1.setNeighbors(new ArrayList<Node>(Arrays.asList(node2,node3)));
        node2.setNeighbors(new ArrayList<Node>(Arrays.asList(node1)));
        node3.setNeighbors(new ArrayList<Node>(Arrays.asList(node1)));

        node1.start();
        node2.start();
        node3.start();*/




    }
}
