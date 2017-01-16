package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.mappers.IMapResultSetToEntity;
import dao.uow.IUnitOfWork;
import dao.uow.IUnitOfWorkRepository;
import domain.Entity;
import domain.IHaveId;

public abstract class RepositoryBase<TEntity extends IHaveId> implements
		IUnitOfWorkRepository, IRepository<TEntity> {

	protected Connection connection;
	protected Statement createTable;
	protected PreparedStatement insert;
	protected PreparedStatement delete;
	protected PreparedStatement update;
	protected PreparedStatement get;
	protected PreparedStatement list;
	protected PreparedStatement drop;
	protected IMapResultSetToEntity<TEntity> mapper;
	protected IUnitOfWork uow;

	public RepositoryBase(Connection connection,
			IMapResultSetToEntity<TEntity> mapper, IUnitOfWork uow) {

		try {
			this.uow = uow;
			this.mapper = mapper;
			this.connection = connection;
			createTable = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if (tableName().equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			if (!tableExists)
				createTable.executeUpdate(createTableSql());

			insert = connection.prepareStatement(insertSql());
			delete = connection.prepareStatement(deleteSql());
			update = connection.prepareStatement(updateSql());
			get = connection.prepareStatement(getSql());
			list = connection.prepareStatement(listSql());
			drop = connection.prepareStatement(dropSql());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#persistDelete(domain.Entity)
	 */
	public void persistDelete(Entity p) {
		try {
			delete.setInt(1, p.getId());
			delete.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#delete(TEntity)
	 */
	public void delete(TEntity entity) {
		uow.markAsDeleted((Entity) entity, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#persistUpdate(domain.Entity)
	 */
	public void persistUpdate(Entity p) {
		try {
			setUpdateQuery((TEntity) p);
			update.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#update(TEntity)
	 */
	public void update(TEntity entity) {
		uow.markAsChanged((Entity) entity, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#persistAdd(domain.Entity)
	 */
	public void persistAdd(Entity p) {
		try {
			setInsertQuery((TEntity) p);
			insert.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#add(TEntity)
	 */
	public void add(TEntity entity) {
		uow.markAsNew((Entity) entity, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#getAll()
	 */
	public List<TEntity> getAll() {
		List<TEntity> persons = new ArrayList<TEntity>();

		try {
			ResultSet rs = list.executeQuery();

			while (rs.next()) {
				persons.add(mapper.map(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.IRepository#get(int)
	 */
	public TEntity get(int id) {

		try {
			get.setInt(1, id);
			ResultSet rs = get.executeQuery();
			rs.next();
			return mapper.map(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void drop(){
		try {
			drop.execute();
			connection.commit();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	protected String deleteSql() {
		return "DELETE FROM " + tableName() + " WHERE id=?;";
	}

	protected String getSql() {
		return "SELECT * FROM " + tableName() + " WHERE id=?;";
	}

	protected String listSql() {
		return "SELECT * FROM " + tableName();
	}

	protected String dropSql() {
		return "DROP TABLE " + tableName();
	}
	
	protected abstract void setUpdateQuery(TEntity p) throws SQLException;

	protected abstract void setInsertQuery(TEntity p) throws SQLException;

	protected abstract String tableName();

	protected abstract String createTableSql();

	protected abstract String insertSql();

	protected abstract String updateSql();

}
