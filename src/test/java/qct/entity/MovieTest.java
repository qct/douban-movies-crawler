package qct.entity;

import org.junit.Test;
import qct.MovieRepo;

import static org.junit.Assert.*;

/**
 * Created by quchentao on 16/1/8.
 */
public class MovieTest {

    @Test
    public void testTrim() throws Exception {
        Movie movie = new Movie("   name trim ");
        System.out.println(movie.getName());
        movie.trim();
        System.out.println(movie.getName());
    }

    @Test
    public void testHashCode() throws Exception {
        for (Movie m : new MovieRepo().findAll()) {
            System.out.println(m.getName() + ": " + m.getHashCode());
        }
    }
}