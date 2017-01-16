package dao;

import java.sql.SQLException;

public interface IRepositoryCatalog {

	public IPersonRepository people();

	public IRoleRepository role();

	public IPermissionRepository permission();

	public void saveAndClose() throws SQLException;

}
