package com.example.texteditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import static com.sun.java.accessibility.util.SwingEventMonitor.addChangeListener;

public class TextEditor extends JFrame implements ActionListener {
     JTextArea textArea;
     JScrollPane scrollPane;
     JSpinner fontSizeSpinner;
     JLabel fontLabel;
     JLabel textLabel;
     JButton fontColorButton;
     JComboBox fontBox;
     JMenuBar menuBar;
     JMenu menu;
     JMenu fileMenu;
     JMenu editMenu;
     JMenuItem openItem;
     JMenuItem saveItem;
    JMenuItem printItem;
     JMenuItem exitItem;
    JMenuItem cutItem;
    JMenuItem pasteItem;
    JMenuItem copyItem;
    JMenuItem newItem;
    TextEditor() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TextEditor");
        this.setSize(700, 700);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        textArea = new JTextArea();
        //textArea.setPreferredSize(new Dimension(500,500));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));


        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 600));

        scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fontLabel=new JLabel("Font: ");
        textLabel=new JLabel("Text Editor",SwingConstants.RIGHT);
        textLabel.setSize(200,200);

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));

        fontSizeSpinner.setValue(20);

        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),
                        Font.PLAIN,(int)fontSizeSpinner.getValue()));


            }
        });
        fontColorButton=new JButton("Color");
        fontColorButton.addActionListener(this);

        String[]fonts =GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox=new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");

        //---------------MenuBar Area
          menuBar=new JMenuBar();
          menuBar.setBackground(Color.pink);
          fileMenu=new JMenu("File");
          newItem=new JMenuItem("New");
          openItem=new JMenuItem("Open");
          saveItem=new JMenuItem("Save");
          exitItem=new JMenuItem("Exit");
          printItem=new JMenuItem("Print");

        fileMenu.addActionListener(this);
        newItem.addActionListener(this);
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        printItem.addActionListener(this);

        editMenu=new JMenu("Edit");
        cutItem=new JMenuItem("Cut CTRL+Z");
        copyItem=new JMenuItem("Copy CTRL+C");
        pasteItem=new JMenuItem("Paste CTRL+V");

        editMenu.addActionListener(this);
        cutItem.addActionListener(this);
        copyItem.addActionListener(this);
        cutItem.addActionListener(this);



          fileMenu.add(newItem);
          fileMenu.add(openItem);
          fileMenu.add(exitItem);
          fileMenu.add(saveItem);
          fileMenu.add(printItem);
          menuBar.add(fileMenu);
          menuBar.add(editMenu);
          editMenu.add(cutItem);
          editMenu.add(copyItem);
          editMenu.add(pasteItem);



         this.setJMenuBar(menuBar);
         this.add(fontLabel);
         this.add(fontSizeSpinner);
         this.add(fontColorButton);
         this.add(fontBox);
         this.add(scrollPane);
        this.add(textLabel);
         this.setVisible(true);
    }
         @Override
         public void actionPerformed(ActionEvent e) {
        //choose font color
              if(e.getSource()==fontColorButton){
                  JColorChooser colorChooser=new JColorChooser();
                  Color color=colorChooser.showDialog(null,"Choose a color",Color.black);

                  textArea.setForeground(color);
              }
              //FontBox Choose font and size
              if(e.getSource()==fontBox){
                  textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
              }
              if(e.getSource()==newItem){
                  textArea.setText(" ");
              }
              if(e.getSource()==openItem){
                  JFileChooser fileChooser=new JFileChooser();
                  fileChooser.setCurrentDirectory(new File("."));
                  int response=fileChooser.showOpenDialog(null);
                  if(response==JFileChooser.APPROVE_OPTION){
                      File file=new File(fileChooser.getSelectedFile().getAbsoluteFile().toURI());
                      Scanner fileIn=null;
                     try {
                         fileIn=new Scanner(file);
                         if(file.isFile()){
                             while (fileIn.hasNextLine()){
                                 String line=fileIn.nextLine()+"\n";
                                 textArea.append(line);
                             }
                         }
                     } catch (FileNotFoundException ex) {
                         throw new RuntimeException(ex);
                     }
                     finally {
                         fileIn.close();
                     }

                  }
              }
              if(e.getSource()==exitItem){
                  System.exit(0);
              }
              if(e.getSource()==saveItem){
                  JFileChooser fileChooser=new JFileChooser();
                  fileChooser.setCurrentDirectory(new File("."));

                  int response=fileChooser.showSaveDialog(null);
                  if(response==JFileChooser.APPROVE_OPTION){
                      File file;
                      PrintWriter fileOut=null;
                      file=new File(fileChooser.getSelectedFile().getAbsoluteFile().toURI());
                      try {
                          fileOut=new PrintWriter(file);
                          fileOut.println(textArea.getText());
                      } catch (FileNotFoundException ex) {
                          throw new RuntimeException(ex);
                      }
                      finally {
                          fileOut.close();
                      }

                  }

              }
             if(e.getSource()==printItem){
                 try {
                     textArea.print();
                 } catch (PrinterException ex) {
                     throw new RuntimeException(ex);
                 }
             }
              if(e.getSource()==cutItem){
                  textArea.cut();
              }
             if(e.getSource()==copyItem){
                 textArea.copy();
             }
             if(e.getSource()==pasteItem){
                 textArea.paste();
             }

           }
}
