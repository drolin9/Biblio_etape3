Drop Table auteur;
CREATE TABLE auteur ( code INT PRIMARY KEY , nom VARCHAR (50) , pays VARCHAR (50));
Drop Table livre;
CREATE TABLE livre (titre VARCHAR (50), code_livre INT PRIMARY KEY ,code_auteur INT  , categorie VARCHAR (30), nbPages INT, prix DECIMAL(7,2));