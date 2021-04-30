package Profile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import AccountManagement.Account;
import Blog.Blogs;
import Status.BlogStatus;

public class BlogQuery{
	ArrayList<Blogs> blog = new ArrayList<Blogs>();
	
	public BlogQuery() {}
	
	public ArrayList<Blogs> getBlog() {
		return blog;
	}

	public void setBlog(ArrayList<Blogs> blog) {
		this.blog = blog;
	}

	public void getBlogByUsername(String username) {
    	try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Social_Network;integratedSecurity=true;";
    		Connection conn = DriverManager.getConnection(DB_URL);
    		Statement stmt = conn.createStatement();
    		String getAllUser = "Select * From Blog Where Username = \'" + username + "\'";
    		ResultSet r = stmt.executeQuery(getAllUser);
			while(r.next()) {
					BlogStatus stt = new BlogStatus();
					stt.set(r.getBoolean("Edit"));
					Blogs b = new Blogs(r.getString("Title"),r.getInt("BlogID"), r.getString("Username"),
							r.getString("Body"), r.getBoolean("CommentEnabled"),r.getBoolean("DeleteBlog"),
							r.getString("Date_of_blog"),r.getBoolean("Edit"), stt);
					this.blog.add(b);
			}
			r.close();
		}catch(ClassNotFoundException ex)
		{
			System.out.println("Error: unable to load driver class.");
    		System.exit(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
	
	public boolean insertBlog(Account account, String title, String content, boolean commentEnable, String date) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Social_Network;integratedSecurity=true;";
			Connection conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			String queryStatement = "Insert into Blog (UsernameID, Username, Title, Body, CommentEnabled, DeleteBlog, Edit, Date_of_blog, HitComment, HitLike) "
					+ "				values ('" + account.getId()
									+ "', '" + account.getUsername() 
									+ "', '" + title 
									+ "', '" + content
									+ "', '" + commentEnable
									+ "', '" + "True" 
									+ "', '" + "True"
									+ "', '" + date 
									+ "', '" + "0"
									+ "', '" + "0" + "');";
			int rowChange = stmt.executeUpdate(queryStatement);
			if (rowChange != 1)
				return false;
		}catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class.");
			System.exit(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;	
	}

	public boolean updateBlog(String id, String titleBlog, String contentBlog, String date) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Social_Network;integratedSecurity=true;";
			Connection conn = DriverManager.getConnection(DB_URL);
			Statement stmt = conn.createStatement();
			String queryStatement = "Update Blog "
								+ "Set Title = '" + titleBlog + "', "
										+ "Body = '" + contentBlog + "', "
										+ "Date_of_blog = '" + date + "' "
								+ "Where BlogID = '" + id + "'";
			int rowChange = stmt.executeUpdate(queryStatement);
			System.out.println(rowChange);
			if (rowChange != 1)
				return false;
		}catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class.");
			System.exit(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return true;
	}
}