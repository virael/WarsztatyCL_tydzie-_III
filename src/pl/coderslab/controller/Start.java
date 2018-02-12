package pl.coderslab.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.coderslab.model.Solution;
import pl.warsztaty.services.DbUtil;

/**
 * Servlet implementation class Start
 */
@WebServlet("/views/main.jsp")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// pobrać parametr z web.xml
		
		
		
		String tableLimit = getServletContext().getInitParameter("number-solutions");
		
		int tableLimitToInt = Integer.parseInt(tableLimit);
//		int ableLimitToInt = Integer.parseInt(tableLimit);
//		System.out.println(tableLimit);
//		System.out.println(tableLimitToInt);
		
		Solution solution = new Solution();
		
		try {
			solution.loadAllWithLimit(tableLimitToInt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("dupa");
			e.printStackTrace();
		} // przetestować czy trzeba castować
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
