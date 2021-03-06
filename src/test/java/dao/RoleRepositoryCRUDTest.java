package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import domain.Role;

public class RoleRepositoryCRUDTest {
	
	@After
	public void tearDown() throws Exception {
		IRepositoryCatalog catalogOf = getRepository();
		catalogOf.role().drop();
	}

	@Test
	public void testCreate() throws SQLException {
		createRole();
	}
	
	@Test
	public void testRead() throws SQLException {
		createRole();
		IRepositoryCatalog catalogOf = getRepository();
		Role role = catalogOf.role().get(0);
		catalogOf.saveAndClose();
		
		assertEquals("admin", role.getRoleName());
	}

	@Test
	public void testUpdate() throws SQLException {
		Role savedRole = createRole();
		IRepositoryCatalog catalogOf = getRepository();
		Role role = catalogOf.role().get(savedRole.getId());
		
		assertEquals("admin", role.getRoleName());
		
		role.setRoleName("gosc");
		catalogOf.role().update(role);
		catalogOf.saveAndClose();
		
		IRepositoryCatalog catalogOf2 = getRepository();
		Role role2 = catalogOf2.role().get(role.getId());
		catalogOf2.saveAndClose();
		
		assertEquals("gosc", role2.getRoleName());
	}
	
	@Test
	public void testDelete() throws SQLException {
		Role savedRole = createRole();
		IRepositoryCatalog catalogOf = getRepository();
		Role role = catalogOf.role().get(savedRole.getId());
		catalogOf.role().delete(role);
		catalogOf.saveAndClose();
	}
	
	// helper function to create Role (for DRY principal)
	public Role createRole() throws SQLException {
		IRepositoryCatalog catalogOf = getRepository();
		Role admin = new Role();
		admin.setRoleName("admin");
		catalogOf.role().add(admin);
		catalogOf.saveAndClose();
		return admin;
	}
	
	// helper function to get connection with database and return Repository
	// (for DRY principal)
	public IRepositoryCatalog getRepository() throws SQLException {
		Connection connection = DriverManager.getConnection(""
				+ "jdbc:hsqldb:hsql://localhost/workdb");
		
		IRepositoryCatalog catalogOf = new RepositoryCatalog(connection);
		return catalogOf;
	}
}