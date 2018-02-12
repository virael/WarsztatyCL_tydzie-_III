package pl.coderslab.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;

import pl.warsztaty.services.DbUtil;

public class Solution {
	private int id;
	private Date created; 
	private Date updated; 
	private String description;
	private int exercise_id;
	private int users_id;
	
	
	public Solution (Date created, Date updated, String description, int exercise_id, int users_id) {
		this.created=created;
		this.updated=updated;
		this.description=description;
		this.exercise_id=exercise_id;
		this.users_id=users_id;
	}
	
	public Solution (Date created, int exercise_id, int users_id) {
		this.created=created;
		this.exercise_id=exercise_id;
		this.users_id=users_id;
	}
	
	
	public Solution() {};

	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public Date getUpdated() {
		return updated;
	}


	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getExercise_id() {
		return exercise_id;
	}


	public void setExercise_id(int exercise_id) {
		this.exercise_id = exercise_id;
	}


	public int getUsers_id() {
		return users_id;
	}


	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	
	public void saveToDB() throws SQLException {
		if	(this.id == 0) {
			String sql = "INSERT INTO Solution (created, updated, description, exercise_id, users_id) VALUES (?,?,?,?,?);";
			String generatedColumns[] = {"ID"};
			PreparedStatement preparedStatement; 
			preparedStatement = DbUtil.getConn().prepareStatement(sql, generatedColumns);
			java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
			preparedStatement.setDate(1, sqlDate);
			preparedStatement.setDate(2, null);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.exercise_id);
			preparedStatement.setInt(5, this.users_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if	(rs.next())	{
				this.id	= rs.getInt(1);
			}
			preparedStatement.close();
			}
		else {
			String	sql	= "UPDATE Solution SET updated=Now(), description=? WHERE exercise_id=? AND users_id=?;";
			PreparedStatement	preparedStatement;
			preparedStatement	=	Connect.connect().prepareStatement(sql);
			preparedStatement.setString(1,	this.description);
			preparedStatement.setInt(2,	this.id);
			preparedStatement.setInt(3, this.id);
			preparedStatement.setInt(4, this.users_id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			}
		}

		static public Solution loadSolutionById(int id) throws SQLException {
			String sql = "SELECT * FROM	Solution where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Solution loadedSolution = new Solution();
					loadedSolution.created = resultSet.getDate("created");
					loadedSolution.updated = resultSet.getDate("updated");
					loadedSolution.description	= resultSet.getString("description");
					loadedSolution.exercise_id = resultSet.getInt("exercise_id");
					loadedSolution.users_id	= resultSet.getInt("users_id");
					return	loadedSolution;
			}
			return	null;
		}
		
		static	public Solution[] loadAllSolution() throws SQLException	{
			ArrayList<Solution> solution = new ArrayList<Solution>();
			String	sql	= "SELECT * FROM Solution";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedSolution = new Solution();
				loadedSolution.created = resultSet.getDate("created");
				loadedSolution.updated = resultSet.getDate("updated");
				loadedSolution.description	= resultSet.getString("description");
				loadedSolution.exercise_id = resultSet.getInt("exercise_id");
				loadedSolution.users_id	= resultSet.getInt("users_id");
				solution.add(loadedSolution);
			}
			Solution[]	uArray	=	new	Solution[solution.size()];
			uArray	=	solution.toArray(uArray);
			return uArray;
		}

		public void delete() throws SQLException	{
			if (this.id != 0) {
				String	sql	= "DELETE FROM Solution WHERE id= ?";
				PreparedStatement preparedStatement;
				preparedStatement = Connect.connect().prepareStatement(sql);
				preparedStatement.setInt(1, this.id);
				preparedStatement.executeUpdate();
				this.id=0;
			}
		}		

		static public Solution[] loadAllByUserId (int id) throws SQLException	{
			ArrayList<Solution> userSolution = new ArrayList<Solution>();
			String	sql	= "SELECT * FROM Solution where users_id=?";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedUserSolution = new Solution();
					loadedUserSolution.id = resultSet.getInt("id");
					loadedUserSolution.created = resultSet.getDate("created");
					loadedUserSolution.updated = resultSet.getDate("updated");
					loadedUserSolution.description = resultSet.getString("description");
					loadedUserSolution.exercise_id = resultSet.getInt("exercise_id");
					loadedUserSolution.users_id = resultSet.getInt("users_id");
					userSolution.add(loadedUserSolution);
			}
			Solution[]	uArray	=	new	Solution[userSolution.size()];
			uArray	=	userSolution.toArray(uArray);
			return uArray;
		}
		
		static public Solution[] loadAllByGroupId (int id) throws SQLException	{
			ArrayList<Solution> exerciseSolution = new ArrayList<Solution>();
			String	sql	= "SELECT * FROM Solution LEFT JOIN Users ON Solution.users_id=Users.id WHERE Users.person_group_id=?;";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedExerciseSolution = new Solution();
					loadedExerciseSolution.id = resultSet.getInt("id");
					loadedExerciseSolution.created = resultSet.getDate("created");
					loadedExerciseSolution.updated = resultSet.getDate("updated");
					loadedExerciseSolution.description = resultSet.getString("description");
					loadedExerciseSolution.exercise_id = resultSet.getInt("exercise_id");
					loadedExerciseSolution.users_id = resultSet.getInt("users_id");
					exerciseSolution.add(loadedExerciseSolution);
			}
			Solution[]	uArray	=	new	Solution[exerciseSolution.size()];
			uArray	=	exerciseSolution.toArray(uArray);
			return uArray;
			}
		

		static public Solution[] loadAllByExerciseId (int id) throws SQLException	{
			ArrayList<Solution> exerciseSolution = new ArrayList<Solution>();
			String	sql	= "SELECT * FROM Solution where exercise_id=?";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedExerciseSolution = new Solution();
					loadedExerciseSolution.id = resultSet.getInt("id");
					loadedExerciseSolution.created = resultSet.getDate("created");
					loadedExerciseSolution.updated = resultSet.getDate("updated");
					loadedExerciseSolution.description = resultSet.getString("description");
					loadedExerciseSolution.exercise_id = resultSet.getInt("exercise_id");
					loadedExerciseSolution.users_id = resultSet.getInt("users_id");
					exerciseSolution.add(loadedExerciseSolution);
			}
			Solution[]	uArray	=	new	Solution[exerciseSolution.size()];
			uArray	=	exerciseSolution.toArray(uArray);
			return uArray;
			}
		
		static public Solution[] loadAllDone (int id) throws SQLException	{
			ArrayList<Solution> userSolution = new ArrayList<Solution>();
			String	sql	= "SELECT * FROM Exercise RIGHT JOIN Solution ON Exercise.id=exercise_id WHERE users_id=? AND updated IS NOT NULL";
			PreparedStatement preparedStatement;
			preparedStatement = Connect.connect().prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Solution loadedUserSolution = new Solution();
					loadedUserSolution.id = resultSet.getInt("users_id");
					loadedUserSolution.created = resultSet.getDate("created");
					loadedUserSolution.updated = resultSet.getDate("updated");
					loadedUserSolution.description = resultSet.getString("description");				
					loadedUserSolution.exercise_id = resultSet.getInt("exercise_id");
					
					userSolution.add(loadedUserSolution);
			}
			Solution[]	uArray	=	new	Solution[userSolution.size()];
			uArray	=	userSolution.toArray(uArray);
			return uArray;
		}

	@Override
	public String toString () {
		return "updated: " + updated + " description: " + description + " excersise: " + this.exercise_id + " user: " + this.users_id; 
	}
}
