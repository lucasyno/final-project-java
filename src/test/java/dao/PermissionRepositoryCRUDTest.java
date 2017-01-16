package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import domain.Permission;

public class PermissionRepositoryCRUDTest {

	@After
	public void tearDown() throws Exception {
		IRepositoryCatalog catalogOf = getRepository();
		catalogOf.permission().drop();
	}
	
	@Test
	public void testCreate() throws SQLException {
		createPermission();
	}
	
	@Test
	public void testRead() throws SQLException {
		createPermission();
		IRepositoryCatalog catalogOf = getRepository();
		Permission perm = catalogOf.permission().get(0);
		catalogOf.saveAndClose();
		
		assertEquals("edycja", perm.getName());
	}

	@Test
	public void testUpdate() throws SQLException {
		Permission savedperm = createPermission();
		IRepositoryCatalog catalogOf = getRepository();
		Permission perm = catalogOf.permission().get(savedperm.getId());
		
		assertEquals("edycja", perm.getName());
		
		perm.setName("usuwanie");
		catalogOf.permission().update(perm);
		catalogOf.saveAndClose();
		
		IRepositoryCatalog catalogOf2 = getRepository();
		Permission perm2 = catalogOf2.permission().get(savedperm.getId());
		catalogOf2.saveAndClose();
		
		assertEquals("usuwanie", perm2.getName());
	}
	
	@Test
	public void testDelete() throws SQLException {
		Permission savedperm = createPermission();
		IRepositoryCatalog catalogOf = getRepository();
		Permission perm = catalogOf.permission().get(savedperm.getId());
		catalogOf.permission().delete(perm);
		catalogOf.saveAndClose();
	}
	
	// helper function to create permission
	public Permission createPermission() throws SQLException {
		IRepositoryCatalog catalogOf = getRepository();
		Permission perm = new Permission();
		perm.setName("edycja");
		catalogOf.permission().add(perm);
		catalogOf.saveAndClose();
		return perm;
	}
	
	// helper function to get connection with database and return Repository
	public IRepositoryCatalog getRepository() throws SQLException {
		Connection connection = DriverManager.getConnection(""
				+ "jdbc:hsqldb:hsql://localhost/workdb");
		
		IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);
		return catalogOf;
	}

}
