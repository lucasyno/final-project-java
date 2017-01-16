package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.IRepositoryCatalog;
import dao.RepositoryCatalog;

public class App 
{
    public static void main( String[] args )
    {
    	try {
			Connection connection = DriverManager.getConnection(""
					+ "jdbc:hsqldb:hsql://localhost/workdb");
			
			IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);

			Person janek = new Person();
			janek.setLogin("aaa");
			janek.setPassword("bbb");
			catalogOf.people().add(janek);
			catalogOf.saveAndClose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	try {
			Connection connection = DriverManager.getConnection(""
					+ "jdbc:hsqldb:hsql://localhost/workdb");
			
			IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);
			Person user = catalogOf.people().get(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	try {
			Connection connection = DriverManager.getConnection(""
					+ "jdbc:hsqldb:hsql://localhost/workdb");
			
			IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);
			Person user = catalogOf.people().get(1);
			catalogOf.people().delete(user);
			catalogOf.saveAndClose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
			Connection connection = DriverManager.getConnection(""
					+ "jdbc:hsqldb:hsql://localhost/workdb");
			
			IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);

			Role admin = new Role();
			admin.setRoleName("admin");
			catalogOf.role().add(admin);
			catalogOf.saveAndClose();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
			Connection connection = DriverManager.getConnection(""
					+ "jdbc:hsqldb:hsql://localhost/workdb");
			
			IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);

			Permission readOnly = new Permission();
			readOnly.setName("Read Only");
			catalogOf.permission().add(readOnly);
			catalogOf.saveAndClose();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println( "koniec!" );
    }
}
