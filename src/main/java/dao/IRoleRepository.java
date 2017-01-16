package dao;

import java.util.List;

import domain.Role;

public interface IRoleRepository extends IRepository<Role>{
	
	public List<Role> withName(String name);
}
