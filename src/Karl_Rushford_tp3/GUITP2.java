package Karl_Rushford_tp3;


import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/************************
cours: IFT1176
TP3
Karl Rushford



 **************************/


public class GUITP2  extends JFrame implements ActionListener, KeyListener 
{
    
    private JMenuBar mb;
    
    private JTextField numeroT;
    private JTextField nomT;
    private JTextField paysT;
    
    private JTextField codeA;
    private JTextField nomA;
    
    private JTextField codeL;
    private JTextField nomL;
    
    private JTextField titreAddL;
    private JTextField codeLivreAddL;
    private JTextField codeAuteurAddL;
    private JTextField categorieAddL;
    private JTextField nbPagesAddL;
    private JTextField prixAddL;
    
    private JTextField oeuvresGetCode;
    private JTextField oeuvresGetNom;
    
    private Bdd bdDonnees = new Bdd();
    private Map<Auteur,HashSet<Livre>> m1;
    
    private JList list;
    
    private JFrame j;
    
    private JPanel principal;
    private JPanel subAddAuteur;
    private JPanel subAddLivre;
    private JPanel subGetAuteur;
    private JPanel subGetLivre;
    private JPanel subGetOeuvres;
    private JPanel subRapports;
    
    private JButton remplireA ;
    private JButton remplireL ;
    
    private JButton addA ;
    private JButton addL ;
    
    private JButton getAc ;
    private JButton getAn ;
    private JButton getLc ;
    private JButton getLn ;
    private JButton getOc ;
    private JButton getOn ;
    
    private JButton rapportA ;
    private JButton rapportL ;
    private JButton afficherListe ;

    public GUITP2 (int l,int h, String titre) throws IOException 
    {
        
        Container c = getContentPane();
        setSize(l,h);
        setTitle(titre);

        
        
        setLayout(new GridLayout());
        
        
        //on creer la fenetre principal avec des panneau
        principal = new JPanel();
        subAddAuteur = new JPanel();
        subAddLivre = new JPanel();
        subGetAuteur= new JPanel();
        subGetLivre = new JPanel();
        subGetOeuvres = new JPanel();
        subRapports = new JPanel();
        
        mb = new JMenuBar();
        JMenuItem mi;
        JMenu mAide = new JMenu("Aide");
        JMenuItem miC = new JMenuItem("A Propos");
        JMenuItem miA = new JMenuItem("Aide Online");
        mb.add(mAide);
        mAide.add(miC);
        mAide.add(miA);
        setJMenuBar(mb);


        
        //principal.setSize(50, 50);
        principal.setBorder(BorderFactory.createLineBorder(Color.black));
        principal.setBorder(BorderFactory.createTitledBorder("Remplir BD"));
        
        //subAddAuteur.setSize(100, 50);
        subAddAuteur.setBorder(BorderFactory.createLineBorder(Color.black));
        subAddAuteur.setBorder(BorderFactory.createTitledBorder("Ajout de Auteur"));
        
        subAddLivre.setBorder(BorderFactory.createLineBorder(Color.black));
        subAddLivre.setBorder(BorderFactory.createTitledBorder("Ajout de Livre"));
        
        subGetAuteur.setBorder(BorderFactory.createLineBorder(Color.black));
        subGetAuteur.setBorder(BorderFactory.createTitledBorder("Find Auteur"));
        
        subGetLivre.setBorder(BorderFactory.createLineBorder(Color.black));
        subGetLivre.setBorder(BorderFactory.createTitledBorder("Find Livre"));
        
        subGetOeuvres.setBorder(BorderFactory.createLineBorder(Color.black));
        subGetOeuvres.setBorder(BorderFactory.createTitledBorder("Find Oeuvres"));
        
        subRapports.setBorder(BorderFactory.createLineBorder(Color.black));
        subRapports.setBorder(BorderFactory.createTitledBorder("Rapports"));

        //on creer les bouttons
        remplireA = new JButton("Remplir BD Auteur");
        remplireL = new JButton("Remplir BD Livre");
        
        addA = new JButton("Ajouter Auteur");
        addL = new JButton("Ajouter Livre");
        
        getAc = new JButton("Chercher Auteur Code");
        getAn = new JButton("Chercher Auteur Nom");
        getLc = new JButton("Chercher Livre Code");
        getLn = new JButton("Chercher Livre Nom");
        getOc = new JButton("Chercher Oeuvres Code");
        getOn = new JButton("Chercher Oeuvres Nom");
        
        rapportA = new JButton("Rapport Auteur");
        rapportL = new JButton("Rapport Livre");
        afficherListe = new JButton("Afficher_liste");

        
        JLabel numeroL = new JLabel("numero");
        numeroT = new JTextField("",10);
        JLabel nomLAdd = new JLabel("nom");
        nomT = new JTextField("",10);
        JLabel paysLAdd = new JLabel("pays");
        paysT = new JTextField("",10); 

        JLabel codeLA = new JLabel("code Auteur");
        codeA = new JTextField("",10);
        JLabel nomLA = new JLabel("nom Auteur");  
        nomA =new JTextField("",10);
        
        JLabel codeLl = new JLabel("code Livre");
        codeL = new JTextField("",10);
        JLabel nomLl = new JLabel("nom Livre");  
        nomL = new JTextField("",10);
        
        JLabel titreALl = new JLabel("Titre");
        JLabel codeALl = new JLabel("code Auteur");
        JLabel codeAlLl = new JLabel("code Livre");
        JLabel categorieALl = new JLabel("categorie");
        JLabel nbPagesALl = new JLabel("Nb Pages");
        JLabel prixALl = new JLabel("prix");
        
        titreAddL= new JTextField("",10);
        codeLivreAddL= new JTextField("",10);
        codeAuteurAddL= new JTextField("",10);
        categorieAddL= new JTextField("",10);
        nbPagesAddL= new JTextField("",10);
        prixAddL= new JTextField("",10);
        
        JLabel codeLAo = new JLabel("code Auteur");
        oeuvresGetCode = new JTextField("",10);
        JLabel nomLAo = new JLabel("nom Auteur");  
        oeuvresGetNom =new JTextField("",10);
        
        
        JButton quit= new JButton("test");
        quit.addActionListener(this);
       // m1=bdDonnees.getMap();
        //list=new JList(m1.keySet().toArray());
        
        //on ajoute les bouton
        principal.add(remplireA);
        principal.add(remplireL);
        principal.add(afficherListe);
        subAddAuteur.add(addA);
        subAddLivre.add(addL);
        subGetAuteur.add(getAc);
        subGetAuteur.add(codeA);
        subGetAuteur.add(getAn);
        subGetAuteur.add(nomA);
        subGetLivre.add(getLc);
        subGetLivre.add(codeL);
        subGetLivre.add(getLn);
        subGetLivre.add(nomL);
        subGetOeuvres.add(getOc);
        subGetOeuvres.add(getOn);
        subRapports.add(rapportA);
        subRapports.add(rapportL);

        //on ajoute les Label et TextField
        subAddAuteur.add(numeroL);
        subAddAuteur.add(numeroT);
        subAddAuteur.add(nomLAdd);
        subAddAuteur.add(nomT);
        subAddAuteur.add(paysLAdd);
        subAddAuteur.add(paysT);
        
        subAddLivre.add(titreALl);
        subAddLivre.add(titreAddL);
        subAddLivre.add(codeALl);
        subAddLivre.add(codeLivreAddL);
        subAddLivre.add(codeAlLl);
        subAddLivre.add(codeAuteurAddL);
        subAddLivre.add(categorieALl);
        subAddLivre.add(categorieAddL);
        subAddLivre.add(nbPagesALl);
        subAddLivre.add(nbPagesAddL);
        subAddLivre.add(prixALl);
        subAddLivre.add(prixAddL);
        
        
        
        subGetAuteur.add(codeLA);
        subGetAuteur.add(codeA);
        subGetAuteur.add(nomLA);
        subGetAuteur.add(nomA); 
        
        subGetLivre.add(codeLl);
        subGetLivre.add(codeL);
        subGetLivre.add(nomLl);
        subGetLivre.add(nomL);
   
        subGetOeuvres.add(codeLAo);
        subGetOeuvres.add(oeuvresGetCode);
        subGetOeuvres.add(nomLAo);
        subGetOeuvres.add(oeuvresGetNom);
        //subList.add(list);
 
        
        
        remplireA.addActionListener(this);
        //remplireA.setMnemonic(KeyEvent.VK_F1);
        remplireL.addActionListener(this);
        afficherListe.addActionListener(this);
        addA.addActionListener(this);
        addL.addActionListener(this);
        getAc.addActionListener(this);
        getAn.addActionListener(this);
        getLc.addActionListener(this);
        getLn.addActionListener(this);
        getOc.addActionListener(this);
        getOn.addActionListener(this);
        rapportA.addActionListener(this);
        rapportL.addActionListener(this);
        
        mAide.addActionListener(this);
        miC.addActionListener(this);
        miA.addActionListener(this);
        
        KeyStroke k = KeyStroke.getKeyStroke(KeyEvent.VK_F1,0);
        miA.setAccelerator(k);

        remplireA.addKeyListener(this);
        remplireL.addKeyListener(this);
        
        //remplireA.setMnemonic(k);
        

        add(principal);
        add(subAddAuteur);
        add(subAddLivre);
        add(subGetAuteur);
        add(subGetLivre);
        add(subGetOeuvres);
        add(subRapports);
        //add(subList);
        setVisible(true);;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        remplireL.setEnabled(false);
        setButtonState(false);
        
 
        //j.setVisible(false);

    }
    
    

    public void setButtonState(boolean state)
    {
        if(state==true)
        {
            
            addA.setEnabled(true);
            addL.setEnabled(true); 
            
            getAc.setEnabled(true); 
            getAn.setEnabled(true); 
            getLc.setEnabled(true); 
            getLn.setEnabled(true);
            getOc.setEnabled(true); 
            getOn.setEnabled(true); 
            
            rapportA.setEnabled(true); 
            rapportL.setEnabled(true); 
            afficherListe.setEnabled(true);  
            
            numeroT.setEnabled(true);
            nomT.setEnabled(true);
            paysT.setEnabled(true);
            
            nomA.setEnabled(true);
            codeA.setEnabled(true);
            nomL.setEnabled(true);
            codeL.setEnabled(true);
            
            titreAddL.setEnabled(true);
            codeLivreAddL.setEnabled(true);
            codeAuteurAddL.setEnabled(true);
            categorieAddL.setEnabled(true);
            nbPagesAddL.setEnabled(true);
            prixAddL.setEnabled(true);
            
            oeuvresGetCode.setEnabled(true);
            oeuvresGetNom.setEnabled(true);
            
        }
        else
        {
            addA.setEnabled(false);
            addL.setEnabled(false); 
            
            getAc.setEnabled(false); 
            getAn.setEnabled(false); 
            getLc.setEnabled(false); 
            getLn.setEnabled(false);
            getOc.setEnabled(false); 
            getOn.setEnabled(false); 
            
            rapportA.setEnabled(false); 
            rapportL.setEnabled(false); 
            afficherListe.setEnabled(false);     
            
            numeroT.setEnabled(false);
            nomT.setEnabled(false);
            paysT.setEnabled(false);
            
            nomA.setEnabled(false);
            codeA.setEnabled(false);
            nomL.setEnabled(false);
            codeL.setEnabled(false);
            
            titreAddL.setEnabled(false);
            codeLivreAddL.setEnabled(false);
            codeAuteurAddL.setEnabled(false);
            categorieAddL.setEnabled(false);
            nbPagesAddL.setEnabled(false);
            prixAddL.setEnabled(false);
            
            oeuvresGetCode.setEnabled(false);
            oeuvresGetNom.setEnabled(false);
        }
    }
    public void keyPressed(KeyEvent e)
    {
        char ch = e.getKeyChar();
        //String txt=e.getComponent();
        //System.out.println(ch); 
        
        if(e.getModifiers() == InputEvent.CTRL_MASK && e.getKeyCode()==KeyEvent.VK_A)
        {
            try
            {
   
                bdDonnees.lireBddAut("Auteurs.txt");
                System.out.println("bd Auteus Lue");
                bdDonnees.afficherMap();
                remplireL.setEnabled(true);
    
            }
            catch(IOException ex)
            {
                System.out.println("LE fichier na pas ete lue");
                JOptionPane.showMessageDialog(this, "LE fichier na pas ete lue");
                
            }
            catch(SQLException esql)
            {
            	JOptionPane.showMessageDialog(this, "probleme SQL");
            }
            catch(Exception exc)
            {
            	
            	JOptionPane.showMessageDialog(this, "autre probleme");
            }
            
        }
        
        
        if(e.getModifiers() == InputEvent.CTRL_MASK && e.getKeyCode()==KeyEvent.VK_L)
        {
            try
            {
        
                bdDonnees.lireBddLivre("Livres.txt");
                System.out.println("bd Livres Lue");
                bdDonnees.afficherMap();
                setButtonState(true);
        
            }
            catch(IOException ex)
            {
                System.out.println("LE fichier na pas ete lue");
                JOptionPane.showMessageDialog(this, "LE fichier na pas ete lue");
            }

            catch(Exception exc)
            {
            	
            	JOptionPane.showMessageDialog(this, "autre probleme");
            }
            
        }
        if(e.getModifiers() == InputEvent.CTRL_MASK && e.getKeyCode()==KeyEvent.VK_Q)
        {
            System.exit(0);    
        }
        //System.out.println(ch);   
    }

    public void keyTyped(KeyEvent e)
    {
        
    }
    
    public void keyReleased(KeyEvent e)
    {
        
    }

    public void actionPerformed (ActionEvent evt)  
    {
         String arg = evt.getActionCommand();
         //System.out.println(evt.getSource());
         System.out.println(arg);
         
        
       // Map<Auteur,HashSet<Livre>> m1;
        
        if(arg.equals("A Propos"))
        {
            JOptionPane.showMessageDialog(this,  "A Propos : \n GUITP2 V1.6 par Karl Rushford ");   
        }
        
        if(arg.equals("Aide Online"))
        {
            JFrame k = new JFrame();
            k.setSize(500, 500);
            String message="";
            
            message="Fichier Aider: \n \n " ;        
            DefaultListModel<String> dlm = new DefaultListModel<String>();
            
            dlm.addElement(message);
            message="1- remplir la bd Auteur \n";
            dlm.addElement(message);
            message="2- remplir la bd Livre \n";
            dlm.addElement(message);
            message="3- appuyer sur lun des bouton pour une action \n \n";
            dlm.addElement(message);
            message= "touche ctrl+A ou bouton Remplir BD Auteur pour remplire la bd \n \n";
            dlm.addElement(message);
            message="touche ctrel+L ou bouton Remplir BD Livre pour remplir la bd Livre \n \n";
            dlm.addElement(message);
            message="touche ctrl+Q pour quitter l'application \n \n";
            dlm.addElement(message);
            message="bouton Afficher_liste pour avoir acces a la map des Auteurs \n \n";
            dlm.addElement(message);
            message="bouton Chercher Auteur  pour trouver un auteur apres avoir remplie lun des champs \n \n";
            dlm.addElement(message);
            message="bouton Chercher Livre pour trouver un livre \n \n";
            dlm.addElement(message);
            message="bouton chercher Oeuvre pour avoir tous les oeuvres dun auteur apres avoir remplie les champ \n \n";
            dlm.addElement(message);
            message="bouton Ajouter Auteur pour ajouter un auteur apres avoir remplie les champs \n \n";
            dlm.addElement(message);
            message="bouton Ajouter Livres pour ajouter un livre a un auteur apres avoir remplie les champs \n \n";
            dlm.addElement(message);
            message="bouton Rappoert Auteur pour genere le fichier parAuteur dans fichier TXT \n \n";
            dlm.addElement(message);
            message="bouton Rappoert Livre pour genere le fichier parLivre dans fichier TXT \n \n";
            dlm.addElement(message);
            
            
            JList<String> list = new JList(dlm);
            
            k.add(list);
            k.setVisible(true);      
        }
        
        
        try
        {
            if (arg.equals("Remplir BD Auteur"))
            {
                bdDonnees.lireBddAut("Auteurs.txt");
                System.out.println("bd Auteus Lue");
                bdDonnees.afficherMap();
                //m1= bdDonnees.getMap();
                //list=new JList(m1.keySet().toArray());
                //subList.add(list);
                
                remplireL.setEnabled(true);
                
            }
            if (arg.equals("Remplir BD Livre"))
            {
                bdDonnees.lireBddLivre("Livres.txt");
                System.out.println("bd Livres Lue");
                bdDonnees.afficherMap();
                setButtonState(true);
                //System.out.println("test");
                
                
            }
            
        }
        catch(IOException e)
        {
            System.out.println("LE fichier na pas ete lue");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "LE fichier na pas ete lue");
        }
        catch(Exception e)
        {
            System.out.println("LE fichier na pas ete lue");
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "LE fichier na pas ete lue");
        }
        if (arg.equals("Ajouter Auteur"))
        {
            System.out.println("dans le sumit");
            int tempCode=0;
            String tempNom="";
            String tempPays="";
            
   
            try
            {
                tempCode=Integer.parseInt(numeroT.getText());
                tempNom=nomT.getText();
                tempPays = paysT.getText();
                
                Auteur a = new Auteur (tempNom,tempCode,tempPays);
                bdDonnees.addAuteur(a);
                
                bdDonnees.afficherMap();
                
                //System.out.println(numeroT.getText());
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Il y a un des champs qui est en erreur");   
            }
        }
       
        else if(arg.equals("Afficher_liste"))
        {
            j = new JFrame();
            j.setSize(500, 200);
            
            m1=bdDonnees.getMap();
            JList list = new JList(m1.keySet().toArray());
            j.add(list);
           
            
            j.setVisible(true);
        }
        else if(arg.equals("Ajouter Livre"))
        {
            String tempTitre="";
            int tempCodeAuteur=0;
            int tempCodeLivre=0;
            String tempCategorie="";
            int tempNbPages=0;
            double tempPrix=0.0;
            
            try
            {
                tempTitre=titreAddL.getText();
                tempCodeAuteur=Integer.parseInt(codeAuteurAddL.getText());
                tempCodeLivre=Integer.parseInt(codeLivreAddL.getText());
                tempCategorie=categorieAddL.getText();
                tempNbPages=Integer.parseInt(nbPagesAddL.getText());
                tempPrix=Double.parseDouble(prixAddL.getText());
                
                Livre l = new Livre (tempTitre,tempCodeLivre,tempCodeAuteur,tempCategorie,tempNbPages,tempPrix);

                bdDonnees.addLivre(l);
                
                bdDonnees.getMap();
                bdDonnees.afficherMap();
                
                //System.out.println(numeroT.getText());
                JOptionPane.showMessageDialog(this, "LE livre a ete ajouter");
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Il y a un des champs qui est en erreur");  
                System.out.println(e);
            }   
        }
        else if(arg.equals("Chercher Auteur Code"))
        {
            try
            {
                Auteur a = bdDonnees.getAuteur(Integer.parseInt(codeA.getText()));
                JOptionPane.showMessageDialog(this, "lauteur a ete trouver"+ a.getCode()+ " "+a.getNom()+" "+a.getPays());
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "l'auteur na pas ete trouvee ");   
            }
        }
        else if(arg.equals("Chercher Auteur Nom"))
        {
            try
            {
                Auteur a = bdDonnees.getAuteur(nomA.getText());
                JOptionPane.showMessageDialog(this, "lauteur a ete trouver"+ a.getCode()+ " "+a.getNom()+" "+a.getPays());
            }
            catch(Exception e)
            {
            	//e.printStackTrace();
                //JOptionPane.showMessageDialog(this, "l'auteur na pas ete trouvee ");   
            }    
        }
        else if(arg.equals("Chercher Livre Code"))
        {
            try
            {
                Livre l = bdDonnees.getLivre(Integer.parseInt(codeL.getText()));
                //JOptionPane.showMessageDialog(this, "le livre a ete trouver "+ l.getTitre()+ " "+l.getCodeLivre()+" "+l.getCategorie()+" "+l.getNbPages()+" "+l.getPrix());
            }
            catch(Exception e)
            {
                //JOptionPane.showMessageDialog(this, "le livre  na pas ete trouvee ");   
            }    
        }
        else if(arg.equals("Chercher Livre Nom"))
        {
            try
            {
                Livre l = bdDonnees.getLivre(nomL.getText());
               // JOptionPane.showMessageDialog(this, "le livre a ete trouver"+ l.getTitre()+ " "+l.getCodeLivre()+" "+l.getCategorie()+" "+l.getNbPages()+" "+l.getPrix());
            }
            catch(Exception e)
            {
                //JOptionPane.showMessageDialog(this, "le livre  na pas ete trouvee ");   
            }        
        }
        else if(arg.equals("Chercher Oeuvres Code"))
        {
            try
            {
                Auteur a = bdDonnees.getAuteur(Integer.parseInt(oeuvresGetCode.getText())); 
                Collection o = bdDonnees.getColLivresAut(a);
               // JOptionPane.showMessageDialog(this, "la collection de " + a.getNom()+ " \n"+o);
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "la collection na pas ete trouver ");
            }
        }
        else if(arg.equals("Chercher Oeuvres Nom"))
        {
            try
            {
                Auteur a = bdDonnees.getAuteur(oeuvresGetNom.getText()); 
                Collection o = bdDonnees.getColLivresAut(a);
                //JOptionPane.showMessageDialog(this, "la collection de " + a.getNom()+ " \n"+o);
                
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "la collection na pas ete trouver ");
            }    
        }
        else if(arg.equals("Rapport Auteur"))
        {
            try
            {
                bdDonnees.rapportParAuteurs(); 
                JOptionPane.showMessageDialog(this, "Le rapport de Auteur a ete creer ");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Il ya un probleme a la creation e fichier");   
            }
        }
        
        else if(arg.equals("Rapport Livre"))
        {
            try
            {
                bdDonnees.rapportParLivres();
                JOptionPane.showMessageDialog(this, "Le rapport de Livre a ete creer ");
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(this, "Il ya un probleme a la creation e fichier");   
            }
        }
        
    } // fin du listener
  
    


    public static void main(String[] args) throws IOException 
    {
        GUITP2 gUITP2 = new GUITP2(1000,450,"GUI");

    }
}
