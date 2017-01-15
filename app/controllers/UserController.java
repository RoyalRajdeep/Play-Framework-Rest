package controllers;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


@Security.Authenticated(AuthController.class)
public class UserController extends Controller {

    public static Result getUsers() {
        return ok(Json.toJson(User.findall()));
    }

    public static Result getUser(String emailId) {
        User user = User.findByEmail(emailId);
        if (user == null) {
            return badRequest(AppController.sendRepsonse("error", "No User exists with this email"));
        }
        return ok(Json.toJson(User.findByEmail(emailId)));
    }

}
