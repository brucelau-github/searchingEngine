package analysis;


import database.*;
public class InvertedItemDAO {
	private RedisDAO dao = new RedisDAO();
	public InvertedItemDAO(){
		
	}
	public void saveItem(long DocID,String keyword,int Frequency, String pos) {
		dao.zadd("key:"+keyword, Frequency, DocID + pos);
	}
}
