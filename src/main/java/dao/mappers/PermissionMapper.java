package dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Permission;

public class PermissionMapper implements IMapResultSetToEntity<Permission>{
	
	public Permission map(ResultSet rs) throws SQLException {
		Permission perm = new Permission();
		perm.setId(rs.getInt("id"));
		perm.setName(rs.getString("name"));
		return perm;
	}
}