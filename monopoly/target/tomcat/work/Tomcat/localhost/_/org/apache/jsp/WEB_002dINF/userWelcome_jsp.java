/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2021-08-20 05:45:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class userWelcome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <title>Hello Sir</title>\n");
      out.write("    <link rel=\"stylesheet\" type=\"text/css\" href=\"loginStyle.css\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    ");

        String name = (String) request.getSession().getAttribute("username");
    
      out.write("\n");
      out.write("\n");
      out.write("    <img src=\"monopolian.png\" alt=\"monopolyman\" height=\"100%\" class=\"monopolianman\">\n");
      out.write("    <img src=\"money.png\" alt=\"moneycolor\" height=500px width=400px class=\"money\">\n");
      out.write("\n");
      out.write("    <div class=\"middle-class\">\n");
      out.write("        \n");
      out.write("        <h2 id=begolden> Good luck ");
      out.print(name);
      out.write(" </h2>\n");
      out.write("\n");
      out.write("\t    <form method=\"post\" action=\"/friends\">\n");
      out.write("\t\t<button type=\"submit\" >See friends</button>\n");
      out.write("\t    </form>\n");
      out.write("\t    <br>\n");
      out.write("\t    <form method=\"post\" action=\"/friendRequests\">\n");
      out.write("\t\t<button type=\"submit\" >See friend requests</button>\n");
      out.write("\t    </form>\n");
      out.write("\t    <br>\n");
      out.write("\t    <form method=\"post\" action=\"/invitations\">\n");
      out.write("\t\t<button type=\"submit\">See invitations</button>\n");
      out.write("\t    </form>\n");
      out.write("\t    <br>\n");
      out.write("\t    <form action=\"/leaderboards\" method=\"post\">\n");
      out.write("\t\t<button type = \"submit\" name=\"leaderboards\" id=\"leaderboards\">Leaderboards</button>\n");
      out.write("\t    </form>\n");
      out.write("\t    <br>\n");
      out.write("\t    <form method=\"post\" action=\"lobbyCreation\">\n");
      out.write("\t\t<button type=\"submit\">Create Lobby</button>\n");
      out.write("\t    </form>\n");
      out.write("\t    <br>\n");
      out.write("\t    <form method=\"post\" action=\"/sendRequest\">\n");
      out.write("\t\t<input type=\"text\" name=\"newFriend\"><br/>\n");
      out.write("\t\t<button type=\"submit\" >Add a friend</button>\n");
      out.write("\t    </form>\n");
      out.write("        \n");
      out.write("\n");
      out.write("        <form action=\"/chat\" method=\"post\">\n");
      out.write("            <button type = \"submit\" name=\"chatButton\" id=\"leaderBoards\"> Chat someone </button>\n");
      out.write("        </form>\n");
      out.write("    </div>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
