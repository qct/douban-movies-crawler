package qct;

import qct.entity.Movie;

/**
 * Created by quchentao on 16/1/4.
 */
public class MovieRepo extends BaseRepo<Movie>{

    public MovieRepo() {
        super(Movie.class);
    }
}
