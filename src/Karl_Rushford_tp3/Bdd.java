package Karl_Rushford_tp3;


import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.swing.JOptionPane;
/************************
cours: IFT1176
TP3
Karl Rushford


 **************************/

public class Bdd  
{
    

    private Map<Auteur,HashSet<Livre>> m1 = new TreeMap<Auteur,HashSet<Livre>>();
    

    //constructeur vide pour la class
    public Bdd() 
    {
        super();
    }
    
    public Map getMap()
    {
        return m1;
    }
    //fonction de lecture du fichier auteur
    public void lireBddAut(String nomFichier) throws IOException , ClassNotFoundException, SQLException
    {
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql;
		Statement stat = conn.createStatement();
    	
        //TreeSet<Auteur> ensemble = new TreeSet<Auteur>();
        FileReader fr = null;
        boolean existeFile = true;
        boolean finFichier = false;
        String path="fichierTXT/";
        
        //valider que le fichier est valide sinon on retounr un message d'erreur
        try 
        {
            fr = new FileReader(path+nomFichier);
            
        } 
        catch(java.io.FileNotFoundException e) 
        {
            System.out.println(e);
            System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
            JOptionPane.showMessageDialog(null, "Probleme d'ouvrir le fichier " + nomFichier); 
            existeFile = false;
        }
        
    	try{
    		stat.execute("Drop Table auteur");
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("La table n'existe pas");
    		JOptionPane.showMessageDialog(null, "La table existe pas  ");
    	}
    	sql = "CREATE TABLE auteur ( code INT PRIMARY KEY , nom VARCHAR (50) , pays VARCHAR (50))";
        try
        {
        	stat.execute(sql);
        }
		catch(SQLException exsqlc)
        {
			System.out.println("Erreur dans creation de table");
			exsqlc.printStackTrace();
		}
        //si le fichier existe
        if (existeFile)
        {
            BufferedReader entree = new BufferedReader (fr);
            //System.out.println(" Le fichier original de Auteur ");
            while (!finFichier)
            {
                String auteur = entree.readLine(); // null si fin de fichier 
                String contenu[]={};
                
                try
                {
                    contenu = auteur.split("\\t");
                }
                catch(NullPointerException e)
                {
                    //System.out.print("NullPointerException Caught \n");
                }

                if(auteur!=null)
                {

                    Auteur nouveau= new Auteur(contenu[1],Integer.parseInt(contenu[0]),contenu[2]);
                    m1.put(nouveau, new HashSet<Livre>());
                    //System.out.println("la valeur de compteurcle "+compteurCle+" la valeur de nouveau code "+nouveau.getCode());
                    //System.out.println("la longeur de clePrimaireAuteur" + clePrimaireAuteur.size());

                    try
                    {
                    	System.out.println("INSERT INTO auteur VALUES ("+Integer.parseInt(contenu[0])+",'"+contenu[1]+"','"+contenu[2]+"')");
                    	stat.execute("INSERT INTO auteur VALUES ("+Integer.parseInt(contenu[0])+",'"+contenu[1]+"','"+contenu[2]+"')");
                    }
            		catch(SQLException ex){
            			System.out.println("Erreur dans insert de table");
            			ex.printStackTrace();

            			
            		}
            		finally{
            			
            		}
                }
                else
                {
                    finFichier = true; 
                    stat.close();
                }
            }//fin du while la fin de fichier est atteint
            entree.close();//on ferme le fichier
            conn.close();
            //System.out.println("la taille de la map est "+ m1.size());
            //System.out.println("Fin de lire fichier auteur");
            JOptionPane.showMessageDialog(null, "La collection de auteur a ete creer " ); 
        }// fin du if le fichier existe
    }
    
    public void addAuteur(Auteur a) throws ClassNotFoundException, SQLException
    {
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql;
		Statement stat = conn.createStatement();
    	
        boolean dejaPresent=false;
       System.out.println("debut de ajout auteur " + a.getNom()+a.getCode());
       
        //on valide que le code n'Est pas deja utiliser comme cest la cle primaire
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            if(key.getCode()==a.getCode())
            {
                dejaPresent=true;
                System.out.println("Le code a ete trouve on ne fait pas lajout "+a.getCode());    
                JOptionPane.showMessageDialog(null, "Le code a ete trouve on ne fait pas lajout "+a.getCode()); 
            }
            
        }

        //si le code auteur nest pas present on fait lajout
        if(dejaPresent==false)
        {
            m1.put(a, new HashSet<Livre>());
            sql="INSERT INTO auteur VALUES ("+a.getCode()+",'"+a.getNom()+"','"+a.getPays()+"')";
            //System.out.println("lon a ajouter auteur  " +a.getCode());
            //System.out.println("LA taille de la map est "+m1.size());
            
            try
            {
            	stat.execute(sql);
            	JOptionPane.showMessageDialog(null, "Lauteur a ete ajoute " ); 
            	
            }
    		catch(SQLException ex)
            {
    			System.out.println("Erreur dans insert de table");
    			ex.printStackTrace();
    			JOptionPane.showMessageDialog(null, "Lauteur na pas ete ajoute SQL erreur " ); 
    			
    		}
            stat.close();
            conn.close();
        }
        //afficherMap();


    }
    
    public void lireBddLivre(String nomFichier) throws IOException , ClassNotFoundException, SQLException
    {
        FileReader fr = null;
        boolean existeFile = true;
        boolean finFichier = false;
        Set<Auteur> keys=m1.keySet();
        HashSet<Livre> setLivreComplete=new HashSet<Livre>();
        String path="fichierTXT/";
        
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql;
		Statement stat = conn.createStatement();
        
        
        //valider que le fichier est valide sinon on retounr un message d'erreur
        try 
        {
            fr = new FileReader(path+nomFichier);
        } 
        catch(java.io.FileNotFoundException e) 
        {
            System.out.println("Probleme d'ouvrir le fichier " + nomFichier);
            JOptionPane.showMessageDialog(null, "Probleme d'ouvrir le fichier " + nomFichier); 
            
            existeFile = false;
        }
        
        
    	try{
    		stat.execute("Drop Table livre");
    	}
    	catch(SQLException ex)
    	{
    		System.out.println("La table n'existe pas");
    		JOptionPane.showMessageDialog(null, "La table existe pas  ");
    	}
    	sql = "CREATE TABLE livre (titre VARCHAR (50), code_livre INT PRIMARY KEY ,code_auteur INT  , categorie VARCHAR (30), nbPages INT, prix DECIMAL(7,2))";
       
    	// avec le foreing key on peux pas elete la table auteur au debut onc on le met pas 
    	//sql = "CREATE TABLE livre (titre VARCHAR (50), code_livre INT PRIMARY KEY ,code_auteur INT, FOREIGN KEY (code_auteur) REFERENCES auteur(code)  , categorie VARCHAR (30), nbPages INT, prix DECIMAL(7,2))";
        
    	System.out.println(sql);
    	try
        {
        	stat.execute(sql);
        }
		catch(SQLException exsqlc)
        {
			System.out.println("Erreur dans creation de table");
			exsqlc.printStackTrace();
		}
        
        //si le fichier existe
        if (existeFile)
        {
            BufferedReader entree = new BufferedReader (fr);
           //System.out.println("la collention de livre original ");
        
            while (!finFichier)
            {
                String livre = entree.readLine(); // null si fin de fichier
                
                String contenu[]={};
                try
                {
                    contenu = livre.split("\\t");
                }
                catch(NullPointerException e)
                {
                    //System.out.println("NullPointerException Caught \n");
                }

                if(livre!=null)
                {  
                  setLivreComplete.add(new Livre(contenu[1],Integer.parseInt(contenu[0]),Integer.parseInt(contenu[3]),contenu[2],Integer.parseInt(contenu[5]),Double.parseDouble(contenu[4].replaceAll(" ",""))));
                  for(Iterator i=keys.iterator();i.hasNext();)
                  {
                      Auteur key = (Auteur) i.next();
                      
                      //Livre nouveau = new Livre(contenu[1],Integer.parseInt(contenu[0]),Integer.parseInt(contenu[3]),contenu[2],Integer.parseInt(contenu[5]),Double.parseDouble(contenu[4].replaceAll(" ","")));
                      int codeAuteurFind =key.getCode();
                      //sSystem.out.println("le Code est "+codeAuteurFind);
                      //System.out.println("la valeur de keygetcode "+ key.getCode()+" LA vaeur de codeAuteurFind "+ codeAuteurFind);
                      Iterator<Livre> itr=setLivreComplete.iterator(); 
                      HashSet <Livre> value =new HashSet<Livre>();
                      while(itr.hasNext())
                      {
                          Livre nouveau =itr.next();
                          if(nouveau.getCodeAuteur()==codeAuteurFind)
                          {
                              value.add(nouveau);
                              //on enleve le livre ajoute a la liste complete pour accelerer la recherche
                              //setLivreComplete.remove(itr);
                          }
                          //System.out.println("la valeur de value "+value);
                      }
                      m1.put(key,value);
                  
                      

                      
                      
                  
                  }                    
                  
                  sql="INSERT INTO livre VALUES ('"+contenu[1]+"',"+Integer.parseInt(contenu[0])+","+Integer.parseInt(contenu[3])+",'"+contenu[2]+"',"+Integer.parseInt(contenu[5])+","+Double.parseDouble(contenu[4])+")";
                  System.out.println(sql);
                  try
                  {
                  	//sql="INSERT INTO livre VALUES ('"+contenu[1]+"',"+Integer.parseInt(contenu[0])+","+Integer.parseInt(contenu[3])+",'"+contenu[2]+"',"+Integer.parseInt(contenu[5])+","+Double.parseDouble(contenu[4].replaceAll(" ","")+")");
                  //	System.out.println(sql);
                  	stat.execute(sql);
                  	
                  }
          		  catch(SQLException ex)
                  {
          			System.out.println("Erreur dans insert de table");
          			ex.printStackTrace();
          	
          			
          		}
                  
                }
                else
                {
                    finFichier = true;  
                    stat.close();
                }
            }//fin du while la fin de fichier est atteint
            entree.close();//on ferme le fichier
            conn.close();
            JOptionPane.showMessageDialog(null, "La collection de livre a ete creer " ); 
            //on prepare un haset avec les livre de un auteur
            //Iterator<Livre> itr=setLivreComplete.iterator();


            
           // Set<Auteur> keys=m1.keySet();

        }// fin du if le fichier existe  
        
    }

    public void addLivre(Livre l)  throws ClassNotFoundException, SQLException
    {
       //System.out.println(" dans ajout livre ");
    	
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql;
		Statement stat = conn.createStatement();
       
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            if(key.getCode()==l.getCodeAuteur())
            {
                //System.out.println("le code de lauteur a ete trouver avant lajout du livre");
                //aller chercher la collection de livre pour valider si le livre existe
                HashSet <Livre> setLivreAuteur =new HashSet<Livre>();
                setLivreAuteur=(HashSet <Livre>) m1.get(key);
                
                //Iterator<Livre> itr=setLivreAuteur.iterator();
                Livre livreAjouter=null;
                for(Iterator j =setLivreAuteur.iterator();j.hasNext();)
                {
                    // reassayer un put sortir le hashet de literateur de livre 
                    Livre nouveau =(Livre)j.next();
 
                    //System.out.println("la valeur de la collection de livre avant ajout "+setLivreAuteur);
                    if(nouveau.equals(l.getTitre()))
                    {
                        System.out.println("le livre existe deja");
                        JOptionPane.showMessageDialog(null, "Le livre existe deja" );
                    }
                    else
                    {
                       // System.out.println("le livre nexiste pas on lajoute "+l.getTitre());
                        //setLivreAuteur.add(l);
                        livreAjouter=l;
                        
                    }
                }//fin du for iterator livre
                
                //si il y a un livre a rajouter
                if(livreAjouter!=null)
                {
                    setLivreAuteur.add(livreAjouter);
                    m1.put(key, setLivreAuteur);
                    sql="INSERT INTO livre VALUES ('"+livreAjouter.getTitre()+"',"+livreAjouter.getCodeLivre()+","+
                         livreAjouter.getCodeAuteur()+",'"+livreAjouter.getCategorie()+"',"+livreAjouter.getNbPages()+
                         ","+livreAjouter.getPrix()+")";
                    
                    try
                    {
                    	stat.execute(sql);
                    	JOptionPane.showMessageDialog(null, "Lauteur a ete ajoute  SQL" ); 
                    	
                    }
            		catch(SQLException ex)
                    {
            			System.out.println("Erreur dans insert de table");
            			ex.printStackTrace();
            			JOptionPane.showMessageDialog(null, "Lauteur na pas ete ajoute SQL erreur " ); 
            			
            		}
                    stat.close();
                    conn.close();
                    //System.out.println("la valeur de la collection de livre apres ajout "+setLivreAuteur);
                    JOptionPane.showMessageDialog(null, "Le livre a ete ajoute " );
                }
                //on gere la cas ou la collection est vide
                if(setLivreAuteur.isEmpty())
                {
                    setLivreAuteur.add(l);
                    sql="INSERT INTO livre VALUES ('"+livreAjouter.getTitre()+"',"+livreAjouter.getCodeLivre()+","+
                            livreAjouter.getCodeAuteur()+",'"+livreAjouter.getCategorie()+"',"+livreAjouter.getNbPages()+
                            ","+livreAjouter.getPrix()+")";
                    m1.put(key, setLivreAuteur);
                    
                    try
                    {
                    	stat.execute(sql);
                    	JOptionPane.showMessageDialog(null, "livre a ete ajoute  SQL" ); 
                    	
                    }
            		catch(SQLException ex)
                    {
            			System.out.println("Erreur dans insert de table");
            			ex.printStackTrace();
            			JOptionPane.showMessageDialog(null, "livre na pas ete ajoute SQL erreur " ); 
            			
            		}
                    stat.close();
                    conn.close();
                    //System.out.println("la valeur de la collection de livre apres ajout "+setLivreAuteur);
                    JOptionPane.showMessageDialog(null, "Le livre a ete ajoute " );
                
        
                }
 
            }//fin du if getcode
        }   //fin  iterator Auteur
       
    }
    
    public Auteur getAuteur(String nom) throws ClassNotFoundException, SQLException
    {
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		sql="SELECT * from auteur where nom like '%"+ nom + "%';";
		sqlQ(sql,stat,reset);
	
		conn.close();
		
		
		
		
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            //HashSet <Livre> value= (HashSet <Livre>) m1.get(key);
            String temp = key.getNom();
            if(temp.equals(nom))
            {
                //System.out.println("le nom de l'auteur " + temp);
                return key;
            }
            //else
            //    System.out.println("La nom "+ nom+ " n'a pas ete trouve");
           // System.out.println("la valeur de key " +key+" la valeur de la value "+value);
        }       
        
        return null;
    }
    public Auteur getAuteur(int codeAuteur) throws ClassNotFoundException, SQLException
    {
        Set<Auteur> keys=m1.keySet();
        
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		sql="SELECT * from auteur where code = "+ codeAuteur + ";";
		
		sqlQ(sql,stat,reset);
		
		conn.close();
        
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            int temp = key.getCode();
            if(temp==codeAuteur)
            {
                //System.out.println("le code de l'auteur " + temp);
                return key;
            }

        }       
        
        return null;
    }
    
    public Livre getLivre(String titre) throws ClassNotFoundException, SQLException
    {
    	
    	
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		sql="SELECT * from livre where titre like  '%"+ titre + "%';";
		
		sqlQ(sql,stat,reset);
		
		conn.close();
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            HashSet <Livre> setLivre =new HashSet<Livre>();
            setLivre=(HashSet <Livre>) m1.get(key);
            for(Iterator j=setLivre.iterator();j.hasNext();)
            {
                Livre livreTrouver = (Livre) j.next();
                if(livreTrouver.getTitre().equals(titre))
                {
                    System.out.println(livreTrouver.getTitre()+" le livre a ete trouver "+ titre);
                    return livreTrouver;
                }
            }
        }
        
        return null;    
    }

    public Livre getLivre(int codeLivre) throws ClassNotFoundException, SQLException
    {
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		sql="SELECT * from livre where code_livre = "+ codeLivre + ";";
		
		sqlQ(sql,stat,reset);
		
		conn.close();
    	
    	
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            HashSet <Livre> setLivre =new HashSet<Livre>();
            setLivre=(HashSet <Livre>) m1.get(key);
            for(Iterator j=setLivre.iterator();j.hasNext();)
            {
                Livre livreTrouver = (Livre) j.next();
                if(livreTrouver.getCodeLivre()==codeLivre)
                {
                    System.out.println(livreTrouver.getCodeLivre()+" le livre a ete trouver "+ codeLivre);
                    return livreTrouver;
                }
            }
        }
        
        
        
        return null;    
    }
    
    public Collection getColLivresAut(Auteur unAuteur) throws ClassNotFoundException, SQLException
    {
    	
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		sql="SELECT code,nom,titre,categorie,nbPages,prix from auteur" +
		" JOIN livre on code=code_auteur  where code_livre = "+ unAuteur.getCode() +" or nom like '%"+unAuteur.getNom() + "%';";
		
		System.out.println(sql);
		sqlQ(sql,stat,reset);
		
		conn.close();
    	
    	
    	
    	
    	
    	
    	
        Set<Auteur> keys=m1.keySet();
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur key = (Auteur) i.next();
            HashSet <Livre> value= (HashSet <Livre>) m1.get(key);
            if(key.getCode()==unAuteur.getCode())
            {
                //System.out.println(" on a trouver lauteur " + unAuteur.getNom()+ "sa value est "+ value);
                return value;
                
            }
   

        } 
        return null;    
    }
    
    public void rapportParAuteurs() throws IOException, ClassNotFoundException, SQLException
    {
        //System.out.println("LA map est sorte \n"+m1.entrySet());
        createPath("fichierTXT");
        createFile("fichierTXT/parAuteur.txt");
        Set<Auteur> keys=m1.keySet();
        BufferedWriter writer = new BufferedWriter(new FileWriter("fichierTXT/parAuteur.txt"));
        
        
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		
		sql="SELECT code,nom,titre,categorie,nbPages,prix from auteur" + 
			" left JOIN livre on code=code_auteur order by nom,titre;";
		//sql="select * from livre order by code_auteur";
		System.out.println(sql);
		

			try{

	        	reset= stat.executeQuery(sql);


	        	ResultSetMetaData rsmd= reset.getMetaData();
	    		int nbCol = rsmd.getColumnCount();

				for(int i=1; i<=nbCol;i++)
				{
		  			System.out.print("  " +rsmd.getColumnName(i) );
		  			message+="  " +rsmd.getColumnName(i);
		  			writer.write("  " +rsmd.getColumnName(i) );
				}
				
				System.out.println();
				message+="\n";
				writer.write("\n");

				while( reset.next() ) 
				{
		    		System.out.print(reset.getRow() + ") \t ");
		    		message+=reset.getRow() + ")  ";
		    		writer.write(reset.getRow() + ") \t ");
		    		for(int i=1; i<=nbCol;i++)
		    		{
		    			System.out.print(reset.getString(i) + " \t ");
		    		    message+=reset.getString(i) + "  ";
		    		    writer.write(reset.getString(i) + "  ");
		    		}
		    		System.out.println();
		    		message+="\n";
		    		writer.write("\n");
				}
				//JOptionPane.showMessageDialog(null, message );
				

			}
			catch(SQLException ex){
				System.out.println("Erreur dans requete");
				ex.printStackTrace();
			}
			finally
			{
				if(reset != null)
					reset.close();
				stat.close();
			}
		
		
		conn.close();
        
        /*
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur a=(Auteur)i.next();
            String ligneAuteur=a.getNom()+": \n";
            //System.out.println(ligneOutput);
            writer.write(ligneAuteur);
            HashSet <Livre> setLivre =new HashSet<Livre>();
            setLivre=(HashSet <Livre>) m1.get(a);
            for(Iterator j=setLivre.iterator();j.hasNext();)
            {
                Livre l =(Livre)j.next();
                String ligneLivre="\t"+l.getTitre()+"\t"+l.getCategorie()+"\t"+l.getPrix()+"$ "+l.getNbPages()+"\n";
                writer.write(ligneLivre);
            }
  
        }*/
        writer.close();
    }
    
    public void rapportParLivres()throws IOException, ClassNotFoundException, SQLException
    {
        createPath("fichierTXT");
        createFile("fichierTXT/parLivre.txt");
        Set<Auteur> keys=m1.keySet();
        BufferedWriter writer = new BufferedWriter(new FileWriter("fichierTXT/parLivre.txt"));
        
        
    	ConnectionBean bean = new ConnectionBean();
    	bean.setDriver();
    	Connection conn = bean.getConnection();
    	
    	String sql,message="";
		Statement stat = conn.createStatement();
		ResultSet reset = null;
		
		
		sql="SELECT titre,categorie,nbPages,prix,nom from livre" + 
			" JOIN auteur on code=code_auteur order by nom,titre;";
		//sql="select * from livre order by code_auteur";
		System.out.println(sql);
		

			try{

	        	reset= stat.executeQuery(sql);


	        	ResultSetMetaData rsmd= reset.getMetaData();
	    		int nbCol = rsmd.getColumnCount();

				for(int i=1; i<=nbCol;i++)
				{
		  			System.out.print("  " +rsmd.getColumnName(i) );
		  			message+="  " +rsmd.getColumnName(i);
		  			writer.write("  " +rsmd.getColumnName(i) );
				}
				
				System.out.println();
				message+="\n";
				writer.write("\n");

				while( reset.next() ) 
				{
		    		System.out.print(reset.getRow() + ") \t ");
		    		message+=reset.getRow() + ")  ";
		    		writer.write(reset.getRow() + ") \t ");
		    		for(int i=1; i<=nbCol;i++)
		    		{
		    			System.out.print(reset.getString(i) + " \t ");
		    		    message+=reset.getString(i) + "  ";
		    		    writer.write(reset.getString(i) + "  ");
		    		}
		    		System.out.println();
		    		message+="\n";
		    		writer.write("\n");
				}
				//JOptionPane.showMessageDialog(null, message );
				

			}
			catch(SQLException ex){
				System.out.println("Erreur dans requete");
				ex.printStackTrace();
			}
			finally
			{
				if(reset != null)
					reset.close();
				stat.close();
			}
		
		
		conn.close();
        
        /*
        for(Iterator i=keys.iterator();i.hasNext();)
        {
            Auteur a=(Auteur)i.next();
            HashSet <Livre> setLivre =new HashSet<Livre>();
            setLivre=(HashSet <Livre>) m1.get(a);
            
            for(Iterator j=setLivre.iterator();j.hasNext();)
            {
                Livre l =(Livre)j.next();
                String ligneLivre=l.getTitre()+"\t"+l.getCategorie()+"\t"+l.getPrix()+"$ "+l.getNbPages()+"\t"+a.getNom()+"\n";
                writer.write(ligneLivre);
            }
            
        }*/
        
        
        writer.close();
    }
    public static void sqlQ(String sql,Statement stat, ResultSet reset) throws ClassNotFoundException, SQLException
    {
    	String message="";
		

		try{

        	reset= stat.executeQuery(sql);


        	ResultSetMetaData rsmd= reset.getMetaData();
    		int nbCol = rsmd.getColumnCount();

			for(int i=1; i<=nbCol;i++)
			{
	  			System.out.print("  " +rsmd.getColumnName(i) );
	  			message+="  " +rsmd.getColumnName(i);
			}
			
			System.out.println();
			message+="\n";

			while( reset.next() ) 
			{
	    		System.out.print(reset.getRow() + ") \t ");
	    		message+=reset.getRow() + ")  ";
	    		for(int i=1; i<=nbCol;i++)
	    		{
	    			System.out.print(reset.getString(i) + " \t ");
	    		    message+=reset.getString(i) + "  ";
	    		}
	    		System.out.println();
	    		message+="\n";
			}
			JOptionPane.showMessageDialog(null, message );
			

		}
		catch(SQLException ex){
			System.out.println("Erreur dans requete");
			ex.printStackTrace();
		}
		finally
		{
			if(reset != null)
				reset.close();
			stat.close();
		}
			
    	
    	
    }
    //on creer un folder pour les fichiers rapports
    public static void createPath(String path)
    {
        File dir = new File(path);
        if(!dir.exists())
        {
            try
            {
                dir.mkdir();
            }
            catch(SecurityException se)
            {
                System.out.println("probleme de securite");    
            }
        }
    }
    
    public static void createFile(String path)
    {
        try 
        {
            File file = new File(path);
            if (!file.exists()) 
            {
                file.createNewFile();
            } 
            else 
            {
                FileOutputStream writer = new FileOutputStream(path);
                writer.write(("").getBytes());
                writer.close();
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }    
    
    public void afficherMap() 
    {
        
         Set<Auteur> keys=m1.keySet();
         for(Iterator i=keys.iterator();i.hasNext();)
         {
             Auteur key = (Auteur) i.next();
             HashSet <Livre> value= (HashSet <Livre>) m1.get(key);
             int temp = key.getCode();
             System.out.println("le code de l'auteur " + temp);
             System.out.println("la valeur de key " +key+" la valeur de la value "+value);
         }
    }
}
