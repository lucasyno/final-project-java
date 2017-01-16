package dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Role;

public class RoleMapper implements IMapResultSetToEntity<Role> {
	
	public Role map(ResultSet rs) throws SQLException {
		Role r = new Role();
		r.setId(rs.getInt("id"));
		r.setRoleName(rs.getString("roleName"));
		return r;
	}
}
