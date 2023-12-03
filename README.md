# MazeRivalry

# Steps to run project :

1. clone git repository in IntelliJ
2. make sure connector/j is installed
3. open the project structure and add connector/j as a module dependency
4. Open my sql and run the following quesries :
 - create database mazerunnerdb
 - CREATE TABLE mazerunnerdb.playertable ( varchar(255) primary key playerName,int score);
 - ALTER TABLE MazeRunnerDb.playertable ALTER score SET DEFAULT 0;
5. change paths of all images to the local paths
6. change password and username in the dbconnection class
7. Run the mainclass.java class and enjoy

