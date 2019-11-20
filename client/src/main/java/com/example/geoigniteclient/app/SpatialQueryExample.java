package com.example.geoigniteclient.app;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import java.util.Collection;
import java.util.Random;

@Component
public class SpatialQueryExample implements ApplicationRunner {
    private static final String CACHE_NAME = SpatialQueryExample.class.getSimpleName();

    @Autowired
    private Ignite ignite;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        CacheConfiguration<Integer, MapPoint> cc = new CacheConfiguration<>(CACHE_NAME);

        cc.setIndexedTypes(Integer.class, MapPoint.class);

        try (IgniteCache<Integer, MapPoint> cache = ignite.createCache(cc)) {
            Random rnd = new Random();

            WKTReader r = new WKTReader();

            // Adding geometry points into the cache.
            for (int i = 0; i < 1000; i++) {
                int x = rnd.nextInt(10000);
                int y = rnd.nextInt(10000);

                Geometry geo = r.read("POINT(" + x + " " + y + ")");

                cache.put(i, new MapPoint(geo));
            }

            // Query to fetch the points that fit into a specific polygon.
            SqlQuery<Integer, MapPoint> query = new SqlQuery<>(MapPoint.class, "coords && ?");

            // Selecting points that fit into a specific polygon.
            for (int i = 0; i < 10; i++) {
                // Defining the next polygon boundaries.
                Geometry cond = r.read("POLYGON((0 0, 0 " + rnd.nextInt(10000) + ", " +
                        rnd.nextInt(10000) + " " + rnd.nextInt(10000) + ", " +
                        rnd.nextInt(10000) + " 0, 0 0))");

                // Executing the query.
                Collection<Cache.Entry<Integer, MapPoint>> entries = cache.query(query.setArgs(cond)).getAll();

                // Printing number of points that fit into the area defined by the polygon.
                System.out.println("Fetched points [cond=" + cond + ", cnt=" + entries.size() + ']');
            }
        }

    }
}