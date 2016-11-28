# searchingEngine

invertedIndex table 

Document in DataBase:<br>
It should save as sorted set;




term uses sorted set in redis database.
the format looks like
ZADD keyword frequency documentID:postion1:postion2:postion3</br>

for example<br>
ZADD the 3 98:1:20:31 //this means "the" shows up 3 times in document with id 98 and the index of the positions are 1,20,31