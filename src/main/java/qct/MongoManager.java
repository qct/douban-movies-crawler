package qct;

import com.mongodb.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qct.entity.BaseEntity;

/**
 * mongo数据库连接池管理类
 * Created by quchentao on 16/1/3.
 */
public enum MongoManager {

    INSTANCE;

//    public static final String DB_HOST = "10.10.0.199";
    public static final String DB_HOST = "192.168.199.10";
    public static final int DB_PORT = 27017;
    public static final String DB_NAME = "douban";

    private final Logger LOG = LoggerFactory.getLogger(MongoManager.class);


    private final Datastore datastore;

    MongoManager() {
        MongoClientOptions mongoOptions = MongoClientOptions.builder()
                .socketTimeout(60000) // Wait 1m for a query to finish, https://jira.mongodb.org/browse/JAVA-1076
                .connectTimeout(15000) // Try the initial connection for 15s, http://blog.mongolab.com/2013/10/do-you-want-a-timeout/
                .maxConnectionIdleTime(600000) // Keep idle connections for 10m, so we discard failed connections quickly
                .readPreference(ReadPreference.primaryPreferred()) // Read from the primary, if not available use a secondary
                .build();
        MongoClient mongoClient;
        mongoClient = new MongoClient(new ServerAddress(DB_HOST, DB_PORT), mongoOptions);

        mongoClient.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        datastore = new Morphia().mapPackage(BaseEntity.class.getPackage().getName())
                .createDatastore(mongoClient, DB_NAME);
        datastore.ensureIndexes();
        datastore.ensureCaps();
        LOG.info("Connection to database '" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "' initialized");
    }

    // Creating the mongo connection is expensive - (re)use a singleton for performance reasons.
    // Both the underlying Java driver and Datastore are thread safe.
    public Datastore getDatabase() {
        return datastore;
    }
}