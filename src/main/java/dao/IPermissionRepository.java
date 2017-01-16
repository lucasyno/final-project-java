package dao;

import java.util.List;

import domain.Permission;

public interface IPermissionRepository extends IRepository<Permission>{
	
	public List<Permission> withName(String name);
}
