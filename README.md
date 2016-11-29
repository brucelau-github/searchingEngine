# searchingEngine
this project is a maven project, download the package and decompress the file.
open the eclipse. right click the package explore window and select import.</br>
choose maven existing project. 
then there you go.




invertedIndex table 

Document in DataBase:<br>
It should save as sorted set;

term uses sorted set in redis database.
the format looks like
ZADD keyword frequency documentID:postion1:postion2:postion3</br>

for example<br>
ZADD the 3 98:1:20:31 //this means "the" shows up 3 times in document with id 98 and the index of the positions are 1,20,31