package dao.mappers;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import domain.Role;

public class RoleMapperTest extends Mockito{

	@Test
	public void mapTest() throws SQLException {
		
		RoleMapper sut = new RoleMapper();
		ResultSet rs = mock(ResultSet.class);

		when(rs.getString("roleName")).thenReturn("admin");

		Role role = sut.map(rs);

		assertEquals("admin", role.getRoleName());
		assertEquals(0, role.getId());
	}
}
