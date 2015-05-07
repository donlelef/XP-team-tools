package login;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.project.ProjectsCollector;

public class SignInService extends AccountAction {

	@Override
	public void perform(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = super.getUserName(request);
		String password = super.getPassword(request);
		HashMap<String, String> users = super.getUsers(request);
		boolean success = false;
		if (users.containsKey(userName)) {
			if (users.get(userName).equals(password)) {
				doLogin(userName, request);
				success = true;
			}
		}
		if (!success) {
			handleFailure(request);
		}
		super.forward(response);
	}

	private void handleFailure(HttpServletRequest request) {
		request.getSession().setAttribute("exception",
				new Exception("Invalid user name or password"));
	}

	private void doLogin(String userName, HttpServletRequest request) {
		request.getSession().setAttribute("currentUser", userName);
		@SuppressWarnings("unchecked")
		HashMap<String, ProjectsCollector> environments = (HashMap<String, ProjectsCollector>) request
				.getServletContext().getAttribute("environments");
		ProjectsCollector projects = environments.get(userName);
		request.getSession().setAttribute("currentProject",
				projects.getProject(0));
	}
}