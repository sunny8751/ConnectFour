package com.sunwoo.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunwoo.game.GameLogic;

@WebServlet(urlPatterns = "/GameServlet")
/**
 * Servlet implementation class GameServlet
 */
public class GameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GameLogic gl;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        gl = new GameLogic(6, 7);
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("play") == null) {
            //clear board when there is no parameter passed
            gl.clearBoard();
        } else {
            try {
                int value = Integer.parseInt(request.getParameter("play"));
                if (value == -1) {
                    //clear board if -1 is passed, aka the "Start Over" button
                    if (gl.isGameOver()) {
                        gl.startOver();
                        request.getRequestDispatcher("/index.jsp").forward(request, response);
                        return;
                    } else {
                        gl.clearBoard();
                    }
                } else {
                    //play the turn
                    gl.playerTurn(value);
                }
            } catch (NumberFormatException e) {
                //System.out.println("Not a valid number");
            }
        }
        if (gl.isGameOver()) {
            request.setAttribute("winner", gl.getWinner());
//            if (request.getAttribute("boardView") != null) {
//                request.removeAttribute("boardView");
//            }
        }
        request.setAttribute("boardView", getBoardView());
        request.getRequestDispatcher("/game.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        gl.setPlayerName(name);
        doGet(request, response);
    }

    private String getBoardView() {
        String view = "";
        view += "<table align=\"center\" style=\"width:70%\">";
        for (int i = 0; i < gl.getRowLength(); i++) {
            // render column buttons
            if (i == 0 && !gl.isGameOver()) {
                view += "<tr>";
                for (int j = 0; j < gl.getColLength(); j++) {
                    view += "<td>";
                    view += "<form action=\"GameServlet\" method=\"GET\" style=\"text-align:center\">";
                    view += String.format(
                            "<button name=\"play\" value=\"%d\" style=\"text-align:center; width:80px\">Move</button>",
                            j);
                    view += "</form>";
                    view += "</td>";
                }
                view += "</tr>";
            }

            // render board
            view += "<tr>";
            for (int j = 0; j < gl.getColLength(); j++) {
                view += String.format("<td><img alt=\"Slot\" src=%s style=\"width:64px;height:64px;\"></td>",
                        gl.getImage(i, j));
            }
            view += "</tr>";

        }
        view += "</table>";
        return view;
    }

}
