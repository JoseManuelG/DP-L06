package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	//Find all the comments for a given recipe
		@Query("select r.comments from Recipe r where r.id=?1")
		public Collection<Comment> findCommentsByRecipe(int recipeId);

}
