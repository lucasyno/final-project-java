package dao.mappers;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import domain.Permission;

public class PermissionMapperTest extends Mockito {

	@Test
	public void mapTest() throws SQLException {

		PermissionMapper sut = new PermissionMapper();
		ResultSet rs = mock(ResultSet.class);

		when(rs.getString("name")).thenReturn("delete");

		Permission perm = sut.map(rs);

		assertEquals("delete", perm.getName());
		assertEquals(0, perm.getId());
	}
}
