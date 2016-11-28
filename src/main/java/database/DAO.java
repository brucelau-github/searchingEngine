package database;

public interface DAO {

	void set(String key, String value);

	String get(String key);

	void disconnect();

}