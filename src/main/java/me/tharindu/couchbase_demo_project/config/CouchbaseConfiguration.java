package me.tharindu.couchbase_demo_project.config;

import com.couchbase.client.core.error.BucketNotFoundException;
import com.couchbase.client.core.error.UnambiguousTimeoutException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.env.ClusterEnvironment;
import com.couchbase.client.java.transactions.config.TransactionsConfig;
import lombok.extern.slf4j.Slf4j;
import me.tharindu.couchbase_demo_project.util.NaiveAuditorAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.Duration;

@Slf4j
@Configuration
@EnableCouchbaseRepositories("me.tharindu.couchbase_demo_project.repositories")
@EnableCouchbaseAuditing
@EnableTransactionManagement
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    @Value("#{systemEnvironment['DB_CONN_STR'] ?: '${spring.couchbase.connection-string:localhost}'}")
    private String connectionString;

    @Value("#{systemEnvironment['DB_USERNAME'] ?: '${spring.couchbase.bucket.user:Administrator}'}")
    private String username;

    @Value("#{systemEnvironment['DB_PASSWORD'] ?: '${spring.couchbase.bucket.password:tnbr20011}'}")
    private String password;

    @Value("${spring.couchbase.bucket.name:travel-sample}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return connectionString;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    @Bean
    Bucket getCouchbaseBucket(Cluster cluster) {

        try {
            if (!cluster.buckets().getAllBuckets().containsKey(bucketName)) {
                throw new BucketNotFoundException("Bucket " + bucketName + " does not exist");
            }
            Bucket bucket = cluster.bucket(bucketName);
            bucket.waitUntilReady(Duration.ofSeconds(15));
            return bucket;
        } catch (UnambiguousTimeoutException e) {
            log.error("Connection to bucket {} timed out", bucketName);
            throw e;
        } catch (BucketNotFoundException e) {
            log.error("Bucket {} does not exist", bucketName);
            throw e;
        } catch (Exception e) {
            log.error(e.getClass().getName());
            log.error("Could not connect to bucket {}", bucketName);
            throw e;
        }
    }

    //should be override to enable automatic index creation
    @Override
    protected  boolean autoIndexCreation(){
        return true;
    }

    //add auditor aware been to the config
    @Bean
    public NaiveAuditorAware testAuditorAware() {
        return new NaiveAuditorAware();
    }

    // Customization of transaction behavior is via the configureEnvironment() method
    @Override
    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
        builder.transactionsConfig(
                TransactionsConfig.builder().timeout(Duration.ofSeconds(10))); // add 10 second transaction duration
    }

}
