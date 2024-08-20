package me.tharindu.couchbase_demo_project.util;

import lombok.Setter;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

//Auditor aware implementation to inject in couchbase autoconfiguration
@Setter
public class NaiveAuditorAware implements AuditorAware<String> {

    private String auditor = "auditor";

    @Override
    public Optional<String> getCurrentAuditor() {
        return auditor.describeConstable();
    }

}
