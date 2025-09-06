package com.winxclub.controller;

import static spark.Spark.*;

import com.google.gson.Gson;
import com.winxclub.dao.FairyDAO;
import com.winxclub.model.Fairy;

import java.util.List;

public class FairyController {

	private final FairyDAO dao = new FairyDAO();
	private final Gson gson = new Gson();

	public void registerRoutes() {

		// list all
		get("/fairies", (req, res) -> {
			res.type("application/json");
			List<Fairy> fairies = dao.getAllFairies();
			return gson.toJson(fairies);
		});

		// add
		post("/fairies", (req, res) -> {
			Fairy fairy = gson.fromJson(req.body(), Fairy.class);
			dao.addFairy(fairy);
			res.status(201); // Created
			return "Fairy added!";
		});

		// update
		put("/fairies/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			Fairy fairy = gson.fromJson(req.body(), Fairy.class);
			fairy.setId(id);
			dao.updateFairy(fairy);
			return "Fairy updated!";
		});

		// delete
		delete("/fairies/:id", (req, res) -> {
			int id = Integer.parseInt(req.params(":id"));
			dao.deleteFairy(id);
			return "Fairy deleted!";
		});
	}
}
