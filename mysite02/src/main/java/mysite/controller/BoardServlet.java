package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.BoardListAction;
import mysite.controller.action.board.DeleteAction;
import mysite.controller.action.board.UpdateAction;
import mysite.controller.action.board.UpdateFormAction;
import mysite.controller.action.board.ViewAction;
import mysite.controller.action.board.WriteAction;
import mysite.controller.action.board.WriteFormAction;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"writeform", new WriteFormAction(),
			"write", new WriteAction(),
			"view", new ViewAction(),
			"updateform", new UpdateFormAction(),
			"update", new UpdateAction(),
			"delete", new DeleteAction()
	);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardListAction());
	}

}
