package org.gl.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.gl.entity.Subjects;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubjectRepository implements PanacheRepository<Subjects> {
}
