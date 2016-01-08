package qct;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by quchentao on 16/1/7.
 */
public class MongoManagerTest {

    @Test
    public void testGetDatabase() throws Exception {
        Assert.assertNotNull(MongoManager.INSTANCE.getDatabase());
    }
}