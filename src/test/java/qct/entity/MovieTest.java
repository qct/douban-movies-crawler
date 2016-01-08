package qct.entity;

import org.junit.Test;

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
}