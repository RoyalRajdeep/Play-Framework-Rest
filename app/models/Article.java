package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article extends Model {

    @Id
    public Long id;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String title;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String category;

    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String content;

    @ManyToOne
    public User user;

    public static final Finder<Long, Article> find = new Finder<Long, Article>(
            Long.class, Article.class);

    public static List<Article> findArticlesByUser(final User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

    public static Article findArticleById(final Long id) {
        return find
                .where()
                .eq("id", id)
                .findUnique();
    }

    public static List<Article> findArticlesByCategory(final String category) {
        return find
                .where()
                .eq("category", category)
                .findList();
    }

    public static boolean deleteArticleById(final Long id) {
        Article article = find.where().eq("id", id).findUnique();
        article.delete();
        return true;
    }

    public static boolean changeCategoryByArticle(final Long id, final String category) {
        Article article = find.where().eq("id", id).findUnique();
        article.category = category;
        article.save();
        return true;
    }

}
