package dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.mockito.Mockito;

import dao.mappers.IMapResultSetToEntity;
import dao.uow.IUnitOfWork;
import domain.Person;

public class PersonRepositoryTest extends Mockito {

	@Test
	public void get_by_id_test() throws SQLException {

		Person person = new Person();
		person.setLogin("aaa");
		person.setPassword("bbb");
		person.setId(0);

		Connection connection = mock(Connection.class);
		IMapResultSetToEntity<Person> mapper = mock(IMapResultSetToEntity.class);
		IUnitOfWork uow = mock(IUnitOfWork.class);
		DatabaseMetaData metadata = mock(DatabaseMetaData.class);
		PreparedStatement preparedStatement = mock(PreparedStatement.class);

		when(preparedStatement.executeQuery())
				.thenReturn(mock(ResultSet.class));
		when(metadata.getTables(null, null, null, null)).thenReturn(
				mock(ResultSet.class));
		when(connection.createStatement()).thenReturn(mock(Statement.class));
		when(connection.getMetaData()).thenReturn(metadata);
		when(connection.prepareStatement(any(String.class))).thenReturn(
				preparedStatement);
		when(mapper.map(any(ResultSet.class))).thenReturn(person);

		PersonRepository sut = new PersonRepository(connection, mapper, uow);

		Person result = sut.get(1);

		assertEquals("aaa", result.getLogin());
		assertEquals("bbb", result.getPassword());
		assertEquals(0, result.getId());

	}
}
