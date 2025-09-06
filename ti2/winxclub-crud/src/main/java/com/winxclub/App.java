package com.winxclub;

import static spark.Spark.*;
import com.winxclub.controller.FairyController;

public class App {

	public static void main(String[] args) {
		port(6969);

		staticFiles.location("/public");

		FairyController fairyController = new FairyController();
		fairyController.registerRoutes();
	}
}
