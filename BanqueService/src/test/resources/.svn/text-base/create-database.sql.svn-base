DROP TABLE IF EXISTS role;
CREATE CACHED TABLE role(
   idrole integer NOT NULL,
   labelrole varchar(100) NOT NULL,
   PRIMARY KEY(idrole)
);

DROP TABLE IF EXISTS utilisateur;
CREATE CACHED TABLE  utilisateur (
  id integer not NULL PRIMARY KEY auto_increment,
  login  varchar(100) NOT NULL,
  password varchar(200) NOT NULL,
  idrole INTEGER NOT NULL,
  Foreign Key (idrole) references role(idrole)
);



DROP TABLE IF EXISTS banque;
CREATE CACHED TABLE  banque(
  montant float NOT NULL,
  login varchar(45) NOT NULL
);

DROP TABLE IF EXISTS operationcompte;
CREATE CACHED TABLE operationcompte(
   id integer NOT NULL AUTO_INCREMENT,
   login varchar(45) NOT NULL,
   libelleOperation varchar(150) NOT NULL,
   dateOperation TIMESTAMP  NOT NULL,
   montantCredite float NULL,
   montantDebite float NULL,
   montantGlobal float NOT NULL,
   PRIMARY KEY(id));

DROP TABLE IF EXISTS parametre;
CREATE CACHED TABLE parametre(
   code varchar(20) NOT NULL,
   label varchar(96) NOT NULL,
   description varchar(512),
   valeur integer, 
   PRIMARY KEY(code));

INSERT INTO role(idrole,labelrole) values(1,'admin');
INSERT INTO role(idrole,labelrole) values(2,'user');


INSERT INTO utilisateur(login,password,idrole) values('Didier','36a1bae1a2effa7201861a3661adb1f149a86a2f',1);
INSERT INTO utilisateur(login,password,idrole) values('Karim','36b8266bc0126922ad13f4d3009e7e4409148bcd',2);

INSERT INTO banque(montant,login) VALUES (5700,'Didier');
INSERT INTO banque(montant,login) VALUES (0,'Karim');

INSERT INTO operationcompte(login,libelleOperation ,montantGlobal,dateOperation) values('Didier','Initialisation',5700,CURRENT_DATE());
INSERT INTO operationcompte(login,libelleOperation ,montantGlobal,dateOperation) values('Karim','Initialisation',0,CURRENT_DATE());

INSERT INTO parametre(code,label,description,valeur) values('NBPTAFFGRA','nombre de points affiche graphique','Nombre de points à partir duquel on affiche le graphique',3);

INSERT INTO parametre(code,label,description,valeur) values('NBPTMAXCOUGRA','nombre de points maximum courbe graphique','Nombre de points maximum que l''on peut afficher sur le graphique',10);

COMMIT;

