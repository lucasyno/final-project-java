package dao.mappers;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.mockito.Mockito;

import domain.Person;

public class PersonMapperTest extends Mockito {

	@Test
	public void mapTest() throws SQLException {

		PersonMapper sut = new PersonMapper();
		ResultSet rs = mock(ResultSet.class);

		when(rs.getString("login")).thenReturn("aaa");
		when(rs.getString("password")).thenReturn("bbb");
		when(rs.getInt("id")).thenReturn(1);

		Person person = sut.map(rs);

		assertEquals("aaa", person.getLogin());
		assertEquals("bbb", person.getPassword());
		assertEquals(1, person.getId());
	}
}
