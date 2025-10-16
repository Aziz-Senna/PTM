package PTM;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

    private static JFrame window;
    private static DefaultListModel<String> modele;
    private static JScrollPane jScrollPane1;
    private static JList<String> jList1;
    private static RoundBtn cancel_button;
    private static JButton save_button, add_button, replace_button, remove_button;
    private static JTextField add_text;
    private static java.util.List<String> container;
    private static int x = 0, y = 0, height = 0, width = 0;
    
    static Clicklistener click;
    
    public Main(){
        click = new Clicklistener();
        container = new ArrayList<>();
    }
    
    public static void main(String[] args) {
        Main mainToDo = new Main();        
        mainToDo.createWindow();
        mainToDo.load_data();
    }
    
    public static void createWindow(){
        window = new JFrame();
        window.setSize(500,700);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        
        JLabel title = new JLabel("Personnal Task Manager");
        Font font = new Font("Segoe UI Historic Normal", Font.BOLD, 20);
        title.setFont(font);
        title.setBounds(window.getX()/4, 30, 235,50);
        window.add(title);
        
        JLabel list_task = new JLabel("Task's list :");
        list_task.setFont(font);
        list_task.setBounds(window.getX()/8, 120, 110, 40);
        window.add(list_task);
        
        jScrollPane1 = new javax.swing.JScrollPane();
        modele = new DefaultListModel<>();
        
        jList1 = new javax.swing.JList<>(modele);
        jList1.setFont(new java.awt.Font("Segoe UI Historic Normal", 0, 17));        
        jList1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jList1);        
        jScrollPane1.setBounds(list_task.getX() + 20, list_task.getY() + 50, 275, 120);
        window.add(jScrollPane1);
        
        x = window.getX()/2+5;    y = 125;    height = 105; width = 35;
        cancel_button = new RoundBtn("Cancel", 20, click, font, x, y, height, width);
        cancel_button.setBackground(Color.red);
        window.add(cancel_button);
        
        x = window.getX()/8 + 115;  y = 125;    height = 90;    width = 35;
        save_button = new RoundBtn("Save", 20, click, font, x, y, height, width);
        save_button.setBackground(Color.CYAN);
        window.add(save_button);
        
        JLabel add_task = new JLabel("Add task : ");
        add_task.setFont(font);
        add_task.setBounds(window.getX()/8, 325, 110, 40);
        window.add(add_task);
        
        x = save_button.getX();  y = add_task.getY()+5;    height = 85;    width = 35;
        add_button = new RoundBtn("Add", 20, click, font, x, y, height, width);
        window.add(add_button);
        
        add_text = new JTextField();
        add_text.setFont(jList1.getFont());
        add_text.setBounds(jScrollPane1.getX(), add_task.getY()+50, 253, 35);
        window.add(add_text);
        
        JLabel any_task = new JLabel("Any task to : ");
        any_task.setFont(font);
        any_task.setBounds(add_task.getX(), 450, 130, 30);
        window.add(any_task);
        
        x = any_task.getX() + 35;  y = 525;    height = 140;    width = 50;
        replace_button = new RoundBtn("Replace ?", 20, click, font, x, y, height, width);
        replace_button.setBackground(Color.PINK);
        window.add(replace_button);
        
        x = replace_button.getX() + 150;  y = 525;    height = 140;    width = 50;
        remove_button = new RoundBtn("Remove ?", 20, click, font, x, y, height, width);
        window.add(remove_button);
        
        window.setVisible(true);
    }
    
    
    public static void load_data(){
        try{
            FileInputStream fis = new FileInputStream("data.dat");
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);            
            DataStorage dStorage = (DataStorage)ois.readObject();
            container = dStorage.listTasks;
            for(int i = 0 ; i < container.size() ; i++){
                modele.add(i, container.get(i));
            }
            ois.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public static class Clicklistener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == add_button)
            {
                if(add_text.getText() != ""){
                    modele.addElement(add_text.getText());
                    container.add(add_text.getText());
                    add_text.setText(null);
                }
                else{
                    JOptionPane.showMessageDialog(null, "YOU MUST TO PUT SOMETHING !", "ERROR", 0);
                }
            }
            else if(e.getSource() == remove_button){
                int index = jList1.getSelectedIndex();
                if(index != -1) {
                    modele.remove(index);
                    container.remove(index);
                }
                else{
                    JOptionPane.showMessageDialog(null,"YOU MUST TO SELECT A ROW IN YOUR LIST FIRST !", "ERROR", 0);
                }
            }
            else if(e.getSource() == replace_button){
                int index = jList1.getSelectedIndex();
                if (index != -1){
                String newValue = JOptionPane.showInputDialog(null, "Oh Okay, you want to replace it by something else ?\n"
                        + "No problem, enter your new value please :", "Enter your new value", 3);
                modele.set(index, newValue);
                container.set(index, newValue);
                }
                else
                    JOptionPane.showMessageDialog(null,"YOU MUST TO SELECT A ROW IN YOUR LIST FIRST !", "ERROR", 0);
            }
            else if(e.getSource() == save_button){
                try{
                    FileOutputStream fos = new FileOutputStream("data.dat");
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    DataStorage dStorage = new DataStorage();
                    dStorage.listTasks = container;                    
                    oos.writeObject(dStorage);
                    oos.close();
                }
                catch(IOException ex){
                    ex.printStackTrace();
                }
            }
            else if(e.getSource() == cancel_button){
                modele.removeAllElements();
                load_data();
            }
        }
    }    
}
