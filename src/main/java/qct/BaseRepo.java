package qct;

import java.util.List;

/**
 * Created by quchentao on 16/1/4.
 */
public abstract class BaseRepo<T> {

    Class<T> clazz;

    protected BaseRepo(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void insert(T t) {
        MongoManager.INSTANCE.getDatabase().save(t);
    }

    public void removeAll() {
        MongoManager.INSTANCE.getDatabase().delete(MongoManager.INSTANCE.getDatabase().createQuery(clazz));
    }

    public List<T> findAll() {
        return MongoManager.INSTANCE.getDatabase().find(clazz).asList();
    }

    public long getCount() {
        return MongoManager.INSTANCE.getDatabase().getCount(clazz);
    }
}
