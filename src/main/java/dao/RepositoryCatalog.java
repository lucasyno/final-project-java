package dao;

import java.sql.Connection;
import java.sql.SQLException;

import dao.mappers.PermissionMapper;
import dao.mappers.PersonMapper;
import dao.mappers.RoleMapper;
import dao.uow.IUnitOfWork;
import dao.uow.UnitOfWork;

public class RepositoryCatalog implements IRepositoryCatalog {

	IPersonRepository peopleRepo;
	IRoleRepository roleRepo;
	IPermissionRepository permRepo;
	IUnitOfWork uow;
	Connection connection;

	public RepositoryCatalog(Connection connection) throws SQLException {
		this.connection = connection;
		uow = new UnitOfWork(connection);
		peopleRepo = new PersonRepository(connection, new PersonMapper(), uow);
		roleRepo = new RoleRepository(connection, new RoleMapper(), uow);
		permRepo = new PermissionRepository(connection, new PermissionMapper(), uow);
	}

	public IPersonRepository people() {
		return peopleRepo;
	}

	public void saveAndClose() throws SQLException {
		uow.saveChanges();
		connection.close();
		connection = null;
	}

	public IRoleRepository role() {
		return roleRepo;
	}

	public IPermissionRepository permission() {
		return permRepo;
	}
}
