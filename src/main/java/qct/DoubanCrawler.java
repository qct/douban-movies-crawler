package qct;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qct.entity.Movie;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by quchentao on 16/1/2.
 */
public class DoubanCrawler extends WebCrawler {

    static final Logger logger = LoggerFactory.getLogger(DoubanCrawler.class);

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg|png|mp3|mp3|zip|gz))$");
    //    private final static Pattern POSSITIVE_FILTERS = Pattern.compile("^(http\\://movie.douban.com/subject/\\d).*(\\d|\\d/)$");
    private final static Pattern POSSITIVE_FILTERS = Pattern.compile(".*(\\d|\\d/)$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
//                && POSSITIVE_FILTERS.matcher(href).matches()
                && href.startsWith("http://movie.douban.com/subject/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();

//            System.out.println("Title: " + htmlParseData.getTitle());
//            System.out.println("Text length: " + text.length());
//            System.out.println("Html length: " + html.length());
//            System.out.println("Number of outgoing links: " + links.size());

            boolean dealResult = false;
            try {
//                Thread.sleep(ThreadLocalRandom.current().nextInt(20));
                dealResult = deal(page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("deal result: " + dealResult);
        }
    }

    private boolean deal(Page page) throws Exception {
        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        Movie movie = null;
        String name = htmlParseData.getTitle().replace("(豆瓣)", "").trim();
        if (!Strings.isNullOrEmpty(name) && !"豆瓣电影".equals(name)) movie = new Movie(name);

        try {
            regMatchMovieField(htmlParseData, movie);
            if (movie == null) return false;

            // if directedBy is empty, don't save.
            // if it's exists in db, don't save.
            if (!Strings.isNullOrEmpty(movie.getDirectedBy())
                    && !movie.isExists()
                    && movie.isMovie()) {
                movie.setHashCode(movie.getHashCode());
                movie.save();
                logger.debug(movie.toString());
                return true;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void regMatchMovieField(HtmlParseData htmlParseData, Movie movie) throws Exception {
        Map<String, String> movie_patterns = Maps.newHashMap();
        movie_patterns.put("directedBy", "导演:(.*)");
        movie_patterns.put("writer", "编剧:(.*)");
        movie_patterns.put("actor", "主演:(.*)");
        movie_patterns.put("type", "类型:(.*)");
        movie_patterns.put("website", "官方网站:(.*)");
        movie_patterns.put("area", "制片国家/地区:(.*)");
        movie_patterns.put("language", "语言:(.*)");
        movie_patterns.put("initialReleaseDate", "上映日期:(.*)");
        movie_patterns.put("runtime", "片长:(.*)");
        movie_patterns.put("otherName", "又名:(.*)");
        movie_patterns.put("imdbLink", "IMDb链接:(.*)");
        movie_patterns.put("ratingNum", "\"v\\:average\">(.*)</strong>");

        for (String key : movie_patterns.keySet()) {
            Pattern p = Pattern.compile(movie_patterns.get(key));
            Matcher m;
            if ("ratingNum".equals(key))
                m = p.matcher(htmlParseData.getHtml());
            else
                m = p.matcher(htmlParseData.getText());
            while (m.find()) {
                Method method = movie.getClass().getDeclaredMethod(
                        "set" + key.substring(0, 1).toUpperCase() + key.substring(1, key.length()),
                        String.class);
                method.invoke(movie, m.group(1));
            }
        }
    }

    public static void main(String[] args) {
        /*String text = "        导演: 昆汀·塔伦蒂诺";
        Pattern p = Pattern.compile("导演:(.*)");
        Matcher m = p.matcher(text);
        while (m.find()) {
            logger.info(m.group(1));
        }*/


        /*Pattern p = Pattern.compile("^(http\\://movie.douban.com/subject/\\d).*(\\d|\\d/)$");
        String input = "http://movie.douban.com/subject/25787888/";
        Matcher m = p.matcher(input);
        while (m.find()) {
            logger.info(m.group(0) + ": " + m.group(1) + ": " + m.group(2));
        }*/

        /*Pattern p = Pattern.compile("\"v\\:average\">(.*)</strong>");
        String input = "<strong class=\"ll rating_num\" property=\"v:average\">8.6</strong>";
        Matcher m = p.matcher(input);
        while (m.find()) {
            logger.info(m.group(0) + ": " + m.group(1));
        }*/

        System.out.println(ThreadLocalRandom.current().nextInt(30, 60));
    }
}
