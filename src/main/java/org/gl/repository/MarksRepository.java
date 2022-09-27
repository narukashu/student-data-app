package org.gl.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.gl.entity.Marks;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarksRepository implements PanacheRepository<Marks> {
}
