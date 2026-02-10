package com.bank.service;
import java.sql.*;
import com.bank.exception.BankException;
import com.bank.util.DBConnection;
public class BankService {

	public void register(String username,String password) throws Exception{
		if(username.isEmpty() || password.length()<4)
			throw new BankException("Invalid username or password");
	
	Connection con =DBConnection.getConnection();
	PreparedStatement pst =
			con.prepareStatement("INSERT INTO users(username,password) VALUES(?,?)");
	pst.setString(1, username);
	pst.setString(2, password);
	pst.executeUpdate();
	pst = con.prepareStatement(
            "INSERT INTO accounts(user_id) VALUES(LAST_INSERT_ID())");
	pst.executeUpdate();

        con.close();
	}
	public int login (String username, String password) throws Exception{
		Connection con = DBConnection.getConnection();
		PreparedStatement ps =
	            con.prepareStatement(
	            "SELECT user_id FROM users WHERE username=? AND password=?");

	        ps.setString(1, username);
	        ps.setString(2, password);
	        ResultSet rs = ps.executeQuery();
	        if (!rs.next())
	            throw new BankException("Invalid Login");

	        return rs.getInt(1);
		
	}
	 public void deposit(int userId, double amount) throws Exception{
		 if(amount<=0) {
			  throw new BankException("Invalid amount");
		 }
		 Connection con = DBConnection.getConnection();
	        PreparedStatement ps =
	                con.prepareStatement(
	                "UPDATE accounts SET balance = balance + ? WHERE user_id=?");
	            ps.setDouble(1, amount);
	            ps.setInt(2, userId);
	            ps.executeUpdate();

	            ps = con.prepareStatement(
	                "INSERT INTO transactions(acc_id,type,amount) " +
	                "SELECT acc_id,'Deposit',? FROM accounts WHERE user_id=?");
	            ps.setDouble(1, amount);
	            ps.setInt(2, userId);
	            ps.executeUpdate();

	            con.close();
	 }
	 public void withdraw(int userId, double amount) throws Exception {
	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps =
	            con.prepareStatement(
	            "SELECT balance, acc_id FROM accounts WHERE user_id=?");
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        if (!rs.next() || rs.getDouble(1) < amount)
	            throw new BankException("Insufficient Balance");

	        ps = con.prepareStatement(
	            "UPDATE accounts SET balance = balance - ? WHERE user_id=?");
	        ps.setDouble(1, amount);
	        ps.setInt(2, userId);
	        ps.executeUpdate();

	        ps = con.prepareStatement(
	            "INSERT INTO transactions(acc_id,type,amount) VALUES(?, 'Withdraw', ?)");
	        ps.setInt(1, rs.getInt(2));
	        ps.setDouble(2, amount);
	        ps.executeUpdate();

	        con.close();
	    }

	    // Transaction History
	    public void showTransactions(int userId) throws Exception {
	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps =
	            con.prepareStatement(
	            "SELECT type, amount, tx_date FROM transactions t " +
	            "JOIN accounts a ON t.acc_id=a.acc_id WHERE a.user_id=?");

	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            System.out.println(
	                rs.getString(1) + " | " +
	                rs.getDouble(2) + " | " +
	                rs.getTimestamp(3));
	        }
	        con.close();
	    }
	    public double checkBalance(int userId) throws Exception{
	    	 Connection con = DBConnection.getConnection();
	    	    PreparedStatement ps =
	    	            con.prepareStatement("SELECT balance FROM accounts WHERE user_id=?");
	    	        ps.setInt(1, userId);
	    	        ResultSet rs = ps.executeQuery();
	    	        if (!rs.next())
	    	            throw new BankException("Account not found");

	    	        return rs.getDouble(1);
	    	 
	    }
	    public void changeUsername(int userId, String newUsername) throws Exception {
	        if (newUsername.isEmpty())
	            throw new BankException("Username cannot be empty");

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps =
	            con.prepareStatement("UPDATE users SET username=? WHERE user_id=?");

	        ps.setString(1, newUsername);
	        ps.setInt(2, userId);

	        ps.executeUpdate();
	        con.close();
	    }
	    public void deleteAccount(int userId) throws Exception {

	        Connection con = DBConnection.getConnection();
	        con.setAutoCommit(false); // start transaction

	        try {
	            PreparedStatement ps1 =
	                con.prepareStatement(
	                "DELETE FROM transactions WHERE acc_id IN " +
	                "(SELECT acc_id FROM accounts WHERE user_id=?)");

	            ps1.setInt(1, userId);
	            ps1.executeUpdate();

	            PreparedStatement ps2 =
	                con.prepareStatement("DELETE FROM accounts WHERE user_id=?");
	            ps2.setInt(1, userId);
	            ps2.executeUpdate();

	            PreparedStatement ps3 =
	                con.prepareStatement("DELETE FROM users WHERE user_id=?");
	            ps3.setInt(1, userId);
	            ps3.executeUpdate();

	            con.commit();
	            System.out.println("Account deleted successfully");

	        } catch (Exception e) {
	            con.rollback();
	            throw new BankException("Account deletion failed");
	        } finally {
	            con.close();
	        }
	    }
	    public void changePassword(int userId, String newPassword) throws Exception {
	        if (newPassword.length() < 4)
	            throw new BankException("Password must be at least 4 characters");

	        Connection con = DBConnection.getConnection();

	        PreparedStatement ps =
	            con.prepareStatement("UPDATE users SET password=? WHERE user_id=?");

	        ps.setString(1, newPassword);
	        ps.setInt(2, userId);

	        ps.executeUpdate();
	        con.close();
	    }



}
