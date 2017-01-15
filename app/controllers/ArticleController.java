package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import models.User;
import models.Article;

import static controllers.AppController.sendRepsonse;
import static models.Article.findArticleById;


@Security.Authenticated(AuthController.class)
public class ArticleController extends Controller {

    public static Result addArticle() {
        Form<ArticleForm> articleForm = Form.form(ArticleForm.class).bindFromRequest();

        if (articleForm.hasErrors()) {
            return badRequest(articleForm.errorsAsJson());
        } else {
            Article article = new Article();
            article.category = articleForm.get().category;
            article.title = articleForm.get().title;
            article.content = articleForm.get().content;
            article.user = getUser();
            article.save();
        }
        return ok(sendRepsonse("success", "Article added successfully"));
    }

    public static Result deleteArticle(Long id) {
        if (findArticleById(id) == null) {
            return badRequest(sendRepsonse("error", "No Article exists with this id"));
        }
        if (Article.deleteArticleById(id)) {
            return ok(sendRepsonse("success", "Article Deleted Successfully"));
        } else {
            return internalServerError(sendRepsonse("error", "Platform Unstable"));
        }
    }

    private static User getUser() {
        return User.findByEmail(session().get("email"));
    }

    public static Result getUserArticles(String email) {
        User user = User.findByEmail(email);
        if (user == null) {
            return badRequest(sendRepsonse("error", "No such user"));
        }
        return ok(Json.toJson(Article.findArticlesByUser(user)));
    }

    public static Result changeArticleCategory(Long id, String category) {
        Article article = Article.findArticleById(id);
        if (article == null) {
            return badRequest(sendRepsonse("error", "No such article"));
        }
        if (Article.changeCategoryByArticle(id, category)) {
            return ok(sendRepsonse("success", "Article Updated Successfully"));
        } else {
            return internalServerError(sendRepsonse("error", "Platform Unstable"));
        }
    }

    public static Result getCategoryArticles(String category) {
        return ok(Json.toJson(Article.findArticlesByCategory(category)));
    }

    public static class ArticleForm {

        @Constraints.Required
        public String category;

        @Constraints.Required
        @Constraints.MaxLength(255)
        public String title;

        @Constraints.Required
        public String content;

    }

}
