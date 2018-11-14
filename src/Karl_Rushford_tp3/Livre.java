package Karl_Rushford_tp3;

import java.io.*;
import java.util.*;
/************************
cours: IFT1176
TP3
Karl Rushford


**************************/

public class Livre 
{
    private String titre;
    private int codeLivre;
    private int codeAuteur;
    private String categorie;
    private int nbPages;
    private double prix;

    //declaration du constructeur pour Livre
    public Livre(String titre, int codeLivre, int codeAuteur,
                  String categorie, int nbPages, double prix) 
    {
       this.titre=titre;
       this.codeLivre=codeLivre;
       this.codeAuteur=codeAuteur;
       this.categorie=categorie;
       this.nbPages=nbPages;
       this.prix=prix;
    }


    public String getTitre() 
    {
        return titre;
    }

    public int getCodeLivre() 
    {
        return codeLivre;
    }

    public int getCodeAuteur() 
    {
        return codeAuteur;
    }

    public String getCategorie() 
    {
        return categorie;
    }

    public int getNbPages() 
    {
        return nbPages;
    }

    public double getPrix() 
    {
        return prix;
    }
    

    
    public String toString()
    {
        String message = titre;
        return message;
    }


}
