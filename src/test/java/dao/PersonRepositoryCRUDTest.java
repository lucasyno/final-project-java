package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import domain.Person;

public class PersonRepositoryCRUDTest {
	
	@After
	public void tearDown() throws Exception {
		IRepositoryCatalog catalogOf = getRepository();
		catalogOf.people().drop();
	}

	@Test
	public void testCreate() throws SQLException {
		createPerson();
	}
	
	@Test
	public void testRead() throws SQLException {
		createPerson();
		IRepositoryCatalog catalogOf = getRepository();
		Person user = catalogOf.people().get(0);
		catalogOf.saveAndClose();
	}

	@Test
	public void testUpdate() throws SQLException {
		Person savedUser = createPerson();
		IRepositoryCatalog catalogOf = getRepository();
		Person user = catalogOf.people().get(savedUser.getId());
		user.setLogin("ccc");
		user.setPassword("tajne_haslo");
		catalogOf.people().update(user);
		catalogOf.saveAndClose();
	}
	
	@Test
	public void testDelete() throws SQLException {
		Person savedUser = createPerson();
		IRepositoryCatalog catalogOf = getRepository();
		Person user = catalogOf.people().get(savedUser.getId());
		catalogOf.people().delete(user);
		catalogOf.saveAndClose();
	}
	
	// helper function to create person
	public Person createPerson() throws SQLException {
		IRepositoryCatalog catalogOf = getRepository();
		Person janek = new Person();
		janek.setLogin("aaa");
		janek.setPassword("bbb");
		catalogOf.people().add(janek);
		catalogOf.saveAndClose();
		return janek;
	}
	
	// helper function to get connection with database and return Repository
	public IRepositoryCatalog getRepository() throws SQLException {
		Connection connection = DriverManager.getConnection(""
				+ "jdbc:hsqldb:hsql://localhost/workdb");
		
		IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);
		return catalogOf;
	}
}
