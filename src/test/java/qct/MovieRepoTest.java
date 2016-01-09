package qct;

import org.junit.Test;
import qct.entity.Movie;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by quchentao on 16/1/7.
 */
public class MovieRepoTest {

    @Test
    public void testFindAll() throws Exception {
        MovieRepo movieRepo = new MovieRepo();
        List<Movie> movieList = movieRepo.findAll();
        for (Movie movie : movieList) {
            System.out.println(movie.toString());
        }
    }

    @Test
    public void testInsert() throws Exception {
        new MovieRepo().insert(new Movie("qct-movie-name"));
    }

    @Test
    public void testRemoveAll() throws Exception {
        new MovieRepo().removeAll();
    }

    @Test
    public void testGetCount() throws Exception {
        System.out.println(new MovieRepo().getCount());
    }

    @Test
    public void testFindByHashCode() throws Exception {
        System.out.println(new MovieRepo().findByHashCode(669225995).getName());
        System.out.println(new MovieRepo().findByHashCode(1260642845).getName());
    }
}