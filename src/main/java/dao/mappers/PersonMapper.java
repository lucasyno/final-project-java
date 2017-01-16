package dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Person;

public class PersonMapper implements IMapResultSetToEntity<Person>{

	public Person map(ResultSet rs) throws SQLException {
		Person p = new Person();
		p.setId(rs.getInt("id"));
		p.setLogin(rs.getString("login"));
		p.setPassword(rs.getString("password"));
		return p;
	}
}
